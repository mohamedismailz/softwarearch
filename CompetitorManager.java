import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CompetitorManager {
    private List<Competitor> competitors;

    public CompetitorManager() {
        this.competitors = new ArrayList<>();
    }

    public void readCompetitorsFromCSV(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int[] scores = {
                        Integer.parseInt(values[8].trim()),
                        Integer.parseInt(values[9].trim()),
                        Integer.parseInt(values[10].trim()),
                        Integer.parseInt(values[11].trim())
                };

                if (values[7].trim().equalsIgnoreCase("Golf")) {
                    competitors.add(new GolfCompetitor(
                            Integer.parseInt(values[0].trim()),
                            values[1].trim(),
                            values[2].trim(),
                            Integer.parseInt(values[3].trim()),
                            values[4].trim(),
                            values[5].trim(),
                            values[6].trim(),
                            scores
                    ));
                } else if (values[7].trim().equalsIgnoreCase("Running")) {
                    competitors.add(new RunningCompetitor(
                            Integer.parseInt(values[0].trim()),
                            values[1].trim(),
                            values[2].trim(),
                            Integer.parseInt(values[3].trim()),
                            values[4].trim(),
                            values[5].trim(),
                            values[6].trim(),
                            scores
                    ));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        CompetitorManager manager = new CompetitorManager();
        manager.readCompetitorsFromCSV("C:\\Users\\Mohamed\\Downloads\\RunCompetitor.csv");

        // Print the full details of each competitor
        for (Competitor competitor : manager.competitors) {
            System.out.println(competitor.getFullDetails());
            System.out.println(); // Print a blank line for readability
        }
    }
}
