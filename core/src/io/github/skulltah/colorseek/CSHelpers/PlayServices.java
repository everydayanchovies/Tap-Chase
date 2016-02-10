package io.github.skulltah.colorseek.CSHelpers;

public interface PlayServices {
    void signIn();

    void signOut();

    void rateGame();

    void unlockAchievement(String achievementId);

    void submitScore(int highScore);

    void showAchievement();

    void showScore();

    boolean isSignedIn();
}