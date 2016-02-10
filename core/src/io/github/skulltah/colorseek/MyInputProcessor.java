package io.github.skulltah.colorseek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public class MyInputProcessor extends InputAdapter {
    float offsetx = 0;
    float offsety = 0;//150?

    @Override
    public boolean touchDown(
            int screenX,
            int screenY,
            int pointer,
            int button) {
        return true;
    }

    @Override
    public boolean touchDragged(
            int screenX,
            int screenY,
            int pointer) {
        MyGdxGame.c.touchPos.set(screenX + offsetx, Gdx.graphics.getHeight() - screenY + offsety);
        return true;
    }

//    @Override
//    public boolean touchDragged(int screenX, int screenY, int pointer) {
//        dragPos.set(screenX, Gdx.graphics.getHeight() - screenY);
//        float distance = touchPos.dst(dragPos);
//
//        if (distance <= radius) {
//            // gives you a 'natural' angle
//            float angle =
//                    MathUtils.atan2(
//                            touchPos.x - dragPos.x, dragPos.y - touchPos.y)
//                            * MathUtils.radiansToDegrees + 90;
//            if (angle < 0)
//                angle += 360;
//            // move according to distance and angle
//        } else {
//            // keep moving at constant speed
//        }
//        return true;
//    }
}
