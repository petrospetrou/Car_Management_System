import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import java.util.Scanner;

public class ElectricCar extends Car{

    Scanner scanner = new Scanner(System.in);

    public void Battery_Balance(int customerID) throws SQLException {

        Connection databaseConnection = new Database().openConnection();

        ResultSet stmt;

        int batteryBalance = 0;

        String batteryQuery = "SELECT Car_battery_level FROM Customer WHERE Customer_id = " + customerID;

        stmt =databaseConnection.createStatement().executeQuery(batteryQuery);

        stmt.next();

        batteryBalance = stmt.getInt("Car_battery_level");

        System.out.println("The car's battery level is: " + batteryBalance);

        stmt.close();
        databaseConnection.close();

    }
}
