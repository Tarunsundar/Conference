import java.io.PrintWriter;
import java.util.*;

public class Talk extends Event{


    private boolean dataProjectorRequired;
    private ArrayList<Speaker> speakers = new ArrayList<>();

    public Talk() { super();
    }


    /**
     * Sets the venue. This will only be allowed
     * if the meets the data projector requirement.
     * Otherwise displays an error message. This should really throw an exception
     * @param venue The venue for the talk
     */
    public void setVenue(Venue venue) {
        // Only allow this if the venue spec matches the
        // the talk requirement
        if (dataProjectorRequired && !venue.hasDataProjector()) {
            System.err.println("Talk " + super.getName() + " requires a data projector. " +
                    "Venue " + venue.getName() + " does not have one");
        } else {
            super.setVenue(venue);
        }
    }

    /**
     *
     * @param startDateTime
     * @param endDateTime
     * @param name
     * @param venue
     * @param dataProjectorRequired
     * @param speakers
     */
    public Talk(Calendar startDateTime, Calendar endDateTime, String name, Venue venue, boolean dataProjectorRequired, ArrayList<Speaker> speakers) {
        super(startDateTime, endDateTime, name, venue);
        this.dataProjectorRequired = dataProjectorRequired;
        this.speakers = speakers;
    }

    public boolean isDataProjectorRequired() {
        return dataProjectorRequired;
    }

    /**
     * Sets the data projector requirement. This will only be allowed
     * if there is an associated venue that meets the requirement.
     * Otherwise displays an error message. This should really throw an exception
     * @param dataProjectorRequired Whether required or not
     */
    public void setDataProjectorRequired(boolean dataProjectorRequired) {
        if (super.getVenue() != null && (dataProjectorRequired && !super.getVenue().hasDataProjector())){
            System.err.println("Talk " + super.getName() + " currently has a venue " +
                    super.getVenue().getName() + " that does not have a data projector. Change the venue first");
        } else {
            this.dataProjectorRequired = dataProjectorRequired;
        }
    }


    /**
     * makes a copy of list of speakers and then adds it to Arraylist of speakers
     * @param speakers
     */
    public final void setSpeakers(List<Speaker> speakers) {
        // We make a true copy of the speakers ArrayList to make sure that we
        // don't break encapsulation: i.e. don't share object references with
        // other code
        if (speakers == null){
            throw new IllegalArgumentException("speakers must not be null");
        }
        this.speakers.clear();
        for (Speaker s : speakers) {
            Speaker copy = new Speaker(s.getName(), s.getPhone());
            this.speakers.add(copy);
        }
    }

    /**
     * Returns a copy of the speakers
     * @return A copy of the speakers as an array
     */
    public Speaker[] getSpeakers(){
        Speaker[] result = new Speaker[speakers.size()];
        result = speakers.toArray(result);
        return result;
    }

    /**
     * A basic implementation to just return all the data in string form.
     * CHANGE THIS TO USE StringBuffer
     * @return All the string data for the talk
     */
    @Override
    public String toString()
    {   String result =
            ", name='" + super.getName() + '\'' +
                    ", venue=" + super.getVenue() +
                    ", dataProjectorRequired=" + dataProjectorRequired +
                    ", speakers=";

        StringBuilder results = new StringBuilder();
        results.append("Talk {" );
        results.append("startDateTime=");
        results.append(dateTimeToString(super.getStartDateTime()));
        results.append(", endDateTime=" );
        results.append(dateTimeToString(super.getEndDateTime()));
        results.append(result);


        for(Speaker speaker: getSpeakers()){
            results.append(speaker).append("\n");
        }

        return results.toString();
    }


    /**
     * Reads in Talk specific information from the file
     * @param infile An open file
     * @throws IllegalArgumentException if infile is null
     */
    public void load(Scanner infile) {
       super.load(infile);

        dataProjectorRequired = infile.nextBoolean();

        int numSpeakers = infile.nextInt();
        Speaker speaker = null;
        speakers.clear();
        for (int i=0; i<numSpeakers; i++){
            String speakerName = infile.next();
            String phone = infile.next();
            speaker = new Speaker(speakerName, phone);
            speakers.add(speaker);
        }

    }

    /**
     * method to add speaker
     * @param s
     */
    public void  addspeaker(Speaker s){
        speakers.add(s);
    }

    /**
     * allows to remove speaker
     * @param name
     */
  public   void removeSpeaker(String name) {
        boolean sucess=false;
        for (Speaker s : speakers) {
            if (s.getName().equals(name)) {
                speakers.remove(s);
                sucess = true;
                break;
            }
        }
        if(!sucess) System.err.println("Speaker not found");
    }

    /**
     * Writes out information about the Talk to the file
     * @param outfile An open file
     * @throws IllegalArgumentException if outfile is null
     */
    public void save(PrintWriter outfile) {
        outfile.println(" Talk");
        super.save(outfile);
        outfile.println(dataProjectorRequired);

        outfile.println(speakers.size());
        for (Speaker speaker: speakers){
            outfile.println(speaker.getName());
            outfile.println(speaker.getPhone());
        }
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
        Talk talk = (Talk) o;  // Do the cast to Talk
        // Now just check the names
        return Objects.equals(this.getName(), talk.getName()); // Another way of checking equality. Also checks for nulls
    }

}
