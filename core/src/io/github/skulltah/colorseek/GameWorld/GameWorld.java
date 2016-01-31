package io.github.skulltah.colorseek.GameWorld;

import com.badlogic.gdx.utils.Timer;

import io.github.skulltah.colorseek.Constants.Values;
import io.github.skulltah.colorseek.GameObjects.Pacman;

public class GameWorld {

    private Pacman pacman;
    private io.github.skulltah.colorseek.GameObjects.ScrollHandler scroller;
    //    private Rectangle ground;
    private int score = 0;
    private float runTime = 0;
    private int midPointY;
    private GameRenderer renderer;

    private GameState currentState;

    public GameWorld(int midPointY) {
        currentState = GameState.MENU;
        this.midPointY = midPointY;
        pacman = new Pacman(33, midPointY - 8);
        scroller = new io.github.skulltah.colorseek.GameObjects.ScrollHandler(this, midPointY + Values.GAME_HEIGHT_FROM_MIDDLEPOINT);
//        ground = new Rectangle(0, midPointY + 66, 137, 11);
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
            default:
                break;
        }
    }

    private void updateReady(float delta) {
        pacman.updateReady(runTime);
        scroller.updateReady(delta);
    }

    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        pacman.update(delta);
        scroller.update(delta);

        if (scroller.collides(pacman) && pacman.isAlive()) {
            scroller.stop();
            pacman.die();

            double r = Math.random();
            if (r < .2f)
                io.github.skulltah.colorseek.ZBHelpers.AssetLoader.dead1.play();
            else if (r < .4f)
                io.github.skulltah.colorseek.ZBHelpers.AssetLoader.dead2.play();
            else if (r < .6f)
                io.github.skulltah.colorseek.ZBHelpers.AssetLoader.dead3.play();
            else if (r < .8f)
                io.github.skulltah.colorseek.ZBHelpers.AssetLoader.dead4.play();
            else
                io.github.skulltah.colorseek.ZBHelpers.AssetLoader.dead5.play();

            renderer.prepareTransition(255, 0, 0, .3f);

//            AssetLoader.fall.play();

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    currentState = GameState.GAMEOVER;

                    if (score > io.github.skulltah.colorseek.ZBHelpers.AssetLoader.getHighScore()) {
                        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.setHighScore(score);
                        currentState = GameState.HIGHSCORE;

                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                io.github.skulltah.colorseek.ZBHelpers.AssetLoader.highscore.play();
                            }
                        }, .5f);
                    }
                }
            }, .7f);
        }

//        if (Intersector.overlaps(pacman.getBoundingCircle(), ground)) {
//            if (pacman.isAlive()) {
//                AssetLoader.dead.play();
//                renderer.prepareTransition(255, 255, 255, .3f);
//
//                pacman.die();
//            }
//
//            scroller.stop();
//            pacman.decelerate();
//            currentState = GameState.GAMEOVER;
//
//            if (score > AssetLoader.getHighScore()) {
//                AssetLoader.setHighScore(score);
//                currentState = GameState.HIGHSCORE;
//            }
//        }
    }

    public Pacman getPacman() {
        return pacman;
    }

    public int getMidPointY() {
        return midPointY;
    }

    public io.github.skulltah.colorseek.GameObjects.ScrollHandler getScroller() {
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
    }

    public void ready() {
        currentState = GameState.READY;
        renderer.prepareTransition(0, 0, 0, 1f);
    }

    public void restart() {
        score = 0;
        pacman.onRestart(midPointY - 5);
        scroller.onRestart();
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

    public void setRenderer(GameRenderer renderer) {
        this.renderer = renderer;
    }

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE
    }
}
