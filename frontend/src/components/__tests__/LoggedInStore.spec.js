import {setupServer} from 'msw/node'
import {afterAll, afterEach, beforeAll, beforeEach, describe, expect, test} from 'vitest';
import {rest} from 'msw'
import {useLoggedInStore} from "@/store/store";
import {createPinia, setActivePinia} from "pinia";
import {setMockToken} from "@/features/SessionToken";

const server = setupServer(
    rest.get('http://localhost:8089/api/user/get/info', (req, res, ctx) => {
        return res(
            ctx.status(200),
            ctx.json({
                userId: 123,
                email: 'test@example.com',
                firstname: 'John',
                lastname: 'Doe',
                username: 'johndoe',
            })
        );
    })
);

beforeAll(() => server.listen());
afterEach(() => server.resetHandlers());

describe('useLoggedInStore', () => {
    beforeEach(() => {
        setActivePinia(createPinia());
        setMockToken('fake_token');
    });

    test('fetches user information and updates store', async () => {
        useLoggedInStore.$state = {
            sessionToken: null,
            user: {
                userId: null,
                email: null,
                firstname: null,
                lastname: null,
                username: null,
            },
        };

        server.use(
            rest.get('http://localhost:8089/api/user/get/info', (req, res, ctx) => {
                return res(
                    ctx.status(200),
                    ctx.json({
                        userId: 123,
                        email: 'test@example.com',
                        firstname: 'John',
                        lastname: 'Doe',
                        username: 'johndoe',
                    })
                );
            })
        );

        const store = useLoggedInStore();

        expect(store.sessionToken).toBeNull();
        expect(store.user.userId).toBeNull();

        await store.fetchUser();

        expect(store.user.userId).not.toBeNull();
        expect(store.user.email).toEqual('test@example.com');
        expect(store.user.firstname).toEqual('John');
        expect(store.user.lastname).toEqual('Doe');
        expect(store.user.username).toEqual('johndoe');
    });



    test('logs out and clears session information', () => {
        const store = useLoggedInStore()

        store.setSessionToken('abc123')
        store.user = {
            userId: 123,
            email: 'test@example.com',
            firstname: 'John',
            lastname: 'Doe',
            username: 'johndoe',
        }

        store.logout()

        expect(store.sessionToken).toBeNull()
        expect(store.user.email).toBeNull()
        expect(store.user.firstname).toBeNull()
        expect(store.user.lastname).toBeNull()
        expect(store.user.username).toBeNull()
    })

    afterAll(() => server.close());
})
