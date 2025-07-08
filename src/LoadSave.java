import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadSave {
    private static final String FILE_PATH = "RehearsalPlanner.txt";

    public LoadSave() { }

    public void saveToFile(List<Actor> actors) {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            System.out.println("File exists at: " + file.getAbsolutePath());
            System.out.println("File size: " + file.length() + " bytes");
        }

        if (actors == null || actors.isEmpty()) {
            System.out.println("No actors to save.");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("REHEARSAL_PLANNER_DATA\n");
            for (Actor actor : actors) {
                writer.write(actorToString(actor));
            }
            writer.write("END_FILE");
            System.out.println("Data saved successfully to " + FILE_PATH);
        } catch (IOException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }

    public List<Actor> loadFromFile() {
        List<Actor> actors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

            if (lines.isEmpty() || !lines.get(0).equals("REHEARSAL_PLANNER_DATA")) {
                System.out.println("File is empty or is not a valid RehearsalPlanner file.");
                return actors;
            }

            int currentLine = 1;
            while (currentLine < lines.size() && !lines.get(currentLine).equals("END_FILE")) {
                Actor actor = parseActorFromLines(lines.toArray(new String[0]), currentLine);
                actors.add(actor);
                // Skip to next actor
                while (!lines.get(currentLine).equals("END_ACTOR")) {
                    currentLine++;
                }
                currentLine++;
            }
            System.out.println("Data loaded successfully from " + FILE_PATH);
        } catch (FileNotFoundException e) {
            System.out.println("No saved data found. Starting with empty schedule");
        } catch (IOException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
        return actors;
    }

    private String actorToString(Actor actor) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s,%s,%s,%s\n", actor.getFirstName(), actor.getLastName(), actor.getEmail(), actor.getPhone()));

        // Save availability data
        Map<String, boolean[]> availability = actor.getAvailability();
        for (Map.Entry<String, boolean[]> entry : availability.entrySet()) {
            sb.append(entry.getKey()).append(":");
            boolean[] slots = entry.getValue();
            for (boolean slot : slots) {
                sb.append(slot ? "1" : "0");
            }
            sb.append("\n");
        }
        sb.append("END_ACTOR\n");
        return sb.toString();
    }

    private Actor parseActorFromLines(String[] lines, int startIndex) {
        String[] personalInfo = lines[startIndex].split(",");
        String firstName = personalInfo[0];
        String lastName = personalInfo[1];
        String email = personalInfo[2];
        String phone = personalInfo[3];

        Map<String, boolean[]> availability = new HashMap<>();
        int currentLine = startIndex + 1;

        while(!lines[currentLine].equals("END_ACTOR")) {
            String[] dayData = lines[currentLine].split(":");
            String day = dayData[0];
            boolean[] slots = new boolean[4];
            for (int i = 0; i < 4; i++) {
                slots[i] = dayData[1].charAt(i) == '1';
            }
            availability.put(day, slots);
            currentLine++;
        }
        return new Actor(firstName, lastName, email, phone, availability);
    }
}
