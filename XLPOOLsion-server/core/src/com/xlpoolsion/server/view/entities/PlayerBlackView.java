package com.xlpoolsion.server.view.entities;

import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.view.TextureManager;

/**
 * A view used to create the animation for the black player
 */
public class PlayerBlackView extends PlayerView {
    public PlayerBlackView(XLPOOLsionServer xlpooLsionServer) {
        super(xlpooLsionServer);
    }

    /**
     * Create Black player animations
     * @param xlpooLsionServer The game these animations belong to
     */
     void createAnimations(XLPOOLsionServer xlpooLsionServer) {
         super.upAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLACK, TextureManager.PlayerAnimType.UP);
         super.rightAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLACK, TextureManager.PlayerAnimType.RIGHT);
         super.downAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLACK, TextureManager.PlayerAnimType.DOWN);
         super.leftAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLACK, TextureManager.PlayerAnimType.LEFT);
         super.deathAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLACK, TextureManager.PlayerAnimType.DEATH);
         super.stunAnim = xlpooLsionServer.getTextureManager().getPlayerAnimation(TextureManager.PlayerColor.BLACK, TextureManager.PlayerAnimType.STUN);
    }
}
