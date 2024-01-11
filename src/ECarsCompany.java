import java.sql.*;
import java.util.Date;
import java.util.Scanner;

public class ECarsCompany {

    //-------------Variable and Object Declarations----------------------

    //Variables
    Scanner readInputVar = new Scanner(System.in);

    //Objects
    Customer customer = new Customer();
    Car car = new Car();
    ElectricCar electricCar = new ElectricCar();
    EChargingPoint echarge = new EChargingPoint();

    //ECarsCompany method declarations

    public void printCarModels(){
        try {

            Connection databaseConnection = new Database().openConnection();

            ResultSet stmt;
            int count = 1;

            String allCars = "SELECT Model FROM E_Car ORDER BY Model ASC";
            stmt = databaseConnection.createStatement().executeQuery(allCars);

            System.out.println("Our Company's Available Cars are:");
            while(stmt.next()){
                String model = stmt.getString("Model");

                System.out.print("Model " + count + ": ");
                System.out.print(model);
                System.out.println();

                count++;
            }
            stmt.close();
            databaseConnection.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void printAllCars(){
        try {

            Connection databaseConnection = new Database().openConnection();
            ResultSet stmt;
            int count = 1;

            String allCars = "SELECT * FROM E_Car ORDER BY model ASC";
            stmt =databaseConnection.createStatement().executeQuery(allCars);

            System.out.println("The Company's Available Cars are:");
            while(stmt.next()){
                int modelId = stmt.getInt("Model_id");
                String model = stmt.getString("Model");
                String cSpeed = stmt.getString("Charging_Speed");
                Date releaseDate = stmt.getDate("Release_Date");


                System.out.println("Model " + count + ":");
                System.out.print(modelId + ", " + model + ", " + cSpeed + ", " + releaseDate);
                System.out.println();

                count++;
            }
            stmt.close();
            databaseConnection.close();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void sumOfStations(){
        try {
            Connection databaseConnection = new Database().openConnection();
            int sumLarnacaStations = 0, sumLimassolStations = 0, sumPaphosStations = 0, sumNicosiaStations = 0;

            ResultSet stmt;

            String stations = "SELECT Number_of_outlets, City FROM Charging_Point";
            stmt =databaseConnection.createStatement().executeQuery(stations);

            while(stmt.next()){
                String city = stmt.getString("City");
                int numOfOutlets = stmt.getInt("Number_of_outlets");

                if(city.equals("Larnaca") || city.equals("larnaca")){
                    sumLarnacaStations += numOfOutlets;
                }
                else if(city.equals("Limassol") || city.equals("limassol")){
                    sumLimassolStations += numOfOutlets;
                }
                else if(city.equals("Papho") || city.equals("papho")){
                    sumPaphosStations += numOfOutlets;
                }
                else if(city.equals("Nicosia") || city.equals("nicosia")){
                    sumNicosiaStations += numOfOutlets;
                }
            }

            System.out.println("The Total Number of Stations in each City are: ");
            System.out.println("Paphos: " + sumPaphosStations);
            System.out.println("Larnaca: " + sumLarnacaStations);
            System.out.println("Limassol: " + sumLimassolStations);
            System.out.println("Nicosia: " + sumNicosiaStations);

            stmt.close();
            databaseConnection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void numberOfStations(String city) throws SQLException {

        try {
            Connection databaseConnection = new Database().openConnection();
            ResultSet stmt;


            String stations = "SELECT Number_of_outlets, Available_outlets, Address FROM Charging_Point WHERE City = '"+city+"'";
            stmt =databaseConnection.createStatement().executeQuery(stations);


            System.out.println("\nThe Charging Outlets in " + city + " are: ");
            while(stmt.next()){
                int numOfOutlets = stmt.getInt("Number_of_outlets");
                int availableOutlets = stmt.getInt("Available_outlets");
                String address = stmt.getString("Address");


                System.out.println(address + " with " + numOfOutlets + " outlets, of which the " + availableOutlets + " are free");
            }
            stmt.close();
            databaseConnection.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void availableEChargerPoints(String city, boolean availableOutlets) throws SQLException {

        if(availableOutlets){
            echarge.availableOutlets();

        }else{
            numberOfStations(city);
        }
    }

    public int userMenu(){
        System.out.println("\n--------------------Main Menu--------------------\n\n" +

                            "\t1. Show more info about the company\n" +
                            "\t2. Find a charging point\n" +
                            "\t3. Login\n" +
                            "\t4. Register\n" +
                            "\t5. Exit\n");
        System.out.print("Please choose one from the above options: ");
        int userSelection = Integer.parseInt(readInputVar.nextLine());

        return userSelection;
    }

    public int userSubMenu(){
        System.out.println("\n--------------------Login Menu--------------------\n\n" +

                "\t1. Show car status\n" +
                "\t2. Start charging\n" +
                "\t3. Complete charging process\n" +
                "\t4. Logout\n");
        System.out.print("Please choose one from the above options: ");
        int userSelection = Integer.parseInt(readInputVar.nextLine());

        return userSelection;
    }

    public void findChargingPointImplementation() throws SQLException {

        String givenCity = null;
        int flag = 0;

        while(flag == 0){
            System.out.print("Please specify the desired city: ");
            givenCity = readInputVar.nextLine();

            if(givenCity.equals("Larnaca") || givenCity.equals("larnaca")){
                flag = 1;
            }
            else if(givenCity.equals("Limassol") || givenCity.equals("limassol")){
                flag = 1;
            }
            else if(givenCity.equals("Papho") || givenCity.equals("papho")){
                flag = 1;
            }
            else if(givenCity.equals("Nicosia") || givenCity.equals("nicosia")){
                flag = 1;
            }
            else
                System.out.println("Wrong city! Please specify a right one!");
        }

        System.out.print("\n1. Show all the charging points in the city" +
                "\n2. Show the charging points with available outlets" +
                "\nPlease choose one option: ");
        int givenOption = Integer.parseInt(readInputVar.nextLine());
        if(givenOption == 1)
            availableEChargerPoints(givenCity,false);
        else
            availableEChargerPoints(givenCity,true);
    }

    public void subMenuImplementation() throws SQLException {

        int userSubSelection, pointId = 0;

        System.out.print("Enter the Customer's ID: ");
        customer.customerID = Integer.parseInt(readInputVar.nextLine());

        boolean ifExist = customer.checkIfCustomerExists(customer.customerID);

        if(!ifExist){
            System.out.println("This ID does not exist to anyone! Please Register first!");
        }else{
            do{
                userSubSelection = userSubMenu();
                switch(userSubSelection) {
                    case 1:
                        electricCar.Battery_Balance(customer.customerID);
                        car.calcTotalMillimetres(customer.customerID);
                        car.carLastService(customer.customerID);
                        break;
                    case 2:
                        System.out.println("Please specify the Point ID of your charging station: ");
                        pointId = Integer.parseInt(readInputVar.nextLine());
                        echarge.startCharging(customer,pointId);
                        break;
                    case 3:
                        echarge.finishCharging(customer);
                        break;
                }
                if(userSubSelection > 4 || userSubSelection < 1)
                    System.out.println("\n!!!Wrong Selection, Please choose one from the options!!!\n");
            }while(userSubSelection != 4);
        }
    }

    public void registerImplementation() throws SQLException {
        System.out.print("Please give the Customer's ID: ");
        int customerId = Integer.parseInt(readInputVar.nextLine());
        boolean ifExist = customer.checkIfCustomerExists(customerId);

        if(ifExist)
            System.out.println("This user already exists!");
        else{
            customer.getUserDetails();
            customer.getCarDetails();
            customer.intoCustomer(customerId);
        }
    }

    //Main Function that the program runs
    public static void main(String[] args) throws SQLException {

        //-----Variable and Object Declarations------------
        Scanner readInputVar = new Scanner(System.in);
        ECarsCompany ecars = new ECarsCompany();


        //Creating and filling the tables with data
        Database SQL_management = new Database();
        SQL_management.connectToDatabase();
        SQL_management.chargingPoint_table();
        SQL_management.E_Car_table();
        SQL_management.Customer_table();
        SQL_management.Charging_Process_table();

        SQL_management.chargingPoint_tableFill();
        SQL_management.E_Car_tableFill();
        SQL_management.customerTable_fill();
        SQL_management.Charging_Process_table_fill();

        //----------------------------Part 3 - User Interaction ----------------------------------

        System.out.println("---------------------Welcome to UC(AR)Y---------------------");
        System.out.println("Our company is a pioneer automotive company which is specialised to the electric cars.\n" +
                            "We started out journey from the laboratories of the University of Cyprus and now we are developing gradually.");

        System.out.println();
        ecars.printCarModels();
        System.out.println();

        do {
            int userSelection = ecars.userMenu();


            switch (userSelection) {
                case 1:
                    ecars.printAllCars();
                    System.out.println();
                    ecars.sumOfStations();
                    break;
                case 2:
                    ecars.findChargingPointImplementation();
                    break;
                case 3:
                    ecars.subMenuImplementation();
                    break;
                case 4:
                    ecars.registerImplementation();
                    break;
                case 5:
                    System.out.println("Thank you for choosing UC(AR)Y!");
                    System.exit(0);
                default:
                    System.out.println("\n!!!Wrong Selection, Please choose one from the options!!!\n");
            }
        }while(true);
    }
}
