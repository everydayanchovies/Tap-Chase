package io.github.skulltah.colorseek.CSHelpers;

import io.github.skulltah.colorseek.CS.CSGame;

public class PastGameEvaluator {
    CSGame game;
    boolean isSignedIn;

    public PastGameEvaluator(CSGame game) {
        this.game = game;
        this.isSignedIn = CSGame.playServices.isSignedIn();
    }

    public void score(int highscore, int score) {
        if (isSignedIn) {
            CSGame.playServices.submitScore(score);
        }
    }
}
