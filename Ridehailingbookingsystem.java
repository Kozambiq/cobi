import java.io.*;
import java.util.*;

class Booking {
    String passengerName;
    String date;
    String time;
    String pickupLocation;
    String dropoffLocation;
    double distance;
    double fare;

    //
    public Booking(String passengerName, String date, String time, String pickupLocation, String dropoffLocation, double distance) {
        this.passengerName = passengerName;
        this.date = date;
        this.time = time;
        this.pickupLocation = pickupLocation;
        this.dropoffLocation = dropoffLocation;
        this.distance = distance;
        this.fare = calculateFare(distance);
    }

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

public class RideHailingBookingSystem {
    static ArrayList<Booking> bookings = new ArrayList<>();
    static ArrayList<Booking> allbooking = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        

        do {
            System.out.println("\n==============================");
            System.out.println("  RIDE-HAILING BOOKING SYSTEM");
            System.out.println("==============================");
            System.out.println("a. View All Bookings");
            System.out.println("b. Book a Ride");
            System.out.println("c. Delete a Booking");
            System.out.println("d. Generate Booking Report");
            System.out.println("e. Exit Application");
            System.out.print("Enter choice: ");
            String input = sc.nextLine().toLowerCase();

            switch (input) {
                case "a" -> viewAllBookings();
                case "b" -> bookARide();
                case "c" -> deleteBooking();
                case "d" -> generateBookingReport();
                case "e" -> {
                    exitApplication();
                    return;
                }
                default -> System.out.println("Invalid option! Please choose again.");
            }
        } while (true);
    }

    static void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }

        System.out.println("\n#   Date        Time       Passenger   Pickup Location  Drop-off Location  Distance(km) Fare(PHP)");
        System.out.println("----------------------------------------------------------------------------------------------");

        int i = 1;
        for (Booking b : bookings) {
            System.out.printf("%-3d %s\n", i++, b.toString());
        }
    }

    static void bookARide() {
        try {
            System.out.print("Enter Passenger Name: ");
            String name = sc.nextLine().trim();
            System.out.print("Enter Date (MM/DD/YYYY): ");
            String date = sc.nextLine().trim();
            System.out.print("Enter Time (e.g. 10:00 AM): ");
            String time = sc.nextLine().trim();
            System.out.print("Enter Pick-up Location: ");
            String pickup = sc.nextLine().trim();
            System.out.print("Enter Drop-off Location: ");
            String dropoff = sc.nextLine().trim();
            System.out.print("Enter Distance (km): ");
            double distance = Double.parseDouble(sc.nextLine());

            if (name.isEmpty() || date.isEmpty() || time.isEmpty() || pickup.isEmpty() || dropoff.isEmpty()) {
                System.out.println("Error: All fields must be filled!");
                return;
            }

            Booking booking = new Booking(name, date, time, pickup, dropoff, distance);
            allbooking.add(booking);
            bookings.add(booking);
            System.out.println("Booking successfully added!");
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid distance input! Please enter a number.");
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    static void deleteBooking() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings to delete!");
            return;
        }

        viewAllBookings();
        System.out.print("Enter booking number to delete: ");
        try {
            int index = Integer.parseInt(sc.nextLine());
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

    static void generateBookingReport() {
        if (allbooking.isEmpty()) {
            System.out.println("No bookings found!");
            return;
        }

        double totalDistance = 0;
        double totalFare = 0;

        System.out.println("\nREPORT");
        System.out.println("#   Date        Time       Passenger   Distance(km)   Fare(PHP)");
        System.out.println("-------------------------------------------------------------");

        int i = 1;
        for (Booking b : allbooking) {
            System.out.printf("%-3d %-10s %-10s %-10s %-13.1f %-10.2f\n", 
                    i++, b.date, b.time, b.passengerName, b.distance, b.fare);
            totalDistance += b.distance;
            totalFare += b.fare;
        }

        System.out.println("-------------------------------------------------------------");
        System.out.printf("Total Number of Bookings: %d\n", bookings.size());
        System.out.printf("Total Distance: %.1f km\n", totalDistance);
        System.out.printf("Total Fare Collected: PHP %.2f\n", totalFare);

        // Optional: Save report to text file
        saveReportToFile(totalDistance, totalFare);
    }

    static void saveReportToFile(double totalDistance, double totalFare) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("BookingReport.txt"))) {
            writer.println("NU BALIWAG - RIDE-HAILING BOOKING REPORT");
            writer.println("=======================================");
            writer.println("#   Date        Time       Passenger   Distance(km)   Fare(PHP)");
            int i = 1;
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

    static void exitApplication() {
        System.out.println("Thank you!");
        System.exit(0);
    }
}
