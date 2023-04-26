
const BASE_URL = 'http://localhost:5173'

describe('Cart', () => {

  it('should display the cart items after logging in', () => {
    cy.visit('http://localhost:5173/cart', {
      onBeforeLoad(win) {
        const sessionToken = window.localStorage.getItem('sessionToken');
        win.sessionStorage.setItem('sessionToken', sessionToken);
      }
    });

    cy.get('#cart-item-1').should('contain', 'Milk');
    cy.get('#cart-item-2').should('contain', 'Bread');
  });

  it('should trigger handleSearch function when search button is clicked', () => {
    cy.visit('http://localhost:5173/cart', {
      onBeforeLoad(win) {
        const sessionToken = window.localStorage.getItem('sessionToken');
        win.sessionStorage.setItem('sessionToken', sessionToken);
      }
    });

    cy.get('#searchbtn').click();
    cy.get('@handleSearch').should('have.been.calledOnce');
  });

  it('should add an item to the cart when addItemToList is called', () => {
    cy.visit('http://localhost:5173/cart', {
      onBeforeLoad(win) {
        const sessionToken = window.localStorage.getItem('sessionToken');
        win.sessionStorage.setItem('sessionToken', sessionToken);
      }
    });

    cy.get('#searchbtn').click();
    cy.get('@addItemToList').should('have.been.calledOnce');

    cy.get('#cart-item-3').should('contain', 'Eggs');
  });
});
