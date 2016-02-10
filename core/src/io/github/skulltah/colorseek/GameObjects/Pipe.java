package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.InGameEvaluator;
import io.github.skulltah.colorseek.Constants.IDs;

public class Pipe extends Scrollable {
    public static final int VERTICAL_GAP = 44;
    private Random r;
    private Rectangle
//            pipeTopUp, pipeTopDown, pipeMiddleTopUp, pipeMiddleTopDown,
            pipeDownNonleathal,
            pipeUp, pipeDown, pipeMiddle;
    //    public static final int PIPE_TOP_WIDTH = Textures.PIPE_TOP_WIDTH;
//    public static final int PIPE_TOP_HEIGHT = Textures.PIPE_TOP_HEIGHT;
    private boolean isScored = false;
    private boolean isDoubleGaped = false;
    private InGameEvaluator inGameEvaluator;
    private CSGame game;

    // When Pipe's constructor is invoked, invoke the super (Scrollable)
    // constructor
    public Pipe(CSGame game, float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        this.game = game;
        this.inGameEvaluator = new InGameEvaluator(game);
        // Initialize a Random object for Random number generation
        r = new Random();
//        pipeTopUp = new Rectangle();
//        pipeTopDown = new Rectangle();
//        pipeMiddleTopDown = new Rectangle();
//        pipeMiddleTopUp = new Rectangle();
        pipeUp = new Rectangle();
        pipeDown = new Rectangle();
        pipeMiddle = new Rectangle();
        pipeDownNonleathal = new Rectangle();
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (isDoubleGaped())
            drawTwoGaps();
        else
            drawOneGap();
    }

    private void drawOneGap() {
        pipeUp.set(position.x, position.y, width, height - 2);
        pipeDown.set(position.x,
                position.y + height + VERTICAL_GAP,
                width,
                position.y + height + VERTICAL_GAP);
        pipeDownNonleathal.set(position.x, position.y + height + VERTICAL_GAP, width, 500);

//        pipeTopUp.set(position.x - (PIPE_TOP_WIDTH - width) / 2, position.y + height
//                - PIPE_TOP_HEIGHT, PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);
//        pipeTopDown.set(position.x - (PIPE_TOP_WIDTH - width) / 2, pipeDown.y,
//                PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);
    }

    private void drawTwoGaps() {
        int verticalGap = VERTICAL_GAP * 3;

        pipeUp.set(position.x, position.y, width, height - 2);
        pipeDown.set(position.x, position.y + height + verticalGap, width,
                position.y + height * 2 + verticalGap);
        pipeMiddle.set(position.x, position.y + height - 4 + VERTICAL_GAP, width, VERTICAL_GAP);

//        pipeTopUp.set(position.x - (PIPE_TOP_WIDTH - width) / 2, position.y + height
//                - PIPE_TOP_HEIGHT, PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);
//        pipeTopDown.set(position.x - (PIPE_TOP_WIDTH - width) / 2, pipeDown.y,
//                PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);
//
//        pipeMiddleTopUp.set(position.x - (PIPE_TOP_WIDTH - width) / 2, position.y + height + (VERTICAL_GAP * 2)
//                - PIPE_TOP_HEIGHT, PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);
//        pipeMiddleTopDown.set(position.x - (PIPE_TOP_WIDTH - width) / 2, pipeDown.y + VERTICAL_GAP,
//                PIPE_TOP_WIDTH, PIPE_TOP_HEIGHT);
    }

    @Override
    public void reset(float newX) {
        // Call the reset method in the superclass (Scrollable)
        super.reset(newX);
        // Change the height to a random number
        height = r.nextInt(90) + 30;
        isScored = false;

        isDoubleGaped = Math.random() < .35f;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

//    public Rectangle getPipeTopUp() {
//        return pipeTopUp;
//    }
//
//    public Rectangle getPipeTopDown() {
//        return pipeTopDown;
//    }

    public Rectangle getPipeUp() {
        return pipeUp;
    }

    public Rectangle getPipeDown() {
        return pipeDown;
    }

    public boolean collides(Pacman pacman) {
        if (position.x < pacman.getX() + pacman.getWidth()) {
            if (Intersector.overlaps(pacman.getBoundingCircle(), pipeUp)
                    || Intersector.overlaps(pacman.getBoundingCircle(), pipeDown)
//                    || Intersector.overlaps(pacman.getBoundingCircle(), pipeTopUp)
//                    || Intersector.overlaps(pacman.getBoundingCircle(), pipeTopDown)
                    || (isDoubleGaped && (
                    Intersector.overlaps(pacman.getBoundingCircle(), pipeMiddle)
//                            || Intersector.overlaps(pacman.getBoundingCircle(), pipeMiddleTopUp)
//                            || Intersector.overlaps(pacman.getBoundingCircle(), pipeMiddleTopDown)
            ))) {
                return true;
            } else {
                if (Intersector.overlaps(pacman.getBoundingCircle(), pipeDownNonleathal)) {
                    inGameEvaluator.unlockGenericAchievement(IDs.achGlitch);
                }
                return false;
            }
        }
        return false;
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

    public int getGapPosition() {
        return height + (VERTICAL_GAP / 2) - 5;
    }

    public int getPositionX() {
        return (int) pipeUp.getX();
    }
}
