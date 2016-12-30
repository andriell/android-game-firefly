package com.andriell.firefly;

import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.andriell.game.base.Animation;
import com.andriell.game.base.DrawSprite;
import com.andriell.game.base.GameActivity;
import com.andriell.game.base.InterfaceSpriteButtonDownListener;
import com.andriell.game.base.InterfaceSpriteButtonUpListener;
import com.andriell.game.base.InterfaceSpriteCollisionListener;
import com.andriell.game.base.InterfaceSpriteCollisionTarget;
import com.andriell.game.base.SpriteAnimation;
import com.andriell.game.base.SpriteBitmap;
import com.andriell.game.base.SpriteButtonClear;
import com.andriell.game.base.SpriteRouteLissajous;
import com.andriell.game.base.SpriteRunnerAnimation;
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

        Monster monster = new Monster();
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
        setSizeP(jumpButton, 0.5F, 1F);
        setPositionPTL(jumpButton, 0F, 0F);
        drawSprite.addSprite(3, jumpButton);

        SpriteButtonClear fireButton = new SpriteButtonClear();
            fireButton.setUpListener(new InterfaceSpriteButtonUpListener() {
            @Override
            public boolean onUp(MotionEvent e, float x, float y) {
                drawSprite.addSprite(2, new PlayerShell(x, y));
                return true;
            }
        });
        setSizeP(fireButton, 0.5F, 1F);
        setPositionPTR(fireButton, 0F, 0F);
        drawSprite.addSprite(3, fireButton);

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
        public boolean onDown(MotionEvent e, float x, float y) {
            accelerationY = -A;
            return true;
        }

        @Override
        public boolean onUp(MotionEvent e, float x, float y) {
            accelerationY = A;
            return true;
        }
    }

    class Monster extends SpriteAnimation implements InterfaceSpriteCollisionListener {
        Animation animation;
        public Monster() {
            if (animation == null) {
                animation = createAnimationP(new int[]{R.drawable.monster1_1, R.drawable.monster1_2}, new int[]{150,150});
            }
            setAnimation(animation);
        }

        @Override
        public boolean onCollision(InterfaceSpriteCollisionTarget sprite) {
            if (sprite instanceof PlayerShell) {
                PlayerShell shell = (PlayerShell) sprite;
                shell.destroy();
            }
            return false;
        }
    }

    class PlayerShell extends SpriteRunnerAnimation implements InterfaceSpriteCollisionTarget {
        Animation animation1;
        Animation animation2;

        public PlayerShell(float x2, float y2) {
            if (animation1 == null) {
                animation1 = createAnimationP(new int[]{R.drawable.shell3_1}, new int[]{100});
                animation2 = createAnimationP(new int[]{R.drawable.shell3_2, R.drawable.shell3_3, R.drawable.shell3_4, 0}, new int[]{100, 100, 100, 100});
            }
            setAnimation(animation1);

            x = player.getCenterX() + player.getWidth();
            y = player.getCenterY();
            setSpeed(this, x2, y2, 10F);
        }

        public void destroy() {
            setAnimation(animation2);
            setSpeedX(0);
            setSpeedY(0);
        }

        @Override
        public boolean onDraw(Canvas c) {
            if (getAnimation() == animation2 && animation2.getBitmap() == null) {
                return false;
            }
            return super.onDraw(c);
        }
    }
}
