import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

public class CompetitorList {
    // List to store competitors
    private List<Competitor> competitors;

    public CompetitorList() {
        this.competitors = new ArrayList<>();
    }
    // Method to add a competitor to the list
    public void addCompetitor(Competitor competitor) {
        this.competitors.add(competitor);
    }
    // Method to remove a competitor by their ID
    // Returns true if a competitor was removed
    public boolean removeCompetitorByNumber(int competitorId) {
        return competitors.removeIf(c -> c.getCompetitorId() == competitorId);
    }
    // Method to retrieve a competitor by their ID
    public Competitor getCompetitorByNumber(int competitorId) {
        return competitors.stream()
                .filter(c -> c.getCompetitorId() == competitorId)
                .findFirst()
                .orElse(null);
    }
    // Method to set the list of competitors
    public void setCompetitors(List<Competitor> readObject) {
    }
    // Method to get the next unique competitor ID
    public int getNextCompetitorId() {
        int i = 0;
        return i;
    }


    public class competitorList implements Serializable {
        // existing code
    }
    public List<Competitor> getCompetitors() {
        return new ArrayList<>(competitors);
    }
    // Method to generate a frequency report of scores across all competitors
    public Map<Integer, Long> generateFrequencyReport() {
        return competitors.stream()
                .flatMapToInt(c -> Arrays.stream(c.getScores()))
                .boxed()
                .collect(Collectors.groupingBy(score -> score, Collectors.counting()));
    }
}
