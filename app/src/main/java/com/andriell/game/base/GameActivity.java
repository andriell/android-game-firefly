package com.andriell.game.base;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Андрей on 02.12.2016.
 */
public abstract class GameActivity extends Activity {
    public static final int ALIGN_TOP = 0;
    public static final int ALIGN_RIGHT = 1;
    public static final int ALIGN_BOTTOM = 2;
    public static final int ALIGN_LEFT = 3;
    public static final int ALIGN_CENTER = 4;

    public final Point displaySize = new Point();
    public boolean isRun = false;

    @Override
    protected void onStart() {
        super.onStart();
        isRun = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRun = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRun = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // если хотим, чтобы приложение было полноэкранным
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // и без заголовка
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // если хотим, чтобы приложение постоянно имело портретную ориентацию
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindowManager().getDefaultDisplay().getSize(displaySize);

        init();

        super.onCreate(savedInstanceState);

        View drawView = preload();
        if (drawView != null) {
            setContentView(drawView);
        }

        drawView = game();
        if (drawView != null) {
            setContentView(drawView);
        }
    }

    protected abstract void init();

    protected abstract View preload();

    protected abstract View game();

    //<editor-fold desc="widthP heightP">
    public float xP(float p) {
        return displaySize.x * p;
    }

    public float widthP(float p) {
        return displaySize.x * p;
    }

    public float yP(float p) {
        return displaySize.y * p;
    }

    public float heightP(float p) {
        return displaySize.y * p;
    }
    //</editor-fold>

    //<editor-fold desc="createBitmap">
    public Bitmap[] createBitmapArray(int[] id) {
        Bitmap[] r = new Bitmap[id.length];
        for (int i = 0; i < id.length; i++) {
            r[i] = createBitmapP(id[i], 0F, 0F);
        }
        return r;
    }

    public Bitmap createBitmapP(int id) {
        return createBitmapP(id, 0F, 0F);
    }

    public Bitmap createBitmapP(int id, float height) {
        return createBitmapP(id, 0F, height);
    }

    public Bitmap createBitmapP(int id, float width, float height) {
        Bitmap bitmapPlayer = BitmapFactory.decodeResource(getResources(), id);
        if (width > 0 && height > 0) {
            bitmapPlayer = Bitmap.createScaledBitmap(bitmapPlayer, (int) (displaySize.x * width), (int) (displaySize.y * height), true);
        } else if (width > 0) {
            bitmapPlayer = Bitmap.createScaledBitmap(bitmapPlayer, (int) (displaySize.x * width), (int) ((bitmapPlayer.getHeight() * displaySize.x * width) / bitmapPlayer.getWidth()), true);
        } else if (height > 0) {
            bitmapPlayer = Bitmap.createScaledBitmap(bitmapPlayer, (int) ((bitmapPlayer.getWidth() * displaySize.y * height) / bitmapPlayer.getHeight()), (int) (displaySize.y * height), true);
        }
        return bitmapPlayer;
    }
    //</editor-fold>

    //<editor-fold desc="createAnimation">
    public Animation createAnimationP(int[] id, int[] timeMap, float height) {
        return createAnimationP(id, timeMap, 0, height, 1F);
    }

    public Animation createAnimationP(int[] id, int[] timeMap) {
        return createAnimationP(id, timeMap, 0, 0, 1F);
    }

    public Animation createAnimationP(int[] id, int[] timeMap, float width, float height) {
        return createAnimationP(id, timeMap, width, height, 1F);
    }

    public Animation createAnimationP(int[] id, int[] timeMap, float width, float height, float speed) {
        Bitmap[] bitmaps = new Bitmap[id.length];
        for (int i = 0; i < id.length; i++) {
            bitmaps[i] = createBitmapP(id[i], width, height);
        }
        return new Animation(bitmaps, timeMap, speed);
    }
    //</editor-fold>

