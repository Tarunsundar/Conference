import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

public class SocialEvent extends Event {
    private boolean foodAndDrink;
    private boolean invitation;


    public SocialEvent(){
        super();
    }

    /**
     * creates Social Event
     * @param startDateTime
     * @param endDateTime
     * @param name
     * @param v
     * @param foodAndDrink
     * @param invitation
     */

    public SocialEvent(Calendar startDateTime, Calendar endDateTime,String name, Venue v ,boolean foodAndDrink, boolean invitation) {
        super(startDateTime, endDateTime,name, v);
        this.foodAndDrink = foodAndDrink;
        this.invitation = invitation;
    }


    /**
     *
     * @return foodAndDrink
     */
    public boolean isFoodAndDrink() {
        return foodAndDrink;
    }

    /**
     * sets Food And Drink availability
     * @param foodAndDrink
     */
    public void setFoodAndDrink(boolean foodAndDrink) {
        this.foodAndDrink = foodAndDrink;
    }

    /**
     *
     * @return Invitation requirement
     */
    public boolean isInvitation() {
        return invitation;
    }

    /**
     * sets Invitation requirement
     * @param invitation
     */
    public void setInvitation(boolean invitation) {
        this.invitation = invitation;
    }

    /**
     * reads Social Event data from file
     * @param infile
     */
    public void load(Scanner infile) {
        super.load(infile);
        boolean foodAndDrink = infile.nextBoolean();
        boolean invitation = infile.nextBoolean();

    }

    /**
     * writes Social Event data to file
     * @param outfile
     */

    public void save(PrintWriter outfile){
        outfile.println(" SocialEvent");
        super.save(outfile);
        outfile.println(foodAndDrink);
        outfile.println(invitation);
        outfile.println(super.getVenue().getName());
        outfile.println(super.getVenue().hasDataProjector());
    }

    /**
     * Note that this only compares equality based on a
     * talks's name.
     * @param o the other talk to compare against.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;  // Are they the same object?
        if (o == null || getClass() != o.getClass()) return false; // Are they the same class?
        SocialEvent socialEvent = (SocialEvent) o;  // Do the cast to Talk
        // Now just check the names
        return Objects.equals(this.getName(), socialEvent.getName()); // Another way of checking equality. Also checks for nulls
    }

    /**
     *
     * @return string to display details about Social Event
     */
    @Override
    public String toString() {
        String result = "SocialEvent {" + "startDateTime=" + dateTimeToString(super.getStartDateTime()) +
                ", endDateTime=" + dateTimeToString(super.getEndDateTime()) +
                ", name='" + super.getName() + '\'' +
                ", venue=" + super.getVenue() +
                "foodAndDrink=" + foodAndDrink +
                ", invitation=" + invitation +
                '}';
        return result;
    }
}
