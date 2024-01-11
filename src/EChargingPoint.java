import java.sql.*;
import java.util.Scanner;

public class EChargingPoint {

    Scanner readInputVar = new Scanner(System.in);

    public void availableOutlets() throws SQLException {

        Connection databaseConnection = new Database().openConnection();
        ResultSet stmt;

        System.out.print("Enter the Point's ID: ");
        int givenID = Integer.parseInt(readInputVar.nextLine());

        String availableChargingQuery = "SELECT Available_outlets FROM Charging_Point WHERE Point_id = " + givenID;

        stmt =databaseConnection.createStatement().executeQuery(availableChargingQuery);

        stmt.next();

        int freeOutlets = stmt.getInt("Available_outlets");

        System.out.println("The available outlets on point " + givenID + " are: "+freeOutlets);

        stmt.close();
        databaseConnection.close();

    }

    public void startCharging(Customer customer, int pointID) {

        Connection databaseConnection = new Database().openConnection();

        System.out.print("The charging process has begun!");

        ResultSet stmt;

     try {
         Timestamp timestamp = new Timestamp(System.currentTimeMillis());

         //-------------Update Available_outlets----------------

         String getOutlet = "SELECT Available_outlets FROM Charging_Point WHERE Point_id = " + pointID;

         stmt = databaseConnection.createStatement().executeQuery(getOutlet);

         stmt.next();

         int outlet = stmt.getInt("Available_outlets");

         outlet--;

         String updatedOutlet = "UPDATE Charging_Point SET Available_outlets = ? WHERE Point_id = " + pointID;

         PreparedStatement update = databaseConnection.prepareStatement(updatedOutlet);

         update.setInt(1, outlet);

         update.addBatch();
         update.executeBatch();

         //-------------Insert record to Charging_process---------

         String getProcessId = "SELECT Process_id FROM Charging_Process";
         stmt = databaseConnection.createStatement().executeQuery(getProcessId);
         
         int lastProcessId = 0;
         while(stmt.next()){
             lastProcessId = stmt.getInt("Process_id");
         }
         lastProcessId++;

         String intoChargingProcess = "INSERT INTO Charging_Process(Process_id, Customer_id, Point_id, Starting_timestamp, Fully_charge_timestamp) VALUES(?,?,?,?,?)";

         PreparedStatement stmtIntoCp = databaseConnection.prepareStatement(intoChargingProcess);

         stmtIntoCp.setInt(1, lastProcessId);
         stmtIntoCp.setInt(2, customer.customerID);
         stmtIntoCp.setInt(3, pointID);
         stmtIntoCp.setTimestamp(4, timestamp);
         stmtIntoCp.setString(5,"null");

         stmtIntoCp.addBatch();
         stmtIntoCp.executeBatch();

         update.close();
         stmt.close();
         databaseConnection.close();

     }catch (Exception e){
         e.printStackTrace();
     }
    }

    public void finishCharging(Customer customer) throws SQLException {

        Connection databaseConnection = new Database().openConnection();

        System.out.println("The charging process has finished!");

        ResultSet stmt;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        int pointID = 0;




        String ifChargeCheck = "SELECT Process_id, Point_id, Fully_charge_timestamp FROM Charging_Process WHERE Customer_id = " + customer.customerID;

        stmt = databaseConnection.createStatement().executeQuery(ifChargeCheck);

        while(stmt.next()) {
            int processID = stmt.getInt("Process_id");
            pointID = stmt.getInt("Point_id");


            if (stmt.equals("null"))
                System.out.println("This user isn't charging the car at the moment");
            else {
                String setFullyChargeDate = "UPDATE Charging_Process SET Fully_charge_timestamp = ? WHERE (Customer_id = '" + customer.customerID + "') AND (Process_id ='" + processID +"')";
                PreparedStatement update = databaseConnection.prepareStatement(setFullyChargeDate);

//            String update_fully_charge = "UPDATE  Charging_Process SET fully_charge_timestamp= '"+timestamp+"'  WHERE (customer_id='" + customer.customerID + "')AND (process_id='"+processID+"')";
//            PreparedStatement update = databaseConnection.prepareStatement(update_fully_charge);


                update.setTimestamp(1, timestamp);

                update.addBatch();
                update.executeBatch();
            }
        }

        //-------------Update Available_outlets----------------

        String getOutlet = "SELECT Available_outlets FROM Charging_Point WHERE Point_id = " + pointID;

        stmt = databaseConnection.createStatement().executeQuery(getOutlet);

        stmt.next();

        int outlet = stmt.getInt("Available_outlets");

        outlet++;

        String updatedOutlet = "UPDATE Charging_Point SET Available_outlets = ? WHERE Point_id = " + pointID;

        PreparedStatement update = databaseConnection.prepareStatement(updatedOutlet);

        update.setInt(1, outlet);

        update.addBatch();
        update.executeBatch();

        update.close();
        stmt.close();
        databaseConnection.close();
    }
}
