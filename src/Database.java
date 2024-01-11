import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.lang.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static java.lang.Integer.parseInt;

public class Database implements DatabaseInterface{

    //Declaration of the Connection and Statement parameters
    private Connection connection;
    private Statement statement;

    //Method for establishing the connection
    public void connectToDatabase(){
        try{
            Class.forName(JDBC_DRIVER).newInstance();

            connection = DriverManager.getConnection(DATABASE_URL, USER, PASS);

            statement = connection.createStatement();
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public Connection openConnection(){
        try{
            Class.forName(JDBC_DRIVER).newInstance();

            connection = DriverManager.getConnection(DATABASE_URL, USER, PASS);


        }
        catch (Exception e){
            System.out.println(e);
        }
        return connection;
    }

    //Methods for creating the tables and storing data from CSV files into them
    public void chargingPoint_table() {
        try{
            //Creating the table
            String cp_table = "CREATE TABLE IF NOT EXISTS Charging_Point" + "(Point_id INTEGER NOT NULL, " + "Number_of_outlets INTEGER NOT NULL, "
                    + "Available_outlets INTEGER NOT NULL, " + "City VARCHAR(255), " + "Address VARCHAR(255), " + "PRIMARY KEY( Point_id ))" ;

            statement.executeUpdate(cp_table);

        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void chargingPoint_tableFill(){
        //Filling the table with the data from the CSV file
        String cp_table_filepath = "/Users/petros/3rd_year/ECE_318/Assignment_2/Assignment_2/resources/chargePoints.csv";

        try{

            String cp_fill = "INSERT IGNORE INTO Charging_Point(Point_id,Number_of_outlets,Available_outlets,City,Address) VALUES(?,?,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(cp_fill);

            BufferedReader lineReader = new BufferedReader(new FileReader(cp_table_filepath));

            String lineText = null;
            int count = 0;

            //Reading from the file line by line and storing the data to Strings
            lineReader.readLine();
            while((lineText = lineReader.readLine()) != null){
                String[] data = lineText.split(",");

                String Point_id = data[0];
                String Number_of_outlets = data[1];
                String Available_outlets = data[2];
                String City = data[3];
                String Address = data[4];

                stmt.setInt(1, parseInt(Point_id));
                stmt.setInt(2, parseInt(Number_of_outlets));
                stmt.setInt(3, parseInt(Available_outlets));
                stmt.setString(4, City);
                stmt.setString(5, Address);
                stmt.addBatch();
                if(count%batchSize==0){
                    stmt.executeBatch();
                }
            }
            lineReader.close();
            stmt.executeBatch();

            System.out.println("Data has been inserted successfully in table Charging_Point");


        }
        catch(Exception exception){
            System.out.println(exception);
        }
    }

    public void E_Car_table() {
        try{
            String eCar_table = "CREATE TABLE IF NOT EXISTS E_Car" + "(Model_id INTEGER NOT NULL, " + "Model VARCHAR(255), "
                    + "Charging_Speed VARCHAR(255), " + "Release_Date DATE, " + "PRIMARY KEY( Model_id ))" ;

            statement.executeUpdate(eCar_table);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void E_Car_tableFill(){
        //Filling the table with the data from the CSV file
        String E_Car_table_filepath = "/Users/petros/3rd_year/ECE_318/Assignment_2/Assignment_2/resources/e-cars.csv";

        try{

            String E_Car_fill = "INSERT IGNORE INTO E_Car(Model_id, Model, Charging_Speed, Release_Date) VALUES(?,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(E_Car_fill);

            BufferedReader lineReader = new BufferedReader(new FileReader(E_Car_table_filepath));

            String lineText = null;
            int count = 0;

            //Reading from the file line by line and storing the data to Strings
            lineReader.readLine();
            while((lineText = lineReader.readLine()) != null){
                String[] data = lineText.split(",");

                String Model_id = data[0];
                String Model = data[1];
                String Charging_Speed = data[2];
                String Release_Date = data[3];


                //!!!IMPORTANT: Converting the String Release_Date to the correct Date format
                java.util.Date releaseDate_Convert = new SimpleDateFormat("dd/MM/yyyy").parse(Release_Date);
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
                String correctDate = DATE_FORMAT.format(releaseDate_Convert);


                stmt.setInt(1, parseInt(Model_id));
                stmt.setString(2, Model);
                stmt.setString(3, Charging_Speed);
                stmt.setDate(4, Date.valueOf(correctDate));
                stmt.addBatch();
                if(count%batchSize==0){
                    stmt.executeBatch();
                }
            }
            lineReader.close();
            stmt.executeBatch();

            System.out.println("Data has been inserted successfully in table E_Car");


        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public void Customer_table() {
        try{
            String customer_table = "CREATE TABLE IF NOT EXISTS Customer(Customer_id INTEGER NOT NULL, " + "Full_name VARCHAR(255), "
                    + "Car_registration_number VARCHAR(255), " + "Model_id INTEGER NOT NULL, " + "Car_color VARCHAR(255), "
                    + "Car_battery_level INTEGER, " + "Car_last_service DATE, " + "Total_millimetres INTEGER, "
                    + "FOREIGN KEY(Model_id) REFERENCES E_Car(Model_id), " + "PRIMARY KEY( Customer_id ))" ;

            statement.executeUpdate(customer_table);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void customerTable_fill(){
        //Filling the table with the data from the CSV file
        String customerTable_table_filepath = "/Users/petros/3rd_year/ECE_318/Assignment_2/Assignment_2/resources/customer.csv";

        try{

            String customerTable_fill = "INSERT IGNORE INTO Customer(Customer_id, Full_name, Car_registration_number, Model_id, " +
                    "Car_color, Car_battery_level, Car_last_service, Total_millimetres) VALUES(?,?,?,?,?,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(customerTable_fill);

            BufferedReader lineReader = new BufferedReader(new FileReader(customerTable_table_filepath));

            String lineText = null;
            int count = 0;

            //Reading from the file line by line and storing the data to Strings
            lineReader.readLine();
            while((lineText = lineReader.readLine()) != null){
                String[] data = lineText.split(",");

                String Customer_id = data[0];
                String Full_name = data[1];
                String Car_registration_number = data[2];
                String Model_id = data[3];
                String Car_color = data[4];
                String Car_battery_level = data[5];
                String Car_last_service = data[6];
                String Total_millimetres = data[7];


                stmt.setInt(1, parseInt(Customer_id));
                stmt.setString(2, Full_name);
                stmt.setString(3, Car_registration_number);
                stmt.setInt(4, parseInt(Model_id));
                stmt.setString(5, Car_color);
                stmt.setInt(6, parseInt(Car_battery_level));

                if(Car_last_service.equals("null")){
                    stmt.setString(7, "null");
                }
                else{
                    //!!!IMPORTANT: Converting the String Car_last_service to the correct Date format
                    java.util.Date cls_Convert = new SimpleDateFormat("dd/MM/yyyy").parse(Car_last_service);
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
                    String correctDate = DATE_FORMAT.format(cls_Convert);
                    stmt.setDate(7, Date.valueOf(correctDate));
                }

                //Because the Total_millimetres fiels is empty, I fill the field with NULL
                if(Total_millimetres.equals("null")){
                    stmt.setString(8, "null");
                }
                else{
                    stmt.setNull(8, parseInt(Total_millimetres));
                }

                stmt.addBatch();
                if(count%batchSize==0){
                    stmt.executeBatch();
                }
            }
            lineReader.close();
            stmt.executeBatch();

            System.out.println("Data has been inserted successfully in table Customer");


        }
        catch(Exception exception){
            exception.printStackTrace();
        }
    }

    public void Charging_Process_table() {
        try{
            String cProcess = "CREATE TABLE IF NOT EXISTS Charging_Process" + "(Process_id INTEGER NOT NULL, " + "Customer_id INTEGER NOT NULL, "
                    + "Point_id INTEGER NOT NULL, " + "Starting_timestamp TIMESTAMP, " + "Fully_charge_timestamp TIMESTAMP, "
                    + "FOREIGN KEY(Customer_id) REFERENCES Customer(Customer_id), " + "FOREIGN KEY(Point_id) REFERENCES Charging_Point(Point_id), "
                    + "PRIMARY KEY( Process_id, Customer_id, Point_id ))" ;

            statement.executeUpdate(cProcess);
        }
        catch (SQLException e){
            System.out.println(e);
        }
    }

    public void Charging_Process_table_fill() {
        //Filling the table with the data from the CSV file
        String chargingProcess_table_filepath = "/Users/petros/3rd_year/ECE_318/Assignment_2/Assignment_2/resources/chargingProcess.csv";

        try {

            String cprocess_fill = "INSERT IGNORE INTO Charging_Process(Process_id, Customer_id, Point_id, Starting_timestamp, " +
                    "Fully_charge_timestamp) VALUES(?,?,?,?,?)";

            PreparedStatement stmt = connection.prepareStatement(cprocess_fill);

            BufferedReader lineReader = new BufferedReader(new FileReader(chargingProcess_table_filepath));

            String lineText = null;
            int count = 0;

            //Reading from the file line by line and storing the data to Strings
            lineReader.readLine();
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");

                String Process_id = data[0];
                String Customer_id = data[1];
                String Point_id = data[2];
                String Starting_timestamp = data[3].replace("."," ");
                String Fully_charge_timestamp = data[4].replace("."," ");


                stmt.setInt(1, parseInt(Process_id));
                stmt.setInt(2, parseInt(Customer_id));
                stmt.setInt(3, parseInt(Point_id));

                if(Starting_timestamp.equals("null")){
                    stmt.setString(4, "null");
                }
                else{

                    //!!!IMPORTANT: Converting the String Starting_timestamp to the correct timestamp format
                    java.util.Date Starting_timestamp_Convert = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(Starting_timestamp);
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String correctStartingTimestamp = DATE_FORMAT.format(Starting_timestamp_Convert);

                    stmt.setTimestamp(4, Timestamp.valueOf(correctStartingTimestamp));

                }

                if(Fully_charge_timestamp.equals("null")){
                    stmt.setString(5, "null");
                }
                else{
                    //!!!IMPORTANT: Converting the String Fully_charge_timestamp to the correct timestamp format
                    java.util.Date fCharge_Convert = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(Fully_charge_timestamp);
                    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    String correctFullyCharge = DATE_FORMAT.format(fCharge_Convert);

                    stmt.setTimestamp(5, Timestamp.valueOf(correctFullyCharge));

                }

                stmt.addBatch();
                if (count % batchSize == 0) {
                    stmt.executeBatch();
                }
            }
            lineReader.close();
            stmt.executeBatch();

            System.out.println("Data has been inserted successfully in table Charging_Process");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
