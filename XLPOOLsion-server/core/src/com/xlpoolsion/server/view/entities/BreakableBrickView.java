package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.xlpoolsion.server.XLPOOLsionServer;

/**
 * A view representing a breakable brick
 */
public class BreakableBrickView extends EntityView {
    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;

    /**
     * Creates a breakable brick view
     * @param xlpooLsionServer The game this view belongs to
     */
    public BreakableBrickView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        Texture img = xlpooLsionServer.getAssetManager().get("BreakableBrick2.png");
        Sprite temp_sprite = new Sprite(img);
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }
}
