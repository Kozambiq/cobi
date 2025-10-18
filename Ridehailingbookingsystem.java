import java.io.*;
import java.util.*;

// created booking object
class Booking {
    String passengerName;
    String date;
    String time;
    String pickupLocation;
    String dropoffLocation;
    double distance;
    double fare;

    // assigned object variables
    public Booking(String passengerName, String date, String time, String pickupLocation, String dropoffLocation, double distance) {
        this.passengerName = passengerName;
        this.date = date;
        this.time = time;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.distance = distance;
        this.fare = calculateFare(distance);
    }

    // method that handles distance calculations
    private double calculateFare(double distance) {
        if (distance < 1) {
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

public class RideHailingBookingSystem {
    static ArrayList<Booking> bookings = new ArrayList<>(); // created array list with variable bookings
    static ArrayList<Booking> allbooking = new ArrayList<>(); // created array list with variable allbooking
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        String input;

        do {

            // print out menu
            System.out.println("\n==============================");
            System.out.println("  RIDE-HAILING BOOKING SYSTEM");
            System.out.println("==============================");
            System.out.println("a. View All Bookings");
            System.out.println("b. Book a Ride");
            System.out.println("c. Delete a Booking");
            System.out.println("d. Generate Booking Report");
            System.out.println("e. Exit Application");
            System.out.print("Enter choice: ");
            input = sc.nextLine().toLowerCase();

            // validation of user input
            switch (input) {
                case "a" -> viewAllBookings();
                case "b" -> bookARide();
                case "c" -> deleteBooking();
                case "d" -> generateBookingReport();
                case "e" -> {
                    System.out.println("Thank you for using!");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option! Please choose again.");
            }
        } while (!input.equals("e"));
    }

    // method for viewing all bookings
    static void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }

        // prints table format
        System.out.println("\n#   Date        Time       Passenger   Pickup Location  Drop-off Location  Distance(km) Fare(PHP)");
        System.out.println("----------------------------------------------------------------------------------------------");

        int i = 1; // handles # counting

        // loops inside the booking array list
        for (Booking b : bookings) {
            System.out.printf("%-3d %s\n", i++, b.toString());
        }
    }

    // method for booking a ride
    static void bookARide() {
        try {
            
            // asks for user name
            System.out.print("Enter Passenger Name: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty!");
                return;
            }

            // asks user for pick up date
            System.out.print("Enter Date (MM/DD/YYYY): ");
            String date = sc.nextLine().trim();

            if (date.isEmpty()) {
                System.out.println("Date cannot be empty!");
                return;
            }

            // asks user for time pickup
            System.out.print("Enter Time (e.g. 10:00 AM): ");
            String time = sc.nextLine().trim();

            if (time.isEmpty()) {
                System.out.println("Time cannot be empty!");
                return;
            }

            // asks for user pick up location
            System.out.print("Enter Pick-up Location: ");
            String pickup = sc.nextLine().trim();

            if (pickup.isEmpty()) {
                System.out.println("Pick_up cannot be empty!");
                return;
            }

            // asks user for drop-off location
            System.out.print("Enter Drop-off Location: ");
            String dropoff = sc.nextLine().trim();

            if (dropoff.isEmpty()) {
                System.out.println("Drop-off cannot be empty!");
                return;
            }

            // asks user for distance
            System.out.print("Enter Distance (km): ");
            double distance = Double.parseDouble(sc.nextLine());

            Booking booking = new Booking(name, date, time, pickup, dropoff, distance);
            allbooking.add(booking); // adds the given input to the array list
            bookings.add(booking); // adds the given input to the array lsit
            System.out.println("Booking successfully added!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid distance input! Please enter a number.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    // method for deleting a booking
    static void deleteBooking() {

        // condition to check if booking array is empty
        if (bookings.isEmpty()) {
            System.out.println("No bookings to delete!");
            return;
        }

        viewAllBookings(); // print out all booking

        System.out.print("Enter booking number to delete: ");
        try {
            int index = Integer.parseInt(sc.nextLine());
            
            // checks if booking number exists
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

    // method to generate booking report
    static void generateBookingReport() {
        if (allbooking.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }

        double totalDistance = 0;
        double totalFare = 0;

        // print table format
        System.out.println("\nREPORT");
        System.out.println("#   Date        Time       Passenger   Distance(km)   Fare(PHP)");
        System.out.println("-------------------------------------------------------------");

        int i = 1; // handles # counting

        // loops inside the booking list and prints out the listed objects
        for (Booking b : allbooking) {
            System.out.printf("%-3d %-10s %-10s %-10s %-13.1f %-10.2f\n", 
                    i++, b.date, b.time, b.passengerName, b.distance, b.fare);
            totalDistance += b.distance; // adds to the total distance
            totalFare += b.fare; // adds to the total fares
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf("Total Number of Bookings: %d\n", bookings.size());
        System.out.printf("Total Distance: %.1f km\n", totalDistance);
        System.out.printf("Total Fare Collected: PHP %.2f\n", totalFare);

        saveReportToFile(totalDistance, totalFare);
    }

    // method for database txt
    static void saveReportToFile(double totalDistance, double totalFare) {

        // writes this following format inside the BookingReport.txt
        try (PrintWriter writer = new PrintWriter(new FileWriter("BookingReport.txt"))) {
            writer.println("NU BALIWAG - RIDE-HAILING BOOKING REPORT");
            writer.println("=======================================");
            writer.println("#   Date        Time       Passenger   Distance(km)   Fare(PHP)");
            int i = 1;

            // loops inside the booking array list and fills out the asked objects
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
