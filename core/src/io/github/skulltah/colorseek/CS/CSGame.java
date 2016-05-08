package io.github.skulltah.colorseek.CS;

import com.badlogic.gdx.Game;

import io.github.skulltah.colorseek.CSHelpers.AssetLoader;
import io.github.skulltah.colorseek.CSHelpers.PlayServices;
import io.github.skulltah.colorseek.CSHelpers.PowerupManager;
import io.github.skulltah.colorseek.Screens.SplashScreen;

public class CSGame extends Game {
    public static PlayServices playServices;
    public PowerupManager powerupManager;
    public ActionResolver actionResolver;

    public CSGame(PlayServices playServices, ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
        CSGame.playServices = playServices;
        powerupManager = new PowerupManager();
    }

    @Override
    public void create() {
        AssetLoader.load();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
}