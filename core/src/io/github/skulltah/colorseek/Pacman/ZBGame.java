package io.github.skulltah.colorseek.Pacman;

import com.badlogic.gdx.Game;

public class ZBGame extends Game {
    @Override
    public void create() {
        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.load();
        setScreen(new io.github.skulltah.colorseek.Screens.SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        io.github.skulltah.colorseek.ZBHelpers.AssetLoader.dispose();
    }
}