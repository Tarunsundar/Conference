import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

/**
 * The main application class for the Conference. Has a command line menu.
 *
 * @author Chris Loftus
 * @version 1.0 (27/02/19)
 */

public class ConferenceApp {

    private String filename;
    private Scanner scan;
    private Conference conference;

    /*
     * Notice how we can make this private, since we only call from main which
     * is in this class. We don't want this class to be used by any other class.
     */
    private ConferenceApp() {
        scan = new Scanner(System.in);
        System.out.print("Please enter the filename of conference information: ");
        filename = scan.next();

        conference = new Conference();
    }

    /*
     * initialise() method runs from the main and reads from a file
     */
    private void initialise() {
        System.out.println("Using file " + filename);

        try {
            conference.load(filename);
        } catch (FileNotFoundException e) {
            System.err.println("The file: " + " does not exist. Assuming first use and an empty file." +
                    " If this is not the first use then have you accidentally deleted the file?");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred when trying to open the file " + filename);
            System.err.println(e.getMessage());
        }
    }

    /*
     * runMenu() method runs from the main and allows entry of data etc
     */
    private void runMenu() {
        String response;
        do {
            printMenu();
            System.out.println("What would you like to do:");
            scan = new Scanner(System.in);
            response = scan.nextLine().toUpperCase();
            switch (response) {
                case "1":
                    addEvent();
                    break;
                case "2":
                    changeConferenceName();
                    break;
                case "3":
                    searchForEvent();
                    break;
                case "4":
                    removeEvent();
                    break;
                case "5":
                    changeDetails();
                    break;
                case "6":
                    addVenue();
                    break;
                case "7":
                    displayVenue();
                    break;
                case "8":
                    printAll();
                    break;
                case "Q":
                    break;
                default:
                    System.out.println("Try again");
            }
        } while (!(response.equals("Q")));
    }

    /**
     * prints the Menu to be displayed to the user
     */
    private void printMenu() {
        System.out.println("1 -  add a new Event");
        System.out.println("2 -  change conference name ");
        System.out.println("3 -  search for a Event");
        System.out.println("4 -  remove an Event");
        System.out.println("5 -  change Event details");
        System.out.println("6 -  add a venue");
        System.out.println("7 -  display all venues ");
        System.out.println("8 -  display everything");
        System.out.println("q -  Quit");
    }

    /**
     * method to display all the venues
     */
    private void displayVenue() {
        System.out.println(conference.getVenues());
    }

    /**
     * method to change the event details
     */
    private void changeDetails() {
        System.out.println("Enter the Event you want to change");
        String name = scan.nextLine();
        Event result = conference.searchForEvent(name);
        if (result != null) {
            String ans;
            do {
                System.out.println("Which information do you want to change");
                System.out.println("1 -   Event name ");
                System.out.println("2 -   start Date Time ");
                System.out.println("3 -   End Date Time ");
                System.out.println("4 -   Venue ");
                System.out.println("5 -   For further options");
                System.out.println("Q -   Back to main menu");
                ans = scan.nextLine().toUpperCase();
                switch (ans) {
                    case "1":
                        System.out.println("Enter new name ");
                        String newName = scan.nextLine();
                        result.setName(newName);
                        break;
                    case "2":
                        result.setStartDateTime(getDateTime());
                        break;
                    case "3":
                        result.setEndDateTime(getDateTime());
                        break;
                    case "4":
                        System.out.println("Enter Venue name");
                        String venue = scan.nextLine();
                        Venue v = conference.searchForVenue(venue);
                        if (v != null) {
                            result.setVenue(v);
                        } else System.err.println("Venue not found, try adding venue first");
                        break;
                    case "5":
                        if (result.getClassName().equals("Talk")) {
                            changeTalk(result);
                        } else if (result.getClassName().equals("SocialEvent")) {
                            changeSocialEvent(result);
                        }
                        break;
                    case "Q":
                        break;
                    default:
                        System.err.println("Invalid choice, try again");
                }

            } while (!ans.equals("Q"));
        }
    }

    /**
     * method to get the choice from user
     *
     * @return boolean value
     */

    private boolean getChoice() {
        boolean tryAgain = false;
        boolean output = false;
        do {
            String ans = scan.nextLine().toUpperCase();
            if (ans.equals("Y")) {
                output = true;
            } else if (ans.equals("N")) {
                output = false;
            } else {
                System.err.println("Wrong input, try again");
                tryAgain = true;
            }
        } while (tryAgain);
        return output;
    }

