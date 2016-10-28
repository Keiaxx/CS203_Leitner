package pw.gose.leitner.ui;

import pw.gose.leitner.app.FlashCardApp;
import pw.gose.leitner.types.FlashCard;
import pw.gose.leitner.types.Box;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import pw.gose.leitner.modes.Drill;
import pw.gose.leitner.modes.LeitnerSystem;
import pw.gose.leitner.modes.Simple;
import pw.gose.leitner.modes.StudyMethod;
import pw.gose.leitner.types.Challenge;
import pw.gose.leitner.types.ImageChallenge;
import pw.gose.leitner.types.TextChallenge;

/**
 *
 * Class LeitnerTUI.java
 *
 * This class incorporates and implements the StudyMethod12 study system in a
 * text-based interface for the end user. Users can import, search cards, view
 * what is in a current box, and enter the study system itself.
 *
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose
 *
 */
public class LeitnerTUI {

    /* Static variables used only by this class */
    private static FlashCardApp app;
    private static StudyMethod sm;

    final static Scanner keyboard = new Scanner(System.in);

    /**
     * The main driver of the Text user interface.
     */
    public LeitnerTUI() {

        System.out.println("Adrian Gose Assignment 4 CS203 10/9/2016\nLeitner System FlashCard TUI");

        app = new FlashCardApp();

        boolean applicationActive = true;

        /* The main loop of the TUI */
        while (applicationActive) {

            /* IF RELAXED WAS CHOSEN THE TITLE CHANGES ACCORDINGLY */
            System.out.print("\nWelcome to the Leitner Study system! Please enter a command:\n"
                    + "\nimport <filename>  : Imports a file of premade flashcards. Text only!"
                    + "\nstudy              : Study your flashcards!"
                    + "\nlist-box <boxID>   : Lists the cards within a specified box. Allowed values are 0-" + Integer.toString(app.leitner().getBoxCount() - 1)
                    + "\nlist-with <String> : Lists all flashcards with a specified string."
                    + "\n!load              : Loads the last session before the last exit."
                    + "\n!exit              : Exits the program at any given moment.\n\nEnter a command: ");

            /* Takes the user input and splits it into two parts (Command, Argument) */
            String[] input = keyboard.nextLine().toLowerCase().split(" ", 2);

            /* Switch for first command token */
            switch (input[0]) {

                /* Import comand */
                case "import":

                    /* ERROR CHECKING: Ensures a second argument was given, otherwise display error message to the user. */
                    if (input.length == 2) {
                        importFile(input[1]);
                    } else {
                        System.out.println("\nERROR: Incorrect syntax, import <filename> no filename given. Press ENTER to continue.");
                        keyboard.nextLine();
                    }

                    break;

                /* Study command */
                case "study":

                    Boolean isGUI = false;

                    /* ERROR CHECKING: Ensures cards have been imported before running the command, so nothing breaks */
                    if (app.getAllCards().isEmpty()) {
                        System.out.println("\nThe no cards have been added to the leitner system yet. Please use import first before studying. Press ENTER to continue.");
                        keyboard.nextLine();
                        break;
                    }

                    /**
                     * TODO ASK USER WHICH STUDY METHOD *
                     */
                    boolean studyMethodPrompt = true;
                    while (studyMethodPrompt) {

                        System.out.print(
                                "\nPlease choose a study mode:"
                                + "\n\nEnter a number then press ENTER : "
                                + "\n\t- leitner            / leitner-gui             : Correct cards are moved up a box, incorrect cards are moved to the first box."
                                + "\n\t- simple <boxNumber> / simple-gui <boxNumber>  : Correct cards are moved up a box, incorrect cards are moved down one box."
                                + "\n\t- drill  <boxNumber> / drill-gui  <boxNumber>  : Cards are moved to a separate pile. Correct cards are removed from the pile,"
                                + "\n\t-                       while incorrect cards stay. Drill goes on until there are no more cards"
                                + "\n>>> ");

                        String[] args = keyboard.nextLine().toLowerCase().split(" ", 2);

                        /**
                         * Assignment 6 Modification: Check if GUI mode *
                         */
                        String[] hasGUI = args[0].split("-", 2);

                        if (hasGUI.length == 2) {
                            if (hasGUI[1].equalsIgnoreCase("gui")) {

                                isGUI = true;

                            }
                        }
                        /**
                         * End Assignment 6 Modification *
                         */

                        switch (hasGUI[0].toLowerCase()) {
                            case "leitner":

                                sm = new LeitnerSystem(app);

                                if (isGUI) {
                                    System.out.println("Starting GUI for" + hasGUI[0]);
                                }

                                studyMethodPrompt = false;
                                break;
                            case "simple":

                                if (args.length == 1) {

                                    System.out.println("\nERROR: Incorrect syntax, simple <BoxNumber>. Press ENTER to continue.");
                                    keyboard.nextLine();
                                    break;
                                }

                                try {
                                    int box = Integer.parseInt(args[1]);

                                    if (box >= 0 && box < app.leitner().getBoxCount()) {

                                        if (app.getCards(box).isEmpty()) {
                                            System.out.println("\nThe box chosen contains no FlashCards! Press ENTER to continue.");
                                            keyboard.nextLine();
                                            break;

                                        }

                                        sm = new Simple(box, app);

                                        if (isGUI) {
                                            System.out.println("Starting GUI for" + hasGUI[0]);
                                        }

                                    } else {
                                        System.out.println("\nThe number you have input is not a valid selecton. Please try again! Press ENTER to continue");
                                        keyboard.nextLine();
                                        break;
                                    }

                                } catch (NumberFormatException ex) { /* Ensures the argument is a valid number */

                                    System.out.println("\nThe number you have input is not a valid integer. Please try again! Press ENTER to continue");
                                    keyboard.nextLine();
                                    break;
                                }
                                studyMethodPrompt = false;
                                break;
                            case "drill":

                                if (args.length == 1) {

                                    System.out.println("\nERROR: Incorrect syntax, drill <BoxNumber>. Press ENTER to continue.");
                                    keyboard.nextLine();
                                    break;
                                }

                                try {
                                    int box = Integer.parseInt(args[1]);

                                    if (box >= 0 && box < app.leitner().getBoxCount()) {

                                        if (app.getCards(box).isEmpty()) {
                                            System.out.println("\nThe box chosen contains no FlashCards! Press ENTER to continue.");
                                            keyboard.nextLine();
                                            break;

                                        }

                                        sm = new Drill(box, app);

                                        if (isGUI) {
                                            System.out.println("Starting GUI for" + hasGUI[0]);
                                        }

                                    } else {
                                        System.out.println("\nThe number you have input is not a valid selecton. Please try again! Press ENTER to continue");
                                        keyboard.nextLine();
                                        break;
                                    }

                                } catch (NumberFormatException ex) { /* Ensures the argument is a valid number */

                                    System.out.println("\nThe number you have input is not a valid integer. Please try again! Press ENTER to continue");
                                    keyboard.nextLine();
                                    break;
                                }
                                studyMethodPrompt = false;
                                break;
                            default:
                                System.out.println("\n\"" + args[0] + "\" was not a valid command. Please try again!. Press ENTER to continue.");
                                keyboard.nextLine();
                                break;
                        }
                    }

                    /**
                     * Starts the GUI if the GUI switch is selected. *
                     */
                    if (isGUI) {

                        LeitnerGUI leitnerGUI = new LeitnerGUI(sm);

                        break;
                    }

                    System.out.println("\nWelcome to the " + sm.getName() + " study mode. Type !exit at any time to leave study mode.Happy studying!");

                    while (true) {

                        /**
                         * Assignment 4 Modification Checks to ensure there are
                         * more cards left (For drill) *
                         */
                        if (!sm.hasNext()) {
                            System.out.println("\n\nThere are no more cards to study! Press ENTER to return to the main menu.");
                            keyboard.nextLine();
                            break;
                        }
                        /**
                         * End Assignment 4 Modificaiton *
                         */


                        /* Picks card into the system */
                        sm.pickCard();

                        /* Used to generate random boolean values */
                        Random rand = new Random();

                        String correctAnswer;
                        String toShow;

                        /* Chooses a random side of the card, front or back */
                        if (rand.nextBoolean()) {
                            toShow = (String) sm.getQuestion().toString();
                            correctAnswer = (String) sm.getAnswer().toString();
                        } else {
                            toShow = (String) sm.getAnswer().toString();
                            correctAnswer = (String) sm.getQuestion().toString();
                        }

                        System.out.print("\nPrompt: " + toShow
                                + "\n\nYou have the following options: "
                                + "\n\t- Type in your answer and check whether it is correct."
                                + "\n\t- If you know the answer, press ENTER and the card will be moved to the next box."
                                + "\n\t- Type '%' to view the other side of the card."
                                + "\n\t- Type '!exit' to leave study mode."
                                + "\n>>> ");

                        String keyboardResponse = keyboard.nextLine();
                        Challenge response = new TextChallenge(keyboardResponse);


                        /* Checks user input for !exit, empty strings, or input strings */
                        if ("!exit".equals(keyboardResponse)) {
                            /* Exits the study system loop */
                            break;
                        } else if ("%".equals(keyboardResponse)) {

                            System.out.println("\nOther side of card: " + correctAnswer);
                            System.out.println("\nPress ENTER to continue.");
                            keyboard.nextLine();

                        } else if (keyboardResponse.isEmpty()) {

                            /* If an empty string is input the program assumes the user knows the answer */
                            sm.correctAnswer();

                        } else {

                            /* Checks if the response is correct or incorrect */
                            if (sm.testAnswer(response)) {
                                System.out.println("\nYour answer was correct!");
                                sm.correctAnswer();
                            } else {
                                System.out.println("\nYour answer was incorrect! "
                                        + "\nThe correct answer was: " + correctAnswer
                                        + "\nYour input was        : " + response);
                                sm.wrongAnswer();

                            }

                            System.out.println("\nPress ENTER to continue.");
                            keyboard.nextLine();

                        }

                    }

                    break;

                /* List box command. Lists all cards in a specified box */
                case "list-box":

                    /* ERROR CHECKING: Ensures cards have been imported before running the command, so nothing breaks */
                    if (app.getAllCards().isEmpty()) {
                        System.out.println("\nThe no cards have been added to the leitner system yet. Please use import first before studying. Press ENTER to continue.");
                        keyboard.nextLine();
                        break;
                    }

                    /* ERROR CHECKING: Ensures a second argument was given, otherwise display error message to the user. */
                    if (input.length == 2) {

                        //Error checking ensuring the input string was actually an integer.
                        try {

                            int boxID = Integer.parseInt(input[1]);

                            // Error checking ensuring the input value is within the number of boxes available
                            if (boxID < app.leitner().getBoxCount() && boxID >= 0) {
                                listBox(boxID);
                            } else {
                                System.out.println("\nThe number you have input is outside the number of boxes! Allowed values are 0-" + Integer.toString(app.leitner().getBoxCount() - 1));
                                keyboard.nextLine();
                            }

                        } catch (NumberFormatException ex) { /* Ensures the argument is a valid number */

                            System.out.println("\nThe number you have input is not a valid integer. Please try again! Press ENTER to continue");
                            keyboard.nextLine();
                        }

                    } else {
                        System.out.println("\nERROR: Incorrect syntax, list-box <box ID>, no ID was given. Press ENTER to continue.");
                        keyboard.nextLine();
                    }

                    break;

                /* list-with command. Lists cards containing a specific string */
                case "list-with":

                    /* Ensures cards have been imported before running the command, so nothing breaks */
                    if (app.getAllCards().isEmpty()) {
                        System.out.println("\nThe no cards have been added to the leitner system yet. Please use import first before studying. Press ENTER to continue.");
                        keyboard.nextLine();
                        break;
                    }

                    /* ERROR CHECKING: Ensures a second argument was given, otherwise display error message to the user. */
                    if (input.length == 2) {
                        listWith(input[1]);
                    } else {
                        System.out.println("\nERROR: Incorrect syntax, list-with <pattern>, no pattern was given. Press ENTER to continue.");
                        keyboard.nextLine();
                    }

                    break;

                /* Saves the current state of the program to be loaded later */
                case "!save":
                    this.saveState();

                    break;

                /* Loads last state of the program before it was closed */
                case "!load":

                    try {
                        app.loadLastState();
                        System.out.println("\nLast state successfully loaded.");

                        System.out.println("\nPress ENTER to continue.");
                        keyboard.nextLine();
                    } catch (IOException | ClassNotFoundException ex) {

                        System.out.println("\nERROR: There is no last save-state to load.");
                        keyboard.nextLine();
                    }

                    break;

                /* Exit the program */
                case "!exit":

                    this.saveState();

                    System.out.println("\n\nThanks for studying! See you soon!");
                    applicationActive = false;

                    break;
                default:

                    System.out.println("\n\"" + input[0] + "\" was not a valid command. Please try again!. Press ENTER to continue.");
                    keyboard.nextLine();
                    break;
            }

        }

    }

