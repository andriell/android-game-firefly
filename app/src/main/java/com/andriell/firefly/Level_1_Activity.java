package com.andriell.firefly;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.View;

import com.andriell.game.base.DrawSprite;
import com.andriell.game.base.GameActivity;
import com.andriell.game.base.SpriteBitmap;
import com.andriell.game.base.SpriteSheetBitmap;

/**
 * Created by Андрей on 25.12.2016.
 */

public class Level_1_Activity extends GameActivity {
    DrawSprite drawSprite;

    @Override
    protected void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected View preload() {
        return null;
    }

    @Override
    protected View game() {
        drawSprite = new DrawSprite(this, 4);

        SpriteBitmap bg1 = createSpriteBitmapP(R.drawable.bg1_1);
        drawSprite.addSprite(0, bg1);

        SpriteSheetBitmap bg2 = new SpriteSheetBitmap();
        bg2.setBitmap(createBitmapP(R.drawable.bg2_1));
        setWidthP(bg2, 1F);
        setPositionPBL(bg2, 0, 0);
        bg2.setSpeedX(yP(-0.001F));
        drawSprite.addSprite(0, bg2);

        return drawSprite;
    }
}
