package com.xlpoolsion.server.view.entities;

import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.view.TextureManager;

/**
 *  A view used to create the animation for the blue player
 */
public class PlayerBlueView extends PlayerView {
    public PlayerBlueView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    /**
     * Create Blue player animations
     * @param xlpooLsionServer The game these animations belong to
     */
     void createAnimations(XLPOOLsionServer xlpooLsionServer) {
         super.upAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLUE, TextureManager.PlayerAnimType.UP);
         super.rightAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLUE, TextureManager.PlayerAnimType.RIGHT);
         super.downAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLUE, TextureManager.PlayerAnimType.DOWN);
         super.leftAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLUE, TextureManager.PlayerAnimType.LEFT);
         super.deathAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLUE, TextureManager.PlayerAnimType.DEATH);
         super.stunAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLUE, TextureManager.PlayerAnimType.STUN);
    }
}
