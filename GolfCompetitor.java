public class GolfCompetitor extends Competitor {
    // Removed the unnecessary golfSpecificAttribute
    private double handicap;

    // Adjusted the constructor to not require the handicap parameter
    public GolfCompetitor(int competitorId, String firstName, String lastName, int age, String gender, String country, String level, int[] scores) {
        super(competitorId, firstName, lastName, age, gender, country, level, "Golf", scores);
        this.handicap =handicap ;
    }

    // Getters and Setters
    public double getHandicap() {
        return handicap;
    }

    public void setHandicap(double handicap) {
        this.handicap = handicap;
    }

    //  Golf-method
    public double calculateGolfPerformance() {
        double performance = getOverallScore() - handicap;
        return performance > 0 ? performance : 0;
    }

    // Override the getFullDetails method to include golf-specific details
    @Override
    public String getFullDetails() {
        return super.getFullDetails() + "\nHandicap: " + handicap + "\nGolf Performance: " + calculateGolfPerformance();
    }

    public static void main(String[] args) {
        // test instance of GolfCompetitor
        int[] golfScores = {3, 4, 2, 5, 4};
        GolfCompetitor golfCompetitor = new GolfCompetitor(103, "Charlie", "Brown", 28, "Male", "Canada", "Professional", golfScores);

        // Testing methods
        System.out.println("Golf Competitor Full Details:");
        System.out.println(golfCompetitor.getFullDetails());
        System.out.println("Golf Competitor Short Details:");
        System.out.println(golfCompetitor.getShortDetails());
    }

}
