import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Core class for managing rehearsal schedules.
 * Handles actor roster management, schedule generation, and persistence operations.
 * Provides color-coded console output for schedule visualization.
 */
public class Schedule {
    // List of all actors in the system
    private List<Actor> actors = new ArrayList<>();

    // Handler for saving and loading schedule data
    private final LoadSave loadSave;

    /**
     * Record representing a time slot in the schedule with its available actors
     * @param timeSlot Time period (e.g., "9-12")
     * @param actors List of actors available during this slot
     */
    private record ScheduleSlot(String timeSlot, List<Actor> actors) { }

    // ANSI color codes for schedule visualization
    private static final String ANSI_GREEN = "\u001B[32m";  // 3+ actors available
    private static final String ANSI_YELLOW = "\u001B[33m"; // 2 actors available
    private static final String ANSI_RED = "\u001B[31m";    // 1 actor available or no sessions
    private static final String ANSI_RESET = "\u001B[0m";   // Reset color formatting

    /**
     * Creates a new Schedule instance with an empty actor roster
     * and initializes data persistence handler
     */
    public Schedule() {
        loadSave = new LoadSave();
    }

    /**
     * Adds a new actor to the roster
     * @param actor Actor to be added
     */
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    /**
     * Removes an actor from the roster
     * @param actor Actor to be removed
     */
    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    /**
     * @return List of all actors in the roster
     */
    public List<Actor> getActors() { return actors; }

    /**
     * Replaces the entire actor roster
     * @param actors New list of actors
     */
    public void setActors(List<Actor> actors) { this.actors = actors; }

    /**
     * Saves current actor roster and their availability to file
     */
    public void saveToFile() { loadSave.saveToFile(actors); }

    /**
     * Loads actor roster and availability from a file
     */
    public void loadFromFile() { actors = loadSave.loadFromFile(); }

    /**
     * Generates a weekly schedule based on actors' availability.
     * Creates a schedule showing all time slots where at least one actor is available.
     * Output is color-coded based on a number of available actors:
     * - Green: 3 or more actors
     * - Yellow: 2 actors
     * - Red: 1 actor
     * - Red text "No scheduled sessions": No actors available
     */
    public void generateSchedule() {
        System.out.printf("Generating schedule for %d actors...\n", actors.size());
        if (actors.isEmpty()) {
            System.out.println("No actors available to create a schedule for. Please add actors before generating a schedule.");
        }

        Map<String, List<ScheduleSlot>> weeklySchedule = new HashMap<>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        // Initialize the empty schedule for each day
        for (String day : days) {
            weeklySchedule.put(day, new ArrayList<>());
        }

        // Populate the schedule with available actors for each time slot
        for (String day : days) {
            for (int slot = 0; slot < 4; slot++) {
                List<Actor> availableActors = findAvailableActors(day, slot);
                if (!availableActors.isEmpty()) {
                    String timeSlot = getTimeSlotString(slot);
                    weeklySchedule.get(day).add(new ScheduleSlot(timeSlot, availableActors));
                }
            }
        }

        // Print generated schedule
        printSchedule(weeklySchedule);

    }

    /**
     * Finds all actors available for a specific day and time slot
     * @param day Day of the week
     * @param slot Time slot index (0-3)
     * @return List of available actors
     */
    private List<Actor> findAvailableActors(String day, int slot) {
        List<Actor> availableActors = new ArrayList<>();
        for (Actor actor : actors) {
            Map<String, boolean[]> availability = actor.getAvailability();
            if (availability.containsKey(day) && availability.get(day)[slot]) {
                availableActors.add(actor);
            }
        }
        return availableActors;
    }

    /**
     * Converts time slot index to human-readable time range
     * @param slot Time slot index (0-3)
     * @return String representation of time range
     */
    private String getTimeSlotString(int slot) {
        return switch(slot) {
            case 0 -> "9-12";
            case 1 -> "12-15";
            case 2 -> "15-18";
            case 3 -> "18-21";
            default -> "Invalid Time Slot";
        };
    }

    /**
     * Prints the generated schedule in a color-coded format
     * Colors indicate a number of available actors for each slot
     * @param weeklySchedule Map containing schedule data
     */
    public void printSchedule(Map<String, List<ScheduleSlot>> weeklySchedule) {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for (String day : days) {
            System.out.println("\n" + day + ":");
            List<ScheduleSlot> slots = weeklySchedule.get(day);
            if (slots.isEmpty()) {
                System.out.println(ANSI_RED + " No scheduled sessions" + ANSI_RESET);
                continue;
            }

            for (ScheduleSlot slot : slots) {
                // Color coding based on the number of available actors
                String color = switch (slot.actors().size()) {
                    case 0 -> ANSI_RESET; // Plain white for 0 actors
                    case 1 -> ANSI_RED; // Red for 1 actor
                    case 2 -> ANSI_YELLOW; // Yellow for 2 actors
                    default -> ANSI_GREEN; // Green for 3+ actors
                };
                System.out.println(color + " " + slot.timeSlot() + ": " + ANSI_RESET);
                System.out.println(color + "   Available Actors (" + slot.actors().size() + "): " + ANSI_RESET);
                for (Actor actor : slot.actors()) {
                    System.out.println(color + "     - " + actor.getFirstName() + " " + actor.getLastName() + ANSI_RESET);
                }
            }
        }
    }
}
