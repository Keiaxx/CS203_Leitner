package pw.gose.leitner.app;

import java.util.HashMap;
import java.util.Random;
import pw.gose.leitner.types.Box;
import pw.gose.leitner.types.FlashCard;

/**
 *
 * Class Leitner.java

 Contains all necessary methods to use the leitner system of flashcard
 learning. A specified number of boxes can be created and flashcards begin in
 the first box. When a user gets the answer correct for that flashcard, it
 then is moved into the next box. If the wrong answer, the flashcard then
 moves to the beginning box. Each successive box has an exponentially less
 probability of being selected than the box before it.

 Assignment Description:

 Leitner system: The application randomly picks a card from one of the
 five boxes. Note, the likelihood of a card to be picked from a lower numbered
 box is exponentially higher than from a higher numbered box. For example, for
 a card in Box 1 it is twice as likely to be picked as a card in Box 2 (four
 times as likely as a card in Box 3, ...). If the user knows the answer, the
 flashcard moves to the next box (if there is one). If the user does not know
 the answer, the flashcard is moved back to Box.

 Copyright (c) 2016
 *
 * @author Adrian Gose
 *
 */
public class Leitner {

    private int probabilityTotal = 0;
    private HashMap<Integer, Box> boxes;
    private FlashCard pickedCard;

    /**
     * Initializes the Leitner System with a specified number of boxes. Also
     * initializes the HashMap with an empty set.
     *
     * @param numberOfBoxes
     */
    public Leitner(int numberOfBoxes) {
        boxes = new HashMap<>();
        this.createBoxes(numberOfBoxes);
    }

    /**
     * Chooses a random card from all the boxes.
     *
     */
    public void pickCard() {

        Box randBox = this.getRandomBox();
        FlashCard randCard = randBox.getRandomCard();

        if (randCard == null) {
            pickCard();
        } else {
            pickedCard = randCard;
        }

    }

    /**
     * Returns the amount of boxes
     *
     * @return
     */
    public int getBoxCount() {
        return boxes.size();
    }

    /**
     * Returns the current picked card of the system
     *
     * @return
     */
    public FlashCard getPickedCard() {
        return this.pickedCard;
    }

    /**
     * Returns the question for the last picked card.
     *
     * @return the question
     *
     * @precondtion a card has been picked and not been tested.
     */
    public String getQuestion() {
        return pickedCard.getPrompt();
    }

    /**
     * Returns the answer for the last picked card.
     *
     * @return the answer
     *
     * @precondtion a card has been picked and not been tested.
     */
    public String getAnswer() {
        return pickedCard.getResponse();
    }

    /**
     * Validates the response against the last picked card.
     *
     * @param s the response.
     * @return true, iff s was correct. s is assumed to be correct, if it is
     * empty or the string returned by getAnswer equals s.
     *
     * @precondition s != null
     */
    public boolean testAnswer(String s) {

        return s.equalsIgnoreCase(pickedCard.getResponse()) || s.equalsIgnoreCase(pickedCard.getPrompt());
    }

    /**
     * Adds a FlashCard to a specified box.
     *
     * @param card A FlashCard object.
     * @param boxNumber The number of the box
     */
    public void addCardToBox(FlashCard card, int boxNumber) {

        Box box = this.getBoxByNumber(boxNumber);
        box.addCard(card);

    }

    /**
     * Gets a specified box.
     *
     * @param n The box number
     * @return The box
     */
    public Box getBoxByNumber(int n) {
        return this.boxes.get(n);
    }

    /**
     *
     * When the user gets a correct answer, the card is moved to the next box.
     * For example, from box 0 to box 1. If the card is already in the last box,
     * the card stays in that box.
     *
     * This method takes the card, removes it from its current box and moves it
     * to the next box, if there is one.
     *
     */
    public void correctAnswer() {

        int currentBoxNumber = pickedCard.getCurrentBox();

        if (currentBoxNumber != (boxes.size() - 1)) {

            this.moveCardToAnotherBox(currentBoxNumber + 1);

        }

    }

