describe('Register', () => {
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8089/api";


    beforeEach(() => {
        cy.intercept('POST', 'http://localhost:8089/api/user/register', {
            statusCode: 200,
            body: {
                token: 'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9'
            }
        }).as('registerRequest')

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



        cy.visit(`${base_url_site}/register`)
    })


    it('displays the registration form', () => {
        // Assert that the registration form is displayed
        cy.get('.form-box.login').should('be.visible')

        // Assert that all form fields are displayed
        cy.get('input[name="firstName"]').should('be.visible')
        cy.get('input[name="lastName"]').should('be.visible')
        cy.get('input[name="username"]').should('be.visible')
        cy.get('input[name="email"]').should('be.visible')
        cy.get('input[name="password"]').should('be.visible')

        // Assert that the submit button is displayed
        cy.get('button[type="submit"]').should('be.visible')
    })

    it('registers a new user', () => {

        // Fill out the registration form
        cy.get('input[name="firstName"]').type('John')
        cy.get('input[name="lastName"]').type('Doe')
        cy.get('input[name="username"]').type('johndoe')
        cy.get('input[name="email"]').type('johndoe@example.com')
        cy.get('input[name="password"]').type('password')
        cy.get('input[name="confirmPassword"]').type('password')

        // Submit the registration form
        cy.get('button[type="submit"]').click()

        cy.wait('@registerRequest' , { timeout: 10000 })
        cy.url().should('include', '/fridges')
    })


    it('Error when password missing', () => {

        // Fill out the registration form
        cy.get('input[name="firstName"]').type('John')
        cy.get('input[name="lastName"]').type('Doe')
        cy.get('input[name="username"]').type('johndoe')
        cy.get('input[name="email"]').type('johndoe@example.com')

        // Submit the registration form
        cy.get('button[type="submit"]').click()

        cy.get('.has-errors', { timeout: 10000 }).should('exist') // checking that the form displays error messages
    })

    it('Error when username missing', () => {

        // Fill out the registration form
        cy.get('input[name="firstName"]').type('johndoe')
        cy.get('input[name="lastName"]').type('Doe')
        cy.get('input[name="email"]').type('johndoe@example.com')
        cy.get('input[name="password"]').type('password')

        // Submit the registration form
        cy.get('button[type="submit"]').click()

        cy.get('.has-errors').should('exist') // checking that the form displays error messages
    })

    it('Error when email missing', () => {

        // Fill out the registration form
        cy.get('input[name="firstName"]').type('Test')
        cy.get('input[name="lastName"]').type('Doe')
        cy.get('input[name="username"]').type('johndoe')
        cy.get('input[name="password"]').type('password')

        // Submit the registration form
        cy.get('button[type="submit"]').click()

        cy.get('.has-errors').should('exist') // checking that the form displays error messages
    })

    it('Error when firstName missing', () => {

        // Fill out the registration form
        cy.get('input[name="lastName"]').type('Doe')
        cy.get('input[name="username"]').type('johndoe')
        cy.get('input[name="email"]').type('johndoe@example.com')
        cy.get('input[name="password"]').type('password')

        // Submit the registration form
        cy.get('button[type="submit"]').click()

        cy.get('.has-errors').should('exist') // checking that the form displays error messages
    })

    it('Error when lastName missing', () => {

        // Fill out the registration form
        cy.get('input[name="firstName"]').type('Doe')
        cy.get('input[name="username"]').type('johndoe')
        cy.get('input[name="email"]').type('johndoe@example.com')
        cy.get('input[name="password"]').type('password')

        // Submit the registration form
        cy.get('button[type="submit"]').click()

        cy.get('.has-errors').should('exist') // checking that the form displays error messages
    })

    it('Redirect when login clicked', () => {
        cy.get('.register-link').click()

        cy.url().should('include', '/login')
    })
})
