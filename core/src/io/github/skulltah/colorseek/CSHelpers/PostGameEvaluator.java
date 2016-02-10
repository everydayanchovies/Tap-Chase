package io.github.skulltah.colorseek.CSHelpers;

import io.github.skulltah.colorseek.CS.CSGame;

public class PostGameEvaluator {
    CSGame game;
//    boolean isSignedIn;

    public PostGameEvaluator(CSGame game) {
        this.game = game;
//        this.isSignedIn = CSGame.playServices.isSignedIn();
    }

    public void score(int highscore, int score) {
        CSGame.playServices.submitScore(score);
    }
}
