/**
 * The interface provides the basic
 * behaviour that a Behaviour class should implement
 *
 * @author Kyriaki Kekkou
 * @version 1.0.0
 */
public interface VehicleInterface {
    int numberOfPassengers=0;
    int manufactureYear=0;
    double price=0.0;
    String brand="";

    void setNumberOfPassengers(int numberOfPassengers);
    void setManufactureYear(int manufactureYear);
    void setBrand(String brand);
    int getNumberOfPassengers();
    int getManufactureYear();
    String getBrand();



}
