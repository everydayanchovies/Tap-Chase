package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.AssetLoader;
import io.github.skulltah.colorseek.CSHelpers.InGameEvaluator;
import io.github.skulltah.colorseek.Constants.IDs;
import io.github.skulltah.colorseek.Constants.Values;

public class Pacman {
    long millisTapped;
    private float MAX_SCALE = 4;
    private float scaleAmmount = .1f;
    private Vector2 position;
    private Vector2 targetPosition;
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
    private long timeOfSuper;
    private InGameEvaluator inGameEvaluator;
    private CSGame game;

    public Pacman(CSGame game, float x, float y) {
        this.width = io.github.skulltah.colorseek.Constants.Textures.PACMAN_SIZE;
        this.height = io.github.skulltah.colorseek.Constants.Textures.PACMAN_SIZE;
        this.originalY = y;
        position = new Vector2(x, y);
        targetPosition = new Vector2(0, 0);
        velocity = new Vector2(0, 0);
        scale = 1;
        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
        isAlive = true;
        direction = 0;
        verticalSpeed = 95;
        this.isSuper = false;
        this.timeOfSuper = 0;
        this.game = game;
        this.inGameEvaluator = new InGameEvaluator(game);

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = Values.GAME_WIDTH;
        gameHeight = (int) screenHeight / (screenWidth / gameWidth);
    }

    public void update(float delta) {
        // Auto follow
//        Pipe closestPipe = GameScreen.world.getScroller().getClosestPipe(position);
//        if (isAlive && ((int) closestPipe.getX()) % 20 == 0)
//            scale += ThreadLocalRandom.current().nextFloat() * scaleAmmount;
//        float speed = 3f;
//        float endY = closestPipe.getGapPosition();
//        float directionY = endY - position.y;
//        position.y += directionY * speed * delta;

        // Flappy pacman
//		velocity.add(acceleration.cpy().scl(delta));
//        if (velocity.y > 200) {
//            velocity.y = 200;
//        }
//        position.add(velocity.cpy().scl(delta));
//        // Set the circle's center to be (9, 6) with respect to the pacman.
//        // Set the circle's radius to be 6.5f;
//		if (velocity.y < 0) { // Rotate counterclockwise
//			rotation -= 600 * delta;
//
//			if (rotation < -20) {
//				rotation = -20;
//			}
//		}
//        if (isFalling() || !isAlive) { // Rotate clockwise
//            rotation += 480 * delta;
//            if (rotation > 90) {
//                rotation = 90;
//            }
//        }

        // Pacman
        if (velocity.y != verticalSpeed)
            velocity.y = verticalSpeed;
        position.add(velocity.cpy().scl(delta * direction));

        if (scale >= 1)
            scale -= scaleAmmount * delta;
        else if (scale < 1)
            scale = 1;
        if (scale > MAX_SCALE) {
            scale = MAX_SCALE;
        }

//        if (position.y < 2) {
//            position.y = Gdx.graphics.getHeight() + 4;
//        }
//        if (position.y > gameHeight + 6)
//            position.y = -4;

        if (position.y <= -6 || position.y >= gameHeight + 4) {
            velocity.y = 0;
            direction *= -1;
            rotation *= -1;
        }

        boundingCircle.set(position.x + 7, position.y + 7, 5.5f * scale);

        if (isSuper) {
            long timeElapsedSinceSuper = System.currentTimeMillis() - timeOfSuper;
            if (timeElapsedSinceSuper > 6000)
                isSuper = false;
        }
    }

//    public boolean isFalling() {
//        return velocity.y > 110;
//    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + originalY;
    }

    public boolean shouldntFlap() {
        return !isAlive;
//        return velocity.y > 70 || !isAlive;
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

//            velocity.y = -140;
//            if (scale > 1f)
//                scale -= .4f;
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

//    public void decelerate() {
//        acceleration.y = 0;
//    }

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
        eat(8);
        this.timeOfSuper = System.currentTimeMillis();
    }

    public boolean getIsSuper() {
        return isSuper;
    }
}
