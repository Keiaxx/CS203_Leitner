package pw.gose.leitner.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * Class Box.java
 *
 * This Box object holds the TextCards for the LeitnerSytem.
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose and Saad Raja
 *
 */

public class Box implements Serializable{

    /* Variables for the Box */
    private final int exponentialProbability;
    private final int boxNumber;
    private ArrayList<FlashCard> cards;

    public Box(int boxNumber, int exp) {
        cards = new ArrayList<>();
        this.exponentialProbability = exp;
        this.boxNumber = boxNumber;
        cards = new ArrayList<>();
    }

    /**
     * Adds a FlashCard to the current Box
     *
     * @param fc The FlashCard
     */
    public void addCard(FlashCard fc) {
        cards.add(fc);
    }

    /**
     * Removes a FlashCard from the Box.
     *
     * @param fc Th FlashCard
     */
    public void removeCard(FlashCard fc) {
        cards.remove(fc);
    }

    /**
     * Returns a randomly selected card. If the Box contains no card, a null
     * value is then returned.
     *
     * @return A random FlashCard
     */
    public FlashCard getRandomCard() {

        if (cards.isEmpty()) {

            return null;

        } else {

            return cards.get(new Random().nextInt(cards.size()));
        }
    }

    /**
     * Clears the box of all FlashCard objects.
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Checks if the Box contains any FlashCard objects.
     *
     * @return Boolean
     */
    public Boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Used for the LeitnerSystem's probability algorithm.
     *
     * @return The probability
     */
    public int getProbability() {
        return this.exponentialProbability;
    }

    /**
     * Returns the arraylist of all the cards in the current box.
     *
     * @return Returns an arraylist of cards
     */
    public ArrayList<FlashCard> getAllCards() {
        return this.cards;
    }

    /**
     * Returns the current number of the Box
     *
     * @return Integer of the box.
     */
    public int getBoxNumber() {
        return this.boxNumber;
    }

}
