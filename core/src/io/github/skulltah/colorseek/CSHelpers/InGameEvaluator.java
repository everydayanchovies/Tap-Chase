package io.github.skulltah.colorseek.CSHelpers;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.Constants.IDs;

public class InGameEvaluator {
    public static boolean isSignedIn;
    CSGame game;

    public InGameEvaluator(CSGame game) {
        this.game = game;
        InGameEvaluator.isSignedIn = CSGame.playServices.isSignedIn();
    }

    public void unlockGenericAchievement(String id) {
        if (isSignedIn)
            CSGame.playServices.unlockAchievement(id);
    }

    public void score(int score) {
        if (isSignedIn) {
            if (score > 30)
                CSGame.playServices.unlockAchievement(IDs.ach30);
            if (score > 60)
                CSGame.playServices.unlockAchievement(IDs.ach60);
            if (score > 99)
                CSGame.playServices.unlockAchievement(IDs.ach99);
            if (score > 199)
                CSGame.playServices.unlockAchievement(IDs.ach199);
        }
    }
}
