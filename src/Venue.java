import java.util.Objects;

public class Venue {
    private String name;
    private boolean hasDataProjector;


    public Venue(String name){
        this.name = name;
    }

    /**
     * creates Venue
     * @param name
     * @param hasDataProjector
     */
    public Venue(String name, boolean hasDataProjector) {
        this.name = name;
        this.hasDataProjector = hasDataProjector;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of Venue
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return hasDataProjector
     */
    public boolean hasDataProjector() {
        return hasDataProjector;
    }

    /**
     * sets the Data Projector requirements
     * @param hasDataProjector
     */
    public void setHasDataProjector(boolean hasDataProjector) {
        this.hasDataProjector = hasDataProjector;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Venue location = (Venue) o;
        return Objects.equals(name, location.name);
    }

    /**
     *
     * @return String to display details about venue
     */

    @Override
    public String toString() {
        return "Venue{" +
                "name='" + name + '\'' +
                ", hasDataProjector=" + hasDataProjector +
                '}';
    }
}
