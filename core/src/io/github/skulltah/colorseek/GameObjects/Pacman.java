package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.AssetLoader;
import io.github.skulltah.colorseek.CSHelpers.InGameEvaluator;
import io.github.skulltah.colorseek.Constants.IDs;
import io.github.skulltah.colorseek.Constants.Textures;
import io.github.skulltah.colorseek.Constants.Values;

public class Pacman {
    long millisTapped;
    private float MAX_SCALE = 6;
    private float scaleAmount = .1f;
    private Vector2 position;
    private Vector2 velocity;
    private float scale;
    private Vector2 acceleration;
    private float direction;
    private float verticalSpeed;
    private float rotation;
    private int width;
    private float height;
    private float originalY;
    private boolean isAlive;
    private Circle boundingCircle;
    private float gameHeight;
    private boolean isSuper;
    private InGameEvaluator inGameEvaluator;
    private CSGame game;

    public Pacman(CSGame game, float x, float y) {
        this.width = Textures.PACMAN_SIZE;
        this.height = Textures.PACMAN_SIZE;
        this.originalY = y;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        scale = 1;
        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
        isAlive = true;
        direction = 0;
        verticalSpeed = 95;
        this.isSuper = false;
        this.game = game;
        this.inGameEvaluator = new InGameEvaluator(game);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = Values.GAME_WIDTH;
        gameHeight = (int) screenHeight / (screenWidth / gameWidth);
    }

    public void update(float delta) {
        if (velocity.y != verticalSpeed)
            velocity.y = verticalSpeed;
        position.add(velocity.cpy().scl(delta * direction));

        if (scale >= 1) {
            if (isSuper)
                scale -= scaleAmount * 2 * delta;
            else
                scale -= scaleAmount * delta;
        } else if (scale < 1)
            scale = 1;
        if (scale > MAX_SCALE) {
            scale = MAX_SCALE;
        }

        if (position.y <= -6 || position.y >= gameHeight) {
            velocity.y = 0;
            direction *= -1;
            rotation *= -1;
        }

        boundingCircle.set(position.x + width / 2, position.y + height / 2, 5.5f * scale);
    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }

    public void updatePaused(float runTime) {
//        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }

    public boolean shouldntFlap() {
        return !isAlive;
    }

    public void onClick() {
        if (isAlive) {
            long timeElapsedSinceLastTap = System.currentTimeMillis() - millisTapped;
            millisTapped = System.currentTimeMillis();

            if (timeElapsedSinceLastTap < 150) {
                AssetLoader.doubleTap.play();
                inGameEvaluator.unlockGenericAchievement(IDs.achSkillz);
            } else
                AssetLoader.tap.play();

            direction = (direction == 0) ? -1 : direction * -1;
            rotation = direction == 1 ? 40 : -40;
        }
    }

    public void die() {
        if (isSuper) {
            return;
        }

        isAlive = false;
        velocity.y = 0;
        direction = 0;
    }

    // Returns true if eating went according to plan
    public boolean eat(float amount) {
        float newScale = scale + (isSuper ? (amount / 14) : (amount / 7));
        if (newScale < 1) {
            scale = 1;
            return false;
        }
        if (newScale > MAX_SCALE) {
            scale = MAX_SCALE;
            return false;
        }
        scale = newScale;
        return true;
    }

    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        scale = 1;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        direction = 0;
        isAlive = true;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }

    public Circle getBoundingCircle() {
        return boundingCircle;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public float getScale() {
        return scale;
    }

    public void makeSuper() {
        if (isSuper) return;
        this.isSuper = true;
        eat(15);
    }

    public boolean getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(boolean isSuper) {
        this.isSuper = isSuper;
    }
}
