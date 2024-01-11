
# Car Management System

## Introduction
This Car Management System, represents a comprehensive solution for managing electric vehicles and charging infrastructure. Developed in JetBrains IntelliJ IDE, it offers a robust platform for handling various operational aspects of an electric vehicle company. The project was specifically developed as a core assignment for a course in Object-Oriented Programming and Java at the university. It serves as a testament to my technical proficiency in software development with Java, as well as my ability to apply object-oriented principles to real-world problems, particularly in the context of electric vehicle infrastructure and management.

## Features
- **Customer Management**: Efficiently handles all aspects of customer data and interactions.
- **Vehicle Tracking**: Comprehensive tracking and management of various vehicle types, with a special focus on electric cars.
- **Charging Point Locator**: Advanced functionality to manage and locate electric car charging points.
- **Database Integration**: Seamlessly integrates with an SQL server, ensuring robust data handling, storage, and retrieval.
- **User-Friendly Console Interface**: Developed with user experience in mind, ensuring ease of use and intuitive navigation.
- **IDE Integration**: Project is fully developed and tested on JetBrains IntelliJ IDE, ensuring a smooth development and testing workflow.

## Installation
1. Install Java JDK and SQL Server.
2. Clone the repository
3. Open the project in JetBrains IntelliJ IDE.
4. Configure SQL server using provided scripts.
5. Compile and run the application from the IDE.

## Usage
- **Adding a New Customer**: `Customer newCustomer = new Customer(name, contact);`
- **Registering a New Electric Car**: `Car newCar = new ElectricCar(model, range);`
- **Finding the Nearest Charging Point**: `EChargingPoint nearest = EChargingPoint.findNearest(currentLocation);`

## Testing
In the Scenarios Folder, there are some examples on how the program flows.

## License
All rights reserved. This project is proprietary and is not licensed for use, reproduction, or distribution.
