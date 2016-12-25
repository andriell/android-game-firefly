package com.andriell.firefly;

import android.content.pm.ActivityInfo;
import android.view.View;

import com.andriell.game.base.Animation;
import com.andriell.game.base.DrawSprite;
import com.andriell.game.base.GameActivity;
import com.andriell.game.base.SpriteAnimation;
import com.andriell.game.base.SpriteBitmap;
import com.andriell.game.base.SpriteSheetBitmap;
import com.andriell.game.base.SpriteSheetXBitmaps;

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

        SpriteSheetXBitmaps bg3 = new SpriteSheetXBitmaps();
        bg3.setBitmap(createBitmapArray(new int[]{R.drawable.bg3_1, R.drawable.bg3_2}));
        setWidthP(bg3, 1F);
        setPositionPBL(bg3, 0, 0);
        bg3.setY(bg3.getY() + 100);
        bg3.setSpeedX(yP(-0.005F));
        drawSprite.addSprite(0, bg3);

        SpriteSheetXBitmaps bg4 = new SpriteSheetXBitmaps();
        bg4.setBitmap(createBitmapArray(new int[]{R.drawable.bg4_1, R.drawable.bg4_2, R.drawable.bg4_3, R.drawable.bg4_4}));
        setWidthP(bg4, 1F);
        setPositionPBL(bg4, 0, 0);
        bg4.setSpeedX(yP(-0.01F));
        drawSprite.addSprite(0, bg4);

        SpriteAnimation player = new SpriteAnimation(createAnimationP(new int[]{R.drawable.player1_1, R.drawable.player1_2}, new int[]{100,100}));
        setPositionPTL(player, 0.5F, 0.1F);
        drawSprite.addSprite(2, player);

        SpriteAnimation monster = new SpriteAnimation(createAnimationP(new int[]{R.drawable.monster1_1, R.drawable.monster1_2}, new int[]{150,150}));
        setPositionPTR(monster, 0.5F, 0.2F);
        drawSprite.addSprite(2, monster);

        return drawSprite;
    }
}
