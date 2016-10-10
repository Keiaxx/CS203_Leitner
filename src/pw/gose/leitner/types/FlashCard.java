package pw.gose.leitner.types;

/**
 *
 * Class FlashCard.java
 *
 * This class holds the contents of each flashcard, such as the prompt, and the
 * response of the card.
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose
 *
 */

public class FlashCard {

    /**
     * Variables for each parameter for the FlashCard.
     */
    private String prompt;
    private String response;
    private Boolean hasResponse;
    private int currentBox;

    public FlashCard(String prompt, String response, int currentBox) {
        this.prompt = prompt;
        this.response = response;
        this.currentBox = currentBox;
        this.hasResponse = true;
    }

    /**
     * Sets the current box number of this card.
     *
     * @param i The box number that the card is currently inside of.
     */
    public void setCurrentBox(int i) {
        this.currentBox = i;
    }

    /**
     * Sets the string on the 'front' of the card.
     *
     * @param prompt The prompt to be displayed to the user
     */
    public void setFront(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Sets the response or 'back' of the card.
     *
     * @param response The response string
     */
    public void setResponse(Object response) {

        String r = (String) response;
        this.response = r;
        this.hasResponse = true;

    }

    /**
     * Returns the current number of the box that the flash card is currently
     * inside of.
     *
     * @return An integer of the box number.
     */
    public int getCurrentBox() {
        return this.currentBox;
    }

    /**
     * Returns the front, or the prompt of the flash card.
     *
     * @return A string of the prompt
     */
    public String getPrompt() {
        return this.prompt;
    }

    /**
     * Returns the response, the 'back', or the answer to the front of the flash
     * card.
     *
     * @return A string of the response
     */
    public String getResponse() {
        return this.response;
    }

    /**
     * Checks to ensure that the card has a response.
     *
     * @return Boolean
     */
    public Boolean hasResponse() {
        return this.hasResponse;
    }
}
