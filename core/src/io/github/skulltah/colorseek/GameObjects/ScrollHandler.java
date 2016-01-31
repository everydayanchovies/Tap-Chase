package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import io.github.skulltah.colorseek.Constants.Textures;
import io.github.skulltah.colorseek.Constants.Values;
import io.github.skulltah.colorseek.GameWorld.GameWorld;

public class ScrollHandler {
    //    private Grass[] groundPool;
    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 73;
    public static final int PIPE_GAP_OFFSET = 20;
    public static final int FOOD_AMOUNT_X = 10;
    public static final int FOOD_GAP_X = 15;
    public static final int FOOD_GAP_Y = 25;
    private io.github.skulltah.colorseek.GameObjects.Pipe pipe1, pipe2, pipe3;
    private io.github.skulltah.colorseek.GameObjects.Food[] foodPool;
    private List<io.github.skulltah.colorseek.GameObjects.Pipe> pipes;

    private GameWorld gameWorld;

    public ScrollHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        pipe1 = new io.github.skulltah.colorseek.GameObjects.Pipe(210, 0, Textures.PIPE_WIDTH, 60, SCROLL_SPEED);
        pipe2 = new io.github.skulltah.colorseek.GameObjects.Pipe(pipe1.getTailX() + PIPE_GAP, 0, Textures.PIPE_WIDTH, 70, SCROLL_SPEED);
        pipe3 = new io.github.skulltah.colorseek.GameObjects.Pipe(pipe2.getTailX() + PIPE_GAP, 0, Textures.PIPE_WIDTH, 60, SCROLL_SPEED);

        pipes = new ArrayList<io.github.skulltah.colorseek.GameObjects.Pipe>() {{
            add(pipe1);
            add(pipe2);
            add(pipe3);
        }};

        foodPool = new io.github.skulltah.colorseek.GameObjects.Food[Values.FOOD_POOL_SIZE];
//        groundPool = new Grass[5];

        int gameWidth = (int) (Values.GAME_WIDTH * 1.5f);
        for (int i = 0; i < foodPool.length; i++) {
            int x = ((i / FOOD_AMOUNT_X) * FOOD_GAP_X) + gameWidth / 2;
            int y = (i - ((i / FOOD_AMOUNT_X) * FOOD_AMOUNT_X)) * FOOD_GAP_Y;
            foodPool[i] = new io.github.skulltah.colorseek.GameObjects.Food(x, y, SCROLL_SPEED * .82f, this);
        }

//        groundPool[0] = new Grass(0, yPos, 48, 5, SCROLL_SPEED);
//        for (int i = 1; i < groundPool.length; i++) {
//            Grass prevGround = groundPool[(i <= 0) ? groundPool.length - 1 : i - 1];
//            groundPool[i] = new Grass(prevGround.getTailX(), yPos, 48, 5, SCROLL_SPEED);
//        }
    }

    public void updateReady(float delta) {
        for (int i = 0; i < foodPool.length; i++) {
            foodPool[i].update(delta);

            if (foodPool[i].isScrolledLeft()) {
                float x = foodPool[(i - FOOD_AMOUNT_X <= 0) ? foodPool.length - FOOD_AMOUNT_X : i - FOOD_AMOUNT_X].getTailX() + FOOD_GAP_X;
                foodPool[i].reset(x);
            }

            if (!foodPool[i].isEnabled) continue;

            if (foodPool[i].collides(gameWorld.getPacman())) {
                switch (foodPool[i].foodType()) {
                    default:
                        break;
                    case Fat:
                        gameWorld.getPacman().eat(1);
                        break;
                    case Healthy:
                        gameWorld.getPacman().eat(-2);
                        break;
                    case Poison:
                        break;
                }
                foodPool[i].isEnabled = false;
            }
        }
//        for (int i = 0; i < groundPool.length; i++) {
//            Grass ground = groundPool[i];
//            ground.update(delta);
//
//            Grass prevGround = groundPool[(i <= 0) ? groundPool.length - 1 : i - 1];
//            if (ground.isScrolledLeft())
//                ground.reset(prevGround.getTailX());
//        }
    }

    public void update(float delta) {
//        for (int i = 0; i < groundPool.length; i++) {
//            Grass ground = groundPool[i];
//
//            ground.update(delta);
//        }

        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);

        for (int i = 0; i < foodPool.length; i++) {
            foodPool[i].update(delta);

            if (foodPool[i].isScrolledLeft()) {
                float x = foodPool[(i - FOOD_AMOUNT_X <= 0) ? foodPool.length - FOOD_AMOUNT_X : i - FOOD_AMOUNT_X].getTailX() + FOOD_GAP_X;
                foodPool[i].reset(x);
            }

            if (!foodPool[i].isEnabled) continue;

            if (foodPool[i].collides(gameWorld.getPacman())) {
                switch (foodPool[i].foodType()) {
                    default:
                        break;
                    case Fat:
                        gameWorld.getPacman().eat(1);
                        break;
                    case Healthy:
                        gameWorld.getPacman().eat(-2);
                        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.healthy.play();
                        addScore(1);
                        break;
                    case Poison:
                        addScore(3);
                        gameWorld.getPacman().eat(2);
                        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.poison.play();
                        break;
                }
                foodPool[i].isEnabled = false;
            }
        }

        // Check if any of the pipes are scrolled left,
        // and reset accordingly
        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);//ThreadLocalRandom.current().nextInt(PIPE_GAP - PIPE_GAP_OFFSET, PIPE_GAP + PIPE_GAP_OFFSET)
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);
        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }

