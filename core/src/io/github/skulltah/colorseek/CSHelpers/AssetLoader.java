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

    private static final float PIPE_ANIM_SPEED = .32f;
    public static Texture texture, logoTexture;
    public static TextureRegion logo, zbLogo, bg, grass,
            pacmanHalfOpen, pacmanOpen, pacmanClosed,
            pacmanHalfOpenSuper, pacmanOpenSuper, pacmanClosedSuper,
            playButtonUp, playButtonDown,
            ready, gameOver, highScore, scoreboard, star, noStar, retry, leaderboardUp, leaderboardDown, help, achievements,
    //            food1_0, food1_1, food1_2, food1_3, food1_4,
    food1,
            pipeTopUp_0, pipeTopUp_1, pipeTopUp_2,
            pipeTopDown_0, pipeTopDown_1, pipeTopDown_2,
            pipe_0, pipe_1, pipe_2,
            square, triangle, circle;
    public static TextureRegion[] food2Frames = new TextureRegion[5];
    public static TextureRegion[] food3Frames = new TextureRegion[5];
    public static TextureRegion[] food4Frames = new TextureRegion[5];
    public static TextureRegion[] food5Frames = new TextureRegion[5];
    public static TextureRegion[] powerupPipeGapFrames = new TextureRegion[2];
    public static TextureRegion[] powerupSpeedUpFrames = new TextureRegion[8];
    //    public static TextureRegion[] powerupSpeedDownFrames = new TextureRegion[8];
    public static TextureRegion[] backgroundTiles = new TextureRegion[5];
    public static Animation pacmanAnimation, pacmanAnimationSuper,
    //            food1Animation,
    food2Animation, food3Animation, food4Animation, food5Animation, food6Animation,
            pipeAnimation, pipeTopUpAnimation, pipeTopDownAnimation,
            powerupPipeGapAnimation,
            powerupSpeedUpAnimation
//            powerupSpeedDownAnimation
//            , groundAnimation
                    ;
    public static Sound
//            dead, flap, coin, fall
            highscore, scoreUp, doubleTap, tap, dead1, dead2, dead3, dead4, dead5, poison, healthy,
            powerup,
            radar1, radar2;
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

        square = new TextureRegion(texture, 0, 254, 9, 9);
        square.flip(false, true);

        triangle = new TextureRegion(texture, 9 + 1, 254, 9, 9);
        triangle.flip(false, true);

        circle = new TextureRegion(texture, (9 + 1) * 2, 254, 9, 9);
        circle.flip(false, true);

        food1 = new TextureRegion(texture, 0, 20, Textures.FOOD_SIZE, Textures.FOOD_SIZE);
        food1.flip(false, true);

        food2Frames = loadSpriteSeries(true, 29, 1, Textures.FOOD_SIZE, Textures.FOOD_SIZE, food2Frames);
        food3Frames = loadSpriteSeries(true, 38, 1, Textures.FOOD_SIZE, Textures.FOOD_SIZE, food3Frames);
        food4Frames = loadSpriteSeries(true, 47, 1, Textures.FOOD_SIZE, Textures.FOOD_SIZE, food4Frames);
        food5Frames = loadSpriteSeries(true, 20, 1, 46, Textures.FOOD_SIZE, Textures.FOOD_SIZE, food5Frames);

        powerupPipeGapFrames = loadSpriteSeries(true, 1, 1, 250, 11, 17, powerupPipeGapFrames);
//        powerupSpeedDownFrames = loadSpriteSeries(true, 19, 1, 250, 11, 17, powerupSpeedDownFrames);
        powerupSpeedUpFrames = loadSpriteSeries(true, 37, 1, 250, 11, 17, powerupSpeedUpFrames);

        backgroundTiles = loadSpriteSeries(false, 412, 1, 100, 100, backgroundTiles);

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

        zbLogo = new TextureRegion(texture, 0, 55, 108, 24);
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

