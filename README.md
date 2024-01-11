
# Car Management System for UC(AR)Y

## Introduction
This Car Management System, specifically developed for UC(AR)Y, represents a comprehensive solution for managing electric vehicles and charging infrastructure. Developed in JetBrains IntelliJ IDE, it offers a robust platform for handling various operational aspects of an electric vehicle company.

## Features
- **Customer Management**: Efficiently handles all aspects of customer data and interactions.
- **Vehicle Tracking**: Comprehensive tracking and management of various vehicle types, with a special focus on electric cars.
- **Charging Point Locator**: Advanced functionality to manage and locate electric car charging points.
- **Database Integration**: Seamlessly integrates with an SQL server, ensuring robust data handling, storage, and retrieval.
- **User-Friendly Interface**: Developed with user experience in mind, ensuring ease of use and intuitive navigation.
- **IDE Integration**: Project is fully developed and tested on JetBrains IntelliJ IDE, ensuring a smooth development and testing workflow.

## Installation
1. Install Java JDK and SQL Server.
2. Clone the repository: `git clone [repository URL]`.
3. Open the project in JetBrains IntelliJ IDE.
4. Configure SQL server using provided scripts.
5. Compile and run the application from the IDE.

## Usage
- **Adding a New Customer**: `Customer newCustomer = new Customer(name, contact);`
- **Registering a New Electric Car**: `Car newCar = new ElectricCar(model, range);`
- **Finding the Nearest Charging Point**: `EChargingPoint nearest = EChargingPoint.findNearest(currentLocation);`

## Contributing
- Fork the project.
- Create a feature branch (`git checkout -b feature/YourFeatureName`).
- Commit your changes.
- Push to the branch.
- Submit a pull request.

## Testing
Describe the procedure for running unit tests and integration tests within the JetBrains IntelliJ IDE.

## License
All rights reserved. This project is proprietary and is not licensed for use, reproduction, or distribution.
