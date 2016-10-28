package pw.gose.leitner.types;

/**
 *
 * This interface provides a way to handle both image and text FlashCards.
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose and Saad Raja
 *
 */

public interface Challenge {
    
    /**
     * Sets the challenge, path to an image, or a string.
     * @param obj 
     */
    public void setChallenge(Object obj);
    
    /**
     * Gets the current challenge
     * @return 
     */
    public Object getChallenge();
    
    @Override
    public String toString();
    
}
