public interface DatabaseInterface {

    //Declaration of the parameters that needed in order to establish a connection with the SQL Database
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://sql11.freesqldatabase.com/sql11539335?zeroDateTimeBehavior=convertToNull";
    String USER = "sql11539335";
    String PASS = "GVFHy4rffM";

    int batchSize = 20;

    public void connectToDatabase();
    public void chargingPoint_table();
    public void chargingPoint_tableFill();
    public void E_Car_table();
    public void E_Car_tableFill();
    public void Customer_table();
    public void customerTable_fill();
    public void Charging_Process_table();
    public void Charging_Process_table_fill();
}
