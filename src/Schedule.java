import java.util.ArrayList;
import java.util.List;

public class Schedule {
    private List<Actor> actors = new ArrayList<>();
    private final LoadSave loadSave;

    // ANSI color codes
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";

    public Schedule() {
        loadSave = new LoadSave();
    }

    public void addActor(Actor actor) {
        actors.add(actor);
    }

    public List<Actor> getActors() { return actors; }

    public void setActors(List<Actor> actors) { this.actors = actors; }

    public void saveToFile() { loadSave.saveToFile(actors); }

    public void loadFromFile() { actors = loadSave.loadFromFile(); }


    public void generateSchedule() {
        System.out.printf("Generating schedule for %d actors...\n", actors.size());
    }

    public void printSchedule() {
    }

}
