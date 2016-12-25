package com.andriell.game.base;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Андрей on 03.12.2016.
 */

public class SpriteSheetXBitmaps extends SpritePositioned implements InterfaceSpriteResize {
    protected Bitmap[] bitmap;
    protected int bitmapSizeX = 0;
    /* Находится в диапозоне от 0 до bitmapSizeX */
    protected float frameX = 0;
    private float speedX = 0F;

    public Bitmap[] getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap[] bitmap) {
        bitmapSizeX = 0;
        height = Integer.MAX_VALUE;
        for (int i = 0; i < bitmap.length; i++) {
            bitmapSizeX += bitmap[i].getWidth();
            if (height > bitmap[i].getHeight() && bitmap[i].getHeight() > 0) {
                height = bitmap[i].getHeight();
            }
        }
        this.bitmap = bitmap;
    }

    @Override
    public boolean onDraw(Canvas c) {
        if (bitmap == null) {
            return true;
        }
        frameX -= speedX;
        if (frameX >= bitmapSizeX) {
            frameX = frameX % bitmapSizeX;
        } else if (frameX <= 0) {
            frameX = bitmapSizeX + frameX % bitmapSizeX;
        }
        int bitmapIndex = 0;
        int bitmapX = 0;
        for (; bitmapIndex < bitmap.length; bitmapIndex++) {
            if (bitmapX + bitmap[bitmapIndex].getWidth() > frameX) {
                break;
            }
            bitmapX += bitmap[bitmapIndex].getWidth();
        }

        float drawPosition = bitmapX - frameX;
        for (int i = 0; i < 1000000; i++) {
            int i2 = (i + bitmapIndex) % bitmap.length;
            c.drawBitmap(bitmap[i2], drawPosition, y, null);
            drawPosition += bitmap[i2].getWidth();
            if (drawPosition > width) {
                break;
            }
        }
        return true;
    }


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

}
