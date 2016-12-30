package com.andriell.firefly;

import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

import com.andriell.game.base.DrawSprite;
import com.andriell.game.base.GameActivity;
import com.andriell.game.base.InterfaceSpriteButtonDownListener;
import com.andriell.game.base.InterfaceSpriteButtonUpListener;
import com.andriell.game.base.SpriteAnimation;
import com.andriell.game.base.SpriteBitmap;
import com.andriell.game.base.SpriteButtonClear;
import com.andriell.game.base.SpriteRouteLissajous;
import com.andriell.game.base.SpriteSheetBitmap;
import com.andriell.game.base.SpriteSheetXBitmaps;

/**
 * Created by Андрей on 25.12.2016.
 */

public class Level_1_Activity extends GameActivity {
    DrawSprite drawSprite;
    Player player;

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

        player = new Player();
        drawSprite.addSprite(2, player);

        SpriteAnimation monster = new SpriteAnimation(createAnimationP(new int[]{R.drawable.monster1_1, R.drawable.monster1_2}, new int[]{150,150}));
        setPositionPTR(monster, 0.5F, 0.2F);
        SpriteRouteLissajous lissajousMonster = new SpriteRouteLissajous(1);
        lissajousMonster.setSpeed(1);
        lissajousMonster.add(0, monster, 5000);
        lissajousMonster.setCircleY(2);
        setSizeP(lissajousMonster, 0.4F, 0.9F);
        setPositionPTR(lissajousMonster, 0F, 0.2F);
        drawSprite.addSprite(2, lissajousMonster);

        SpriteButtonClear jumpButton = new SpriteButtonClear();
        jumpButton.setDownListener(player);
        jumpButton.setUpListener(player);
        setPositionPTL(jumpButton, 0F, 0F);
        setSizeP(jumpButton, 0.5F, 1F);
        drawSprite.addSprite(3, jumpButton);

        return drawSprite;
    }

    class Player extends SpriteAnimation implements InterfaceSpriteButtonDownListener, InterfaceSpriteButtonUpListener {
        final float A = 0.1F;
        float maxY = 0;
        float speedY = 0;
        float accelerationY = A;

        public Player() {
            setAnimation(createAnimationP(new int[]{R.drawable.player1_1, R.drawable.player1_2}, new int[]{100,100}));
            x = getAnimation().getWidth();
            maxY = displaySize.y - getAnimation().getHeight();
            y = maxY / 2;

        }

        @Override
        public boolean onDraw(Canvas c) {
            speedY += accelerationY;
            y += speedY;
            if (y >= maxY) {
                speedY = 0F;
                y = maxY;
            } else if (y < 0F) {
                speedY = 0F;
                y = 0F;
            }
            return super.onDraw(c);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            accelerationY = -A;
            return true;
        }

        @Override
        public boolean onUp(MotionEvent e) {
            accelerationY = A;
            return true;
        }
    }
}
