package io.github.skulltah.colorseek.GameObjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.AssetLoader;
import io.github.skulltah.colorseek.CSHelpers.InGameEvaluator;
import io.github.skulltah.colorseek.Constants.Textures;
import io.github.skulltah.colorseek.Constants.Values;
import io.github.skulltah.colorseek.GameWorld.GameWorld;

public class ScrollHandler {
    //    private Grass[] groundPool;
    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 76;
    public static final int PIPE_GAP_OFFSET = 20;
    public static final int FOOD_AMOUNT_X = 10;
    public static final int FOOD_GAP_X = 15;
    public static final int FOOD_GAP_Y = 25;
    public static final int POWERUP_AMOUNT_X = 5;
    public static final int POWERUP_GAP_X = 30;
    public static final int POWERUP_GAP_Y = 30;
    public static final int SPEED_LAYER_1 = 1;
    public static final float SPEED_LAYER_2 = .82f;
    public CSGame game;
    private Pipe pipe1, pipe2, pipe3;
    private Food[] foodPool;
    private Powerup[] powerupPool;
    private GodlikeShape[] godlikeShapes;
    private List<Pipe> pipes;
    private InGameEvaluator inGameEvaluator;
    private float scrollSpeed;
    private GameWorld gameWorld;

    public ScrollHandler(CSGame game, GameWorld gameWorld, float yPos) {
        this.game = game;
        this.inGameEvaluator = new InGameEvaluator(game);
        this.gameWorld = gameWorld;
        this.scrollSpeed = SCROLL_SPEED;
        pipe1 = new Pipe(game, 210, 0, Textures.PIPE_WIDTH, 60, this);
        pipe2 = new Pipe(game, pipe1.getTailX() + PIPE_GAP, 0, Textures.PIPE_WIDTH, 70, this);
        pipe3 = new Pipe(game, pipe2.getTailX() + PIPE_GAP, 0, Textures.PIPE_WIDTH, 60, this);

        pipes = new ArrayList<Pipe>() {{
            add(pipe1);
            add(pipe2);
            add(pipe3);
        }};

        foodPool = new Food[Values.FOOD_POOL_SIZE];
        powerupPool = new Powerup[Values.POWERUP_POOL_SIZE];

        Random random = new Random();
        godlikeShapes = new GodlikeShape[]{
                new GodlikeShape(-10, gameWorld.getMidPointY() - 50, 9, 9, 90 + random.nextInt(50), 1),
                new GodlikeShape(-10, gameWorld.getMidPointY() + 25, 9, 9, 90 + random.nextInt(50), -1.4f),
                new GodlikeShape(-10, gameWorld.getMidPointY() + 65, 9, 9, 90 + random.nextInt(50), .8f)
        };
//        groundPool = new Grass[5];

        int gameWidth = (int) (Values.GAME_WIDTH * 1.5f);

        for (int i = 0; i < foodPool.length; i++) {
            int x = ((i / FOOD_AMOUNT_X) * FOOD_GAP_X) + gameWidth / 2;
            int y = (i - ((i / FOOD_AMOUNT_X) * FOOD_AMOUNT_X)) * FOOD_GAP_Y;
            foodPool[i] = new Food(x, y, SCROLL_SPEED * SPEED_LAYER_2, gameWorld.getPacman());
        }

        for (int i = 0; i < powerupPool.length; i++) {
            int x = ((i / POWERUP_AMOUNT_X) * POWERUP_GAP_X) + gameWidth / 2;
            int y = (i - ((i / POWERUP_AMOUNT_X) * POWERUP_AMOUNT_X)) * POWERUP_GAP_Y;
            powerupPool[i] = new Powerup(x, y, SCROLL_SPEED * SPEED_LAYER_2);
        }
    }

    public float getScrollSpeed() {
        return scrollSpeed;
    }

    public void updateReady(float delta) {
        if (gameWorld.isReady())
            updateGodlikeShapes(delta);

        for (int i = 0; i < foodPool.length; i++) {
            foodPool[i].update(delta);

            if (foodPool[i].isScrolledLeft()) {
                float x = foodPool[(i - FOOD_AMOUNT_X <= 0) ? foodPool.length - FOOD_AMOUNT_X : i - FOOD_AMOUNT_X].getTailX() + FOOD_GAP_X;
                foodPool[i].reset(x);
            }

            if (!foodPool[i].isEnabled) continue;

            if (foodPool[i].collides(gameWorld.getPacman())) {
                foodPool[i].isEnabled = false;
            }
        }
    }

    public void update(float delta) {
        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);

        updateFood(delta);
        updatePowerups(delta);

        updateGodlikeShapes(delta);

