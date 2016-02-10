package io.github.skulltah.colorseek.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.InputHandler;
import io.github.skulltah.colorseek.Constants.Values;
import io.github.skulltah.colorseek.GameWorld.GameRenderer;
import io.github.skulltah.colorseek.GameWorld.GameWorld;

public class GameScreen implements Screen {

    public static GameWorld world;
    private GameRenderer renderer;
    private float runTime;

    public GameScreen(CSGame game) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = Values.GAME_WIDTH;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int) (gameHeight / 2);

        Gdx.input.setCatchBackKey(true);

        world = new GameWorld(game, midPointY);
        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight, game));
        renderer = new GameRenderer(world, (int) gameHeight, midPointY);
        world.setRenderer(renderer);
    }

    @Override
    public void render(float delta) {
        runTime += delta;
        world.update(delta);
        renderer.render(delta, runTime);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

}
