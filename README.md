# Spring Boot and Vue App: Virtual Fridge with Shopping List and Dinner Suggestions

## Overview

The Virtual Fridge with Shopping List and Dinner Suggestions is a web application that aims to reduce food waste at home. The app allows users to manage their fridge inventory, plan meals, and generate a shopping list based on what they need to purchase. The app also provides dinner suggestions based on the ingredients available in the user's virtual fridge.

The app is built using the Spring Boot framework for the backend and Vue.js for the frontend. The backend provides a RESTful API that the frontend consumes to display the data to the user.

## Features

- Add, edit and remove items from the virtual fridge inventory
- Plan meals by ingredients from the virtual fridge inventory
- Generate a shopping list based on the planned meals and the current virtual fridge inventory
- Get dinner suggestions based on the available ingredients in the virtual fridge inventory
- User authentication and authorization
- Responsive UI design
- Integration with grocery stores APIs to retrieve real-time prices and availability
- Implementation of a notification system to remind users of expiring items


## Prerequisites

- Java 17
- Node.js
- Vue

## Getting Started

### Clone the repository

```
git clone -b development git@gitlab.stud.idi.ntnu.no:gruppe-06/idatt2106_2023_6.git```

### Run the backend

1. Navigate to the `backend` directory

```
cd backend
```
2. Install maven dependencies 

```
mvn clean install
```

3. Run the application

```
mvn spring-boot:run
```

The backend will run on port `8089`.

### Run the frontend

1. Navigate to the `frontend` directory

```
cd frontend
```

2. Install dependencies

```
npm install
```

3. Run the application

```
npm run dev
```

The frontend will run on port `5173`.

### Access the application

Open your browser and navigate to http://localhost:5173

## Contributing

Contributions are welcome! To contribute, please fork the repository and submit a pull request.

## License

This project is licensed under the MIT License - see the LICENSE.md file for details.