    //<editor-fold desc="createButton">
    public SpriteButtonBitmap createButtonP() {
        return new SpriteButtonBitmap();
    }

    public SpriteButtonBitmap createButtonP(int idNormal, float height) {
        return new SpriteButtonBitmap(createBitmapP(idNormal, 0F, height));
    }

    public SpriteButtonBitmap createButtonP(int idNormal, float width, float height) {
        return new SpriteButtonBitmap(createBitmapP(idNormal, width, height));
    }

    public SpriteButtonBitmap createButtonP(int idNormal, int idPressed, float height) {
        return new SpriteButtonBitmap(createBitmapP(idNormal, 0F, height), createBitmapP(idPressed, 0F, height));
    }

    public SpriteButtonBitmap createButtonP(int idNormal, int idPressed, float width, float height) {
        return new SpriteButtonBitmap(createBitmapP(idNormal, width, height), createBitmapP(idPressed, width, height));
    }
    //</editor-fold>

    //<editor-fold desc="createSpriteBitmap">
    public SpriteBitmap createSpriteBitmapP(int idNormal) {
        return new SpriteBitmap(createBitmapP(idNormal));
    }

    public SpriteBitmap createSpriteBitmapP(int idNormal, float height) {
        return new SpriteBitmap(createBitmapP(idNormal, height));
    }

    public SpriteBitmap createSpriteBitmapP(int idNormal, float width, float height) {
        return new SpriteBitmap(createBitmapP(idNormal, width, height));
    }

    public SpriteBitmap createSpriteBitmapP(int idNormal, float x, float y, float width, float height) {
        return new SpriteBitmap(createBitmapP(idNormal, width, height), x, y);
    }
    //</editor-fold>

    //<editor-fold desc="setPosition">
    public void setPositionCenter(InterfaceSpritePositioned material) {
        setPositionP(material, 0.5F, 0.5F, -1F, -1F, ALIGN_CENTER, ALIGN_CENTER);
    }

    public void setPositionPTR(InterfaceSpritePositioned material, float top, float right, final int align1) {
        setPositionP(material, top, right, -1F, -1F, align1, ALIGN_CENTER);
    }

    public void setPositionPBR(InterfaceSpritePositioned material, float bottom, float right, final int align1) {
        setPositionP(material, -1F, right, bottom, -1F, align1, ALIGN_CENTER);
    }

    public void setPositionPTL(InterfaceSpritePositioned material, float top, float left, final int align1) {
        setPositionP(material, top, -1F, -1F, left, align1, ALIGN_CENTER);
    }

    public void setPositionPBL(InterfaceSpritePositioned material, float bottom, float left, final int align1) {
        setPositionP(material, -1F, -1F, bottom, left, align1, ALIGN_CENTER);
    }

    public void setPositionPTR(InterfaceSpritePositioned material, float top, float right, final int align1, final int align2) {
        setPositionP(material, top, right, -1F, -1F, align1, align2);
    }

    public void setPositionPBR(InterfaceSpritePositioned material, float bottom, float right, final int align1, final int align2) {
        setPositionP(material, -1F, right, bottom, -1F, align1, align2);
    }

    public void setPositionPTL(InterfaceSpritePositioned material, float top, float left, final int align1, final int align2) {
        setPositionP(material, top, -1F, -1F, left, align1, align2);
    }

    public void setPositionPBL(InterfaceSpritePositioned material, float bottom, float left, final int align1, final int align2) {
        setPositionP(material, -1F, -1F, bottom, left, align1, align2);
    }

    public void setPositionPTR(InterfaceSpritePositioned material, float top, float right) {
        setPositionP(material, top, right, -1F, -1F, ALIGN_RIGHT, ALIGN_TOP);
    }

    public void setPositionPBR(InterfaceSpritePositioned material, float bottom, float right) {
        setPositionP(material, -1F, right, bottom, -1F, ALIGN_RIGHT, ALIGN_BOTTOM);
    }