    private void saveState() {

        try {
            app.saveState();
            System.out.println("\nYour session has been saved. If you would like to load this current"
                    + "\nsession again next time you open the program, type !load "
                    + "\nto load the last save-state.");
        } catch (IOException ex) {

        }
    }

    /**
     * Lists all cards in a specified box id in textual form.
     *
     * @param boxid The box ID.
     */
    public static void listBox(int boxid) {

        ArrayList<FlashCard> cards = app.getCards(boxid);

        if (cards.isEmpty()) {
            System.out.println("\n\tThere are no cards in box " + boxid);
            System.out.println("\tPress enter to continue...");
            keyboard.nextLine();
            return;
        }

        System.out.println("\n\tListing String flashcards in box " + boxid + "\n");

        for (FlashCard card : cards) {

            String front = card.getPrompt().toString();
            String back = card.getResponse().toString();

            System.out.println("\t" + front + "\n\t" + back + "\n");

        }

        System.out.println("\tPress enter to continue...");
        keyboard.nextLine();

    }

    /**
     * Searches all the cards currently imported into the system for a specific
     * string.
     *
     * @param pattern
     */
    public static void listWith(String pattern) {

        ArrayList<FlashCard> cards = app.getCardsWith(pattern);

        System.out.println("\nListing flashcards containing string: \"" + pattern + "\"\n");

        for (FlashCard card : cards) {

            String front = card.getPrompt().toString();
            String back = card.getResponse().toString();

            System.out.println("\t" + front + "\n\t" + back + "\n");

        }

        System.out.println("\tPress enter to continue...");
        keyboard.nextLine();

    }

