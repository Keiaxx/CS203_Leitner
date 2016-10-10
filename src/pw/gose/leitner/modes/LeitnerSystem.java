/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.gose.leitner.modes;

import pw.gose.leitner.app.FlashCardApp;

/**
 *
 * @author Administrator
 */
public class LeitnerSystem extends AbstractStudyMethod {
    
    public LeitnerSystem(FlashCardApp fca){
        this.fca = fca;
    }

    @Override
    public void pickCard() {
       fca.leitner().pickCard();
    }

    @Override
    public void correctAnswer() {
        fca.leitner().correctAnswer();
    }

    @Override
    public void wrongAnswer() {
        fca.leitner().wrongAnswer(false);
    }

    @Override
    public Boolean hasNext() {
        return true;
    }

    @Override
    public String getName() {
        return "Leitner";
    }
    
}
