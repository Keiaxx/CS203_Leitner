package pw.gose.leitner.modes;

import pw.gose.leitner.app.FlashCardApp;

/**
 *
 * Class Simple.java
 *
 * Assignment Description:
 *
 * Simple: Under this method, the student chooses a box by number. The study
 * method picks the first card and presents it to the user. If the student knows
 * the answer, the card moves one box higher (if a higher box exists). If the
 * student does not know the answer, the card moves one box lower (if a lower
 * box exists).
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose
 *
 */
public class Simple extends AbstractStudyMethod {

    public Simple(int boxNum, FlashCardApp fca) {
        cards = fca.getCards(boxNum);
        this.fca = fca;
    }

    @Override
    public void pickCard() {
        fca.leitner().setCurrentCard(cards.get(0));
    }

    @Override
    public void correctAnswer() {
        fca.leitner().correctAnswer();
    }

    @Override
    public void wrongAnswer() {
        fca.leitner().wrongAnswer(true);
    }

    @Override
    public Boolean hasNext() {
        return !cards.isEmpty();
    }

    @Override
    public String getName() {
        return "Simple";
    }

}
