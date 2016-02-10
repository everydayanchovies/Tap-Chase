package io.github.skulltah.colorseek.GameObjects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class Powerup extends Scrollable {
    public boolean isEnabled;
    private Rectangle hitbox;
    private PowerupType type;

    public Powerup(float x, float y, float scrollSpeed) {
        super(x, y, 11, 17, scrollSpeed);

        hitbox = new Rectangle();

        updateType();
    }

    private void updateType() {
        isEnabled = true;
        double random = Math.random();
        if (random < .006f)
            type = PowerupType.PipeGap;
        else if (random < .012f)
            type = PowerupType.SpeedUp;
        else isEnabled = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (!isEnabled) return;

        hitbox.set(position.x, position.y, 11, 17);
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);

        updateType();
    }

    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    public boolean collides(Pacman pacman) {
        return isEnabled
                && position.x < pacman.getX() + pacman.getWidth()
                && Intersector.overlaps(pacman.getBoundingCircle(), hitbox);
    }

    public PowerupType getType() {
        return type;
    }

    public enum PowerupType {
        PipeGap, SpeedUp
    }

}
