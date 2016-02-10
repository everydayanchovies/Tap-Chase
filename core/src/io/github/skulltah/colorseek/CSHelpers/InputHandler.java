package io.github.skulltah.colorseek.CSHelpers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.Constants.Values;
import io.github.skulltah.colorseek.GameObjects.Pacman;
import io.github.skulltah.colorseek.GameWorld.GameWorld;
import io.github.skulltah.colorseek.ui.SimpleButton;

public class InputHandler implements InputProcessor {
    private CSGame game;
    private Pacman myPacman;
    private GameWorld myWorld;

    private List<SimpleButton> menuButtons;

    private SimpleButton playButton;
    private SimpleButton retryButton;
    private SimpleButton leaderboardButton;
    private SimpleButton achievementsButton;

    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(GameWorld myWorld, float scaleFactorX,
                        float scaleFactorY, CSGame game) {
        this.game = game;
        this.myWorld = myWorld;
        myPacman = myWorld.getPacman();

        int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<SimpleButton>();
        playButton = new SimpleButton(
                Values.GAME_WIDTH / 2 - AssetLoader.playButtonUp.getRegionWidth() - 2,
                midPointY + 30,
                AssetLoader.playButtonUp.getRegionWidth(), AssetLoader.playButtonUp.getRegionHeight(),
                AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        retryButton = new SimpleButton(
                Values.GAME_WIDTH / 2 - AssetLoader.playButtonUp.getRegionWidth() - 2,
                midPointY + 30,
                AssetLoader.playButtonUp.getRegionWidth(), AssetLoader.playButtonUp.getRegionHeight(),
                AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        leaderboardButton = new SimpleButton(
                Values.GAME_WIDTH / 2 + 2,
                midPointY + 30,
                AssetLoader.leaderboardUp.getRegionWidth(), AssetLoader.leaderboardUp.getRegionHeight(),
                AssetLoader.leaderboardUp,
                AssetLoader.leaderboardDown);
        achievementsButton = new SimpleButton(
                Values.GAME_WIDTH / 2 - AssetLoader.achievements.getRegionWidth() * .7f / 2,
                midPointY + 62,
                AssetLoader.achievements.getRegionWidth(), AssetLoader.achievements.getRegionHeight(),
                .7f,
                AssetLoader.achievements,
                AssetLoader.achievements);
        menuButtons.add(playButton);
        menuButtons.add(retryButton);
        menuButtons.add(leaderboardButton);
        menuButtons.add(achievementsButton);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
            leaderboardButton.isTouchDown(screenX, screenY);
            achievementsButton.isTouchDown(screenX, screenY);
        } else if (myWorld.isReady()) {
            myWorld.start();
            myPacman.onClick();
        } else if (myWorld.isRunning()) {
            myPacman.onClick();
        }

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
//			myWorld.restart();
            retryButton.isTouchDown(screenX, screenY);
            leaderboardButton.isTouchDown(screenX, screenY);
            achievementsButton.isTouchDown(screenX, screenY);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            if (playButton.isTouchUp(screenX, screenY)) {
                myWorld.ready();
                return true;
            }
            if (leaderboardButton.isTouchUp(screenX, screenY)) {
                CSGame.playServices.showScore();
                return true;
            }
            if (achievementsButton.isTouchUp(screenX, screenY)) {
                CSGame.playServices.showAchievement();
                return true;
            }
        }
        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            if (retryButton.isTouchUp(screenX, screenY)) {
                myWorld.restart();
                return true;
            }
            if (leaderboardButton.isTouchUp(screenX, screenY)) {
                CSGame.playServices.showScore();
                return true;
            }
            if (achievementsButton.isTouchUp(screenX, screenY)) {
                CSGame.playServices.showAchievement();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {

        // Can now use Space Bar to play the game
        if (keycode == Keys.SPACE) {

            if (myWorld.isMenu()) {
                myWorld.ready();
            } else if (myWorld.isReady()) {
                myWorld.start();
            }

            myPacman.onClick();

            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                myWorld.restart();
            }

        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<io.github.skulltah.colorseek.ui.SimpleButton> getMenuButtons() {
        return menuButtons;
    }
}
