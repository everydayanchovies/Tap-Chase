package io.github.skulltah.colorseek;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MyGdxGame extends ApplicationAdapter {
    public static MyGdxGame c;
    public Vector2 touchPos = new Vector2();
    public boolean isMoving;
    SpriteBatch batch;
    Sprite playerSprite;
    Texture img;
    float speed = 25;

    @Override
    public void create() {
        c = this;
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        playerSprite = new Sprite(img, 20, 20, 50, 50);
        MyInputProcessor myInputProc = new MyInputProcessor();
        Gdx.input.setInputProcessor(myInputProc);
        isMoving = true;
    }

    @Override
    public void render() {
        movePlayer();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        playerSprite.draw(batch);
        batch.end();
    }

    private void movePlayer() {
        if (!isMoving || (touchPos.x <= 0 && touchPos.y <= 0)) return;

        Vector2 originalPoint = new Vector2(playerSprite.getX(), playerSprite.getY());

        Vector2 delta = touchPos.sub(originalPoint).nor();

        Gdx.app.log("Distance", String.valueOf(touchPos.dst(playerSprite.getX(), playerSprite.getY())));
        if (touchPos.dst(playerSprite.getX(), playerSprite.getY()) > .5)
            playerSprite.setPosition(originalPoint.x + delta.x * speed, originalPoint.y + delta.y * speed);
    }
}
