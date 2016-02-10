package io.github.skulltah.colorseek.CSHelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import io.github.skulltah.colorseek.Constants.Textures;

public class AssetLoader {

    public static Texture texture, logoTexture;
    public static TextureRegion logo, zbLogo, bg, grass,
            pacmanHalfOpen, pacmanOpen, pacmanClosed,
            pacmanHalfOpenSuper, pacmanOpenSuper, pacmanClosedSuper,
            playButtonUp, playButtonDown,
            ready, gameOver, highScore, scoreboard, star, noStar, retry, leaderboardUp, leaderboardDown, help, achievements,
            food1_0, food1_1, food1_2, food1_3, food1_4,
            food2_0, food2_1, food2_2, food2_3, food2_4,
            food3_0, food3_1, food3_2, food3_3, food3_4,
            food4_0, food4_1, food4_2, food4_3, food4_4,
            pipeTopUp_0, pipeTopUp_1, pipeTopUp_2,
            pipeTopDown_0, pipeTopDown_1, pipeTopDown_2,
            pipe_0, pipe_1, pipe_2
//    ,ground_0, ground_1, ground_2
            ;
    public static Animation pacmanAnimation, pacmanAnimationSuper, food1Animation, food2Animation, food3Animation, food4Animation, pipeAnimation, pipeTopUpAnimation, pipeTopDownAnimation
//            , groundAnimation
            ;
    public static Sound
//            dead, flap, coin, fall
            highscore, scoreUp, doubleTap, tap, dead1, dead2, dead3, dead4, dead5, poison, healthy;
    public static BitmapFont font, shadow, whiteFont;
    private static Preferences prefs;

    public static void load() {
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 256, 306);

