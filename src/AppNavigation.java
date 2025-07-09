import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AppNavigation {
    private final Schedule schedule = new Schedule();
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> validDays = Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );

    protected AppNavigation() {
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Welcome to the Console Version of the RehearsalPlanner!");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++\n");
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Display Actor Information");
            System.out.println("2. Add Actor");
            System.out.println("3. Remove Actor");
            System.out.println("4. Set Availability");
            System.out.println("5. Generate Schedule");
            System.out.println("6. Detect Scheduling Conflicts");
            System.out.println("7. Load and Save Data");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

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

    private void displayActorInformation() {
        if (schedule.getActors().isEmpty()) {
            System.out.println("No actors added yet.");
            return;
        }
        for (Actor actor : schedule.getActors()) {
            System.out.println("\n" + actor);
        }
    }

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

    private void removeActor() {
        if (schedule.getActors().isEmpty()) {
            System.out.println("No actors added yet.");
            return;
        }
        schedule.removeActor(schedule.getActors().get(getValidIntInput(1, schedule.getActors().size()) - 1));
    }

    private void setActorAvailability() {
        if (schedule.getActors().isEmpty()) {
            System.out.println("No actors added yet.");
            return;
        }

        System.out.println("\nSelect the actor to set their availability:");
        for (int i = 0; i < schedule.getActors().size(); i++) {
            System.out.printf("%d. %s%n", i + 1, schedule.getActors().get(i));
        }

        System.out.print("Enter actor number: ");
        int actorIndex = getValidIntInput(1, schedule.getActors().size()) - 1;
        Actor selectedActor = schedule.getActors().get(actorIndex);

        while (true) {
            System.out.print("\nEnter Day (e.g. Monday) or 'done' to finish: ");
            String day = scanner.nextLine().trim();

            if (day.equalsIgnoreCase("done")) {
                break;
            }

            // Normalize day input
            day = normalizeDay(day);
            if (day == null) {
                System.out.println("Invalid day. Please enter a valid weekday (Monday-Sunday).");
                continue;
            }

            System.out.println("Available time slots for " + day + ":");
            System.out.println("'1' for 9-12 | '2' for 12-15 | '3' for 15-18 | '4' for 18-21");
            System.out.print("Enter slots (comma-separated, e.g. 1,3 or 1,2,4): ");

            int[] slots = getValidSlots();
            if (slots == null) {
                continue;
            }

            System.out.print("Is the actor available for the selected slots? (Y/N): ");
            Boolean isAvailable = getYesNoInput();
            if (isAvailable == null) {
                continue;
            }

            selectedActor.setAvailability(day, slots, isAvailable);
            System.out.printf("Availability set successfully for %s%n", day);
        }
    }

    private String normalizeDay(String day) {
        if (day == null || day.isEmpty()) return null;

        // Capitalize first letter and lowercase the rest
        day = day.substring(0, 1).toUpperCase() + day.substring(1).toLowerCase();

        return validDays.contains(day) ? day : null;
    }

    private int[] getValidSlots() {
        try {
            String[] slotStrings = scanner.nextLine().trim().split(",");
            int[] slots = new int[slotStrings.length];

            for (int i = 0; i < slotStrings.length; i++) {
                slots[i] = Integer.parseInt(slotStrings[i].trim());
                if (slots[i] < 1 || slots[i] > 4) {
                    System.out.println("Invalid slot number: " + slots[i] + ". Please use numbers between 1 and 4.");
                    return null;
                }
            }
            return slots;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numbers separated by commas.");
            return null;
        }
    }

    private Boolean getYesNoInput() {
        String input = scanner.nextLine().trim().toLowerCase();
        if (input.equals("y") || input.equals("yes")) {
            return true;
        } else if (input.equals("n") || input.equals("no")) {
            return false;
        } else {
            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            return null;
        }
    }

    private int getValidIntInput(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine().trim();
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (NumberFormatException e) {
                System.out.printf("Invalid input. Please enter a number between %d and %d: ", min, max);
            }
        }
    }

    private void generateSchedule() {
        // Implementation for generating schedule
        // System.out.println("Feature not implemented yet.");
        schedule.generateSchedule();
    }

    private void detectSchedulingConflicts() {
        // Implementation for detecting scheduling conflicts
        System.out.println("Feature not implemented yet.");
    }

    private void handleDataOperations() {
        // Implementation for loading and saving data
        System.out.println("Data Operations:");
        System.out.println("1. Save to File");
        System.out.println("2. Load from File");
        System.out.println("3. Return to Main Menu");

        int choice = getValidIntInput(1, 3);
        switch (choice) {
            case 1: schedule.saveToFile(); break;
            case 2: schedule.loadFromFile(); break;
            case 3: break;
        }
    }
}