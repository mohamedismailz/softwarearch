import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.Map;
import java.util.*;
import java.io.PrintWriter;
import java.util.Scanner;


public class CompetitorManager {
    private CompetitorList competitorList;

    public CompetitorManager() {
        this.competitorList = new CompetitorList();
    }

    public void readCompetitorsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine(); // Skip header line
            while ((line = br.readLine()) != null) {
                try {
                    String[] values = line.split(",");
                    int[] scores = Arrays.stream(values, 8, values.length)
                            .mapToInt(Integer::parseInt)
                            .toArray();

                    Competitor competitor = createCompetitor(values, scores);
                    competitorList.addCompetitor(competitor);
                } catch (Exception e) {
                    System.out.println("Error processing line: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Competitor createCompetitor(String[] values, int[] scores) {
        return values[7].trim().equalsIgnoreCase("Golf") ?
                new GolfCompetitor(Integer.parseInt(values[0]), values[1], values[2],
                        Integer.parseInt(values[3]), values[4], values[5], values[6], scores) :
                new RunningCompetitor(Integer.parseInt(values[0]), values[1], values[2],
                        Integer.parseInt(values[3]), values[4], values[5], values[6], scores);
    }

    public void generateReport(String reportFilePath) {
        try (PrintWriter out = new PrintWriter(reportFilePath)) {
            // Full details of each competitor
            competitorList.getCompetitors().forEach(competitor -> out.println(competitor.getFullDetails() + "\n"));

            // Top scorer
            Competitor topScorer = competitorList.getCompetitors().stream()
                    .max(Comparator.comparing(Competitor::getOverallScore))
                    .orElse(null);
            if (topScorer != null) {
                out.println("Top Scorer:\n" + topScorer.getFullDetails() + "\n");
            }

            // Frequency report
            Map<Integer, Long> frequencyReport = competitorList.generateFrequencyReport();
            out.println("Frequency Report:");
            frequencyReport.forEach((score, count) -> out.println("Score " + score + ": " + count + " times"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void displayCompetitorDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Competitor ID to get details:");
        int competitorId = scanner.nextInt();

        Competitor competitor = competitorList.getCompetitorByNumber(competitorId);
        if (competitor != null) {
            System.out.println(competitor.getShortDetails());
        } else {
            System.out.println("Competitor with ID " + competitorId + " not found.");
        }
    }

    public static void main(String[] args) {
        CompetitorManager manager = new CompetitorManager();
        manager.readCompetitorsFromCSV("C:\\Users\\Mohamed\\Downloads\\RunCompetitor.csv");
        manager.generateReport("C:\\Users\\Mohamed\\Downloads\\FinalReport.txt");

        // display competitor details
        manager.displayCompetitorDetails();
    }
}