        // Check if any of the pipes are scrolled left,
        // and reset accordingly
        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);//ThreadLocalRandom.current().nextInt(PIPE_GAP - PIPE_GAP_OFFSET, PIPE_GAP + PIPE_GAP_OFFSET)
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);
        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        }

        if (game.powerupManager.isPowerupActive(Powerup.PowerupType.SpeedUp)) {
            if (scrollSpeed != SCROLL_SPEED * 1.2f)
                scrollSpeed = SCROLL_SPEED * 1.2f;
//        } else if (game.powerupManager.isPowerupActive(Powerup.PowerupType.SpeedDown)) {
//            if (scrollSpeed != SCROLL_SPEED * .8f)
//                scrollSpeed = SCROLL_SPEED * .8f;
        } else {
            if (scrollSpeed != SCROLL_SPEED)
                scrollSpeed = SCROLL_SPEED;
        }
    }

    private void updateGodlikeShapes(float delta) {
        for (GodlikeShape godlikeShape : godlikeShapes) {
            godlikeShape.update(delta);
        }
    }

    private void updateFood(float delta) {
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
                        AssetLoader.healthy.play();
                        addScore(1);
                        break;
                    case Poison:
                        addScore(3);
                        gameWorld.getPacman().eat(2);
                        AssetLoader.poison.play();
                        break;
                    case Super:
                        gameWorld.getPacman().makeSuper();
                        AssetLoader.poison.play();
                        break;
                    case White:
                        if (!collidesNonlethal(gameWorld.getPacman())) {
                            gameWorld.getPacman().eat(-2);
                            if (gameWorld.getPacman().getIsSuper()) {
                                gameWorld.getPacman().setIsSuper(false);
                                AssetLoader.poison.play();
                            }
                        }
                        break;
                }
                foodPool[i].isEnabled = false;
                inGameEvaluator.score(gameWorld.getScore());
            }
        }
    }

    private void updatePowerups(float delta) {
        for (int i = 0; i < powerupPool.length; i++) {
            powerupPool[i].update(delta);

            if (powerupPool[i].isScrolledLeft()) {
                float x = powerupPool[(i - POWERUP_AMOUNT_X <= 0) ? powerupPool.length - POWERUP_AMOUNT_X : i - POWERUP_AMOUNT_X].getTailX() + POWERUP_GAP_X;
                powerupPool[i].reset(x);
            }

            if (!powerupPool[i].isEnabled) continue;

            if (powerupPool[i].collides(gameWorld.getPacman())) {
//                if (!collidesNonlethal(gameWorld.getPacman())) {
                game.powerupManager.activatePowerup(powerupPool[i].getType());
                AssetLoader.powerup.play();
                powerupPool[i].isEnabled = false;
//                }
            }
        }
    }

    public void stop() {
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
        for (Food aFood : foodPool) {
            aFood.stop();
        }
        for (Powerup aPowerup : powerupPool) {
            aPowerup.stop();
        }
    }

    public boolean collides(Pacman pacman) {
        if (!pipe1.isScored()
                && pipe1.getX() + (pipe1.getWidth() / 2) < pacman.getX()
                + pacman.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.scoreUp.play();
        } else if (!pipe2.isScored()
                && pipe2.getX() + (pipe2.getWidth() / 2) < pacman.getX()
                + pacman.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.scoreUp.play();
        } else if (!pipe3.isScored()
                && pipe3.getX() + (pipe3.getWidth() / 2) < pacman.getX()
                + pacman.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.scoreUp.play();
        }

        return (pipe1.collides(pacman) || pipe2.collides(pacman) || pipe3.collides(pacman));
    }

    public boolean collidesNonlethal(Pacman pacman) {
        return (pipe1.collidesNonlethal(pacman) || pipe2.collidesNonlethal(pacman) || pipe3.collidesNonlethal(pacman));
    }

    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }

    public Food[] getFoodPool() {
        return foodPool;
    }

    public Powerup[] getPowerupPool() {
        return powerupPool;
    }

    public GodlikeShape[] getGodlikeShapes() {
        return godlikeShapes;
    }

    public void onRestart() {
        pipe1.onRestart(210);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP);
        int gameWidth = (int) (Values.GAME_WIDTH * 1.5f);
        for (int i = 0; i < foodPool.length; i++)
            foodPool[i].onRestart(((i / FOOD_AMOUNT_X) * FOOD_GAP_X) + gameWidth / 2, SCROLL_SPEED * SPEED_LAYER_2);
        for (int i = 0; i < powerupPool.length; i++)
            powerupPool[i].onRestart(((i / POWERUP_AMOUNT_X) * POWERUP_GAP_X) + gameWidth / 2, SCROLL_SPEED * SPEED_LAYER_2);
        for (GodlikeShape godlikeShape : godlikeShapes)
            godlikeShape.reset(-10);
    }
}