    /**
     * method to change talk specific details
     *
     * @param result
     */
    private void changeTalk(Event result) {
        String ans, sName;
        System.out.println("1 -   Projector Requirement");
        System.out.println("2 -   remove speaker");
        System.out.println("3 -   add speaker");
        ans = scan.nextLine().toUpperCase();
        Talk talk = (Talk) result;
        switch (ans) {
            case "1":
                System.out.println("Enter projector Requirements(Y/N)");
                boolean requirement = getChoice();
                talk.setDataProjectorRequired(requirement);
                break;
            case "2":
                System.out.println("Enter the speaker's name");
                sName = scan.nextLine();
                talk.removeSpeaker(sName);
                break;
            case "3":
                System.out.println("Enter speaker's name and number on each line");
                sName = scan.nextLine();
                String num = scan.nextLine();
                Speaker s = new Speaker(sName, num);
                talk.addspeaker(s);
            default:
                System.err.println("Try again");
                break;
        }
    }

    /**
     * method to change SocialEvent specific details
     *
     * @param result
     */
    private void changeSocialEvent(Event result) {
        String ans;
        System.out.println("1 -   Invitation Requirement");
        System.out.println("2 -   Food and Drinks");
        ans = scan.nextLine().toUpperCase();
        SocialEvent socialEvent = (SocialEvent) result;
        switch (ans) {
            case "1":
                System.out.println("Enter new Invitation Requirement(Y/N)");
                boolean requirement = getChoice();
                socialEvent.setInvitation(requirement);
                break;
            case "2":
                System.out.println("Enter new Food And Drinks Availability(Y/N)");
                boolean foodDrinks = getChoice();
                socialEvent.setFoodAndDrink(foodDrinks);
                break;
            default:
                System.err.println("Try again");
                break;
        }
    }

    /**
     * Allows user to add a new Event
     */
    private void addEvent() {
        System.out.println("Do you want to add talk or social Event?(S/T)");
        String ans = scan.nextLine().toUpperCase();
        if (ans.equals("T")) {
            Talk talk = new Talk();
            populateAndAddToConference(talk);
        } else if (ans.equals("S")) {
            SocialEvent socialEvent = new SocialEvent();
            populateAndAddToConference(socialEvent);
        } else System.err.println("Wrong input");
    }

    /*
     * Adds event general data. This is common to all events. Then
     * adds to the conference.
     */
    private void populateAndAddToConference(Event event) {
        System.out.println("Event name: ");
        String name = scan.nextLine();
        Calendar startDateTime, endDateTime;
        System.out.println("Enter start time for event");
        startDateTime = getDateTime();

        System.out.println("Enter end time for event");
        endDateTime = getDateTime();

        Venue venue = null;

        if (event.getClassName().equals("Talk")) {
            Talk talk = new Talk();
            talk.setStartDateTime(startDateTime);
            checkEndTime(talk, endDateTime);

            System.out.println("Get speakers: ");
            ArrayList<Speaker> speakers = getSpeakers();

            System.out.println("Is a data projector required?(Y/N)");
            boolean answer = getChoice();
            boolean projectorRequired;
            if (answer) {
                projectorRequired = true;
            } else projectorRequired = false;
            String ans;
            do {
                venue = getVenue();
                if (venue != null) {
                    if (projectorRequired && !venue.hasDataProjector()) {
                        System.out.println("Selected venue does not have a data projector. Choose a different venue");
                        ans = "Y";
                    } else {
                        talk.setName(name);
                        talk.setDataProjectorRequired(projectorRequired);
                        talk.setVenue(venue);
                        talk.setSpeakers(speakers);
                        conference.addEvent(talk);
                        ans = "N";
                    }
                } else {
                    System.out.println("Venue " + venue + " does not exist. Do you want to try adding venue first? (Y/N)");
                    answer = getChoice();
                    if (answer == true) {
                        addVenue();
                    }
                    ans = "Y";
                }
            } while (ans.equals("Y"));
        } else if (event.getClassName().equals("SocialEvent")) {
            SocialEvent socialEvent = new SocialEvent();
            socialEvent.setName(name);
            socialEvent.setStartDateTime(startDateTime);
            if (checkEndTime(socialEvent, endDateTime)) {
                System.out.println("Is Invitation Required? (Y/N)");
                boolean invitation = getChoice(); //
                System.out.println("Are Food and Drinks available? (Y/N)");
                boolean foodAndDrinks = getChoice();//implement option
                do {
                    venue = getVenue();
                    if (venue == null) {
                        System.err.println("Venue not found, please try again");
                    }
                } while (venue == null);

                socialEvent.setFoodAndDrink(foodAndDrinks);
                socialEvent.setInvitation(invitation);
                socialEvent.setVenue(venue);
                conference.addEvent(socialEvent);
            }

        }
    }

