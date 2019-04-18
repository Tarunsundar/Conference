import java.io.PrintWriter;
import java.util.Calendar;
import java.util.*;

public class Event implements Comparable<Event> {
    private Calendar startDateTime;
    private Calendar endDateTime;
    private String name;
    private Venue venue;

    public Event() {
        name = "no event";
    }

    /**
     * creates Event
     *
     * @param startDateTime
     * @param endDateTime
     * @param name
     * @param venue
     */
    public Event(Calendar startDateTime, Calendar endDateTime, String name, Venue venue) {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.name = name;
        this.venue = venue;
    }

    /**
     * @param e
     * @return difference between both event's startDateTime.
     */
    public int compareTo(Event e) {
        return this.startDateTime.compareTo(e.startDateTime);
    }

    /**
     * @return String to display className
     */
    public final String getClassName() {
        return this.getClass().getName();
    }

    /**
     * @return name of the even
     */
    public String getName() {
        return name;
    }

    /**
     * sets name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * sets venue
     *
     * @param v
     */
    public void setVenue(Venue v) {
        this.venue = v;
    }

    /**
     * @return venue
     */
    public Venue getVenue() {
        return venue;
    }

    /**
     * @return startDateTime
     */
    public Calendar getStartDateTime() {
        return startDateTime;
    }

    /**
     * sets startDateTime
     *
     * @param startDateTime
     */
    public void setStartDateTime(Calendar startDateTime) {

            this.startDateTime = startDateTime;
    }

    /**
     * @return endDateTime
     */
    public Calendar getEndDateTime() {
        return endDateTime;
    }

    /**
     * sets end date time
     *
     * @param endDateTime
     */
    public void setEndDateTime(Calendar endDateTime)throws IllegalArgumentException{

        if (endDateTime.after(startDateTime)) {
            this.endDateTime = endDateTime;
        } else
        throw new IllegalArgumentException("endDateTIme before StartDateTime");
    }

    /**
     * converts Date and Time to String
     *
     * @param dateTime
     * @return
     */

    public String dateTimeToString(Calendar dateTime) {

        int year = dateTime.get(Calendar.YEAR);
        int month = dateTime.get(Calendar.MONTH) + 1; // We have to add 1 since months start from 0
        int day = dateTime.get(Calendar.DAY_OF_MONTH);
        int hour = dateTime.get(Calendar.HOUR_OF_DAY);
        int minutes = dateTime.get(Calendar.MINUTE);

        return "" + year + ":" + month + ":" + day + ":" + hour + ":" + minutes;
    }

    /**
     * @param scan
     * @return
     */
    private Calendar readDateTime(Scanner scan) {
        Calendar result = Calendar.getInstance();

        int year = scan.nextInt();
        int month = scan.nextInt();
        int day = scan.nextInt();
        int hour = scan.nextInt();
        int minutes = scan.nextInt();
        result.clear();
        result.set(year, month, day, hour, minutes);
        return result;
    }

    /**
     * Reads in Event details from the file
     *
     * @param outfile
     * @param dateTime
     */
    private void writeDateTime(PrintWriter outfile, Calendar dateTime) {
        outfile.println(dateTime.get(Calendar.YEAR));
        outfile.println(dateTime.get(Calendar.MONTH));
        outfile.println(dateTime.get(Calendar.DAY_OF_MONTH));
        outfile.println(dateTime.get(Calendar.HOUR_OF_DAY));
        outfile.println(dateTime.get(Calendar.MINUTE));
    }

    /**
     * writes Event details to the file
     *
     * @param outfile
     */
    public void save(PrintWriter outfile) {
        if (outfile == null)
            throw new IllegalArgumentException("outfile must not be null");
        outfile.println(name);
        writeDateTime(outfile, startDateTime);
        writeDateTime(outfile, endDateTime);
    }

    public void load(Scanner infile) {
        if (infile == null) {
            throw new IllegalArgumentException("infile must not be null");
        }
        name = infile.next();
        startDateTime = readDateTime(infile);
        endDateTime = readDateTime(infile);
    }
}
