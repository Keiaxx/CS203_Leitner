package pw.gose.leitner.modes;

import java.util.ArrayList;
import pw.gose.leitner.app.FlashCardApp;
import pw.gose.leitner.types.FlashCard;

/**
 * This abstract class holds common methods
 * for all of the StudyMethd subclasses.
 * 
 * Copyright (c) 2016
 *
 * @author Adrian Gose
 *
 */

abstract class AbstractStudyMethod implements StudyMethod{
    
    FlashCardApp fca;
    ArrayList<FlashCard> cards;
    
    /**
     * Get the current question
     * 
     * @return 
     */
    @Override
    public String getQuestion() {
        return fca.leitner().getQuestion();
    }

    /**
     * Get the current answer
     * 
     * @return 
     */
    @Override
    public String getAnswer() {
        return fca.leitner().getAnswer();
    }

    /**
     * Check to see if the answer is correct or not.
     * 
     * @param response
     * @return 
     */
    @Override
    public Boolean testAnswer(String response) {
        return fca.leitner().testAnswer(response);
    }

}