    /**
     * checks if startDateTime is after endDateTime
     *
     * @param event
     * @param endDateTime
     * @return boolean
     */
    private boolean checkEndTime(Event event, Calendar endDateTime) {
        try {
            event.setEndDateTime(endDateTime);
            return true;
        } catch (IllegalArgumentException e) {
            System.err.println("EndDateDateTime before StartDateTime");
            return false;
        }
    }

    /**
     * gets the venue name from user and searches for venue in conference
     *
     * @return venue
     */
    private Venue getVenue() {
        System.out.println("Enter venue name");
        String venueName = scan.nextLine();
        Venue venue = conference.searchForVenue(venueName);
        return venue;
    }


    /**
     * gets the Date Time from the user
     *
     * @return result
     */
    private Calendar getDateTime() throws IllegalArgumentException {                     /** Check this method **/
        Calendar result = Calendar.getInstance();
        boolean tryAgain;
        do {
            System.out.println("On one line (numbers): year month day hour minutes");
            int year = scan.nextInt();
            int month = scan.nextInt() - 1;
            int day = scan.nextInt();
            int hour = scan.nextInt();
            int minutes = scan.nextInt();
            scan.nextLine();// Clear the end of line character
            result.clear();
            result.setLenient(false);
            try {

                result.set(year, month, day, hour, minutes);
                tryAgain = false;
                System.out.println("The date/time you entered was: " +
                        result.get(Calendar.YEAR) + "/" +
                        (result.get(Calendar.MONTH) + 1) + "/" +
                        result.get(Calendar.DAY_OF_MONTH) + ":" +
                        result.get(Calendar.HOUR_OF_DAY) + ":" +
                        result.get(Calendar.MINUTE));

            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage() + " incorrect, try again ");
                tryAgain = true;
            }

        } while (tryAgain);
        return result;
    }

    /**
     * Gets the list of speakers from the user
     *
     * @return speakers
     */
    private ArrayList<Speaker> getSpeakers() {
        ArrayList<Speaker> speakers = new ArrayList<>();
        boolean answer;
        do {
            System.out.println("Enter on separate lines: speaker-name speaker-phone");
            String speakerName = scan.nextLine();
            String speakerPhone = scan.nextLine();
            Speaker speaker = new Speaker(speakerName, speakerPhone);
            speakers.add(speaker);
            System.out.println("Another speaker (Y/N)?");
            answer = getChoice();
        } while (answer);
        return speakers;
    }

    /**
     * allows to change the conference Name
     */
    private void changeConferenceName() {
        String name = scan.nextLine();
        if (name.equals(conference.getName())) {
            System.err.println("Name is already " + name);
        } else conference.setName(name);
    }

    /**
     * allows to search for a Event
     */
    private void searchForEvent() {
        System.out.println("Which event do you want to search for");
        String name = scan.nextLine();
        Event talk = conference.searchForEvent(name);
        if (talk != null) {
            System.out.println(talk);
        }
    }

    /**
     * removes event from conference
     */
    private void removeEvent() {
        System.out.println("Which event do you want to remove");
        String talkToBeRemoved;
        talkToBeRemoved = scan.nextLine();
        conference.removeEvent(talkToBeRemoved);
    }

    /**
     * adds venue to conference
     */
    private void addVenue() {
        Venue venue;
        String venueName;
        boolean tryAgain;
        do {
            tryAgain = false;
            System.out.println("Enter the venue name");
            venueName = scan.nextLine();
            venue = conference.searchForVenue(venueName);
            if (venue != null) {
                System.out.println("This venue already exists. Give it a different name");
                tryAgain = true;
            }
        } while (tryAgain);

        System.out.println("Does it have a data projector?(Y/N)");
        String answer = scan.nextLine().toUpperCase();
        boolean hasDataProjector = answer.equals("Y");

        venue = new Venue(venueName);
        venue.setHasDataProjector(hasDataProjector);

        conference.addVenue(venue);
    }

    /**
     * prints all the details about conference
     */
    private void printAll() {
        Collections.sort(conference.getEvents());
        System.out.println(conference);
    }

    /*
     * save() method runs from the main and writes back to file
     */
    private void save() {
        try {
            conference.save(filename);
        } catch (IOException e) {
            System.err.println("Problem when trying to write to file: " + filename);
        }

    }

    // /////////////////////////////////////////////////
    public static void main(String args[]) {
        System.out.println("**********HELLO***********");

        ConferenceApp app = new ConferenceApp();
        app.initialise();
        app.runMenu();
        app.printAll();
        // MAKE A BACKUP COPY OF conf.txt JUST IN CASE YOU CORRUPT IT
        app.save();

        System.out.println("***********GOODBYE**********");
    }


}
