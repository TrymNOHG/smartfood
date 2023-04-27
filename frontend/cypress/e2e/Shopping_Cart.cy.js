describe('Start to end: ', () => {
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8080";

    beforeEach(() => {
        cy.intercept('POST', 'http://localhost:8080/user/login', {
            statusCode: 200,
            body: {
                token: 'my-user-token'
            }
        }).as('loginRequest')

        cy.intercept('GET', `${base_url_endpoint}/user/get/info`, (req) => {
            //req.headers['Authorization'] = `Bearer my-user-token`
            req.reply({
                statusCode: 200,
                body: {
                    userId: 123,
                    username: 'johndoe',
                    firstName: 'John',
                    lastName: 'Doe', email: 'johndoe@example.com'
                }
            })
        }).as('fetchUser')

        cy.intercept('GET', `${base_url_endpoint}/fridge/loadAll?user=johndoe`, (req) => {
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

        cy.get('input[name="email"]').type('t@t.t')
        cy.get('input[name="password"]').type('12345678')

        cy.get('button[type="submit"]').click()

        cy.wait('@loginRequest')
        cy.wait('@fetchUser')
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