    /**
     * 
     * Imports a specified file name to the system. Files must be in text and
     * formatted as such (Question on line line, then response on the other, and
     * white space/new line separating flashcards)
     * 
     * Assignment 6 Modification:
     * Cards are automatically loaded into a FlashCard based on its type.
     * If the img: prefix is given to a card string, such as img:image.jpg
     * The method automatically switches to a ImageChallenge type to add to
     * the FlashCard.
     *
     * question response
     *
     * question1 response1
     *
     *
     * @param inputFileName
     */
    public static void importFile(String inputFileName) {

        /* Obtain the first box only */
        Box box = app.leitner().getBoxByNumber(0);

        try {
            /* Open the input file */
            File inputFile = new File(inputFileName);
            Scanner inputFileScanner = new Scanner(inputFile);

            while (inputFileScanner.hasNext()) {

                String line1 = inputFileScanner.nextLine();
                String line2 = inputFileScanner.nextLine();

                String possiblePrefix1 = "";
                String possiblePrefix2 = "";

                Challenge line1Challenge;
                Challenge line2Challenge;

                if (inputFileScanner.hasNextLine()) {
                    inputFileScanner.nextLine();
                }

                if (line1.length() > 3) {
                    possiblePrefix1 = line1.substring(0, 4);
                }

                if (line2.length() > 3) {
                    possiblePrefix2 = line2.substring(0, 4);
                }

                if (possiblePrefix1.equalsIgnoreCase("img:")) {
                    String fileName = line1.split(":")[1];

                    line1Challenge = new ImageChallenge(fileName);

                } else {
                    line1Challenge = new TextChallenge(line1);
                }

                if (possiblePrefix2.equalsIgnoreCase("img:")) {
                    String fileName = line2.split(":")[1];
                    line2Challenge = new ImageChallenge(fileName);
                } else {
                    line2Challenge = new TextChallenge(line2);
                }

                app.create(line1Challenge, line2Challenge);

            }

            System.out.println("\tSuccessfully imported " + box.getAllCards().size() + " cards. Press ENTER to continue.");
            keyboard.nextLine();

        } catch (FileNotFoundException ex) {
            System.out.println("\nCould not open " + inputFileName + ". Please try again!. Press ENTER to continue.");
            keyboard.nextLine();
            box.clear();

        } catch (NoSuchElementException ex) { /* If the input file is malformed throws this error */

            System.out.println("\nCould not parse the input file. The import file may be malformed!. Press ENTER to continue.");
            keyboard.nextLine();
            box.clear();
        }

    }

}
