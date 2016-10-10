/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pw.gose.leitner.modes;

import java.util.ArrayList;
import java.util.Random;
import pw.gose.leitner.app.FlashCardApp;
import pw.gose.leitner.types.FlashCard;

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
public class Drill implements StudyMethod {

    private FlashCardApp fca;
    private ArrayList<FlashCard> cards;

    public Drill(int boxNum, FlashCardApp fca) {
        cards = fca.getCards(boxNum);
        this.fca = fca;
    }

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
