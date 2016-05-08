package io.github.skulltah.colorseek.GameWorld;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import io.github.skulltah.colorseek.CS.CSGame;
import io.github.skulltah.colorseek.CSHelpers.AssetLoader;
import io.github.skulltah.colorseek.CSHelpers.InputHandler;
import io.github.skulltah.colorseek.CSHelpers.Utils;
import io.github.skulltah.colorseek.Constants.Textures;
import io.github.skulltah.colorseek.GameObjects.Food;
import io.github.skulltah.colorseek.GameObjects.GodlikeShape;
import io.github.skulltah.colorseek.GameObjects.Pacman;
import io.github.skulltah.colorseek.GameObjects.Pipe;
import io.github.skulltah.colorseek.GameObjects.Powerup;
import io.github.skulltah.colorseek.GameObjects.ScrollHandler;
import io.github.skulltah.colorseek.TweenAccessors.ValueAccessor;
import io.github.skulltah.colorseek.ui.SimpleButton;

public class GameRenderer {

    private static final int BACKGROUND_TILE_SIZE = 100;
    private final float logoScale = 1f;
    private GameWorld myWorld;
    private OrthographicCamera cam;
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batcher;
    private int midPointY;
    // Game Objects
    private Pacman pacman;
    private ScrollHandler scroller;
    private Pipe pipe1, pipe2, pipe3;
    private List<Pipe> pipes;
    private Food[] foodPool;
    private Powerup[] powerupPool;
    private GodlikeShape[] godlikeShapes;
    // Game Assets
    private TextureRegion bg, pacmanMid, ready, help,
            zbLogo, gameOver, highScore, scoreboard, star, noStar, food1,
            square, triangle, circle;
    private TextureRegion[] backgroundTiles;
    private Animation pacmanAnimation, pacmanAnimationSuper,
    //            food1Animation,
    food2Animation, food3Animation, food4Animation, food5Animation,
            powerupSpeedUpAnimation,
    //            powerupSpeedDownAnimation,
    powerupPipeGapAnimation,
            pipeAnimation, pipeTopUpAnimation, pipeTopDownAnimation;
    // Tween stuff
    private TweenManager manager;
    private io.github.skulltah.colorseek.TweenAccessors.Value alpha = new io.github.skulltah.colorseek.TweenAccessors.Value();
    // Buttons
    private List<SimpleButton> menuButtons;
    private Color transitionColor;
    private int[][] backgroundTileMap;

    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;

        this.midPointY = midPointY;
        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();

        cam = new OrthographicCamera();
        cam.setToOrtho(true, 136, gameHeight);

        batcher = new SpriteBatch();
        batcher.setProjectionMatrix(cam.combined);
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(cam.combined);

        initGameObjects();
        initAssets();

        transitionColor = new Color();
        prepareTransition(0, 0, 0, .5f);

