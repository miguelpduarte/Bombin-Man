package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.xlpoolsion.server.XLPOOLsionServer;

/**
 * A view representing a radius power down
 */
public class RadiusDownView extends EntityView{
    public static final float WIDTH = 32;
    public static final float HEIGHT = 32;

    /**
     * Creates a radius down view
     * @param xlpooLsionServer The game this view belongs to
     */
    public RadiusDownView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    @Override
    protected Sprite createSprite(XLPOOLsionServer xlpooLsionServer) {
        Texture img = xlpooLsionServer.getAssetManager().get("PwrRadiusDown.png");
        Sprite temp_sprite = new Sprite(img);
        temp_sprite.setSize(WIDTH, HEIGHT);
        return temp_sprite;
    }
}
