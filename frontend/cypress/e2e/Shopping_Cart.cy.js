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

describe('After choosing fridge', () => {
  const base_url = "http://localhost:5173";

  beforeEach(() => {
    cy.intercept('POST', 'http://localhost:8080/item/shopping/add', {
      statusCode: 200,
      body: {
        success: true,
        message: 'Item added to cart'
      }
    }).as('addItemRequest')

    cy.visit(`${base_url}`)

    cy.contains('a', 'Logg inn').click()

    cy.get('input[name="email"]').type('t@t.t')
    cy.get('input[name="password"]').type('12345678')

    cy.get('button[type="submit"]').click()

    cy.contains('Home Fridge').click()
  })

  it('should add test item to cart', () => {
    cy.get('#searchbar').type('Milk{enter}')
    cy.wait('@addItemRequest')
  })

  it('should display the cart items after logging in', () => {
    cy.visit(`${base_url}/cart`);
    cy.get('#cart-item-1').should('contain', 'Milk');
  })

  it('should trigger handleSearch function when search button is clicked', () => {
    cy.visit('http://localhost:5173/cart', {
      onBeforeLoad(win) {
        const sessionToken = window.localStorage.getItem('sessionToken');
        win.sessionStorage.setItem('sessionToken', sessionToken);
      }
    });

    cy.get('#searchbtn').click();
    cy.get('@handleSearch').should('have.been.calledOnce');
  })

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
  })
})
