/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.gose.leitner.modes;

import java.util.ArrayList;
import pw.gose.leitner.app.FlashCardApp;
import pw.gose.leitner.types.FlashCard;

/**
 *
 * @author Administrator
 */
abstract class AbstractStudyMethod implements StudyMethod{
    
    FlashCardApp fca;
    ArrayList<FlashCard> cards;
    
    @Override
    public String getQuestion() {
        return fca.leitner().getQuestion();
    }

    @Override
    public String getAnswer() {
        return fca.leitner().getAnswer();
    }

    @Override
    public Boolean testAnswer(String response) {
        return fca.leitner().testAnswer(response);
    }

}
