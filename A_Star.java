import java.util.*;
public class A_Star {
    public static class Place {
        private String name;
        private double housingPrice;
        private double costOfLiving;
        public Place(String name, double housingPrice, double costOfLiving) {
            this.name = name;
            this.housingPrice = housingPrice;
            this.costOfLiving = costOfLiving;
        }
        public String getName() {
            return name;
        }
        public double getHousingPrice() {
            return housingPrice;
        }
        public double getCostOfLiving() {
            return costOfLiving;
        }
        public String toString() {
            return name;
        }
    }
    public static class UserPreferences {
        private double maxHousingPrice;
        private double maxCostOfLiving;
        public UserPreferences(double maxHousingPrice, double maxCostOfLiving) {
            this.maxHousingPrice = maxHousingPrice;
            this.maxCostOfLiving = maxCostOfLiving;
        }
        public double getMaxHousingPrice() {
            return maxHousingPrice;
        }
        public double getMaxCostOfLiving() {
            return maxCostOfLiving;
        }
    }
    public static class AIFinder {
        private Map<Place, Double> gValues = new HashMap<>();  // Declare gValues as an instance variable
        private List<Place> places;  // Store the list of places as an instance variable
        private AIFinder(List<Place> places) {
            this.places = places;
        }
        public List<Place> findAffordablePlaces(List<Place> places, UserPreferences preferences) {
            PriorityQueue<Place> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(this::computeF));
            Set<Place> visited = new HashSet<>();
            for (Place place : places) {
                gValues.put(place, Double.POSITIVE_INFINITY);
            }
            Place startPlace = places.get(0);
            gValues.put(startPlace, 0.0);
            priorityQueue.add(startPlace);
            while (!priorityQueue.isEmpty()) {
                Place currentPlace = priorityQueue.poll();
                if (currentPlace.getHousingPrice() <= preferences.getMaxHousingPrice()
                        && currentPlace.getCostOfLiving() <= preferences.getMaxCostOfLiving()) {
                    return Collections.singletonList(currentPlace);
                }
                visited.add(currentPlace);
                List<Place> neighbors = generateNeighbors(currentPlace, places.size());
                for (Place neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        double currentGValue = gValues.getOrDefault(currentPlace, Double.POSITIVE_INFINITY);
                        double neighborGValue = gValues.getOrDefault(neighbor, Double.POSITIVE_INFINITY);
                        double tentativeG = currentGValue + 1; // Distance to neighbors is assumed to be 1
                        if (tentativeG < neighborGValue) {
                            gValues.put(neighbor, tentativeG);
                            priorityQueue.add(neighbor);
                        }
                    }
                }
            }
            return Collections.emptyList();
        }
        private List<Place> generateNeighbors(Place place, int numPlaces) {
            List<Place> neighbors = new ArrayList<>();

            for (int i = 0; i < numPlaces; i++) {
                if (!place.getName().equals("Neighbor" + (i + 1))) {
                    double realisticHousingPrice = getRealisticHousingPrice();
                    double realisticCostOfLiving = getRealisticCostOfLiving();

                    neighbors.add(new Place("Neighbor" + (i + 1), realisticHousingPrice, realisticCostOfLiving));
                }
            }

            return neighbors;
        }
        private double getRealisticHousingPrice() {
            return Math.random() * 100000 + 80000; // Example: Random value between 80000 and 180000
        }
        private double getRealisticCostOfLiving() {
            return Math.random() * 20000 + 30000; // Example: Random value between 30000 and 50000
        }
        private double computeF(Place place) {
            return gValues.get(place) + computeHeuristic(place);
        }
        private double computeHeuristic(Place place) {
            return place.getHousingPrice() + place.getCostOfLiving();
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of places: ");
        int numPlaces = scanner.nextInt();
        List<Place> places = new ArrayList<>();
        for (int i = 1; i <= numPlaces; i++) {
            System.out.println("Enter details for Place " + i + ":");
            System.out.print("Name: ");
            String name = scanner.next();
            System.out.print("Housing Price: ");
            double housingPrice = scanner.nextDouble();
            System.out.print("Cost of Living: ");
            double costOfLiving = scanner.nextDouble();
            places.add(new Place(name, housingPrice, costOfLiving));
        }
        System.out.print("Enter your maximum housing price: ");
        double maxHousingPrice = scanner.nextDouble();
        System.out.print("Enter your maximum cost of living: ");
        double maxCostOfLiving = scanner.nextDouble();
        UserPreferences preferences = new UserPreferences(maxHousingPrice, maxCostOfLiving);
        AIFinder aiFinder = new AIFinder(places);
        List<Place> affordablePlaces = aiFinder.findAffordablePlaces(places, preferences);
        System.out.println("\nAffordable Places:");
        for (Place place : affordablePlaces) {
            System.out.println(place.getName() + " - Housing Price: " + place.getHousingPrice()
                    + ", Cost of Living: " + place.getCostOfLiving());
        }
    }
}