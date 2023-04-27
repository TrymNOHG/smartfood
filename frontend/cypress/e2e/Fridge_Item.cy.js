describe('After choosing fridge', () => {
    const base_url = "http://localhost:5173";
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8080";
    let addedItem = {};
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
                    lastName: 'Doe',
                    email: 'johndoe@example.com'
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

        cy.intercept('POST', 'http://localhost:8080/item/shopping/add?fridgeId=88&suggestion=false', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    success: true,
                    message: 'Item added to cart'
                }
            })
            // Save the body of the intercepted request to a variable
            const requestBody = req.body;
            addedItem = requestBody;
            // Do something with the requestBody, like logging it to the console
            console.log('Request body:', requestBody)
        }).as('addItemRequest')

        cy.intercept('GET', 'http://localhost:8080/user/superuser?fridgeId=88', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    data: true
                }
            });
        }).as('isSuperUser');

        cy.intercept('GET', 'http://localhost:8080/item/fridge/get?fridgeId=88', (req) => {
            req.reply({
                statusCode: 200,
                body: [{
                    itemId: 7,
                    name: "Våtservietter 64stk Lillego",
                    description: "LilleGo’ sensitive våtservietter er milde og parfymefrie. Ideelle til bleiestell, samt vask av ansikt og hender. Våtserviettene er dermatologisk testet og dokumentert egnet for sensitiv hud.",
                    store: "Meny",
                    price: 24.0,
                    image: "https://bilder.ngdata.no/7035620025037/meny/large.jpg",
                    quantity: 1,
                    purchaseDate: "2023-04-27T07:16:59.630+00:00",
                    expirationDate: "2023-04-27T07:16:59.630+00:00"
                }]
            });
        }).as('getFridgeItems');

        cy.intercept('GET', 'http://localhost:8080/fridge/loadAllUsers?fridgeId=88', (req) => {
            req.reply({
                statusCode: 200,
                body: {
                    "memberInfo": [
                        {
                            "userId": 10,
                            "username": "tester123",
                            "firstName": "test",
                            "lastName": "test",
                            "email": "t@t.t",
                            "isSuperUser": true
                        }
                    ]
                }
            });
        }).as('getFridgeItems');





        cy.visit(`${base_url}`)

        cy.contains('a', 'Logg inn').click()

        cy.get('input[name="email"]').type('t@t.t')
        cy.get('input[name="password"]').type('12345678')

        cy.get('button[type="submit"]').click()

        cy.contains('Home Fridge').click()

        cy.wait('@loginRequest')
        cy.wait('@fetchUser')
        cy.wait('@loadAllFridges')
        cy.wait('@isSuperUser')
    })

    it('should add item to fridge from fridge-search', () => {
        cy.get('.search-input').type('egg');
        cy.get('.vcp__header').click()
        cy.wait(1500) //wait for about a second for api to retrieve search results
        cy.get('.search-btn').click()
        cy.contains('div', 'Sunda Pålegg 280g').click()
        cy.wait('@addItemRequest');

        cy.wrap(addedItem).get('name') .should('eq', 'Sunda Pålegg 280g')
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