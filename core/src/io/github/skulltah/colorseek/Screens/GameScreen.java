package io.github.skulltah.colorseek.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import io.github.skulltah.colorseek.Constants.Values;
import io.github.skulltah.colorseek.ZBHelpers.InputHandler;

public class GameScreen implements Screen {

    public static io.github.skulltah.colorseek.GameWorld.GameWorld world;
    private io.github.skulltah.colorseek.GameWorld.GameRenderer renderer;
    private float runTime;

    public GameScreen() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = Values.GAME_WIDTH;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int) (gameHeight / 2);

        world = new io.github.skulltah.colorseek.GameWorld.GameWorld(midPointY);
        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight));
        renderer = new io.github.skulltah.colorseek.GameWorld.GameRenderer(world, (int) gameHeight, midPointY);
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
