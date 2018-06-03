package com.xlpoolsion.server.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.server.XLPOOLsionServer;

/**
 * Factory used to create Buttons
 */
public class ButtonFactory {
    /**
     * Creates a button based on the variables
     * @param xlpooLsionServer The game the button belongs to
     * @param upImagePath Image for the button when it is not being pressed
     * @param downImagePath Image for the button when the buttons is being pressed
     * @param x X Position of the button
     * @param y Y Position of the button
     * @param width Width of the button
     * @param height Height of the button
     * @return
     */
    public static Button makeButton(XLPOOLsionServer xlpooLsionServer, String upImagePath, String downImagePath, float x, float y, float width, float height) {
        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionServer.getAssetManager().get(upImagePath)));
        bs.down = new TextureRegionDrawable(new TextureRegion((Texture) xlpooLsionServer.getAssetManager().get(downImagePath)));
        Button b = new Button(bs);
        b.setWidth(width);
        b.setHeight(height);
        b.setPosition(x, y, Align.center);
        return b;
    }
}