    /**
     * TODO UPDATE
     * When the user gets a wrong answer, the card is moved to the first box.
     * For example, from box 3 to box 0.
     *
     * This method takes the card, removes it from its current box and moves it
     * to the first box.
     * 
     * Assignment 4 Modification:
     *  boolean simple changes the way wrong cards are handled. In simple mode, cards
     *  are only moved one box lower than to the first box.
     *
     * @param simple Determines if simple mode is active
     */
    public void wrongAnswer(boolean simple) {

        if (simple) {
            int currentBoxNumber = getPickedCard().getCurrentBox();

            if (currentBoxNumber != 0) {

                moveCardToAnotherBox(currentBoxNumber - 1);

            }
        } else {
            pickedCard.setCurrentBox(0);
            this.moveCardToAnotherBox(0);
        }

    }

    /**
     * Empties all the boxes of its contents
     */
    public void clearAllBoxes() {

        for (Box b : boxes.values()) {
            b.clear();
        }

    }

    /**
     * Sets the current flashcard of the system
     *
     * @param card
     */
    public void setCurrentCard(FlashCard card) {
        this.pickedCard = card;
    }

    /**
     * Creates a specified number of boxes for the StudyMethod12 system. This
     * also sets up the Probability of each box, as each successive box is twice
     * as unlikely to be selected than a box that is of lower level.
     *
     * @param number The number of boxes to create.
     */
    private void createBoxes(int number) {

        int exp = (number);

        for (int i = 0; i < number; i++) {
            int p = (int) Math.pow(2, exp);
            Box b = new Box(i, p);

            probabilityTotal = probabilityTotal + p;
            exp--;
            boxes.put(i, b);

        }

    }

    /**
     * Moves a specified FlashCard to another box.
     *
     * This method removes the FlashCard from its current box, and moves it to a
     * specified box determined by boxNumber.
     *
     * @param boxNumber The number of the box to move it to
     */
    public void moveCardToAnotherBox(int boxNumber) {
        Box fromBox = boxes.get(pickedCard.getCurrentBox());
        Box toBox = boxes.get(boxNumber);

        pickedCard.setCurrentBox(boxNumber);
        fromBox.removeCard(pickedCard);

        toBox.addCard(pickedCard);
    }

    /**
     * Algorithm for determining the probability, and choosing a random box.
     *
     *
     * @return
     */
    private Box getRandomBox() {

        Random random = new Random();
        int rand = random.nextInt(this.probabilityTotal);

        Box randomBox = null;

        for (int i = 0; i < boxCount(); i++) {

            Box box = boxes.get(i);

            int lower = this.getBoxAbsoluteProbability(i);

            int upper;

            if (i == boxCount() - 1) {

                upper = this.probabilityTotal;
            } else {

                upper = this.getBoxAbsoluteProbability(i + 1);
            }

            if (rand >= lower && rand <= upper) {

                randomBox = boxes.get(i);
                break;

            }

        }

        return randomBox;

    }

    /**
     * The absolute probability of a box.
     *
     * Essentially, each box houses a number in which is related to how far away
     * the box is from the first one. With each box, there is an exponential
     * probability based on its position within all of the boxes.
     *
     * For example, if there were 5 boxes total, box 0 has a exponent of 2^5,
     * box 1 has an exponent of 2^4, then 2^3, and so on. This method takes the
     * TOTAL exponents of each box, then subtracts each boxes exponent from the
     * total to obtain the position of the box with respect to the total number
     * the exponent is at.
     *
     * @param boxNumber
     * @return The probaility
     */
    private int getBoxAbsoluteProbability(int boxNumber) {

        int starting = this.probabilityTotal;

        for (int i = boxCount() - 1; i >= boxNumber; i--) {

            Box b = boxes.get(i);
            starting = starting - b.getProbability();

        }

        return starting;

    }

    /**
     * Returns the amount of boxes currently in the system.
     *
     * @return
     */
    private int boxCount() {
        return boxes.size();
    }

    /**
     * Checks if the current system is Relaxed or not.
     *
     * @return Boolean true
     */
    public String getName() {
        return "Leitner";
    }

}