        generateBackground();
    }

    private void initGameObjects() {
        pacman = myWorld.getPacman();
        scroller = myWorld.getScroller();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();

        pipes = new ArrayList<Pipe>() {{
            add(pipe1);
            add(pipe2);
            add(pipe3);
        }};

        foodPool = scroller.getFoodPool();
        powerupPool = scroller.getPowerupPool();
        godlikeShapes = scroller.getGodlikeShapes();
    }

    private void initAssets() {
        bg = AssetLoader.bg;
        pacmanAnimation = AssetLoader.pacmanAnimation;
        pacmanAnimationSuper = AssetLoader.pacmanAnimationSuper;
        pacmanMid = AssetLoader.pacmanHalfOpen;
        ready = AssetLoader.ready;
        help = AssetLoader.help;
        zbLogo = AssetLoader.zbLogo;
        gameOver = AssetLoader.gameOver;
        highScore = AssetLoader.highScore;
        scoreboard = AssetLoader.scoreboard;
        star = AssetLoader.star;
        noStar = AssetLoader.noStar;
//        food1Animation = AssetLoader.food1Animation;
        food1 = AssetLoader.food1;
        food2Animation = AssetLoader.food2Animation;
        food3Animation = AssetLoader.food3Animation;
        food4Animation = AssetLoader.food4Animation;
        food5Animation = AssetLoader.food5Animation;
        powerupPipeGapAnimation = AssetLoader.powerupPipeGapAnimation;
        powerupSpeedUpAnimation = AssetLoader.powerupSpeedUpAnimation;
//        powerupSpeedDownAnimation = AssetLoader.powerupSpeedDownAnimation;
        pipeAnimation = AssetLoader.pipeAnimation;
        pipeTopDownAnimation = AssetLoader.pipeTopDownAnimation;
        pipeTopUpAnimation = AssetLoader.pipeTopUpAnimation;
        backgroundTiles = AssetLoader.backgroundTiles;
        square = AssetLoader.square;
        triangle = AssetLoader.triangle;
        circle = AssetLoader.circle;
    }

    private void drawPipeTops(float runTime) {
        for (Pipe pipe : pipes) {
            batcher.draw(pipeTopUpAnimation.getKeyFrame(runTime), pipe.getX(),
                    pipe.getY() + pipe.getHeight() - Textures.PIPE_TOP_HEIGHT, Textures.PIPE_TOP_WIDTH / 2.0f,
                    Textures.PIPE_TOP_HEIGHT / 2.0f, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT, 1, 1, 0);
            batcher.draw(pipeTopDownAnimation.getKeyFrame(runTime), pipe.getX(),
                    pipe.getY() + pipe.getHeight() + (pipe.isDoubleGaped() ? pipe.getVerticalGap() * 3 : pipe.getVerticalGap()), Textures.PIPE_TOP_WIDTH / 2.0f,
                    Textures.PIPE_TOP_HEIGHT / 2.0f, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT, 1, 1, 0);
            if (pipe.isDoubleGaped()) {
                batcher.draw(pipeTopUpAnimation.getKeyFrame(runTime), pipe.getX(),
                        (pipe.getY() + (pipe.getVerticalGap() * 2)) + pipe.getHeight() - Textures.PIPE_TOP_HEIGHT, Textures.PIPE_TOP_WIDTH / 2.0f,
                        Textures.PIPE_TOP_HEIGHT / 2.0f, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT, 1, 1, 0);
                batcher.draw(pipeTopDownAnimation.getKeyFrame(runTime), pipe.getX(),
                        pipe.getY() + pipe.getVerticalGap() + pipe.getHeight() - Textures.PIPE_TOP_HEIGHT, Textures.PIPE_TOP_WIDTH / 2.0f,
                        Textures.PIPE_TOP_HEIGHT / 2.0f, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT, 1, 1, 0);
            }
        }
    }

    private void drawPipes(float runTime) {
        for (Pipe pipe : pipes) {
            batcher.draw(pipeAnimation.getKeyFrame(runTime), pipe.getX(),
                    pipe.getY(), pipe.getWidth() / 2.0f,
                    pipe.getHeight() / 2.0f, pipe.getWidth(), pipe.getHeight(), 1, 1, 0);
            batcher.draw(pipeAnimation.getKeyFrame(runTime), pipe.getX(),
                    pipe.getY() + pipe.getHeight() + (pipe.isDoubleGaped() ? pipe.getVerticalGap() * 3 : pipe.getVerticalGap()), pipe1.getWidth() / 2.0f,
                    pipe.getHeight() / 2.0f, pipe.getWidth(), Gdx.graphics.getHeight(), 1, 1, 0);
            if (pipe.isDoubleGaped()) {
                batcher.draw(pipeAnimation.getKeyFrame(runTime), pipe.getX(),
                        pipe.getY() + pipe.getHeight() + pipe.getVerticalGap(), pipe.getWidth() / 2.0f,
                        pipe.getHeight() / 2.0f, pipe.getWidth(), pipe.getVerticalGap(), 1, 1, 0);
            }
        }
    }

    private void drawPipeHitbox() {
        for (Pipe pipe : pipes) {
            Rectangle up = pipe.getPipeUp();
            Rectangle down = pipe.getPipeDown();
            Rectangle middle = pipe.getPipeMiddle();
            Rectangle belowNonlethal = pipe.getPipeDownBelowNonlethal();
            Rectangle nonlethal = pipe.getPipeDownNonlethal();

            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(1, 0, 0, .5f);
            if (up != null)
                shapeRenderer.rect(up.x, up.y, up.width, up.height);
            if (down != null)
                shapeRenderer.rect(down.x, down.y, down.width, down.height);
            if (middle != null)
                shapeRenderer.rect(middle.x, middle.y, middle.width, middle.height);
            if (belowNonlethal != null)
                shapeRenderer.rect(belowNonlethal.x, belowNonlethal.y, belowNonlethal.width, belowNonlethal.height);
            shapeRenderer.setColor(.3f, .3f, 0, .2f);
            if (nonlethal != null)
                shapeRenderer.rect(nonlethal.x, nonlethal.y, nonlethal.width, nonlethal.height);
            shapeRenderer.end();
        }
    }

    private void drawFood(float runTime) {
        for (Food foodItem : foodPool) {
            if (foodItem.isEnabled) {
                switch (foodItem.foodType()) {
                    default:
                        break;
                    case Fat:
                        batcher.draw(food1, foodItem.getX(),
                                foodItem.getY(), foodItem.getWidth() / 2.0f,
                                foodItem.getHeight() / 2.0f, foodItem.getWidth(), foodItem.getHeight(), 1, 1, 0);
                        break;
                    case Healthy:
                        batcher.draw(food2Animation.getKeyFrame(runTime), foodItem.getX(),
                                foodItem.getY(), foodItem.getWidth() / 2.0f,
                                foodItem.getHeight() / 2.0f, foodItem.getWidth(), foodItem.getHeight(), 1, 1, 0);
                        break;
                    case Poison:
                        batcher.draw(food3Animation.getKeyFrame(runTime), foodItem.getX(),
                                foodItem.getY(), foodItem.getWidth() / 2.0f,
                                foodItem.getHeight() / 2.0f, foodItem.getWidth(), foodItem.getHeight(), 1, 1, 0);
                        break;
                    case Super:
                        batcher.draw(food4Animation.getKeyFrame(runTime), foodItem.getX(),
                                foodItem.getY(), foodItem.getWidth() / 2.0f,
                                foodItem.getHeight() / 2.0f, foodItem.getWidth(), foodItem.getHeight(), 1, 1, 0);
                        break;
                    case White:
                        batcher.draw(food5Animation.getKeyFrame(runTime), foodItem.getX(),
                                foodItem.getY(), foodItem.getWidth() / 2.0f,
                                foodItem.getHeight() / 2.0f, foodItem.getWidth(), foodItem.getHeight(), 1, 1, 0);
                        break;
                }
            }
        }
    }

    private void drawPowerups(float runTime) {
        for (Powerup powerup : powerupPool) {
            if (powerup.isEnabled) {
                switch (powerup.getType()) {
                    default:
                        break;
                    case PipeGap:
                        batcher.draw(powerupPipeGapAnimation.getKeyFrame(runTime), powerup.getX(),
                                powerup.getY(), powerup.getWidth() / 2.0f,
                                powerup.getHeight() / 2.0f, powerup.getWidth(), powerup.getHeight(), 1, 1, 0);
                        break;
                    case SpeedUp:
                        batcher.draw(powerupSpeedUpAnimation.getKeyFrame(runTime), powerup.getX(),
                                powerup.getY(), powerup.getWidth() / 2.0f,
                                powerup.getHeight() / 2.0f, powerup.getWidth(), powerup.getHeight(), 1, 1, 0);
                        break;
                }
            }
        }
    }

    private void drawPacmanCentered(float runTime) {
        if (pacman.getIsSuper())
            batcher.draw(pacmanAnimationSuper.getKeyFrame(runTime), 59, pacman.getY() - 15,
                    pacman.getWidth() / 2.0f, pacman.getHeight() / 2.0f,
                    pacman.getWidth(), pacman.getHeight(), pacman.getScale(), pacman.getScale(), pacman.getRotation());
        else
            batcher.draw(pacmanAnimation.getKeyFrame(runTime), 59, pacman.getY() - 15,
                    pacman.getWidth() / 2.0f, pacman.getHeight() / 2.0f,
                    pacman.getWidth(), pacman.getHeight(), pacman.getScale(), pacman.getScale(), pacman.getRotation());
    }

    private void drawPacman(float runTime) {
        if (pacman.getIsSuper()) {
            if (pacman.shouldntFlap()) {
                batcher.draw(pacmanMid, pacman.getX(), pacman.getY(),
                        pacman.getWidth() / 2.0f, pacman.getHeight() / 2.0f,
                        pacman.getWidth(), pacman.getHeight(), pacman.getScale(), pacman.getScale(), pacman.getRotation());
            } else {
                batcher.draw(pacmanAnimationSuper.getKeyFrame(runTime), pacman.getX(),
                        pacman.getY(), pacman.getWidth() / 2.0f,
                        pacman.getHeight() / 2.0f, pacman.getWidth(), pacman.getHeight(),
                        pacman.getScale(), pacman.getScale(), pacman.getRotation());
            }
        } else {
            if (pacman.shouldntFlap()) {
                batcher.draw(pacmanMid, pacman.getX(), pacman.getY(),
                        pacman.getWidth() / 2.0f, pacman.getHeight() / 2.0f,
                        pacman.getWidth(), pacman.getHeight(), pacman.getScale(), pacman.getScale(), pacman.getRotation());
            } else {
                batcher.draw(pacmanAnimation.getKeyFrame(runTime), pacman.getX(),
                        pacman.getY(), pacman.getWidth() / 2.0f,
                        pacman.getHeight() / 2.0f, pacman.getWidth(), pacman.getHeight(),
                        pacman.getScale(), pacman.getScale(), pacman.getRotation());
            }
        }
    }

    private void drawMenuUI() {
        batcher.draw(zbLogo, cam.position.x - zbLogo.getRegionWidth() * logoScale / 2, midPointY - 70,
                zbLogo.getRegionWidth() * logoScale, zbLogo.getRegionHeight() * logoScale);

        menuButtons.get(0).draw(batcher);
        menuButtons.get(2).draw(batcher, CSGame.playServices.isSignedIn());
        menuButtons.get(3).draw(batcher, CSGame.playServices.isSignedIn());
    }

    private void drawScoreboard() {
        batcher.draw(scoreboard, 22, midPointY - 30, 97, 37);

        batcher.draw(noStar, 25, midPointY - 15, 10, 10);
        batcher.draw(noStar, 37, midPointY - 15, 10, 10);
        batcher.draw(noStar, 49, midPointY - 15, 10, 10);
        batcher.draw(noStar, 61, midPointY - 15, 10, 10);
        batcher.draw(noStar, 73, midPointY - 15, 10, 10);

        if (myWorld.getScore() > 5) {
            batcher.draw(star, 73, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 17) {
            batcher.draw(star, 61, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 64) {
            batcher.draw(star, 49, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 98) {
            batcher.draw(star, 37, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 172) {
            batcher.draw(star, 25, midPointY - 15, 10, 10);
        }

        int length = ("" + myWorld.getScore()).length();

        AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(),
                104 - (2 * length), midPointY - 20);

        int length2 = ("" + AssetLoader.getHighScore()).length();
        AssetLoader.whiteFont.draw(batcher, "" + AssetLoader.getHighScore(),
                104 - (2.5f * length2), midPointY - 3);

    }

    private void drawGodlikeShapes() {
        if (godlikeShapes == null)
            return;

        TextureRegion[] textures = new TextureRegion[]{
                square, triangle, circle
        };

        for (int i = 0; i < godlikeShapes.length; i++) {
            batcher.draw(textures[i], godlikeShapes[i].getX(), godlikeShapes[i].getY(),
                    godlikeShapes[i].getWidth() / 2.0f, godlikeShapes[i].getHeight() / 2.0f,
                    godlikeShapes[i].getWidth(), godlikeShapes[i].getHeight(), godlikeShapes[i].getScale(), godlikeShapes[i].getScale(), godlikeShapes[i].getRotation());
        }
    }

    private void drawRetry() {
        menuButtons.get(1).draw(batcher);
        menuButtons.get(2).draw(batcher, CSGame.playServices.isSignedIn());
        menuButtons.get(3).draw(batcher, CSGame.playServices.isSignedIn());
        menuButtons.get(4).draw(batcher);
    }

    private void drawReady() {
        batcher.draw(ready, 36, midPointY - 50, 68, 14);
    }

    private void drawHelp() {
        batcher.draw(help, 36, midPointY, 67, 200);
    }

    private void drawGameOver() {
        batcher.draw(gameOver, 24, midPointY - 50, 92, 14);
    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }

    private void drawHighScore() {
        batcher.draw(highScore, 22, midPointY - 50, 96, 14);
    }

    public void generateBackground() {
        backgroundTileMap = new int[Gdx.graphics.getWidth() / BACKGROUND_TILE_SIZE][];
        for (int v = 0; v < backgroundTileMap.length; v++) {
            backgroundTileMap[v] = new int[Gdx.graphics.getHeight() / BACKGROUND_TILE_SIZE];
            for (int iv = 0; iv < backgroundTileMap.length; iv++) {
                backgroundTileMap[v][iv] = Utils.randInt(0, backgroundTiles.length - 1);
            }
        }
    }

    private void drawBackground() {
        if (backgroundTileMap == null) return;

        // Horizontal
        for (int ih = 0; ih < backgroundTileMap.length; ih++) {
            // Vertical
            for (int iv = 0; iv < backgroundTileMap[ih].length; iv++) {
                batcher.draw(backgroundTiles[backgroundTileMap[ih][iv]],
                        ih * BACKGROUND_TILE_SIZE,
                        iv * BACKGROUND_TILE_SIZE,
                        BACKGROUND_TILE_SIZE, BACKGROUND_TILE_SIZE);
            }
        }
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//        shapeRenderer.begin(ShapeType.Filled);
//
//        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();

        drawBackground();

        batcher.enableBlending();

        if (myWorld.isRunning() || myWorld.isReady() || myWorld.isGameOver() || myWorld.isHighScore())
            drawFood(runTime);

        if (myWorld.isRunning() || myWorld.isGameOver() || myWorld.isHighScore())
            drawPowerups(runTime);


        if (myWorld.isRunning()) {
            drawGodlikeShapes();

            batcher.disableBlending();

            drawPipes(runTime);
            drawPipeTops(runTime);

            batcher.enableBlending();

            drawPacman(runTime);
            drawScore();
        } else if (myWorld.isReady()) {
            drawGodlikeShapes();
            drawPacman(runTime);
            drawReady();
            drawHelp();
        } else if (myWorld.isMenu()) {
            drawPacmanCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
            batcher.disableBlending();

            drawPipes(runTime);
            drawPipeTops(runTime);

            batcher.enableBlending();

            drawPacman(runTime);
            drawScoreboard();
            drawGameOver();
            drawRetry();
        } else if (myWorld.isHighScore()) {
            batcher.disableBlending();

            drawPipes(runTime);
            drawPipeTops(runTime);

            batcher.enableBlending();

            drawPacman(runTime);
            drawScoreboard();
            drawHighScore();
            drawRetry();
        } else if (myWorld.isPaused()) {
            drawGodlikeShapes();

            batcher.disableBlending();

            drawPipes(runTime);
            drawPipeTops(runTime);

            batcher.enableBlending();

            drawPacman(runTime);
            drawHelp();
        }

        batcher.end();

//        drawPipeHitbox();

        drawTransition(delta);
    }

    public void prepareTransition(int r, int g, int b, float duration) {
        transitionColor.set(r / 255.0f, g / 255.0f, b / 255.0f, 1);
        alpha.setValue(1);
        Tween.registerAccessor(io.github.skulltah.colorseek.TweenAccessors.Value.class, new ValueAccessor());
        manager = new TweenManager();
        Tween.to(alpha, -1, duration).target(0)
                .ease(TweenEquations.easeOutQuad).start(manager);
    }

    private void drawTransition(float delta) {
        if (alpha.getValue() > 0) {
            manager.update(delta);
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(transitionColor.r, transitionColor.g,
                    transitionColor.b, alpha.getValue());
            shapeRenderer.rect(0, 0, 136, 300);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }
}
