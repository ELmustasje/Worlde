package org.example.wordle.view.gameSelect;

import org.example.wordle.controller.WordleAIController;
import org.example.wordle.controller.WordleHumanController;
import org.example.wordle.model.WordleBoard;
import org.example.wordle.model.WordleModel;
import org.example.wordle.view.gameView.WordleView;

import javax.swing.*;

import static org.example.wordle.WordleMain.WINDOW_TITLE;

public class WordleGame {

    /**
     * Creates a Eordle game with either a human or an ai controller based on a given boolean.
     * @param humanController whether to have a human controller (true) or an ai controller (false).
     */
    public WordleGame(boolean humanController) {
        WordleBoard board = new WordleBoard(8, 5);
        WordleModel model;
        WordleView view;

        if (humanController) {
            model = new WordleModel(board);
            view = new WordleView(model);
            new WordleHumanController(model, view);
        } else {
            model = new WordleModel(board);
            view = new WordleView(model);
            new WordleAIController(model, view);
        }

        JFrame frame = new JFrame(WINDOW_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);
    }
}
