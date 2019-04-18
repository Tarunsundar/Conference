import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * To model a Conference - a collection of events
 *
 * @author Chris loftus
 * @version 1 (20th February 2019)
 */
public class Conference {
    private String name;
    private ArrayList<Event> events;
    private ArrayList<Venue> venues;

    /**
     * Creates a conference
     */
    public Conference() {
        events = new ArrayList<>();
        venues = new ArrayList<>();
    }

    /**
     * This method gets list of Event
     * @return events
     */
    public ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * This method gets the value for the name attribute. The purpose of the
     * attribute is: The name of the Conference e.g. "QCon London 2019"
     *
     * @return String The name of the conference
     */
    public String getName() {
        return name;
    }

    /**
     * This method sets the value for the name attribute. The purpose of the
     * attribute is: The name of the conference e.g. "QCon London 2019"
     *
     * @param name The name of the conference
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Enables a user to add a Event to the Conference
     *
     * @param event
     */
    public void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Add a new venue to the conference
     *
     * @param venue Must be a unique name
     * @return true if venue successfully added or false if the venue already exists
     */
    public boolean addVenue(Venue venue) {
        boolean success = false;
        if (!venues.contains(venue)) {
            venues.add(venue);
            success = true;
        }
        return success;
    }

    /**
     * Enables a user to delete a Event from the Conference.
     *
     * @param eventName The talk to remove
     */
    public boolean removeEvent(String eventName) {
        // Search for the talk by name
        Event which = null;
        which = searchForEvent(eventName);
        if (which != null) {
            events.remove(which); // Requires that Talk has an equals method
            System.out.println("removed " + which);
            return true;
        } else {
            System.err.println("cannot remove " + eventName +
                    " - not in conference " + name);
            return false;
        }
    }

    /**
     * Returns an array of the events in the conference
     *
     * @return An array of the correct size
     */
    public Event[] obtainAllEvents() {
        Event[] result = new Event[events.size()];
        result = events.toArray(result);
        return result;
    }

    /**
     * Returns list of venues
     * @return venues
     */
    public ArrayList<Venue> getVenues() {
        return venues;
    }

    /**
     * Searches for and returns the event, if found
     *
     * @param name The name of the event
     * @return The Event or else null if not found
     */
    public Event searchForEvent(String name) {

        Event result = null;
        for (Event event : events) {
            if (name.equals(event.getName())) {
                result = event;
                break;
            }
        }
        if (result == null) {
            System.err.println(" Event not found");
        }
        return result;
    }

    /**
     * Searches for and returns the venue, if found
     *
     * @param name The name of the venue
     * @return The venue or else null if not found
     */
    public Venue searchForVenue(String name) {
        Venue result = null;
        for (Venue v : venues) {
            if (name.equals(v.getName())) {
                result = v;
                break;
            }
        }
        return result;
    }

    /**
     * @return String showing all the information in the kennel
     */
    public String toString() {
        StringBuilder results = new StringBuilder();
        results.append("Data in Conference " + name + " is: \n");
        Collections.sort(events);
        for (Event event : obtainAllEvents()) {
            results.append(event).append('\n');
        }
        return results.toString();
    }

    /**
     * Reads in Conference information from the file
     *
     * @param filename The file to read from
     * @throws IOException
     * @throws FileNotFoundException
     * @throws IllegalArgumentException if outfileName is null or empty
     */
    public void load(String filename) throws IOException {
        // Using try-with-resource. We will cover this in workshop 15, but
        // what it does is to automatically close the file after the try / catch ends.
        // This means we don't have to worry about closing the file.
        // YOU WILL NEED TO UPDATE THIS METHOD TO DEAL WITH DIFFERENT KINDS OF EVENTS
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr);
             Scanner infile = new Scanner(br)) {

            events.clear();
            venues.clear();

            // Use the delimiter pattern so that we don't have to clear end of line
            // characters after doing a infile.nextInt or infile.nextBoolean and can use infile.next
            infile.useDelimiter("\r?\n|\r"); // End of line characters on Unix or DOS

            name = infile.next();

            while (infile.hasNext()) {
                Event event = null;
                infile.next();
                String type = infile.next();
                switch (type) {
                    case " Talk":
                        event = new Talk();
                        break;
                    case " SocialEvent":
                        event = new SocialEvent();
                        break;
                    default:
                        break;
                }
                if(event == null) throw new IOException("Event type not specified ");
                event.load(infile);
                String venueName = infile.next();
                boolean hasDataProjector = infile.nextBoolean();
                Venue theVenue = searchForVenue(venueName);
                if (theVenue == null) {
                    theVenue = new Venue(venueName);
                    theVenue.setHasDataProjector(hasDataProjector);
                    venues.add(theVenue);
                }
                event.setVenue(theVenue);
                events.add(event);
            }
        }
    }

    /**
     * Write out Conference information to the outfile
     *
     * @param outfileName The file to write to
     * @throws IOException
     * @throws IllegalArgumentException if outfileName is null or empty
     */
    public void save(String outfileName) throws IOException {
        // Again using try-with-resource so that I don't need to close the file explicitly
        try (FileWriter fw = new FileWriter(outfileName);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter outfile = new PrintWriter(bw)) {

            outfile.print(name);
            outfile.println();
            for (Event event : events) {
                outfile.println();
                event.save(outfile);

            }
        }
    }
}
