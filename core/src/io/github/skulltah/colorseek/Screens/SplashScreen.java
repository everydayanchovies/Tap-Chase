package io.github.skulltah.colorseek.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.AssetLoader;

public class SplashScreen implements Screen {

    private TweenManager manager;
    private SpriteBatch batcher;
    private Sprite sprite;
    private CSGame game;

    public SplashScreen(CSGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        sprite = new Sprite(AssetLoader.logo);
        sprite.setColor(1, 1, 1, 0);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float desiredWidth = width * .7f;
        float scale = desiredWidth / sprite.getWidth();

        sprite.setSize(sprite.getWidth() * scale, sprite.getHeight() * scale);
        sprite.setPosition((width / 2) - (sprite.getWidth() / 2), (height / 2)
                - (sprite.getHeight() / 2));

        setupTween();

        batcher = new SpriteBatch();

        Preferences prefs = Gdx.app.getPreferences("generalPrefs");
        boolean hasOfferedToSignIn = prefs.getBoolean("hasOfferedToSignIn", false);
        boolean isSignedIn = prefs.getBoolean("isSignedIn", false);
        if (!hasOfferedToSignIn || isSignedIn) {
            CSGame.playServices.signIn();
            if (!hasOfferedToSignIn) {
                prefs.putBoolean("hasOfferedToSignIn", true);
                prefs.flush();
            }
        }

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                setupTween2();
            }
        }, .8f);
    }

    private void setupTween() {
        Tween.registerAccessor(Sprite.class, new io.github.skulltah.colorseek.TweenAccessors.SpriteAccessor());
        manager = new TweenManager();

//        TweenCallback cb = new TweenCallback() {
//            @Override
//            public void onEvent(int type, BaseTween<?> source) {
//                game.setScreen(new GameScreen());
//            }
//        };

        Tween.to(sprite, io.github.skulltah.colorseek.TweenAccessors.SpriteAccessor.ALPHA, .6f).target(1)
                .ease(TweenEquations.easeInOutQuad)
//                .repeatYoyo(1, 1)
//                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    private void setupTween2() {
        Tween.registerAccessor(Sprite.class, new io.github.skulltah.colorseek.TweenAccessors.SpriteAccessor());
        manager = new TweenManager();

        TweenCallback cb = new TweenCallback() {
            @Override
            public void onEvent(int type, BaseTween<?> source) {
                game.setScreen(new GameScreen(game));
            }
        };

        Tween.from(sprite, io.github.skulltah.colorseek.TweenAccessors.SpriteAccessor.ALPHA, .6f).target(1)
                .ease(TweenEquations.easeInOutQuad)
//                .repeatYoyo(1, 1)
                .setCallback(cb).setCallbackTriggers(TweenCallback.COMPLETE)
                .start(manager);
    }

    @Override
    public void render(float delta) {
        manager.update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batcher.begin();
        sprite.draw(batcher);
        batcher.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
