import java.io.*;
import java.util.*;

// class object for booking variable
class Booking {
    String passengerName;
    String date;
    String time;
    String pickupLocation;
    String dropoffLocation;
    double distance;
    double fare;

    public Booking(String passengerName, String date, String time, String pickupLocation, String dropoffLocation, double distance) {
        this.passengerName = passengerName;
        this.date = date;
        this.time = time;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.distance = distance;
        this.fare = calculateFare(distance);
    }

    // computation for fee
    private double calculateFare(double distance) {
        if (distance <= 1) {
            return 25.0;
        } else {
            return 25.0 + (distance - 1) * 20.0;
        }
    }

    @Override
    public String toString() {
        return String.format("%-10s %-10s %-10s %-15s %-15s %-10.1f %-10.2f", 
                date, time, passengerName, pickupLocation, dropoffLocation, distance, fare);
    }
}


// method for system menu
public class RideHailingBookingSystem {
    static ArrayList<Booking> bookings = new ArrayList<>();
    static ArrayList<Booking> allbooking = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        String input;
        do {
            System.out.println("\n==============================");
            System.out.println("  RIDE-HAILING BOOKING SYSTEM");
            System.out.println("==============================");
            System.out.println("A. View All Bookings");
            System.out.println("B. Book a Ride");
            System.out.println("C. Delete a Booking");
            System.out.println("D. Generate Booking Report");
            System.out.println("E. Exit Application");
            System.out.print("Enter choice: ");
            input = sc.nextLine().toLowerCase();

            switch (input) {
                case "a" -> viewAllBookings();
                case "b" -> bookARide();
                case "c" -> deleteBooking();
                case "d" -> generateBookingReport();
                case "e" -> {
                    System.out.println("Thank you for using");
                    return;
                }
                default -> System.out.println("Invalid Input");
            }
        } while (!input.equals("e"));
    }

    static void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }

        System.out.println("\n#   Date        Time       Passenger   Pickup Location  Drop-off Location  Distance(km) Fare(PHP)");
        System.out.println("----------------------------------------------------------------------------------------------");

        int i = 1;

        // loops inside the the array list
        for (Booking b : bookings) {
            System.out.printf("%-3d %s\n", i++, b.toString());
        }
    }

    // method when user books a ride
    static void bookARide() {
        try {

            // asks for name
            System.out.print("Enter Passenger Name: ");
            String name = sc.nextLine().trim();

            // condition to check if name input is empty
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                return;
            }

            // input date pickup
            System.out.print("Enter Date (MM/DD/YYYY): ");
            String date = sc.nextLine().trim();

            // condition to check if date input is empty
            if (date.isEmpty()) {
                System.out.println("Date cannot be empty!");
                return;
            }

            // input time pickup
            System.out.print("Enter Time (e.g. 10:00 AM): ");
            String time = sc.nextLine().trim();

            // condition to check if ttime input is empty
            if (time.isEmpty()) {
                System.out.println("time cannot be empty!");
                return;
            }

            // input pickup location
            System.out.print("Enter Pick-up Location: ");
            String pickup = sc.nextLine().trim();

            // condition to check if pickup input is empty
            if (pickup.isEmpty()) {
                System.out.println("Pick-up location cannot be empty!");
                return;
            }

            // input drop off location
            System.out.print("Enter Drop-off Location: ");
            String dropoff = sc.nextLine().trim();

            // condition to check if dropoff input is empty
            if (dropoff.isEmpty()) {
                System.out.println("Drop-off cannot be empty!");
                return;
            }

            // input distance
            System.out.print("Enter Distance (in kilometers): ");
            double distance = Double.parseDouble(sc.nextLine());

            Booking booking = new Booking(name, date, time, pickup, dropoff, distance);
            allbooking.add(booking);
            bookings.add(booking);
            System.out.println("Booking successfully added!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid distance input! Please enter a number.");
        }
    }

    // method for deleting a booking
    static void deleteBooking() {

        // condition to check if booking list is empty
        if (bookings.isEmpty()) {
            System.out.println("No bookings to delete!");
            return;
        }

        viewAllBookings(); // calls viewallbooking method
        System.out.print("Enter booking number to delete: ");
        try {
            int index = Integer.parseInt(sc.nextLine());

            // check if user input is a valid choice
            if (index < 1 || index > bookings.size()) {
                System.out.println("Invalid booking number!");
                return;
            }
            bookings.remove(index - 1);
            System.out.println("Booking deleted successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a valid number!");
        }
    }


    // method for generating booking report
    static void generateBookingReport() {

        // check if allbooking list is empty
        if (allbooking.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }

        double totalDistance = 0;
        double totalFare = 0;

        // table format
        System.out.println("\nREPORT");
        System.out.println("#   Date        Time       Passenger   Distance(km)   Fare(PHP)");
        System.out.println("-------------------------------------------------------------");

        int i = 1; // defy counting for #

        // loops inside booking array to print out the list
        for (Booking b : allbooking) {
            System.out.printf("%-3d %-10s %-10s %-10s %-13.1f %-10.2f\n", 
                    i++, b.date, b.time, b.passengerName, b.distance, b.fare);
            totalDistance += b.distance; // adds to the total of all distance
            totalFare += b.fare; // adds to the total amount of all booking
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf("Total Number of Bookings: %d\n", bookings.size());
        System.out.printf("Total Distance: %.1f km\n", totalDistance);
        System.out.printf("Total Fare Collected: PHP %.2f\n", totalFare);

        // save report to text file
        saveReportToFile(totalDistance, totalFare);
    }

    // method for writing format in txt file
    static void saveReportToFile(double totalDistance, double totalFare) {

        // creates a database txt file named BookingReport.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter("BookingReport.txt"))) {
            writer.println("NU BALIWAG - RIDE-HAILING BOOKING REPORT");
            writer.println("=======================================");
            writer.println("#   Date        Time       Passenger   Distance(km)   Fare(PHP)");
            int i = 1;

            // uses a for loop to allocate the right value input
            for (Booking b : bookings) {
                writer.printf("%-3d %-10s %-10s %-10s %-13.1f %-10.2f\n",
                        i++, b.date, b.time, b.passengerName, b.distance, b.fare);
            }
            writer.println("=======================================");
            writer.printf("Total Bookings: %d\n", bookings.size());
            writer.printf("Total Distance: %.1f km\n", totalDistance);
            writer.printf("Total Fare: PHP %.2f\n", totalFare);
            System.out.println("Report saved as 'BookingReport.txt'");
        } catch (IOException e) {
            System.out.println("Error saving report: " + e.getMessage());
        }
    }
}
