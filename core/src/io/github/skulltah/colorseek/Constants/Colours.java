package io.github.skulltah.colorseek.Constants;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.github.skulltah.colorseek.CSHelpers.Classes;

public class Colours {
    public static final Classes.Colour BACKGROUND_COLOUR = new Classes.Colour(0, 0, 0);//55,80,100
    public static final Classes.Colour GRASS_COLOUR = new Classes.Colour(111, 186, 45);
    public static final Classes.Colour DIRT_COLOUR = new Classes.Colour(147, 80, 27);

    public static void setColour(ShapeRenderer target, Classes.Colour colour) {
        target.setColor(colour.r, colour.g, colour.b, colour.a);
    }
}
