import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
/**
 * Represents an actor in the rehearsal planning system.
 * Extends Person class with additional functionality for managing rehearsal availability.
 * Each actor has a weekly schedule divided into four daily time slots (9-12, 12-15, 15-18, 18-21).
 */
public class Actor extends Person {
    // Maps each day to an array of four timer slots representing availability
    private Map<String, boolean[]> availability;

    // ANSI color codes for console output formatting
    private static final String ANSI_GREEN = "\u001B[32m"; // Used for available slots
    private static final String ANSI_RED = "\u001B[31m"; // Used for unavailable slots
    private static final String ANSI_ORANGE = "\u001B[33m"; // Used for warnings/alerts (currently not implemented)
    private static final String ANSI_RESET = "\u001B[0m"; // Resets text formatting

    /**
     * Creates a new Actor with an empty availability schedule.
     *
     * @param firstName Actor's first name
     * @param lastName Actor's last name
     * @param email Contact email address
     * @param phone Contact phone number
     */
    public Actor(String firstName, String lastName, String email, String phone) {
        super(firstName, lastName, email, phone);
        availability = new HashMap<>();
        initAvailability();
    }

    /**
     * Creates a new Actor with a predefined availability schedule.
     *
     * @param firstName Actor's first name
     * @param lastName Actor's last name
     * @param email Contact email address
     * @param phone Contact phone number
     * @param availability Predefined availability schedule, if null creates empty schedule
     */
    public Actor(String firstName, String lastName, String email, String phone, Map<String, boolean[]> availability) {
        super(firstName, lastName, email, phone);
        this.availability = new HashMap<>();
        if (availability != null) {
            this.availability.putAll(availability);
        } else {
            initAvailability();
        }
    }

    /**
     * @return Current availability schedule for all days
     */
    public Map<String, boolean[]> getAvailability() {
        return availability;
    }

    /**
     * Initializes an empty availability schedule for all weekdays.
     * Creates four time slots per day, all set to unavailable by default.
     */
    private void initAvailability() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            availability.put(day, new boolean[4]); // For this demo, each day has 4 time slots
        }
    }

    /**
     * Sets availability for specific time slots on a given day.
     *
     * @param day Day of the week
     * @param slots Array of time slot numbers (1-4)
     * @param isAvailable true if an actor is available, false otherwise
     */
    public void setAvailability(String day, int[] slots, boolean isAvailable) {
        if (availability.containsKey(day)) {
            for (int slot : slots) {
                if (slot >= 1 && slot <= 4) { // Ensure that slots are within range
                    availability.get(day)[slot - 1] = isAvailable;
                }
            }
        }
    }

    /**
     * Generates a formatted string representation of the actor's information
     * and their weekly availability schedule.
     * Uses color coding for better visibility:
     * - Green: Available
     * - Red: Unavailable
     * Time slots are shown as: 9-12 | 12-15 | 15-18 | 18-21
     *
     * @return Formatted string with actor's details and availability
     */
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
        return super.toString() + "\nAvailability: \n" + availabilityString;
    }

    /**
     * Compares two days for sorting purposes.
     * Used to ensure days are always displayed in the correct order (Monday to Sunday).
     *
     * @param day1 First day name
     * @param day2 Second day name
     * @return Negative if day1 comes before day2, positive if after, 0 if equal
     */
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
