package com.xlpoolsion.server.view.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.xlpoolsion.server.XLPOOLsionServer;

public class PlayerBlackView extends PlayerView {
    public PlayerBlackView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

     void createAnimations(XLPOOLsionServer xlpooLsionServer) {
        Texture alltextures = xlpooLsionServer.getAssetManager().get("Bomberman_sprite_Black.png");
        TextureRegion[][] fullregion = TextureRegion.split(alltextures, 16, 32);

        super.upAnim = createUpAnimation(fullregion);
        super.rightAnim = createRightAnimation(fullregion);
        super.downAnim = createDownAnimation(fullregion);
        super.leftAnim = createLeftAnimation(fullregion);
    }
}
