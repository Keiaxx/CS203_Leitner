package pw.gose.leitner.modes;

import java.util.Random;
import pw.gose.leitner.app.FlashCardApp;

/**
 *
 * Class Drill.java
 *
 *
 * Assignment Description:
 *
 * • Drill: Under this method, the flashcards of a given box are copied into a
 * separate pool. The application randomly picks one flashcard from that pool.
 * If the user knows the answer, the card is removed from the pool. If the user
 * does not know the answer the card remains in the pool. Drill ends when the
 * pool is empty. When users end the drill session, the pool can be discarded.
 * During a drill session the Leitner boxes remain unchanged.
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose
 *
 */
public class Drill extends AbstractStudyMethod {

    public Drill(int boxNum, FlashCardApp fca) {
        
        cards = fca.getCards(boxNum);
        this.fca = fca;
    }

    @Override
    public void pickCard() {
        Random rand = new Random();
        int random = rand.nextInt(cards.size());
        fca.leitner().setCurrentCard(cards.get(random));
    }

    @Override
    public void correctAnswer() {
        cards.remove(fca.leitner().getPickedCard());
    }

    @Override
    public void wrongAnswer() {
        /** Do nothing when answer is wrong in Drill mode **/
    }

    @Override
    public Boolean hasNext() {
        return !cards.isEmpty();
    }

    @Override
    public String getName() {
        return "Drill";
    }

}
