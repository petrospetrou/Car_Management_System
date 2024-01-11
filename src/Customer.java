import java.sql.*;
import java.util.Scanner;

public class Customer {
    Scanner readInputVar = new Scanner(System.in);
    int customerID;
    String fullName, carRegistrationNumber, modelID, carColor, carBatteryLevel, carLastService, totalMillimetres;

    public void getUserDetails() throws SQLException {
        System.out.print("Enter the Full name: ");
        fullName = readInputVar.nextLine();
    }

    public void getCarDetails() throws SQLException {

        System.out.print("Enter the Model's ID: ");
        modelID = readInputVar.nextLine();

        boolean ifExist = checkIfModelExists(Integer.parseInt(modelID));

        while(!ifExist){
            System.out.println("This model doesn't exist! Please provide one that exists: ");
            modelID = readInputVar.nextLine();
            ifExist = checkIfModelExists(Integer.parseInt(modelID));
        }

        System.out.print("Enter the Car's registration number: ");
        carRegistrationNumber = readInputVar.nextLine();

        System.out.print("Enter the Car's color: ");
        carColor = readInputVar.nextLine();

        System.out.print("Enter the Car's battery level: ");
        carBatteryLevel = readInputVar.nextLine();

        System.out.print("Enter the Car's last service (yyyy-mm-dd): ");
        carLastService = readInputVar.nextLine();

        System.out.print("Enter the Total millimetres: ");
        totalMillimetres = readInputVar.nextLine();

    }

    public boolean checkIfCustomerExists(int givenId) throws SQLException {

        Connection databaseConnection = new Database().openConnection();

        ResultSet stmt;
        boolean existID = false;

        String customerIdQuery = "SELECT Customer_id FROM Customer";

        stmt =databaseConnection.createStatement().executeQuery(customerIdQuery);

        while(stmt.next()){
            int customerId = stmt.getInt("Customer_id");

            if(customerId == givenId){
                existID = true;
                break;
            } else
                existID = false;

        }
        stmt.close();
        databaseConnection.close();

        return existID;
    }

    public boolean checkIfModelExists(int givenModelID) throws SQLException {

        Connection databaseConnection = new Database().openConnection();
        ResultSet stmt;
        boolean existModel = false;

        String modelIdQuery = "SELECT Model_id FROM E_Car";

        stmt =databaseConnection.createStatement().executeQuery(modelIdQuery);

        while(stmt.next()){
            int modelID = stmt.getInt("Model_id");

            if(modelID == givenModelID){
                existModel = true;
                break;
            } else
                existModel = false;

        }
        stmt.close();
        databaseConnection.close();

        return existModel;
    }

    public void intoCustomer(int customerId) throws SQLException {

        Connection databaseConnection = new Database().openConnection();

        PreparedStatement stmt = databaseConnection.prepareStatement("INSERT INTO Customer(Customer_id, Full_name, Car_registration_number, Model_id, Car_color, Car_battery_level, Car_last_service, Total_millimetres) VALUES(?,?,?,?,?,?,?,?)");

        stmt.setInt(1, customerId);
        stmt.setString(2, fullName);
        stmt.setString(3, carRegistrationNumber);
        stmt.setInt(4, Integer.parseInt(modelID));
        stmt.setString(5, carColor);
        stmt.setInt(6, Integer.parseInt(carBatteryLevel));
        stmt.setDate(7, java.sql.Date.valueOf(carLastService));
        stmt.setInt(8, Integer.parseInt(totalMillimetres));

        stmt.addBatch();
        stmt.executeBatch();

    }

}
