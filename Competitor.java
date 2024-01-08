import java.util.Arrays;

public class Competitor {
    private int competitorId;
    private String firstName;
    private String lastName;
    private  int age;
    private String gender;
    private  String country;
    private String level;
    private String sportType;
    private int[] scores;


    // Constructor
    public Competitor(int competitorId, String firstName,String lastName,int age, String gender, String country ,String level, String sportType,int[] scores) {
        this.competitorId = competitorId;
        this.firstName = firstName;
        this.lastName=lastName;
        this.age = age;
        this.gender=gender;
        this.country=country;
        this.level = level;
        this.sportType = sportType;
        this.scores = scores;
    }



    // Getters
    public int getCompetitorId() {
        return competitorId;
    }

    public String getfirstName() {
        return firstName;
    }

    public String getlastName() {
        return lastName;
    }

    public int getage() {
        return age;
    }

    public String getgender() {
        return gender;
    }


    public  String getcountry() {
        return country;
    }

    public String getlevel() {
        return level;
    }

    public String getsportType() {
        return sportType;
    }



    // Setters
    public void setCompetitorId(int competitorId) {
        this.competitorId = competitorId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    // overall score
    public double getOverallScore() {
        double totalScore = 0;
        double weightSum = 0;
        for (int i = 0; i < scores.length; i++) {
            double weight = (i < scores.length / 2) ? 1.5 : 1.0;
            totalScore += scores[i] * weight;
            weightSum += weight;
        }
        return totalScore / weightSum;
    }

    // Method to get full details of the competitor
    public String getFullDetails() {
        return "Competitor number " + competitorId + ", name " + firstName + " " + lastName + ", country " + country +
                ".\n" + firstName + " is a " + level + " aged " + age + " and received these scores: " + Arrays.toString(scores) +
                "\nThis gives them an overall score of " + getOverallScore() + ".";
    }

    // Method to get short details of the competitor
    public String getShortDetails() {
        String initials = firstName.substring(0, 1) + lastName.substring(0, 1);
        return "CN " + competitorId + " (" + initials + ") has overall score " + getOverallScore() + ".";
    }


    // Main method to test the Competitor class
    public static void main(String[] args) {
        // Create some test Competitors
        Competitor competitor1 = new Competitor(101, "Alice", "Smith", 22, "Female","US","intermediate", "Running",new int[]{4, 2, 3, 4});
        Competitor competitor2 = new Competitor(102, "Bob", "Johnson", 20, "Male", "UK" ,"intermediate", "Golf", new int[]{5, 4, 1, 4, 3});

        // Test getFullDetails and getShortDetails methods
        System.out.println(competitor1.getFullDetails());
        System.out.println(competitor1.getShortDetails());
        System.out.println(competitor2.getFullDetails());
        System.out.println(competitor2.getShortDetails());

    }

    public int[] getScores() {
        return scores;
    }

}
