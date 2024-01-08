public class RunningCompetitor extends Competitor {
    private double bestLapTime; // in seconds

    // Adjusted the constructor to not require the bestLapTime parameter
    public RunningCompetitor(int competitorId, String firstName, String lastName, int age, String gender, String country, String level, int[] scores) {
        super(competitorId, firstName, lastName, age, gender, country, level, "Running", scores);
        this.bestLapTime = bestLapTime;
    }
    public double getBestLapTime() {
        return bestLapTime;
    }

    public void setBestLapTime(double bestLapTime) {
        this.bestLapTime = bestLapTime;
    }

    @Override
    public double getOverallScore() {
        // Assuming lower lap times are better and convert them into scores.
        // This is a simplistic approach and should be adapted to the specific scoring rules.
        return bestLapTime > 0 ? 1000 / bestLapTime : 0;
    }


    public double calculateSpeed() {
        // lap distance for simplicity
        final double lapDistance = 500; // meters
        return lapDistance / bestLapTime; // meters per second
    }

    // Override the getFullDetails method to include running-specific details
    @Override
    public String getFullDetails() {
        return super.getFullDetails() + "\nBest Lap Time: " + bestLapTime + " seconds\nSpeed: " + calculateSpeed() + " m/s";
    }


    public static void main(String[] args) {
        // Test instance of RunningCompetitor
        int[] runningScores = {4, 5, 4, 3, 4};
        RunningCompetitor runningCompetitor = new RunningCompetitor(104, "Diana", "Prince", 24, "Female", "Greece", "Elite", runningScores);

        // Testing methods
        System.out.println("Running Competitor Full Details:");
        System.out.println(runningCompetitor.getFullDetails());
        System.out.println("Running Competitor Short Details:");
        System.out.println(runningCompetitor.getShortDetails());
    }


}
