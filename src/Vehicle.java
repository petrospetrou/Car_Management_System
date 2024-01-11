import java.util.Scanner;

public class Vehicle implements VehicleInterface {

    Scanner readInputVar = new Scanner(System.in);

    int numberOfPassengers, manufactureYear;
    String brand;

    public Vehicle(){
        numberOfPassengers = 0;
        manufactureYear = 0;
        brand = null;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public int getNumberOfPassengers() {
        System.out.print("Enter the Number of Passengers: ");
        numberOfPassengers = Integer.parseInt(readInputVar.nextLine());

        return numberOfPassengers;
    }

    public String getBrand() {
        System.out.print("Enter the Car's Brand: ");
        brand = readInputVar.nextLine();

        return brand;
    }

    public int getManufactureYear() {
        System.out.print("Enter the Car's Manufacture Year: ");
        manufactureYear = Integer.parseInt(readInputVar.nextLine());

        return manufactureYear;
    }

}