//        TextureRegion[] food1s = {food1_0, food1_0, food1_0, food1_0, food1_0, food1_0, food1_0, food1_0, food1_1, food1_2, food1_3, food1_4};
//        food1Animation = new Animation(0.1f, food1s);
//        food1Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] _food2s = {food2Frames[0], food2Frames[0], food2Frames[0], food2Frames[0], food2Frames[0], food2Frames[0], food2Frames[0], food2Frames[0], food2Frames[1], food2Frames[2], food2Frames[3], food2Frames[4]};
        food2Animation = new Animation(0.1f, _food2s);
        food2Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] _food3s = {food3Frames[0], food3Frames[0], food3Frames[0], food3Frames[0], food3Frames[0], food3Frames[0], food3Frames[0], food3Frames[0], food3Frames[1], food3Frames[2], food3Frames[3], food3Frames[4]};
        food3Animation = new Animation(0.1f, _food3s);
        food3Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] _food4s = {food4Frames[0], food4Frames[0], food4Frames[0], food4Frames[0], food4Frames[0], food4Frames[0], food4Frames[0], food4Frames[0], food4Frames[1], food4Frames[2], food4Frames[3], food4Frames[4]};
        food4Animation = new Animation(0.1f, _food4s);
        food4Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] _food5s = {food5Frames[0], food5Frames[0], food5Frames[0], food5Frames[0], food5Frames[0], food5Frames[0], food5Frames[0], food5Frames[0], food5Frames[1], food5Frames[2], food5Frames[3], food5Frames[4]};
        food5Animation = new Animation(0.1f, _food5s);
        food5Animation.setPlayMode(Animation.PlayMode.LOOP);

        TextureRegion[] pipes = {pipe_0, pipe_1, pipe_2};
        pipeAnimation = new Animation(PIPE_ANIM_SPEED, pipes);
        pipeAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] pipeTopUps = {pipeTopUp_0, pipeTopUp_1, pipeTopUp_2};
        pipeTopUpAnimation = new Animation(PIPE_ANIM_SPEED, pipeTopUps);
        pipeTopUpAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        TextureRegion[] pipeTopDowns = {pipeTopDown_0, pipeTopDown_1, pipeTopDown_2};
        pipeTopDownAnimation = new Animation(PIPE_ANIM_SPEED, pipeTopDowns);
        pipeTopDownAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);

        powerupPipeGapAnimation = new Animation(0.35f, powerupPipeGapFrames);
        powerupPipeGapAnimation.setPlayMode(Animation.PlayMode.LOOP);

        powerupSpeedUpAnimation = new Animation(0.12f, powerupSpeedUpFrames);
        powerupSpeedUpAnimation.setPlayMode(Animation.PlayMode.LOOP);

//        powerupSpeedDownAnimation = new Animation(0.3f, powerupSpeedDownFrames);
//        powerupSpeedDownAnimation.setPlayMode(Animation.PlayMode.LOOP);

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
        powerup = Gdx.audio.newSound(Gdx.files.internal("powerUp6.ogg"));
        radar1 = Gdx.audio.newSound(Gdx.files.internal("radar2.ogg"));
        radar2 = Gdx.audio.newSound(Gdx.files.internal("radar3.ogg"));

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

    private static TextureRegion[] loadSpriteSeries(boolean vertical, int position, int spacing, int width, int height, TextureRegion[] textureRegions) {
        int i = 0;
        for (TextureRegion textureRegion : textureRegions) {
            int x = vertical ? (width + spacing) * i : position;
            int y = !vertical ? (width + spacing) * i : position;
            textureRegion = new TextureRegion(texture, x, y, width, height);
            textureRegion.flip(false, true);
            textureRegions[i] = textureRegion;
            i++;
        }
        return textureRegions;
    }

    private static TextureRegion[] loadSpriteSeries(boolean vertical, int position, int spacing, int offset, int width, int height, TextureRegion[] textureRegions) {
        int i = 0;
        for (TextureRegion textureRegion : textureRegions) {
            int x = vertical ? ((width + spacing) * i) + offset : position;
            int y = !vertical ? ((width + spacing) * i) + offset : position;
            textureRegion = new TextureRegion(texture, x, y, width, height);
            textureRegion.flip(false, true);
            textureRegions[i] = textureRegion;
            i++;
        }
        return textureRegions;
    }

    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }

    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    public static void dispose() {
        texture.dispose();

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
        powerup.dispose();

        font.dispose();
        shadow.dispose();
    }

}