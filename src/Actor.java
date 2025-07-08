import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Actor extends Person {
    private Map<String, boolean[]> availability;

    // ANSI color codes
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public Actor(String firstName, String lastName, String email, String phone) {
        super(firstName, lastName, email, phone);
        availability = new HashMap<>();
        initAvailability();
    }

    public Actor(String firstName, String lastName, String email, String phone, Map<String, boolean[]> availability) {
        super(firstName, lastName, email, phone);
        this.availability = new HashMap<>();
        if (availability != null) {
            this.availability.putAll(availability);
        } else {
            initAvailability();
        }
    }

    public Map<String, boolean[]> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, boolean[]> availability) {
        this.availability = availability;
    }

    private void initAvailability() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            availability.put(day, new boolean[4]); // For this demo, each day has 4 time slots
        }
    }

    public void setAvailability(String day, int[] slots, boolean isAvailable) {
        if (availability.containsKey(day)) {
            for (int slot : slots) {
                if (slot >= 1 && slot <= 4) { // Ensure that slots are within range
                    availability.get(day)[slot - 1] = isAvailable;
                }
            }
        }
    }

    @Override
    // Method to print the actor's availabilities
    public String toString() {
        StringBuilder availabilityString = new StringBuilder();
        Map<String, boolean[]> sortedAvailability = new TreeMap<>(this::compareDays);
        sortedAvailability.putAll(availability);

        for (Map.Entry<String, boolean[]> entry : sortedAvailability.entrySet()) {
            availabilityString.append(entry.getKey()).append(": ");
            boolean[] slots = entry.getValue();
            for (int i = 0; i < slots.length; i++) {
                if (slots[i]) {
                    availabilityString.append(ANSI_GREEN).append("Available ").append(ANSI_RESET);
                } else {
                    availabilityString.append(ANSI_RED).append("Unavailable ").append(ANSI_RESET);
                }
                availabilityString.append(i == 0 ? "9-12" : i == 1 ? "12-15" : i == 2 ? "15-18" : "18-21").append(" | ");
            }
            availabilityString.append("\n");
        }
        return super.toString() + "\nAvailability: \n" + availabilityString.toString();
    }

    // Method to sort the weekdays
    private int compareDays(String day1, String day2) {
        String[] orderedDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        int index1 = -1;
        int index2 = -1;
        for (int i = 0; i < orderedDays.length; i++) {
            if (orderedDays[i].equals(day1)) {
                index1 = i;
            }
            if (orderedDays[i].equals(day2)) {
                index2 = i;
            }
        }
        return Integer.compare(index1, index2);
    }
}
