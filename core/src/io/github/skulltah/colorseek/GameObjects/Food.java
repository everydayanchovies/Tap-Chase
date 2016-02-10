package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import io.github.skulltah.colorseek.Constants.Textures;

public class Food extends Scrollable {
    public static final int FOOD_SIZE = 6;
    public boolean isEnabled;
    private Rectangle food;
    private double chance; // Decides whether this food item is enabled or not
    private FoodType foodType;
    private Pacman pacman;

    public Food(float x, float y, float scrollSpeed, Pacman pacman) {
        super(x, y, Textures.FOOD_SIZE, Textures.FOOD_SIZE, scrollSpeed);

        this.pacman = pacman;

        food = new Rectangle();
        chance = .06f;

        isEnabled = Math.random() < chance;

        double random = Math.random();
        if (random < .01f)
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

        isEnabled = Math.random() < (pacman.getIsSuper() ? chance * 5 : chance);

        if (!isEnabled) return;

        if (pacman.getIsSuper())
            foodType = FoodType.White;
        else {
            double random = Math.random();
            if (random < .05f)
                foodType = FoodType.Super;
            else if (random < .07f)
                foodType = FoodType.Poison;
            else if (random < .7f)
                foodType = FoodType.Fat;
            else
                foodType = FoodType.Healthy;
        }
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
        Fat, Healthy, Poison, Super, White
    }
}
