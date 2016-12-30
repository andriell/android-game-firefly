package com.andriell.firefly;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.view.MotionEvent;

import com.andriell.game.base.DrawSprite;
import com.andriell.game.base.DrawView;
import com.andriell.game.base.GameActivity;
import com.andriell.game.base.InterfaceSpriteButtonUpListener;
import com.andriell.game.base.SpriteButtonBitmap;
import com.andriell.game.base.SpriteColor;

public class StartActivity extends GameActivity {
    @Override
    protected void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    protected DrawView preload() {
        DrawSprite drawSprite = new DrawSprite(this, 2);
        drawSprite.addSprite(0, new SpriteColor(Color.WHITE));

        SpriteButtonBitmap spriteButtonBitmap = createButtonP(R.drawable.pause1, 0.2F);
        setPositionCenter(spriteButtonBitmap);
        spriteButtonBitmap.setUpListener(new InterfaceSpriteButtonUpListener() {
            @Override
            public boolean onUp(MotionEvent e, float x, float y) {
                Intent intent = new Intent(StartActivity.this, Level_1_Activity.class);
                startActivity(intent);
                return true;
            }
        });
        drawSprite.addSprite(1, spriteButtonBitmap);
        return drawSprite;
    }

    @Override
    protected DrawView game() {
        return null;
    }
}