    public void setPositionPTL(InterfaceSpritePositioned material, float top, float left) {
        setPositionP(material, top, -1F, -1F, left, ALIGN_LEFT, ALIGN_TOP);
    }

    public void setPositionPBL(InterfaceSpritePositioned material, float bottom, float left) {
        setPositionP(material, -1F, -1F, bottom, left, ALIGN_LEFT, ALIGN_BOTTOM);
    }

    public void setPositionP(InterfaceSpritePositioned material, float top, float right, float bottom, float left) {
        setPositionP(material, top, right, bottom, left, ALIGN_CENTER, ALIGN_CENTER);
    }

    public void setPositionP(InterfaceSpritePositioned material, float top, float right, float bottom, float left, final int align1, final int align2) {
        if (right >= 0) {
            if (align1 == ALIGN_LEFT || align2 == ALIGN_LEFT) {
                material.setX(displaySize.x - displaySize.x * right);
            } else if (align1 == ALIGN_RIGHT || align2 == ALIGN_RIGHT) {
                material.setX(displaySize.x - displaySize.x * right - material.getWidth());
            } else {
                material.setX(displaySize.x - displaySize.x * right - material.getWidth() / 2);
            }
        } else if (left >= 0) {
            if (align1 == ALIGN_LEFT || align2 == ALIGN_LEFT) {
                material.setX(displaySize.x * left);
            } else if (align1 == ALIGN_RIGHT || align2 == ALIGN_RIGHT) {
                material.setX(displaySize.x * left - material.getWidth());
            } else {
                material.setX(displaySize.x * left - material.getWidth() / 2);
            }
        }

        if (top >= 0) {
            if (align1 == ALIGN_TOP || align2 == ALIGN_TOP) {
                material.setY(displaySize.y * top);
            } else if (align1 == ALIGN_BOTTOM || align2 == ALIGN_BOTTOM) {
                material.setY(displaySize.y * top - material.getHeight());
            } else {
                material.setY(displaySize.y * top - material.getHeight() / 2);
            }
        } else if (bottom >= 0) {
            if (align1 == ALIGN_TOP || align2 == ALIGN_TOP) {
                material.setY(displaySize.y - displaySize.y * bottom);
            } else if (align1 == ALIGN_BOTTOM || align2 == ALIGN_BOTTOM) {
                material.setY(displaySize.y - displaySize.y * bottom - material.getHeight());
            } else {
                material.setY(displaySize.y - displaySize.y * bottom - material.getHeight() / 2);
            }
        }
    }
    //</editor-fold>

    public void setWidthP(InterfaceSpriteResize resize, float width) {
        resize.setWidth(displaySize.x * width);
    }

    public void setHeightP(InterfaceSpriteResize resize, float height) {
        resize.setHeight(displaySize.y * height);
    }

    public void setSizeP(InterfaceSpriteResize resize, float width, float height) {
        if (width > 0 && height > 0) {
            resize.setWidth(displaySize.x * width);
            resize.setHeight(displaySize.y * height);
        } else if (width > 0) {
            resize.setWidth(displaySize.x * width);
            resize.setHeight((resize.getHeight() * displaySize.x * width) / resize.getWidth());

        } else if (height > 0) {
            resize.setWidth((resize.getWidth() * displaySize.y * height) / resize.getHeight());
            resize.setHeight(displaySize.y * height);
        }
    }

    public void setSpeed(InterfaceSpriteSetSpeed setSpeed, float x2, float y2, float speed) {
        setSpeed(setSpeed, setSpeed.getX(), setSpeed.getY(), x2, y2, speed);
    }

    public void setSpeed(InterfaceSpriteSetSpeed setSpeed, float x1, float y1, float x2, float y2, float speed) {
        float x = x2 - x1;
        float y = y2 - y1;
        float z = (float) Math.sqrt(x * x + y * y);
        setSpeed.setSpeedX((speed * x) / z);
        setSpeed.setSpeedY((speed * y) / z);
    }
}
