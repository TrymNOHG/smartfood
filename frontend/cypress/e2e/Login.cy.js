describe('Testing the login: ', () => {
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8089/api";

    beforeEach(() => {
        cy.intercept('POST', 'http://localhost:8089/api/user/login', {
            statusCode: 200,
            body: {
                token: 'my-user-token'
            }
        }).as('loginRequest')

        cy.intercept('GET', `${base_url_endpoint}/user/get/info`, (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    userId: 123,
                    username: 'tomaber',
                    firstName: 'Tomas',
                    lastName: 'Beranek',
                    email: 'tomaber@gmail.com'
                }
            })
        }).as('fetchUser')

        cy.intercept('GET', `${base_url_endpoint}/fridge/loadAll?user=tomaber`, (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    fridgeDTOS: [
                        {
                            fridgeId: 88,
                            fridgeName: 'Home Fridge'
                        }
                    ]
                }
            })
        }).as('loadAllFridges')

        cy.intercept('GET', 'http://localhost:8089/api/notification/update', {
            statusCode: 200,
            body: {}
        }).as('updatedNotifications')

        cy.visit(`${base_url_site}`)

        cy.contains('a', 'Logg inn').click()

        cy.get('input[name="email"]').type('t@t.t')
        cy.get('input[name="password"]').type('12345678')

        cy.get('button[type="submit"]').click()

        cy.wait('@loginRequest')
        cy.wait('@fetchUser')
        cy.wait('@updatedNotifications')
        cy.wait('@loadAllFridges')

    })

    it('should redirect authenticated users to fridges view on login', () => {
        cy.url().should('include', '/fridges')
    })

    it('should display the fridges after logging in', () => {
        cy.visit(`${base_url_site}/fridges`);
        cy.contains('Home Fridge').should('be.visible');
    });
});


describe('Testin Errors: ', () => {
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8089/api";

    beforeEach(() => {
        cy.intercept('POST', 'http://localhost:8089/api/user/login', {
            statusCode: 200,
            body: {
                token: 'my-user-token'
            }
        }).as('loginRequest')

        cy.intercept('GET', `${base_url_endpoint}/user/get/info`, (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    userId: 123,
                    username: 'tomaber',
                    firstName: 'Tomas',
                    lastName: 'Beranek',
                    email: 'tomaber@gmail.com'
                }
            })
        }).as('fetchUser')

        cy.intercept('GET', `${base_url_endpoint}/fridge/loadAll?user=tomaber`, (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    fridgeDTOS: [
                        {
                            fridgeId: 88,
                            fridgeName: 'Home Fridge'
                        }
                    ]
                }
            })
        }).as('loadAllFridges')

        cy.visit(`${base_url_site}`)

        cy.contains('a', 'Logg inn').click()


    })

    it('error displayed when username missing', () => {
        cy.get('input[name="password"]').type('12345678')

        cy.get('button[type="submit"]').click()
        cy.get('.has-errors').should('exist')
    });

    it('error displayed when password missing', () => {
        cy.get('input[name="password"]').type('12345678')

        cy.get('button[type="submit"]').click()
        cy.get('.has-errors').should('exist')
    });
})


