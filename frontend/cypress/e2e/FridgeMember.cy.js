describe("Testing fridge member management", () => {
    const base_url = "http://localhost:5173";
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8089/api";
    let cartAddedItem = {};
    let fridgeAddedItem = [];
    let members = [
        {
            userId: 10,
            username: "johndoe",
            firstName: "John",
            lastName: "Doe",
            email: "t@t.t",
            isSuperUser: true,
        },
    ];

    beforeEach(() => {
        cy.intercept("POST", "http://localhost:8089/api/user/login", {
            statusCode: 200,
            body: {
                token: "my-user-token",
            },
        }).as("loginRequest");

        cy.intercept("GET", `${base_url_endpoint}/user/get/info`, (req) => {
            //req.headers['Authorization'] = `Bearer my-user-token`
            req.reply({
                statusCode: 200,
                body: {
                    userId: 123,
                    username: "johndoe",
                    firstName: "John",
                    lastName: "Doe",
                    email: "t@t.t",
                },
            });
        }).as("fetchUser");



        cy.intercept("GET", `${base_url_endpoint}/user/search/johnbeef`, (req) => {
            //req.headers['Authorization'] = `Bearer my-user-token`
            req.reply({
                statusCode: 200,
                body: [{userId: 11, username: "johnbeef"}, {userId: 52, username: "atle"}, {
                    userId: 82,
                    username: "leeet"
                }, {userId: 84, username: "kirol123"}, {userId: 86, username: "leonhest"}],
            });
        }).as("fetchUser");

        cy.intercept(
            "GET",
            `${base_url_endpoint}/fridge/loadAll?user=johndoe`,
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: {
                        fridgeDTOS: [
                            {
                                fridgeId: 88,
                                fridgeName: "Home Fridge",
                            },
                        ],
                    },
                });
            }
        ).as("loadAllFridges");

        cy.intercept(
            "POST",
            "http://localhost:8089/api/item/shopping/add?fridgeId=88&suggestion=false",
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: {
                        success: true,
                        message: "Item added to cart",
                    },
                });
                // Save the body of the intercepted request to a variable
                const requestBody = req.body;
                cartAddedItem = requestBody;
                // Do something with the requestBody, like logging it to the console
                console.log("Request body:", requestBody);
            }
        ).as("addItemRequest");

        cy.intercept(
            "GET",
            "http://localhost:8089/api/user/superuser?fridgeId=88",
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: {
                        data: true,
                    },
                });
            }
        ).as("isSuperUser");

        cy.intercept(
            "GET",
            "http://localhost:8089/api/item/fridge/get?fridgeId=88",
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: fridgeAddedItem,
                });
            }
        ).as("getFridgeItems");

        cy.intercept(
            "POST",
            "http://localhost:8089/api/item/fridge/add?fridgeId=88",
            (req) => {
                fridgeAddedItem.push(req.body);
                req.reply({
                    statusCode: 200,
                });
            }
        ).as("addItemToFridge");

        cy.intercept(
            "GET",
            "http://localhost:8089/api/fridge/loadAllUsers?fridgeId=88",
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: {
                        memberInfo: members,
                    },
                });
            }
        ).as("getFridgeMembers");

        cy.intercept(
            "POST",
            "http://localhost:8089/api/item/shopping/add",
            (req) => {
                req.reply({
                    statusCode: 200,
                });
            }
        ).as("addShopping");

        cy.intercept(
            "POST",
            "http://localhost:8089/api/fridge/add/user",
            (req) => {
                req.reply({
                    statusCode: 200,
                });

                members.push(req.body);
            }
        ).as("addShopping");

        cy.intercept(
            "GET",
            "https://kassal.app/api/v1/products?search=egg",
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: {
                        data: [
                            {
                                name: "Ammeinnlegg 50stk Lillego",
                                brand: "Lillego",
                                vendor: "Unil",
                                ean: "7035620043635",
                                url: "https://meny.no/Varer/barneprodukter/babyartikler/ammeinnlegg/ammeinnlegg-7035620043635",
                                image: "https://bilder.ngdata.no/7035620043635/meny/large.jpg",
                                description: null,
                                ingredients:
                                    "Overflaten som er i kontakt med huden er laget av mykt materiale i polypropylen som kjennes mykt og godt mot huden. kjernen som absorberer væsken er laget av fsc-sertifisert cellulose. baksiden av innleggene er laget av pustende, lekkasjesikkert materiale som beskytter klærne. lillego ammeinnlegg er svanemerket og anbefalt av asthma allergy nordic. dermatologisk testet. \n",
                                current_price: 54.9,
                                store: {
                                    name: "Meny",
                                    code: "MENY_NO",
                                    url: "https://meny.no/Varer",
                                    logo: "https://cdn.kassal.app/a9e4496b-ae68-42b8-80bc-9400bded0ff3/logos/Meny.svg",
                                },
                                price_history: [
                                    {
                                        price: 54.9,
                                        date: "2023-04-26T19:56:20.000000Z",
                                    },
                                ],
                                allergens: [],
                                nutrition: [],
                                created_at: "2023-04-26T19:56:20.000000Z",
                                updated_at: "2023-04-26T19:56:20.000000Z",
                            },
                        ],
                    },
                });
            }
        ).as("getSearchFromKasal");

        cy.intercept(
            "POST",
            "http://localhost:8089/api/stat/add/bought-item",
            (req) => {
                req.reply({
                    statusCode: 200,
                });
            }
        ).as("addToStatBought");

        cy.intercept(
            "POST",
            "http://localhost:8089/api/stat/add/delete-item",
            (req) => {
                req.reply({
                    statusCode: 200,
                });
            }
        ).as("addToStatDelete");

        cy.intercept(
            "DELETE",
            "http://localhost:8089/api/item/fridge/delete",
            (req) => {
                req.reply({
                    statusCode: 200,
                });
                fridgeAddedItem = [];
            }
        ).as("deleteItemFromFridge");

        cy.intercept(
            "DELETE",
            "http://localhost:8089/api/fridge/delete/user",
            (req) => {
                req.reply({
                    statusCode: 200,
                });
                fridgeAddedItem = [];
                members.pop();
            }
        ).as("deleteUserFromFridge");

        cy.intercept(
            {
                url: /^https:\/\/kassal\.app\/api\/v1\/products/,
            },
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: {
                        data: [
                            {
                                name: "Ammeinnlegg 50stk Lillego",
                                brand: "Lillego",
                                vendor: "Unil",
                                ean: "7035620043635",
                                url: "https://meny.no/Varer/barneprodukter/babyartikler/ammeinnlegg/ammeinnlegg-7035620043635",
                                image: "https://bilder.ngdata.no/7035620043635/meny/large.jpg",
                                description: null,
                                ingredients:
                                    "Overflaten som er i kontakt med huden er laget av mykt materiale i polypropylen som kjennes mykt og godt mot huden. kjernen som absorberer væsken er laget av fsc-sertifisert cellulose. baksiden av innleggene er laget av pustende, lekkasjesikkert materiale som beskytter klærne. lillego ammeinnlegg er svanemerket og anbefalt av asthma allergy nordic. dermatologisk testet. \n",
                                current_price: 54.9,
                                store: {
                                    name: "Meny",
                                    code: "MENY_NO",
                                    url: "https://meny.no/Varer",
                                    logo: "https://cdn.kassal.app/a9e4496b-ae68-42b8-80bc-9400bded0ff3/logos/Meny.svg",
                                },
                                price_history: [
                                    {
                                        price: 54.9,
                                        date: "2023-04-26T19:56:20.000000Z",
                                    },
                                ],
                                allergens: [],
                                nutrition: [],
                                created_at: "2023-04-26T19:56:20.000000Z",
                                updated_at: "2023-04-26T19:56:20.000000Z",
                            },
                        ],
                    },
                });
            }
        ).as("getSearchFromKasal");

        cy.visit(`${base_url}`);

        cy.contains("a", "Logg inn").click();

        cy.get('input[name="email"]').type("t@t.t");
        cy.get('input[name="password"]').type("12345678");

        cy.get('button[type="submit"]').click();

        cy.contains("Home Fridge").click();

        cy.wait("@loginRequest");
        cy.wait("@fetchUser");
        cy.wait("@loadAllFridges");
        cy.wait("@isSuperUser");

        cy.contains('#toggle-button', 'Medlemmer').click();

    });



    it("should be able to see yourself as a member", () => {
        cy.contains("johndoe").should("be.visible");
    });

    it("should be able remove yourself from a fridge", () => {
        cy.contains('.member', 'Super bruker')
            .find('.fa-trash')
            .click();
        cy.contains("bekreft").click();
        cy.contains("OK").click();
        cy.get("body").should("not.contain", "johndoe");

    });

    it("should be able to add other members to fridge as a super user", () => {

        cy.get('.container_button').click();
        cy.get('.search-input').find('input[type="text"]').type('johnbeef')
        cy.get('.search-input').find('button[type="submit"]').click();
        cy.contains('.member', 'johnbeef') // select the element with class "member" and text "johnbeef"
            .find('.actions  svg') // find the SVG element inside the "fa-plus" class within the "actions" class
            .click(); // click on the SVG element
        cy.get('input[name="swal2-radio"][value="super"]').click();
        cy.contains("bekreft").click();
        cy.get('.member').filter((_, el) => {
            const $el = Cypress.$(el);
            return (
                $el.text().includes('Super bruker') && $el.text().includes('johnbeef')
            );
        }).should('have.length', 1);


    });

    it("should be able to delete other members from fridge", () => {

        cy.get('.member:contains("johnbeef")') // select the element with class "member" and text "johnbeef"
            .find('.actions .fa-trash') // find the element with the given class name within the "actions" class
            .click(); // click the element

        cy.contains("bekreft").click();
        cy.contains("OK").click();
        cy.get("body").should("not.contain", "johnbeef");

    });

    it("should be able to add other members to fridge as a normal user", () => {
        //remove johnbeef since he could have been added by other tests
        cy.get('.container_button').click();
        cy.get('.search-input').find('input[type="text"]').type('johnbeef')
        cy.get('.search-input').find('button[type="submit"]').click();
        cy.contains('.member', 'johnbeef') // select the element with class "member" and text "johnbeef"
            .find('.actions  .fa-plus')
            .click();

        cy.contains("bekreft").click();
        cy.get("body").should("contain", "johnbeef");

    });



});