//        for (int i = 0; i < groundPool.length; i++) {
//            Grass ground = groundPool[i];
//            Grass prevGround = groundPool[(i <= 0) ? groundPool.length - 1 : i - 1];
//            if (ground.isScrolledLeft())
//                ground.reset(prevGround.getTailX());
//        }
    }

    public void stop() {
//        for (int i = 0; i < groundPool.length; i++) {
//            Grass ground = groundPool[i];
//            ground.stop();
//        }

        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
        for (int i = 0; i < foodPool.length; i++) {
            foodPool[i].stop();
        }
    }

    public boolean collides(Pacman pacman) {
        if (!pipe1.isScored()
                && pipe1.getX() + (pipe1.getWidth() / 2) < pacman.getX()
                + pacman.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            io.github.skulltah.colorseek.ZBHelpers.AssetLoader.scoreUp.play();
        } else if (!pipe2.isScored()
                && pipe2.getX() + (pipe2.getWidth() / 2) < pacman.getX()
                + pacman.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            io.github.skulltah.colorseek.ZBHelpers.AssetLoader.scoreUp.play();
        } else if (!pipe3.isScored()
                && pipe3.getX() + (pipe3.getWidth() / 2) < pacman.getX()
                + pacman.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            io.github.skulltah.colorseek.ZBHelpers.AssetLoader.scoreUp.play();
        }

        return (pipe1.collides(pacman) || pipe2.collides(pacman) || pipe3
                .collides(pacman));
    }

    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

    public io.github.skulltah.colorseek.GameObjects.Pipe getPipe1() {
        return pipe1;
    }

    public io.github.skulltah.colorseek.GameObjects.Pipe getPipe2() {
        return pipe2;
    }

    public io.github.skulltah.colorseek.GameObjects.Pipe getPipe3() {
        return pipe3;
    }

    public io.github.skulltah.colorseek.GameObjects.Food[] getFoodPool() {
        return foodPool;
    }

//    public Grass[] getGroundPool() {
//        return groundPool;
//    }

    public void onRestart() {
//        for (int i = 0; i < groundPool.length; i++) {
//            Grass ground = groundPool[i];
//            ground.onRestart(0, SCROLL_SPEED);
//            if (i > 0) {
//                Grass prevGround = groundPool[i - 1];
//                prevGround.onRestart(ground.getTailX(), SCROLL_SPEED);
//            }
//        }

        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
        int gameWidth = (int) (Values.GAME_WIDTH * 1.5f);
        for (int i = 0; i < foodPool.length; i++)
            foodPool[i].onRestart(((i / FOOD_AMOUNT_X) * FOOD_GAP_X) + gameWidth / 2, SCROLL_SPEED * .82f);
    }

    public io.github.skulltah.colorseek.GameObjects.Pipe getClosestPipe(Vector2 referencePoint) {
        float shortestDist = 0;
        io.github.skulltah.colorseek.GameObjects.Pipe closestPipe = null;

        for (io.github.skulltah.colorseek.GameObjects.Pipe point : pipes) {
            float dst2 = referencePoint.dst2(point.position);
            if ((closestPipe == null || dst2 < shortestDist)
//                    && point.getX() - referencePoint.x > -30
                    ) {
                shortestDist = dst2;
                closestPipe = point;
            }
        }
        return closestPipe;
    }

    public float getDistanceToClosestPipe(float referenceX) {
        float shortestDist = -1;

        if (!gameWorld.isRunning()) return -1;

        for (io.github.skulltah.colorseek.GameObjects.Pipe point : pipes) {
            float dst2 = Math.abs(referenceX - point.getX());
            if ((shortestDist == -1 || dst2 < shortestDist)
//                    && point.getX() - referencePoint.x > -30
                    ) {
                shortestDist = dst2;
            }
        }
        return shortestDist - (((int) (shortestDist / PIPE_GAP)) * PIPE_GAP);
    }
}
