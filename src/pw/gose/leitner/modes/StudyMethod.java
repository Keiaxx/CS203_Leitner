package pw.gose.leitner.modes;

import pw.gose.leitner.types.FlashCard;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrator
 */
public interface StudyMethod {
    
    String getName();

    String getQuestion();

    String getAnswer();

    Boolean testAnswer(String response);
    
    Boolean hasNext();

    void pickCard();

    void correctAnswer();

    void wrongAnswer();

}
