package io.github.skulltah.colorseek.ZBHelpers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

import io.github.skulltah.colorseek.Constants.Values;

public class InputHandler implements InputProcessor {
    private io.github.skulltah.colorseek.GameObjects.Pacman myPacman;
    private io.github.skulltah.colorseek.GameWorld.GameWorld myWorld;

    private List<io.github.skulltah.colorseek.ui.SimpleButton> menuButtons;

    private io.github.skulltah.colorseek.ui.SimpleButton playButton;
    private io.github.skulltah.colorseek.ui.SimpleButton retryButton;

    private float scaleFactorX;
    private float scaleFactorY;

    public InputHandler(io.github.skulltah.colorseek.GameWorld.GameWorld myWorld, float scaleFactorX,
                        float scaleFactorY) {
        this.myWorld = myWorld;
        myPacman = myWorld.getPacman();

        int midPointY = myWorld.getMidPointY();

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        menuButtons = new ArrayList<io.github.skulltah.colorseek.ui.SimpleButton>();
        playButton = new io.github.skulltah.colorseek.ui.SimpleButton(
                Values.GAME_WIDTH / 2 - (AssetLoader.playButtonUp.getRegionWidth() / 2),
                midPointY + 50, 29, 16, AssetLoader.playButtonUp,
                AssetLoader.playButtonDown);
        retryButton = new io.github.skulltah.colorseek.ui.SimpleButton(
                36, midPointY + 10, 66, 14, AssetLoader.retry,
                AssetLoader.retry);
        menuButtons.add(playButton);
        menuButtons.add(retryButton);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (myWorld.isMenu()) {
            playButton.isTouchDown(screenX, screenY);
        } else if (myWorld.isReady()) {
            myWorld.start();
            myPacman.onClick();
        } else if (myWorld.isRunning()) {
            myPacman.onClick();
        }

        if (myWorld.isGameOver() || myWorld.isHighScore()) {
//			myWorld.restart();
            retryButton.isTouchDown(screenX, screenY);
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
        }
        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            if (retryButton.isTouchUp(screenX, screenY)) {
                myWorld.restart();
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
