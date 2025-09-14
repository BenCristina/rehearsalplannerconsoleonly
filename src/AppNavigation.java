import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Main user interface controller for the RehearsalPlanner application.
 * Provides a console-based menu system for managing actors and their schedules.
 * Handles all user input and validates data before processing.
 */
public class AppNavigation {
    // Core schedule management system
    private final Schedule schedule = new Schedule();

    // Input handler for user interactions
    private final Scanner scanner = new Scanner(System.in);

    // List of valid weekdays for input validation
    private final List<String> validDays = Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );

    /**
     * Initializes the application and starts the main menu loop.
     * Handles all user interactions through a numbered menu system.
     */
    protected AppNavigation() {
        // Display a welcome message
        System.out.println("\n+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Welcome to the Console Version of the RehearsalPlanner!");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        // Main application loop
        while (true) {
            displayMainMenu();
            int choice = getValidIntInput(1, 8);

            switch (choice) {
                case 1: displayActorInformation(); break;
                case 2: addActor(); break;
                case 3: removeActor(); break;
                case 4: setActorAvailability(); break;
                case 5: generateSchedule(); break;
                case 6: detectSchedulingConflicts(); break;
                case 7: handleDataOperations(); break;
                case 8:
                    System.out.println("Exiting app...");
                    scanner.close();
                    return;
            }
        }
    }

    /**
     * Displays information for all actors in the system.
     * Shows formatted details including availability schedules.
     */
    private void displayActorInformation() {
        if (schedule.getActors().isEmpty()) {
            System.out.println("No actors added yet.");
            return;
        }
        for (Actor actor : schedule.getActors()) {
            System.out.println("\n" + actor);
        }
    }

    /**
     * Handles the process of adding a new actor to the system.
     * Collects and validates personal information before creation.
     */
    private void addActor() {
        System.out.print("Enter Actor First Name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter Actor Last Name: ");
        String lastName = scanner.nextLine().trim();
        System.out.print("Enter Actor Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Actor Phone: ");
        String phone = scanner.nextLine().trim();

        Actor actor = new Actor(firstName, lastName, email, phone);
        schedule.addActor(actor);
        System.out.printf("Actor added successfully: %s%n", actor);
    }

    /**
     * Removes an actor from the system.
     * Displays a numbered list for selection if actors exist.
     */
    private void removeActor() {
        if (schedule.getActors().isEmpty()) {
            System.out.println("No actors added yet.");
            return;
        }

        // Display actor selection menu
        System.out.println("\nSelect the actor to remove:");
        for (int i = 0; i < schedule.getActors().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, schedule.getActors().get(i));
        }

        // Get actor selection
        System.out.print("Enter actor number to remove: ");
        int index = getValidIntInput(1, schedule.getActors().size()) -1;
        Actor actorToRemove = schedule.getActors().get(index);

        // Confirm deletion
        System.out.printf("Are you sure you want to remove actor %s? (Y/N): ", actorToRemove);
        Boolean confirm = getYesNoInput();
        if (confirm == null || !confirm) {
            System.out.println("Deletion cancelled.");
            return;
        }

        schedule.removeActor(actorToRemove);
        System.out.printf("Actor removed successfully: %s%n", actorToRemove);
    }

    /**
     * Manages the process of setting actor availability.
     * Allows selection of days and time slots with validation.
     */
    private void setActorAvailability() {
        if (schedule.getActors().isEmpty()) {
            System.out.println("No actors added yet.");
            return;
        }

        // Display actor selection menu
        System.out.println("\nSelect the actor to set their availability:");
        for (int i = 0; i < schedule.getActors().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, schedule.getActors().get(i));
        }

        // Get actor selection and process availability
        System.out.print("Enter actor number: ");
        int actorIndex = getValidIntInput(1, schedule.getActors().size()) - 1;
        Actor selectedActor = schedule.getActors().get(actorIndex);

        // Loop for entering availability for different days
        while (true) {
            System.out.print("\nEnter Day (e.g. Monday) or 'done' to finish: ");
            String day = scanner.nextLine().trim();

            if (day.equalsIgnoreCase("done")) {
                break;
            }

            // Normalize day input
            day = normalizeDay(day);
            if (day == null) {
                System.out.println("\nInvalid day. Please enter a valid weekday (Monday-Sunday).");
                continue;
            }

            // Get and validate time slots
            System.out.println("Available time slots for " + day + ":");
            System.out.println("'1' for 9-12 | '2' for 12-15 | '3' for 15-18 | '4' for 18-21");
            System.out.print("Enter slots (comma-separated, e.g. 1,3 or 1,2,4): ");

            int[] slots = getValidSlots();
            if (slots == null) {
                continue;
            }

            // Get availability status
            System.out.print("Is the actor available for the selected slots? (Y/N): ");
            Boolean isAvailable = getYesNoInput();
            if (isAvailable == null) {
                continue;
            }

            selectedActor.setAvailability(day, slots, isAvailable);
            System.out.printf("\nAvailability set successfully for %s%n", day);
        }
    }

    /**
     * Normalizes day input to the proper case format and validates against valid days.
     * @param day Input day string
     * @return Normalized day name or null if invalid
     */
    private String normalizeDay(String day) {
        if (day == null || day.isEmpty()) return null;

        // Capitalize the first letter and lowercase the rest
        day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();

        return validDays.contains(day) ? day : null;
    }

    /**
     * Parses and validates time slot input.
     * @return Array of valid slot numbers or null if invalid
     */
    private int[] getValidSlots() {
        try {
            String[] slotStrings = scanner.nextLine().trim().split(",");
            int[] slots = new int[slotStrings.length];

            for (int i = 0; i < slotStrings.length; i++) {
                slots[i] = Integer.parseInt(slotStrings[i].trim());
                if (slots[i] < 1 || slots[i] > 4) {
                    System.out.println("\nInvalid slot number: " + slots[i] + ". Please use numbers between 1 and 4.");
                    return null;
                }
            }
            return slots;
        } catch (NumberFormatException e) {
            System.out.println("\nInvalid input. Please enter numbers separated by commas.");
            return null;
        }
    }

    /**
     * Validates yes/no input from the user.
     * @return Boolean representation of yes/no or null if invalid
     */
    private Boolean getYesNoInput() {
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("y") || input.equals("yes")) {
            return true;
        } else if (input.equals("n") || input.equals("no")) {
            return false;
        } else {
            System.out.println("\nInvalid input. Please enter 'Y' or 'N'.");
            return null;
        }
    }

    /**
     * Validates numeric input within a specified range.
     * Continues prompting until valid input is received.
     * @param min Minimum valid value
     * @param max Maximum valid value
     * @return Valid integer within range
     */
    private int getValidIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("\nPlease enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("\nInvalid input. Please enter a number between %d and %d: ", min, max);
            }
        }
    }

    /**
     * Triggers schedule generation process
     */
    private void generateSchedule() {
        schedule.generateSchedule();
    }

    /**
     * Placeholder for future scheduling conflict detection feature
     */
    private void detectSchedulingConflicts() {
        // Implementation for detecting scheduling conflicts
        System.out.println("\nFeature not implemented yet.");
    }

    /**
     * Manages data persistence operations through a submenu
     */
    private void handleDataOperations() {
        System.out.println("\nData Operations:");
        System.out.println("1. Save to File");
        System.out.println("2. Load from File");
        System.out.println("3. Return to Main Menu");
        System.out.print("\nEnter your choice: ");

        int choice = getValidIntInput(1, 3);
        switch (choice) {
            case 1: schedule.saveToFile(); break;
            case 2: schedule.loadFromFile(); break;
            case 3: break;
        }
    }

    /**
     * Displays the main menu options
     */
    private void displayMainMenu() {
        System.out.println("\n1. Display Actor Information");
        System.out.println("2. Add Actor");
        System.out.println("3. Remove Actor");
        System.out.println("4. Set Availability");
        System.out.println("5. Generate Schedule");
        System.out.println("6. Detect Scheduling Conflicts");
        System.out.println("7. Load and Save Data");
        System.out.println("8. Exit");
        System.out.print("\nEnter your choice: ");
    }
}
