import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {
    private List<Actor> actors = new ArrayList<>();
    private final LoadSave loadSave;
    private record ScheduleSlot(String timeSlot, List<Actor> actors) { }

    // ANSI color codes
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_RESET = "\u001B[0m";

    public Schedule() {
        loadSave = new LoadSave();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public void removeActor(Actor actor) {
        actors.remove(actor);
    }

    public List<Actor> getActors() { return actors; }

    public void setActors(List<Actor> actors) { this.actors = actors; }

    public void saveToFile() { loadSave.saveToFile(actors); }

    public void loadFromFile() { actors = loadSave.loadFromFile(); }


    public void generateSchedule() {
        System.out.printf("Generating schedule for %d actors...\n", actors.size());
        if (actors.isEmpty()) {
            System.out.println("No actors available to create a schedule for. Please add actors before generating a schedule.");
        }

        Map<String, List<ScheduleSlot>> weeklySchedule = new HashMap<>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        // Initialize the weekly schedule
        for (String day : days) {
            weeklySchedule.put(day, new ArrayList<>());
        }

        // Find available actors for each day and time slot
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

    private String getTimeSlotString(int slot) {
        return switch(slot) {
            case 0 -> "9-12";
            case 1 -> "12-15";
            case 2 -> "15-18";
            case 3 -> "18-21";
            default -> "Invalid Time Slot";
        };
    }


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
                String color = switch (slot.actors().size()) {
                    case 0 -> ANSI_RESET; // plain white for 0 actors
                    case 1 -> ANSI_RED; // red for 1 actor
                    case 2 -> ANSI_YELLOW; // yellow for 2 actors
                    default -> ANSI_GREEN; // green for 3 or more actors
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
