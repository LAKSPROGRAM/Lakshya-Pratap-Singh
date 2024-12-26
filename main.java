import java.util.*;

class Car {
    private String carId;
    private String Brand;
    private String Model;
    private double basepriceperday;
    private boolean isAvailable;

    public Car(String carId, String Brand, String Model, double basepriceperday, boolean isAvailable) {
        this.carId = carId;
        this.Brand = Brand;
        this.Model = Model;
        this.basepriceperday = basepriceperday;
        this.isAvailable = true;
    }

    public String carId() {
        return carId;
    }

    public String getBrand() {
        return Brand;
    }

    public String getModel() {
        return Model;
    }

    public double calculatePrice(int rentaldays) {
        return basepriceperday * rentaldays;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerID;
    private String name;

    public Customer(String customerID, String name) {
        this.customerID = customerID;
        this.name = name;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int Days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.Days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getdays() {
        return Days;
    }
}

class CarRentalSystems {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalSystems() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void getcar(Car car) {
        cars.add(car);
    }

    public void getCustomer(Customer customer) {
        customers.add(customer);
    }

    public void rentcar(Car car, Customer customer, int days) {
        if (car.getIsAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
            System.out.println("Car is rented successfully");
        } else {
            System.out.println("Car is not available");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        System.out.println("Car is returned successfully");
        Rental rentaltoremove = null;
        for (Rental rentals : rentals) {
            if (rentals.getCar() == car) {
                rentaltoremove = rentals;
                break;
            }
        }
        if (rentaltoremove != null) {
            rentals.remove(rentaltoremove);
        } else {
            System.out.println("Car is not rented");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("===== Car Rental System =====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // comsume next line
            if (choice == 1) {
                System.out.println("-----Rent your car------");
                System.out.println("Enter the customer name");
                String customerName = scanner.nextLine();
                System.out.println("Availabale Cars");
                for (Car car : cars) {
                    if (car.getIsAvailable()) {
                        System.out.println(car.carId() + " " + car.getModel() + " " + car.getBrand());
                    }
                }
                System.out.print("\nEnter the car ID you want to rent: ");
                String carId = scanner.nextLine();

                System.out.print("Enter the number of days for rental: ");
                int rentalDays = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                getCustomer(newCustomer);
                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.carId().equals(carId) && car.getIsAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerID());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentcar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = scanner.nextLine();

                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.carId().equals(carId) && !car.getIsAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }

                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }

        System.out.println("\nThank you for using the Car Rental System!");
    }
}

public class Main {
    public static void main(String args[]) {
        CarRentalSystems carRentalSystem = new CarRentalSystems();
        carRentalSystem.getcar(new Car("C001", "TOYOTA", "FORTUNER", 1000.0, true));
        carRentalSystem.getcar(new Car("C002", "TOYOTA", "FORTUNER LEGENDAR", 1500.0, true));
        carRentalSystem.getcar(new Car("C003", "MAHINDRA", "SCORPIO", 9000.0, true));
        carRentalSystem.menu();
    }
}




