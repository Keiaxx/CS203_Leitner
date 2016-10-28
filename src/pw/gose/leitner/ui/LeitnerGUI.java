package pw.gose.leitner.ui;

/**
 *
 * The LeitnerGUI handles all GUI related aspects of each study method.
 *
 * The only input requirement is a StudyMethod class representation.
 *
 * The GUI interfaces with each StudyMethod and its subclasses in order to
 * provide the user with a uniform flashcard interaction no matter which
 * StudyMethod is used.
 *
 * Copyright (c) 2016
 *
 * @author Adrian Gose and Saad Raja
 *
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import pw.gose.leitner.modes.StudyMethod;
import pw.gose.leitner.types.Challenge;
import pw.gose.leitner.types.ImageChallenge;
import pw.gose.leitner.types.TextChallenge;

public class LeitnerGUI extends JFrame {

    /**
     * Width and Height of Frame *
     */
    private final int width = 800;
    private final int height = 500;

    private final StudyMethod sm;

    /**
     * J Related Variables *
     */
    private JFrame frame;
    private JTextPane mainPane;
    private JPanel buttonPanel;
    private JButton button_idk;
    private JButton button_flip;
    private JButton button_check;
    private JButton button_know;
    private JButton button_exit;

    /**
     * Enables the card to be flipped *
     */
    private Boolean showingFront = true;

    /**
     * Constructor starts the LeitnerGUI based on a specific StudyMethod subclass.
     *
     * @param sm StudyMethod subclass
     */
    public LeitnerGUI(StudyMethod sm) {
        this.sm = sm;
        this.startGUI();
        this.newCard();
    }

    /**
     * Initializes JFrames and all its components in order
     * to start the GUI and display cards to the user.
     */
    private void startGUI() {
        /* Create frames and button panels */
        this.frame = new JFrame();
        this.buttonPanel = new JPanel();
        this.mainPane = new JTextPane();
        this.button_idk = new JButton("Don't know");
        this.button_flip = new JButton("Flip Card");
        this.button_check = new JButton("Check Answer");
        this.button_know = new JButton("I know");
        this.button_exit = new JButton("Exit");

        this.button_idk.addActionListener(buttonPress());
        this.button_flip.addActionListener(buttonPress());
        this.button_check.addActionListener(buttonPress());
        this.button_exit.addActionListener(buttonPress());
        this.button_know.addActionListener(buttonPress());

        this.buttonPanel.setVisible(true);
        this.buttonPanel.add(button_idk);
        this.buttonPanel.add(button_flip);
        this.buttonPanel.add(button_check);
        this.buttonPanel.add(button_know);
        this.buttonPanel.add(button_exit);

        this.frame.setTitle(sm.getName() + " Study GUI");
        this.frame.add(mainPane, BorderLayout.CENTER);
        this.frame.add(buttonPanel, BorderLayout.SOUTH);

        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.getContentPane().setPreferredSize(new Dimension(800, 500));
        this.frame.pack();
        this.frame.setAlwaysOnTop(true);
        this.frame.setVisible(true);

        this.mainPane.setContentType("text/html");
        this.mainPane.setEditable(false);
    }

    /**
     * Gets a new card and displays it to the user. If there are no more cards
     * to display to the user, shows an error message.
     */
    private void newCard() {
        this.resetUI();

        if (this.sm.hasNext()) {
            this.sm.pickCard();

            this.showType(sm.getQuestion());
        } else {
            this.messageBox("There are no more cards to study in the current box!", "Empty Box", JOptionPane.ERROR_MESSAGE);
            this.frame.dispose();
        }

    }

    /**
     * Flips the card based on what is currently being displayed to the user
     */
    private void flipCard() {

        this.disableUI();

        if (this.showingFront) {

            Challenge answer = this.sm.getAnswer();
            this.showingFront = false;
            this.showType(answer);

        } else {
            Challenge answer = this.sm.getQuestion();
            this.showingFront = true;
            this.showType(answer);

        }

    }

    /**
     * Disable UI elements so user can only advance or flip the card to prevent
     * cheating.
     */
    private void disableUI() {
        this.button_check.setEnabled(false);
        this.button_know.setEnabled(false);
        this.button_idk.setText("Next Card");
    }

    /**
     * Resets the GUI to a new state for a new card.
     */
    private void resetUI() {
        this.showingFront = true;
        this.button_idk.setText("Do Not Know");
        this.button_check.setEnabled(true);
        this.button_know.setEnabled(true);
    }

    /**
     * Checks if the answer that has been input is correct or not.
     * Displays a message box alerting user of the status of their response.
     * @param answer String of the answer
     */
    private void checkAnswer(String answer) {

        if (sm.testAnswer(new TextChallenge(answer))) {
            sm.correctAnswer();
            this.messageBox("Your answer was correct!", "Correct", JOptionPane.INFORMATION_MESSAGE);
            this.disableUI();
        } else {
            sm.wrongAnswer();
            this.messageBox("Your answer was wrong! The correct answer was: \n" + sm.getAnswer().toString(), "Incorrect", JOptionPane.INFORMATION_MESSAGE);
            this.disableUI();
        }

    }

    
    private void messageBox(String message, String title, int dialogType) {

        JOptionPane.showMessageDialog(null, message, title, dialogType);

    }

    /**
     * Shows the challenge based on the type of Challenge it is.
     * @param challenge Challenge object
     */
    private void showType(Challenge challenge) {

        // Check if it is Textual
        if (challenge instanceof TextChallenge) {

            this.showText(challenge);
        
        // Check if it is an Image
        } else if (challenge instanceof ImageChallenge) {

            this.showImage(challenge);

        }

    }

    /**
     * Show the text of the card to the user.
     * 
     * The challenge must be only an instance of TextChallenge
     * 
     * @param challenge 
     */
    private void showText(Challenge challenge) {
        assert challenge instanceof TextChallenge;

        String text = challenge.toString();

        if (showingFront) {
            this.mainPane.setText("<html><body width=800 height=500/><center><br><br><br><br><br><font size='15'>" + text + "</font>");
        } else {
            this.mainPane.setText("<html><body background='file:index_card.jpg' width=800 height=500/><center><br><br><br><br><font size='13'>" + text + "</font>");
        }
    }

    /**
     * Shows an image to the user.
     * 
     * Challenge must be an instance of ImageChallenge
     * 
     * @param challenge 
     */
    private void showImage(Challenge challenge) {
        assert challenge instanceof ImageChallenge;

        String path = challenge.toString();
        this.mainPane.setText("<html><center><img src='file:" + path + "' width=800 height=500></img>");

    }

    /**
     * Handles JButton press events.
     *
     * @return
     */
    private ActionListener buttonPress() {
        ActionListener action = (ActionEvent e) -> {
            if (e.getSource() == this.button_idk) {
                this.sm.wrongAnswer();
                this.newCard();
            }

            if (e.getSource() == this.button_flip) {
                this.flipCard();
            }

            if (e.getSource() == this.button_exit) {
                this.frame.dispose();
            }

            if (e.getSource() == this.button_check) {
                String input = JOptionPane.showInputDialog(this, "Answer:");
                this.checkAnswer(input);
            }

            if (e.getSource() == this.button_know) {
                this.sm.correctAnswer();
                this.newCard();
            }
        };
        return action;
    }

}
