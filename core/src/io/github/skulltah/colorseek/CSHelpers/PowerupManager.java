package io.github.skulltah.colorseek.CSHelpers;

import com.badlogic.gdx.utils.Timer;

import io.github.skulltah.colorseek.GameObjects.Powerup;

public class PowerupManager {
    private boolean isPipeGapActive;
    private boolean isSpeedUpActive;
    private boolean isSpeedDownActive;

    public PowerupManager() {
        this.isPipeGapActive = false;
        this.isSpeedUpActive = false;
        this.isSpeedDownActive = false;
    }

    public boolean isPowerupActive(Powerup.PowerupType type) {
        switch (type) {
            default:
                return false;
            case PipeGap:
                return isPipeGapActive;
            case SpeedUp:
                return isSpeedUpActive;
//            case SpeedDown:
//                return isSpeedDownActive;
        }
    }

    public void activatePowerup(Powerup.PowerupType type) {
        switch (type) {
            default:
                break;
            case PipeGap:
                isPipeGapActive = true;
                disablePowerupDelayed(Powerup.PowerupType.PipeGap, 5);
                break;
            case SpeedUp:
//                disablePowerup(Powerup.PowerupType.SpeedDown);
                isSpeedUpActive = true;
                disablePowerupDelayed(Powerup.PowerupType.SpeedUp, 9);
                break;
//            case SpeedDown:
//                disablePowerup(Powerup.PowerupType.SpeedUp);
//                isSpeedUpActive = true;
//                disablePowerupDelayed(Powerup.PowerupType.SpeedDown, 9);
//                break;
        }
    }

    public void disablePowerup(Powerup.PowerupType type) {
        switch (type) {
            default:
                break;
            case PipeGap:
                isPipeGapActive = false;
                break;
            case SpeedUp:
                isSpeedUpActive = false;
                break;
//            case SpeedDown:
//                isSpeedDownActive = false;
//                break;
        }
    }

    public void disablePowerups() {
        isPipeGapActive = false;
        isSpeedUpActive = false;
        isSpeedDownActive = false;
    }

    private void disablePowerupDelayed(final Powerup.PowerupType type, int delay) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                switch (type) {
                    default:
                        break;
                    case PipeGap:
                        isPipeGapActive = false;
                        break;
                    case SpeedUp:
                        isSpeedUpActive = false;
                        break;
//                    case SpeedDown:
//                        isSpeedDownActive = false;
//                        break;
                }
            }
        }, delay);
    }
}
