describe('Welcome View', () => {
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8080";


    it('displays the logo and buttons', () => {
        cy.visit(`${base_url_site}`)
        cy.get('.logo').should('be.visible')
        cy.get('.register-btn').should('be.visible').contains('Registrer')
        cy.get('.login-btn').should('be.visible').contains('Logg inn')
    })

    it('redirects to the Register page when clicking the Register button', () => {
        cy.visit(`${base_url_site}`)
        cy.get('.register-btn').click()
        cy.url().should('include', '/register')
    })

    it('redirects to the Login page when clicking the Login button', () => {
        cy.visit(`${base_url_site}`)
        cy.get('.login-btn').click()
        cy.url().should('include', '/login')
    })
})