import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class Car extends Vehicle{

    Scanner scanner = new Scanner(System.in);

    public void calcTotalMillimetres(int customerID) throws SQLException {

        Connection databaseConnection = new Database().openConnection();

        ResultSet stmt;

        System.out.print("Enter the covered Distance (If none then 0):");
        int newDistance = Integer.parseInt(scanner.nextLine());

        String totalMillimetresQuery = "SELECT Total_millimetres FROM Customer WHERE Customer_id = " + customerID;

        stmt = databaseConnection.createStatement().executeQuery(totalMillimetresQuery);

        stmt.next();
        int totalMillimetres = stmt.getInt("Total_millimetres");

        int newTotalMillimetres = totalMillimetres + newDistance;

        String updateTotalMillimetres = "UPDATE Customer SET Total_millimetres = ? WHERE Customer_id = " + customerID;

        PreparedStatement update = databaseConnection.prepareStatement(updateTotalMillimetres);

        update.setInt(1, newTotalMillimetres);

        update.addBatch();
        update.executeBatch();

        System.out.println("The car's total millimetres are: " + newTotalMillimetres);

        stmt.close();
        update.close();
        databaseConnection.close();

    }

    public void carLastService(int customerID) throws SQLException {

        Connection databaseConnection = new Database().openConnection();

        ResultSet stmt;

        String carLastServiceQuery = "SELECT Car_last_service FROM Customer WHERE Customer_id = " + customerID;

        stmt = databaseConnection.createStatement().executeQuery(carLastServiceQuery);

        stmt.next();

        Date lastServiceDate = stmt.getDate("Car_last_service");
        System.out.println("Your car's Last Service was: " + lastServiceDate);

        stmt.close();
        databaseConnection.close();
    }
}
