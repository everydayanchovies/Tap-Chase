package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Food extends Scrollable {
    public static final int FOOD_SIZE = 6;
    public boolean isEnabled;
    private Rectangle food;
    private ScrollHandler scrollHandler;
    private double chance; // Decides whether this food item is enabled or not
    private FoodType foodType;

    public Food(float x, float y, float scrollSpeed, ScrollHandler scrollHandler) {
        super(x, y, io.github.skulltah.colorseek.Constants.Textures.FOOD_SIZE, io.github.skulltah.colorseek.Constants.Textures.FOOD_SIZE, scrollSpeed);
        this.scrollHandler = scrollHandler;

        food = new Rectangle();
        chance = .08f;

//        isEnabled = scrollHandler.getDistanceToClosestPipe(getX()) + 10 > 20;
//        if (isEnabled)
        isEnabled = Math.random() < chance;

        double random = Math.random();
        if (random < .015f)
            foodType = FoodType.Super;
        else if (random < .07f)
            foodType = FoodType.Poison;
        else if (random < .7f)
            foodType = FoodType.Fat;
        else
            foodType = FoodType.Healthy;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (!isEnabled) return;

        food.set(position.x, position.y, FOOD_SIZE, FOOD_SIZE);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);

        float distanceToClosestPipe = scrollHandler.getDistanceToClosestPipe(newX);
        isEnabled = distanceToClosestPipe > 20 || distanceToClosestPipe == -1;

        if (isEnabled)
            isEnabled = Math.random() < chance;
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public Rectangle getFood() {
        return food;
    }

    public boolean collides(io.github.skulltah.colorseek.GameObjects.Pacman pacman) {
        return isEnabled
                && position.x < pacman.getX() + pacman.getWidth()
                && Intersector.overlaps(pacman.getBoundingCircle(), food);
    }

    public FoodType foodType() {
        return foodType;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public enum FoodType {
        Fat, Healthy, Poison, Super
    }
}
