describe('Profile', () => {
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8089/api";

    beforeEach(() => {
        cy.intercept('POST', `${base_url_endpoint}/user/login`, {
            statusCode: 200,
            body: {
                token: 'my-user-token'
            }
        }).as('loginRequest')

        cy.intercept('GET', `${base_url_endpoint}/user/get/info`, (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    userId: 12,
                    username: 'tomaber',
                    firstName: 'Tomas',
                    lastName: 'Beranek',
                    email: 'tomas.beranek02@gmail.com'
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

        cy.intercept('GET', `${base_url_endpoint}/profile`, (req) => {
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

        cy.get('input[name="email"]').type('tomas.beranek02@gmail.com')
        cy.get('input[name="password"]').type('123123123')
        cy.get('button[type="submit"]').click()

        cy.wait('@loginRequest')
        cy.wait('@fetchUser')
        cy.wait('@loadAllFridges')

        cy.visit(`${base_url_site}/profile`)
    })

    it('The fullName values should be visible',  () => {
        cy.get('#fullName')
            .should('have.text', 'Navn: Tomas Beranek');
    })

    it('The username values should be visible',  () => {
        cy.get('#username')
            .should('have.text', 'Brukernavn: tomaber');
    })

    it('The email values should be visible',  () => {
        cy.get('#email')
            .should('have.text', 'E-post: tomas.beranek02@gmail.com');
    })

    it('updates user profile when form is submitted',  () => {
        cy.intercept('PUT', `${base_url_endpoint}/user/update/info`, (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    userUpdateDTO: [
                        {
                            userId: 12,
                            username: 'tomaber',
                            firstName: 'Tomas',
                            lastName: 'Beranek',
                            email: 'tomas.beranek02@gmail.com'
                        }
                    ]
                }
            })
        }).as('updateUser')

        cy.get('.edit-btn').click()

        cy.get('#firstName').type('ino')

        cy.get('button[type="submit"]').click()
        cy.wait('@updateUser')

        cy.get('#fullName')
            .should('have.text', 'Navn: Tomasino Beranek');
    })

    it('edit password should work',  () => {
        cy.intercept('PUT', `${base_url_endpoint}/user/update/password`, (req) => {
            req.reply({
                statusCode: 200,
            })
        }).as('updatePassword')

        cy.get('.change-password-btn').click()

        cy.get('#currentPassword').type('12312313')
        cy.get('#newPassword').type('321321321')
        cy.get('#confirmPassword').type('321321321')

        cy.get('button[type="submit"]').click()
        cy.wait('@updatePassword')
    })

    it('logout should send user to welcome component',  () => {
        cy.get('.logout-btn').click()
        cy.url().should('include', '/')
    })
})
