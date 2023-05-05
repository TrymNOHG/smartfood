describe("Test for the member component and it features", () => {
    const base_url = "http://localhost:5173";
    const base_url_site = "http://localhost:5173";
    const base_url_endpoint = "http://localhost:8089/api";
    let cartAddedItem = {};
    let fridgeAddedItem = [];

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
                    email: "johndoe@example.com",
                },
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
                        memberInfo: [
                            {
                                userId: 10,
                                username: "tester123",
                                firstName: "test",
                                lastName: "test",
                                email: "t@t.t",
                                isSuperUser: true,
                            },
                        ],
                    },
                });
            }
        ).as("getFridgeItems");

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
        ).as("deleteFromFridge");

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

        cy.intercept('GET', 'http://localhost:8089/api/notification/update', {
            statusCode: 200,
            body: {}
        }).as('updatedNotifications')

        cy.intercept('GET', 'http://localhost:8089/api/notification/get',
            (req) => {
                req.reply({
                    statusCode: 200,
                    body: [
                        {
                            notificationId: 1,
                            itemName: "test1",
                            expirationDate: "20.20.2020",
                            isRead: false,
                            fridgeId: 1
                        },
                    ]
                });
            }
        ).as("getNotifications");

        cy.visit(`${base_url}`);

        cy.contains("a", "Logg inn").click();

        cy.get('input[name="email"]').type("t@t.t");
        cy.get('input[name="password"]').type("12345678");

        cy.get('button[type="submit"]').click();

        cy.contains("Home Fridge").click();

        cy.wait("@loginRequest");
        cy.wait("@fetchUser");
        cy.wait("@loadAllFridges");
        cy.wait('@updatedNotifications')
        cy.wait("@isSuperUser");
    });

    it("member tester123 should be visible", () => {
        cy.get(".toggle-members").click();
        cy.wait("@fetchUser");
        cy.contains("tester123").should("be.visible")
    });
});