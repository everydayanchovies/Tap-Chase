package io.github.skulltah.colorseek.GameWorld;

import com.badlogic.gdx.utils.Timer;

import de.tomgrill.gdxfacebook.core.GDXFacebook;
import de.tomgrill.gdxfacebook.core.GDXFacebookConfig;
import de.tomgrill.gdxfacebook.core.GDXFacebookSystem;
import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.AssetLoader;
import io.github.skulltah.colorseek.CSHelpers.FacebookHelper;
import io.github.skulltah.colorseek.CSHelpers.PostGameEvaluator;
import io.github.skulltah.colorseek.Constants.Values;
import io.github.skulltah.colorseek.GameObjects.GodlikeShape;
import io.github.skulltah.colorseek.GameObjects.Pacman;
import io.github.skulltah.colorseek.GameObjects.ScrollHandler;

public class GameWorld {

    private Pacman pacman;
    private io.github.skulltah.colorseek.GameObjects.ScrollHandler scroller;
    //    private Rectangle ground;
    private int score = 0;
    private float runTime = 0;
    private int midPointY;
    private GameRenderer renderer;
    private PostGameEvaluator postGameEvaluator;
    private CSGame game;
    private FacebookHelper facebook;

    private GameState currentState;

    public GameWorld(CSGame game, int midPointY) {
        this.game = game;
        this.midPointY = midPointY;
        currentState = GameState.MENU;
        pacman = new Pacman(game, 33, midPointY - 8);
        scroller = new ScrollHandler(game, this, midPointY + Values.GAME_HEIGHT_FROM_MIDDLEPOINT);
        postGameEvaluator = new PostGameEvaluator(game);
//        ground = new Rectangle(0, midPointY + 66, 137, 11);

        // Facebook
        GDXFacebookConfig facebookConfig = new GDXFacebookConfig();
        facebookConfig.APP_ID = "1005660309524792"; // required
        facebookConfig.PREF_FILENAME = ".facebookSessionData"; // optional
        facebookConfig.GRAPH_API_VERSION = "v2.5"; // optional, default is v2.5
        GDXFacebook gdxFacebook = GDXFacebookSystem.install(facebookConfig);
        this.facebook = new FacebookHelper(gdxFacebook, game);
    }

    public void update(float delta) {
        runTime += delta;

        switch (currentState) {
            case READY:
            case MENU:
                updateReady(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case PAUSED:
                updatePaused(delta);
                break;
            default:
                break;
        }
    }

    private void updateReady(float delta) {
        pacman.updateReady(runTime);
        scroller.updateReady(delta);
    }

    private void updatePaused(float delta) {
//        pacman.updatePaused(runTime);
//        scroller.updatePaused(delta);
    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        pacman.update(delta);
        scroller.update(delta);

        if (scroller.collides(pacman) && pacman.isAlive()) {
            pacman.die();
            if (!pacman.getIsSuper()) {
                scroller.stop();

                double r = Math.random();
                if (r < .2f)
                    AssetLoader.dead1.play();
                else if (r < .4f)
                    AssetLoader.dead2.play();
                else if (r < .6f)
                    AssetLoader.dead3.play();
                else if (r < .8f)
                    AssetLoader.dead4.play();
                else
                    AssetLoader.dead5.play();

                renderer.prepareTransition(255, 0, 0, .3f);

                postGameEvaluator.score(AssetLoader.getHighScore(), score);

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        currentState = GameState.GAMEOVER;

                        if (score > AssetLoader.getHighScore()) {
                            AssetLoader.setHighScore(score);
                            currentState = GameState.HIGHSCORE;

                            Timer.schedule(new Timer.Task() {
                                @Override
                                public void run() {
                                    AssetLoader.highscore.play();
                                }
                            }, .5f);
                        }
                    }
                }, .7f);
            }
        }
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getMidPointY() {
        return midPointY;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int increment) {
        score += increment;
    }

    public void start() {
        currentState = GameState.RUNNING;

        for (GodlikeShape godlikeShape : scroller.getGodlikeShapes()) {
            godlikeShape.setSpeed(godlikeShape.getSpeed() * .7f);
        }
    }

    public void pause() {
        currentState = GameState.PAUSED;
    }

    public void resume() {
        currentState = GameState.RUNNING;
    }

    public void goToMainMenu() {
        renderer.prepareTransition(0, 0, 0, 1f);
        currentState = GameState.MENU;
        score = 0;
        pacman.onRestart(midPointY - 5);
        scroller.onRestart();
        game.powerupManager.disablePowerups();
        renderer.generateBackground();
    }

    public void ready() {
        renderer.prepareTransition(0, 0, 0, 1f);
        currentState = GameState.READY;
    }

    public void restart() {
        score = 0;
        pacman.onRestart(midPointY - 5);
        scroller.onRestart();
        game.powerupManager.disablePowerups();
        renderer.generateBackground();

        for (GodlikeShape godlikeShape : scroller.getGodlikeShapes()) {
            godlikeShape.setSpeed(godlikeShape.getSpeed() / .7f);
        }

        ready();
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public FacebookHelper getFacebook() {
        return facebook;
    }

    public enum GameState {
        MENU, READY, RUNNING, PAUSED, GAMEOVER, HIGHSCORE
    }
}
