package pw.gose.leitner.app;

import pw.gose.leitner.types.Box;
import java.util.ArrayList;
import pw.gose.leitner.types.FlashCard;

/**
 *
 * Class FlashCardApp.java
 *
 * Houses all necessary methods for the applications functions.
 *
 */
public class FlashCardApp {

    /**
     * Number of boxes.
     */
    private final int MAX_BOXES = 5;
    private Leitner leitner;

    /**
     * Constructs a new flashcard app object and initializes the boxes.
     *
     */
    public FlashCardApp() {
        leitner = new Leitner(MAX_BOXES);
    }

    /**
     * Returns an object according to the StudyMethod12 study method.
     *
     * @return
     */
    public Leitner leitner() {

        return this.leitner;

    }

    /**
     * Returns an ArrayList containing all FlashCard(s) in the system.
     *
     * @return ArrayList<FlashCard>
     */
    public ArrayList<FlashCard> getAllCards() {

        ArrayList<FlashCard> cards = new ArrayList<>();

        for (int i = 0; i < MAX_BOXES; i++) {

            Box box = leitner.getBoxByNumber(i);

            cards.addAll(box.getAllCards());

        }

        return cards;
    }

    /**
     * Returns an arraylist containing all flashcards that contain a pattern.
     *
     * @param pattern search pattern for texts on flashcards.
     * @return ArrayList<FlashCard> where all elements contain pattern in either
     * front or back of the card.
     *
     * @precondition pattern != null
     */
    public ArrayList<FlashCard> getCardsWith(String pattern) {

        assert pattern != null;

        ArrayList<FlashCard> filteredCards = new ArrayList<>();

        for (FlashCard card : this.getAllCards()) {

            String allStrings = card.getPrompt().concat(" " + card.getResponse());

            if (allStrings.contains(pattern)) {
                filteredCards.add(card);
            }

        }

        return filteredCards;

    }

    /**
     * Returns an arraylist that contains all flash cards in a given box.
     *
     * @param boxid StudyMethod12 box id.
     * @return ArrayList<FlashCard> with all cards in box boxid.
     *
     * @precondition 0 < boxid <= number of boxes in the app
     */
    public ArrayList<FlashCard> getCards(int boxid) {

        assert boxid > 0;

        Box box = leitner.getBoxByNumber(boxid);

        return box.getAllCards();

    }

    /**
     * Creates a new flashcard and adds it to the first box.
     *
     * @param challenge
     * @param response
     * @precondition challenge != null && response != null
     */
    public void create(String challenge, String response) {
        assert challenge != null && response != null;

        FlashCard card = new FlashCard(challenge, response, 0);

        leitner.addCardToBox(card, 0);

    }

}
