/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.gose.leitner.types;

/**
 *
 * Holds information relating to the image path to a certain flashcard's face.
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose and Saad Raja
 *
 */

import java.io.Serializable;

public class ImageChallenge implements Challenge, Serializable {

    private String path;

    /**
     * Set the path to the image
     * 
     * @param path Path to the image
     */
    public ImageChallenge(String path) {
        this.path = path;
    }

    /**
     * Set the current image
     * @param obj Image path
     */
    @Override
    public void setChallenge(Object obj) {
        this.path = (String) obj;
    }

    /**
     * Gets the current image path
     * @return 
     */
    @Override
    public Object getChallenge() {
        return this.path;
    }

    /**
     * Returns string of the image path
     * @return 
     */
    @Override
    public String toString() {
        return path;
    }

}
