import java.util.Scanner;

// Class to represent a location with coordinates
class Location {
    private double latitude;
    private double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

// Class to represent points of interest (POIs)
class PointOfInterest {
    private String name;
    private Location location;

    public PointOfInterest(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    // Getters
    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}

// Class to represent the AR navigation system
public class ARNavigationSystem {
    private PointOfInterest[] pointsOfInterest;

    public ARNavigationSystem() {
        // Initialize points of interest
        this.pointsOfInterest = new PointOfInterest[] {
            new PointOfInterest("Coffee Shop", new Location(42.3611, -71.0706)),
            new PointOfInterest("Pharmacy", new Location(42.3592, -71.0621)),
            // Add more points of interest as needed
        };
    }

    // Method to find the nearest point of interest to a given location
    public PointOfInterest findNearestPOI(Location location) {
        PointOfInterest nearestPOI = null;
        double minDistance = Double.MAX_VALUE;
        for (PointOfInterest poi : pointsOfInterest) {
            double distance = calculateDistance(location, poi.getLocation());
            if (distance < minDistance) {
                minDistance = distance;
                nearestPOI = poi;
            }
        }
        return nearestPOI;
    }

    // Method to calculate distance between two locations (using Haversine formula)
    private double calculateDistance(Location location1, Location location2) {
        double lat1 = Math.toRadians(location1.getLatitude());
        double lon1 = Math.toRadians(location1.getLongitude());
        double lat2 = Math.toRadians(location2.getLatitude());
        double lon2 = Math.toRadians(location2.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;

        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // Radius of the Earth in kilometers
        double radius = 6371;

        return radius * c;
    }

    public static void main(String[] args) {
        ARNavigationSystem navigationSystem = new ARNavigationSystem();
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter their current location
        System.out.print("Enter your current latitude: ");
        double latitude = scanner.nextDouble();
        System.out.print("Enter your current longitude: ");
        double longitude = scanner.nextDouble();
        Location currentLocation = new Location(latitude, longitude);

        // Find the nearest point of interest
        PointOfInterest nearestPOI = navigationSystem.findNearestPOI(currentLocation);
        if (nearestPOI != null) {
            System.out.println("Nearest Point of Interest: " + nearestPOI.getName());
        } else {
            System.out.println("No points of interest found.");
        }

        scanner.close();
    }
}
