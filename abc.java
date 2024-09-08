import java.util.*;
public class abc {
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
        public List<Place> findAffordablePlaces(List<Place> places, UserPreferences preferences) {
            PriorityQueue<Place> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(this::computeHeuristic));
            Set<Place> visited = new HashSet<>();
            priorityQueue.addAll(places);
            while (!priorityQueue.isEmpty()) {
                Place currentPlace = priorityQueue.poll();
               if (currentPlace.getHousingPrice() <= preferences.getMaxHousingPrice()
                        && currentPlace.getCostOfLiving() <= preferences.getMaxCostOfLiving()) {
                    return Collections.singletonList(currentPlace);
                }
                List<Place> neighbors = generateNeighbors(currentPlace);
                for (Place neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        priorityQueue.add(neighbor);
                    }
                }
            }
            return Collections.emptyList();
        }
        private List<Place> generateNeighbors(Place place) {
            List<Place> neighbors = new ArrayList<>();
            neighbors.add(new Place("Neighbor1", 130000, 48000));
            neighbors.add(new Place("Neighbor2", 110000, 42000));
            return neighbors;
        }
        private double computeHeuristic(Place place) {
            return place.getHousingPrice() + place.getCostOfLiving();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of options available with you : ");
        int n=sc.nextInt();
        List<Place> places = new ArrayList<>();
        System.out.println("Enter information for each place:");
        for (int i = 1; i <= n; i++) {
            System.out.println("Place " + i + ":");
            System.out.print("Name: ");
            String name = sc.next();
            System.out.print("Housing Price: ");
            double housingPrice = sc.nextDouble();
            System.out.print("Cost of Living: ");
            double costOfLiving = sc.nextDouble();
            sc.nextLine();
            places.add(new Place(name, housingPrice, costOfLiving));
        }
        System.out.print("Enter your maximum housing price: ");
        double maxHousingPrice = sc.nextDouble();
        System.out.print("Enter your maximum cost of living: ");
        double maxCostOfLiving = sc.nextDouble();
        UserPreferences preferences = new UserPreferences(maxHousingPrice, maxCostOfLiving);
        AIFinder aiFinder = new AIFinder();
        List<Place> affordablePlaces = aiFinder.findAffordablePlaces(places, preferences);
        System.out.println("Affordable Places:");
        for (Place place : affordablePlaces) {
            System.out.println(place.getName() + " - Housing Price: " + place.getHousingPrice()
                    + ", Cost of Living: " + place.getCostOfLiving());
        }
    }
}