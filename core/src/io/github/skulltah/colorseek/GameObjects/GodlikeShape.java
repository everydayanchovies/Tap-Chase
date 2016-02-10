package io.github.skulltah.colorseek.GameObjects;

import io.github.skulltah.colorseek.CSHelpers.AssetLoader;

public class GodlikeShape extends Scrollable {
    private float scale = 1;
    private float rotation = 0;
    private float spin;
    private boolean hasPlayedSound = false;

    public GodlikeShape(float x, float y, int width, int height, float scrollSpeed, float spin) {
        super(x, y, width, height, scrollSpeed);
        this.spin = spin * 360;
    }

    public float getScale() {
        return scale;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        rotation += delta * spin;

        if (!hasPlayedSound && getX() > 0) {
            hasPlayedSound = true;
            double d = Math.random();
            if (d < 0.5)
                // 50% chance of being here
                AssetLoader.radar1.play(.5f);
            else
                AssetLoader.radar2.play(.5f);
        }
    }

    @Override
    public void reset(float newX) {
        super.reset(newX);

        hasPlayedSound = false;
        velocity.set(90 + (50 * (float) Math.random()), 0);
    }

    public float getSpeed() {
        return velocity.x;
    }

    public void setSpeed(float newSpeed) {
        this.velocity.set(newSpeed, 0);
    }
}
