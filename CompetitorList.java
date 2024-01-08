import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Arrays;

public class CompetitorList {
    private List<Competitor> competitors;

    public CompetitorList() {
        this.competitors = new ArrayList<>();
    }

    public void addCompetitor(Competitor competitor) {
        this.competitors.add(competitor);
    }

    public boolean removeCompetitorByNumber(int competitorId) {
        return competitors.removeIf(c -> c.getCompetitorId() == competitorId);
    }

    public Competitor getCompetitorByNumber(int competitorId) {
        return competitors.stream()
                .filter(c -> c.getCompetitorId() == competitorId)
                .findFirst()
                .orElse(null);
    }

    public void setCompetitors(List<Competitor> readObject) {
    }

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

    public Map<Integer, Long> generateFrequencyReport() {
        return competitors.stream()
                .flatMapToInt(c -> Arrays.stream(c.getScores()))
                .boxed()
                .collect(Collectors.groupingBy(score -> score, Collectors.counting()));
    }
}
