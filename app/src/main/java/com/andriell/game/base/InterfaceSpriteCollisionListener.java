package com.andriell.game.base;

/**
 * Created by Rybalko on 24.11.2016.
 */

public interface InterfaceSpriteCollisionListener extends InterfaceSpritePositioned {
    boolean onCollision(InterfaceSpriteCollisionTarget sprite);
}
