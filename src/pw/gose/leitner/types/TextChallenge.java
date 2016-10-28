package pw.gose.leitner.types;

/**
 *
 * Holds textual information for a FlashCard face
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose and Saad Raja
 *
 */

import java.io.Serializable;

public class TextChallenge implements Challenge, Serializable {

    String challenge = "";

    /**
     * Creates a new TextChallenge with textual challenge information
     * @param string The question/response string
     */
    public TextChallenge(String string) {
        this.challenge = string;
    }

    /**
     * Sets the challenge
     * 
     * @param challenge 
     */
    @Override
    public void setChallenge(Object challenge) {
        this.challenge = (String) challenge;
    }

    /**
     * Returns the challenge
     * @return 
     */
    @Override
    public Object getChallenge() {
        return challenge;
    }

    /**
     * Returns string representation of the challenge
     * @return 
     */
    @Override
    public String toString() {
        return challenge;
    }

}
