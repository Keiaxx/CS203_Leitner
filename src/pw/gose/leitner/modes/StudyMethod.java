package pw.gose.leitner.modes;

/**
 * Copyright (c) 2016
 *
 * @author Adrian Gose
 *
 */

public interface StudyMethod {
    
    /**
     * Return the name of the current study method
     * @return 
     */
    String getName();

    /**
     * Returns the current question of the picked card
     * @return 
     */
    String getQuestion();

    /**
     * Returns the current answer of the picked card
     * @return 
     */
    String getAnswer();

    /**
     * Tests the response and checks if it is correct
     * 
     * @param response
     * @return 
     */
    Boolean testAnswer(String response);
    
    /**
     * Checks to see if there are more cards, if any
     * @return 
     */
    Boolean hasNext();

    /**
     * Chooses a FlashCard
     * 
     */
    void pickCard();

    /**
     * Sets the answer to correct
     * 
     */
    void correctAnswer();

    /**
     * Sets the answer to wrong
     * 
     */
    void wrongAnswer();

}
