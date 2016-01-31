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

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import io.github.skulltah.colorseek.Constants.Textures;
import io.github.skulltah.colorseek.GameObjects.Food;
import io.github.skulltah.colorseek.GameObjects.Pacman;
import io.github.skulltah.colorseek.GameObjects.Pipe;
import io.github.skulltah.colorseek.GameObjects.ScrollHandler;
import io.github.skulltah.colorseek.TweenAccessors.ValueAccessor;
import io.github.skulltah.colorseek.ZBHelpers.InputHandler;
import io.github.skulltah.colorseek.ui.SimpleButton;

public class GameRenderer {

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
//    private Grass[] groundPool;

    // Game Assets
    private TextureRegion bg, pacmanMid, ready,
            zbLogo, gameOver, highScore, scoreboard, star, noStar;
    private Animation pacmanAnimation, food1Animation, food2Animation, food3Animation, pipeAnimation, pipeTopUpAnimation, pipeTopDownAnimation, groundAnimation;

    // Tween stuff
    private TweenManager manager;
    private io.github.skulltah.colorseek.TweenAccessors.Value alpha = new io.github.skulltah.colorseek.TweenAccessors.Value();

    // Buttons
    private List<SimpleButton> menuButtons;
    private Color transitionColor;

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
//        groundPool = scroller.getGroundPool();
    }

    private void initAssets() {
        bg = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.bg;
        pacmanAnimation = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.pacmanAnimation;
        pacmanMid = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.pacmanHalfOpen;
        ready = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.ready;
        zbLogo = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.zbLogo;
        gameOver = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.gameOver;
        highScore = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.highScore;
        scoreboard = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.scoreboard;
        star = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.star;
        noStar = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.noStar;
        food1Animation = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.food1Animation;
        food2Animation = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.food2Animation;
        food3Animation = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.food3Animation;
        pipeAnimation = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.pipeAnimation;
        pipeTopDownAnimation = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.pipeTopDownAnimation;
        pipeTopUpAnimation = io.github.skulltah.colorseek.ZBHelpers.AssetLoader.pipeTopUpAnimation;
//        groundAnimation = AssetLoader.groundAnimation;
    }

//    private void drawGrass() {
//        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(),
//                frontGrass.getWidth(), frontGrass.getHeight());
//        batcher.draw(grass, backGrass.getX(), backGrass.getY(),
//                backGrass.getWidth(), backGrass.getHeight());
//    }