        texture = new Texture(Gdx.files.internal("texture.png"));
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);

        playButtonUp = new TextureRegion(texture, 111, 121, 52, 29);
        playButtonDown = new TextureRegion(texture, 164, 121, 52, 29);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        ready = new TextureRegion(texture, 59, 83, 34, 7);
        ready.flip(false, true);

        retry = new TextureRegion(texture, 59, 110, 33, 7);
        retry.flip(false, true);

        leaderboardUp = new TextureRegion(texture, 111, 152, 52, 29);
        leaderboardUp.flip(false, true);
        leaderboardDown = new TextureRegion(texture, 164, 152, 52, 29);
        leaderboardDown.flip(false, true);

        achievements = new TextureRegion(texture, 177, 190, 62, 36);
        achievements.flip(false, true);

        help = new TextureRegion(texture, 4, 120, 67, 200);
        help.flip(false, true);

        food1_0 = new TextureRegion(texture, 0, 20, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food1_0.flip(false, true);
        food1_1 = new TextureRegion(texture, 9, 20, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food1_1.flip(false, true);
        food1_2 = new TextureRegion(texture, 18, 20, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food1_2.flip(false, true);
        food1_3 = new TextureRegion(texture, 27, 20, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food1_3.flip(false, true);
        food1_4 = new TextureRegion(texture, 36, 20, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food1_4.flip(false, true);

        food2_0 = new TextureRegion(texture, 0, 29, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food2_0.flip(false, true);
        food2_1 = new TextureRegion(texture, 9, 29, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food2_1.flip(false, true);
        food2_2 = new TextureRegion(texture, 18, 29, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food2_2.flip(false, true);
        food2_3 = new TextureRegion(texture, 27, 29, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food2_3.flip(false, true);
        food2_4 = new TextureRegion(texture, 36, 29, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food2_4.flip(false, true);

        food3_0 = new TextureRegion(texture, 0, 38, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food3_0.flip(false, true);
        food3_1 = new TextureRegion(texture, 9, 38, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food3_1.flip(false, true);
        food3_2 = new TextureRegion(texture, 18, 38, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food3_2.flip(false, true);
        food3_3 = new TextureRegion(texture, 27, 38, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food3_3.flip(false, true);
        food3_4 = new TextureRegion(texture, 36, 38, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food3_4.flip(false, true);

        food4_0 = new TextureRegion(texture, 0, 47, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food4_0.flip(false, true);
        food4_1 = new TextureRegion(texture, 9, 47, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food4_1.flip(false, true);
        food4_2 = new TextureRegion(texture, 18, 47, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food4_2.flip(false, true);
        food4_3 = new TextureRegion(texture, 27, 47, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food4_3.flip(false, true);
        food4_4 = new TextureRegion(texture, 36, 47, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food4_4.flip(false, true);

        gameOver = new TextureRegion(texture, 59, 92, 46, 7);
        gameOver.flip(false, true);

        scoreboard = new TextureRegion(texture, 111, 83, 97, 37);
        scoreboard.flip(false, true);

        star = new TextureRegion(texture, 152, 70, 10, 10);
        noStar = new TextureRegion(texture, 165, 70, 10, 10);

        star.flip(false, true);
        noStar.flip(false, true);

        highScore = new TextureRegion(texture, 59, 101, 48, 7);
        highScore.flip(false, true);

        zbLogo = new TextureRegion(texture, 0, 55, 122, 24);
        zbLogo.flip(false, true);

        bg = new TextureRegion(texture, 87, 1, 8, 8);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        pacmanOpen = new TextureRegion(texture, 0, 0, Textures.PACMAN_SIZE, Textures.PACMAN_SIZE);//13,9
        pacmanOpen.flip(false, true);
        pacmanHalfOpen = new TextureRegion(texture, 17, 0, Textures.PACMAN_SIZE, Textures.PACMAN_SIZE);//13,12
        pacmanHalfOpen.flip(false, true);
        pacmanClosed = new TextureRegion(texture, 34, 0, Textures.PACMAN_SIZE, Textures.PACMAN_SIZE);
        pacmanClosed.flip(false, true);

        pacmanOpenSuper = new TextureRegion(texture, 105, 0, Textures.PACMAN_SIZE, Textures.PACMAN_SIZE);//13,9
        pacmanOpenSuper.flip(false, true);
        pacmanHalfOpenSuper = new TextureRegion(texture, 122, 0, Textures.PACMAN_SIZE, Textures.PACMAN_SIZE);//13,12
        pacmanHalfOpenSuper.flip(false, true);
        pacmanClosedSuper = new TextureRegion(texture, 139, 0, Textures.PACMAN_SIZE, Textures.PACMAN_SIZE);
        pacmanClosedSuper.flip(false, true);

        pipeTopUp_0 = new TextureRegion(texture, 71, 1, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT);
        pipeTopUp_1 = new TextureRegion(texture, 71, 8, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT);
        pipeTopUp_2 = new TextureRegion(texture, 71, 15, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT);

        // Create by flipping existing pipeTopUp
        pipeTopDown_0 = new TextureRegion(pipeTopUp_0);
        pipeTopDown_0.flip(false, true);
        pipeTopDown_1 = new TextureRegion(pipeTopUp_1);
        pipeTopDown_1.flip(false, true);
        pipeTopDown_2 = new TextureRegion(pipeTopUp_2);
        pipeTopDown_2.flip(false, true);

        pipe_0 = new TextureRegion(texture, 56, 2, Textures.PIPE_WIDTH, Textures.PIPE_HEIGHT);
        pipe_0.flip(false, true);
        pipe_1 = new TextureRegion(texture, 56, 9, Textures.PIPE_WIDTH, Textures.PIPE_HEIGHT);
        pipe_1.flip(false, true);
        pipe_2 = new TextureRegion(texture, 56, 16, Textures.PIPE_WIDTH, Textures.PIPE_HEIGHT);
        pipe_2.flip(false, true);
//
//        pipe_0 = new TextureRegion(texture, 128, 0, Textures.PIPE_WIDTH, Textures.PIPE_HEIGHT);
//        pipe_0.flip(false, true);
//        pipe_1 = new TextureRegion(texture, 128, 0, Textures.PIPE_WIDTH, Textures.PIPE_HEIGHT);
//        pipe_1.flip(false, true);
//        pipe_2 = new TextureRegion(texture, 128, 0, Textures.PIPE_WIDTH, Textures.PIPE_HEIGHT);
//        pipe_2.flip(false, true);

//        ground_0 = new TextureRegion(texture, 37, 18, 48, 5);
//        ground_0.flip(false, true);
//        ground_1 = new TextureRegion(texture, 37, 24, 48, 5);
//        ground_1.flip(false, true);
//        ground_2 = new TextureRegion(texture, 37, 30, 48, 5);
//        ground_2.flip(false, true);

        TextureRegion[] pacmans = {pacmanOpen, pacmanHalfOpen, pacmanClosed};
        pacmanAnimation = new Animation(0.06f, pacmans);
        pacmanAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] pacmansSuper = {pacmanOpenSuper, pacmanHalfOpenSuper, pacmanClosedSuper};
        pacmanAnimationSuper = new Animation(0.06f, pacmansSuper);
        pacmanAnimationSuper.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] food1s = {food1_0, food1_0, food1_0, food1_0, food1_0, food1_0, food1_0, food1_0, food1_1, food1_2, food1_3, food1_4};
        food1Animation = new Animation(0.1f, food1s);
        food1Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] food2s = {food2_0, food2_0, food2_0, food2_0, food2_0, food2_0, food2_0, food2_0, food2_1, food2_2, food2_3, food2_4};
        food2Animation = new Animation(0.1f, food2s);
        food2Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] food3s = {food3_0, food3_0, food3_0, food3_0, food3_0, food3_0, food3_0, food3_0, food3_1, food3_2, food3_3, food3_4};
        food3Animation = new Animation(0.1f, food3s);
        food3Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] food4s = {food4_0, food4_0, food4_0, food4_0, food4_0, food4_0, food4_0, food4_0, food4_1, food4_2, food4_3, food4_4};
        food4Animation = new Animation(0.1f, food4s);
        food4Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] pipes = {pipe_0, pipe_1, pipe_2};
        pipeAnimation = new Animation(0.15f, pipes);
        pipeAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] pipeTopUps = {pipeTopUp_0, pipeTopUp_1, pipeTopUp_2};
        pipeTopUpAnimation = new Animation(0.15f, pipeTopUps);
        pipeTopUpAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] pipeTopDowns = {pipeTopDown_0, pipeTopDown_1, pipeTopDown_2};
        pipeTopDownAnimation = new Animation(0.15f, pipeTopDowns);
        pipeTopDownAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

//        TextureRegion[] grounds = {ground_0, ground_1, ground_2};
//        groundAnimation = new Animation(0.15f, grounds);
//        groundAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

//        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
//        flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
//        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
//        fall = Gdx.audio.newSound(Gdx.files.internal("fall.wav"));

        highscore = Gdx.audio.newSound(Gdx.files.internal("highscore.ogg"));
        scoreUp = Gdx.audio.newSound(Gdx.files.internal("scoreUp.ogg"));
        doubleTap = Gdx.audio.newSound(Gdx.files.internal("doubleTap.ogg"));
        tap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
        dead1 = Gdx.audio.newSound(Gdx.files.internal("spaceTrash1.ogg"));
        dead2 = Gdx.audio.newSound(Gdx.files.internal("spaceTrash2.ogg"));
        dead3 = Gdx.audio.newSound(Gdx.files.internal("spaceTrash3.ogg"));
        dead4 = Gdx.audio.newSound(Gdx.files.internal("spaceTrash4.ogg"));
        dead5 = Gdx.audio.newSound(Gdx.files.internal("spaceTrash5.ogg"));
        poison = Gdx.audio.newSound(Gdx.files.internal("poison.ogg"));
        healthy = Gdx.audio.newSound(Gdx.files.internal("healthyFood.ogg"));

        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        font.getData().setScale(.25f, -.25f);

        whiteFont = new BitmapFont(Gdx.files.internal("whitetext.fnt"));
        whiteFont.getData().setScale(.1f, -.1f);

        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        shadow.getData().setScale(.25f, -.25f);

        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("Pacman");

        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static void dispose() {
        // We must dispose of the texture when we are finished.
        texture.dispose();

        // Dispose sounds
//        dead.dispose();
//        flap.dispose();
//        coin.dispose();

        highscore.dispose();
        scoreUp.dispose();
        doubleTap.dispose();
        tap.dispose();
        dead1.dispose();
        dead2.dispose();
        dead3.dispose();
        dead4.dispose();
        dead5.dispose();
        poison.dispose();
        healthy.dispose();

        font.dispose();
        shadow.dispose();
    }

}