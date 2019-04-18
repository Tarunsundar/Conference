import java.util.Objects;

public class Speaker {
    private String name;
    private String phone;

    public Speaker(String name) {
        this.name = name;
    }

    /**
     * creates a speaker
     *
     * @param name
     * @param phone
     */
    public Speaker(String name, String phone) {
        this(name);
        this.phone = phone;

    }

    /**
     * @return name
     */

    public String getName() {
        return name;
    }

    /**
     * sets the name of speaker
     *
     * @param name
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return phone number
     */

    public String getPhone() {
        return phone;
    }

    /**
     * sets phone number to speaker
     *
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Speaker speaker = (Speaker) o;
        return Objects.equals(name, speaker.name) &&
                Objects.equals(phone, speaker.phone);
    }

    /**
     * @return string showing all the details about speaker
     */
    @Override
    public String toString() {
        return "Speaker{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