//    private void drawGround(float runTime) {
//        for (int i = 1; i < groundPool.length; i++) {
//            batcher.draw(groundAnimation.getKeyFrame(runTime), groundPool[i].getX(), groundPool[i].getY(), 48 / 2.0f,
//                    5 / 2.0f, 48, 5, 1, 1, 0);
//        }
//    }

    private void drawPipeTops(float runTime) {
        for (Pipe pipe : pipes) {
            batcher.draw(pipeTopUpAnimation.getKeyFrame(runTime), pipe.getX(),
                    pipe.getY() + pipe.getHeight() - Textures.PIPE_TOP_HEIGHT, Textures.PIPE_TOP_WIDTH / 2.0f,
                    Textures.PIPE_TOP_HEIGHT / 2.0f, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT, 1, 1, 0);
            batcher.draw(pipeTopDownAnimation.getKeyFrame(runTime), pipe.getX(),
                    pipe.getY() + pipe.getHeight() + (pipe.isDoubleGaped() ? Pipe.VERTICAL_GAP * 3 : Pipe.VERTICAL_GAP), Textures.PIPE_TOP_WIDTH / 2.0f,
                    Textures.PIPE_TOP_HEIGHT / 2.0f, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT, 1, 1, 0);
            if (pipe.isDoubleGaped()) {
                batcher.draw(pipeTopUpAnimation.getKeyFrame(runTime), pipe.getX(),
                        (pipe.getY() + (Pipe.VERTICAL_GAP * 2)) + pipe.getHeight() - Textures.PIPE_TOP_HEIGHT, Textures.PIPE_TOP_WIDTH / 2.0f,
                        Textures.PIPE_TOP_HEIGHT / 2.0f, Textures.PIPE_TOP_WIDTH, Textures.PIPE_TOP_HEIGHT, 1, 1, 0);
                batcher.draw(pipeTopDownAnimation.getKeyFrame(runTime), pipe.getX(),
                        pipe.getY() + Pipe.VERTICAL_GAP + pipe.getHeight() - Textures.PIPE_TOP_HEIGHT, Textures.PIPE_TOP_WIDTH / 2.0f,
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
                    pipe.getY() + pipe.getHeight() + (pipe.isDoubleGaped() ? Pipe.VERTICAL_GAP * 3 : Pipe.VERTICAL_GAP), pipe1.getWidth() / 2.0f,
                    pipe.getHeight() / 2.0f, pipe.getWidth(), Gdx.graphics.getHeight(), 1, 1, 0);
            if (pipe.isDoubleGaped()) {
                batcher.draw(pipeAnimation.getKeyFrame(runTime), pipe.getX(),
                        pipe.getY() + pipe.getHeight() + Pipe.VERTICAL_GAP, pipe.getWidth() / 2.0f,
                        pipe.getHeight() / 2.0f, pipe.getWidth(), Pipe.VERTICAL_GAP, 1, 1, 0);
            }
        }
    }

    private void drawFood(float runTime) {
        for (Food foodItem : foodPool) {
            if (foodItem.isEnabled) {
                switch (foodItem.foodType()) {
                    default:
                        break;
                    case Fat:
                        batcher.draw(food1Animation.getKeyFrame(runTime), foodItem.getX(),
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
                }
            }
        }
    }

    private void drawPacmanCentered(float runTime) {
        batcher.draw(pacmanAnimation.getKeyFrame(runTime), 59, pacman.getY() - 15,
                pacman.getWidth() / 2.0f, pacman.getHeight() / 2.0f,
                pacman.getWidth(), pacman.getHeight(), pacman.getScale(), pacman.getScale(), pacman.getRotation());
    }

    private void drawPacman(float runTime) {
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

    private void drawMenuUI() {
        batcher.draw(zbLogo, 122 / 2 - 45, midPointY - 50,
                zbLogo.getRegionWidth() / 1.2f, zbLogo.getRegionHeight() / 1.2f);

        menuButtons.get(0).draw(batcher);
//        for (SimpleButton button : menuButtons) {
//            button.draw(batcher);
//        }
    }

    private void drawScoreboard() {
        batcher.draw(scoreboard, 22, midPointY - 30, 97, 37);

        batcher.draw(noStar, 25, midPointY - 15, 10, 10);
        batcher.draw(noStar, 37, midPointY - 15, 10, 10);
        batcher.draw(noStar, 49, midPointY - 15, 10, 10);
        batcher.draw(noStar, 61, midPointY - 15, 10, 10);
        batcher.draw(noStar, 73, midPointY - 15, 10, 10);

        if (myWorld.getScore() > 2) {
            batcher.draw(star, 73, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 17) {
            batcher.draw(star, 61, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 50) {
            batcher.draw(star, 49, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 80) {
            batcher.draw(star, 37, midPointY - 15, 10, 10);
        }

        if (myWorld.getScore() > 120) {
            batcher.draw(star, 25, midPointY - 15, 10, 10);
        }

        int length = ("" + myWorld.getScore()).length();

        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.whiteFont.draw(batcher, "" + myWorld.getScore(),
                104 - (2 * length), midPointY - 20);

        int length2 = ("" + io.github.skulltah.colorseek.ZBHelpers.AssetLoader.getHighScore()).length();
        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.whiteFont.draw(batcher, "" + io.github.skulltah.colorseek.ZBHelpers.AssetLoader.getHighScore(),
                104 - (2.5f * length2), midPointY - 3);

    }

    private void drawRetry() {
//        batcher.draw(retry, 36, midPointY + 10, 66, 14);
        menuButtons.get(1).draw(batcher);
    }

    private void drawReady() {
        batcher.draw(ready, 36, midPointY - 50, 68, 14);
    }

    private void drawGameOver() {
        batcher.draw(gameOver, 24, midPointY - 50, 92, 14);
    }

    private void drawScore() {
        int length = ("" + myWorld.getScore()).length();
        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 82);
        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.font.draw(batcher, "" + myWorld.getScore(),
                68 - (3 * length), midPointY - 83);
    }

    private void drawHighScore() {
        batcher.draw(highScore, 22, midPointY - 50, 96, 14);
    }

    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(10 / 255f, 10 / 255f, 10 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        shapeRenderer.begin(ShapeType.Filled);

        // Draw Background color
//        Colours.setColour(shapeRenderer, Colours.BACKGROUND_COLOUR);
//        shapeRenderer.rect(0, 0, Values.GAME_WIDTH, midPointY + 66);

        // Draw Grass
//        Colours.setColour(shapeRenderer, Colours.GRASS_COLOUR);
//        shapeRenderer.rect(0, midPointY + 66, 136, 11);

        // Draw Dirt
//        Colours.setColour(shapeRenderer, Colours.DIRT_COLOUR);
//        shapeRenderer.rect(0, midPointY + 77, 136, 52);

        shapeRenderer.end();

        batcher.begin();
        batcher.disableBlending();

//		batcher.draw(bg, 0, midPointY + 23, 136, 43);
//        TiledDrawable tile = new TiledDrawable(bg);
//        tile.draw(batcher, 0, 0, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 3, 3, 0);
//        drawGround(runTime);

        batcher.enableBlending();
        if (myWorld.isRunning() || myWorld.isReady() || myWorld.isGameOver() || myWorld.isHighScore())
            drawFood(runTime);

        drawPipes(runTime);

        drawPipeTops(runTime);

        if (myWorld.isRunning()) {
//            drawFood(runTime);
            drawPacman(runTime);
            drawScore();
        } else if (myWorld.isReady()) {
//            drawFood(runTime);
            drawPacman(runTime);
            drawReady();
        } else if (myWorld.isMenu()) {
            drawPacmanCentered(runTime);
            drawMenuUI();
        } else if (myWorld.isGameOver()) {
//            drawFood(runTime);
            drawPacman(runTime);
            drawScoreboard();
            drawGameOver();
            drawRetry();
        } else if (myWorld.isHighScore()) {
//            drawFood(runTime);
            drawPacman(runTime);
            drawScoreboard();
            drawHighScore();
            drawRetry();
        }

//        drawGrass();

        batcher.end();
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
