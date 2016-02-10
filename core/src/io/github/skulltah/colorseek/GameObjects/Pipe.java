package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.InGameEvaluator;
import io.github.skulltah.colorseek.Constants.IDs;

public class Pipe extends Scrollable {
    private static final int VERTICAL_GAP = 44;
    private float verticalGap;
    private Random r;
    private Rectangle
            pipeDownNonlethal;
    private Rectangle pipeDownBelowNonlethal;
    private Rectangle pipe;
    private Rectangle pipeUp;
    private Rectangle pipeDown;
    private Rectangle pipeMiddle;
    private boolean isScored = false;
    private boolean isDoubleGaped = false;
    private InGameEvaluator inGameEvaluator;
    private CSGame game;
    private ScrollHandler scrollHandler;

    public Pipe(CSGame game, float x, float y, int width, int height, ScrollHandler scrollHandler) {
        super(x, y, width, height, scrollHandler.getScrollSpeed());
        this.game = game;
        this.inGameEvaluator = new InGameEvaluator(game);
        this.scrollHandler = scrollHandler;

        r = new Random();

        pipeUp = new Rectangle();
        pipeDown = new Rectangle();
        pipeMiddle = new Rectangle();
        pipeDownNonlethal = new Rectangle();
        pipeDownBelowNonlethal = new Rectangle();
        pipe = new Rectangle();
        this.verticalGap = VERTICAL_GAP;
    }

    public float getVerticalGap() {
        return verticalGap;
    }

    public Rectangle getPipeDownNonlethal() {
        return pipeDownNonlethal;
    }

    public Rectangle getPipeUp() {
        return pipeUp;
    }

    public Rectangle getPipeDown() {
        return pipeDown;
    }

    public Rectangle getPipeMiddle() {
        return pipeMiddle;
    }

    public Rectangle getPipeDownBelowNonlethal() {
        return pipeDownBelowNonlethal;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        pipe.set(position.x, position.y, width, 1000);

        if (isDoubleGaped())
            drawTwoGaps();
        else
            drawOneGap();

        if (isMoving()) {
            if (game.powerupManager.isPowerupActive(Powerup.PowerupType.PipeGap)) {
                if (verticalGap < VERTICAL_GAP * 1.2f)
                    verticalGap += VERTICAL_GAP * .5f * delta;
            } else {
                if (verticalGap > VERTICAL_GAP)
                    verticalGap -= VERTICAL_GAP * .5f * delta;
            }

            if (velocity.x != scrollHandler.getScrollSpeed())
                velocity.x = scrollHandler.getScrollSpeed();
        }
    }

    private void drawOneGap() {
        if (pipeMiddle != null)
            pipeMiddle = null;
        if (pipeDownNonlethal == null)
            pipeDownNonlethal = new Rectangle();
        if (pipeDownBelowNonlethal == null)
            pipeDownBelowNonlethal = new Rectangle();
        drawHitbox(pipeUp,
                position.x, position.y,
                width, height - 4);
        drawHitbox(pipeDown,
                position.x, position.y + height + 4 + verticalGap,
                width, position.y + height + verticalGap / 2);
        drawHitbox(pipeDownNonlethal,
                position.x, position.y + height + verticalGap * 3,
                width, verticalGap);
        drawHitbox(pipeDownBelowNonlethal,
                position.x, pipeDownNonlethal.y + verticalGap,
                width, 500);
    }

    private void drawTwoGaps() {
        if (pipeMiddle == null)
            pipeMiddle = new Rectangle();
        if (pipeDownNonlethal != null)
            pipeDownNonlethal = null;
        if (pipeDownBelowNonlethal != null)
            pipeDownBelowNonlethal = null;

        float middlePiece = verticalGap * 3;

        drawHitbox(pipeUp,
                position.x, position.y,
                width, height - 4);
        drawHitbox(pipeDown,
                position.x, position.y + height + middlePiece,
                width, position.y + height * 2 + middlePiece);
        drawHitbox(pipeMiddle,
                position.x, position.y + height + verticalGap,
                width, verticalGap - 4);
    }

    private void drawHitbox(Rectangle pipe, float x, float y, float w, float h) {
        if (pipe == null) return;
        pipe.set(x, y, w, h);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);

        height = r.nextInt(90) + 30;
        isScored = false;

        isDoubleGaped = Math.random() < .35f;
    }

    public void onRestart(float x) {
        velocity.x = ScrollHandler.SCROLL_SPEED;
        reset(x);
    }

    public boolean collides(Pacman pacman) {
        Circle hitbox = pacman.getBoundingCircle();
        if (Intersector.overlaps(hitbox, pipeUp)
                || Intersector.overlaps(hitbox, pipeDown)
                || (pipeDownBelowNonlethal != null && Intersector.overlaps(hitbox, pipeDownBelowNonlethal))
                || (isDoubleGaped && pipeMiddle != null && Intersector.overlaps(hitbox, pipeMiddle))) {
            return true;
        } else {
            if (pipeDownBelowNonlethal != null && Intersector.overlaps(hitbox, pipeDownNonlethal)) {
                inGameEvaluator.unlockGenericAchievement(IDs.achGlitch);
            }
            return false;
        }
    }

    public boolean collidesNonlethal(Pacman pacman) {
        Circle hitbox = pacman.getBoundingCircle();
        hitbox.setRadius(hitbox.radius * 1.2f);

        return Intersector.overlaps(hitbox, pipe);
    }

    public boolean isScored() {
        return isScored;
    }

    public void setScored(boolean b) {
        isScored = b;
    }

    public boolean isDoubleGaped() {
        return isDoubleGaped;
    }
}
