package com.andriell.game.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Андрей on 03.12.2016.
 */

public class SpriteSheetBitmaps extends SpritePositioned implements InterfaceSpriteResize {
    protected Bitmap[] bitmap;
    private float speedX = 0F;
    private float speedY = 0F;

    /*@Override
    public boolean onDraw(Canvas c) {
        if (bitmap == null) {
            return true;
        }
        if (speedX != 0F) {
            x += speedX;
            if (x >= 0F) {
                x -= bitmap.getWidth();
            } else if (x < -bitmap.getWidth()) {
                x += bitmap.getWidth();
            }
        }
        if (speedY != 0F) {
            y += speedY;
            if (y >= 0F) {
                y -= bitmap.getHeight();
            }else if (y < -bitmap.getHeight()) {
                y += bitmap.getHeight();
            }
        }
        for (float left = x; left < width; left += bitmap.getWidth()) {
            c.drawBitmap(bitmap, left, y, null);
        }
        return true;
    }*/


    @Override
    public void setHeight(float height) {
        this.height = height;
    }

    @Override
    public void setWidth(float width) {
        this.width = width;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }
}
