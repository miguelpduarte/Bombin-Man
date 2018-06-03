package com.xlpoolsion.client.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;

/**
 * Button factory used to create buttons
 */
public class ButtonFactory {
    /**
     * Creates a button based on the arguments received
     * @param xlpooLsionClient The game this button belongs to
     * @param upImagePath The image of the button when it is not being pressed
     * @param downImagePath The image of the button when it is being pressed
     * @param x X coordinate of the button
     * @param y Y coordinate of the button
     * @param width width of the button
     * @param height height of the button
     * @return The Button with the correct characteristics
     */
    public static Button makeButton(XLPOOLsionClient xlpooLsionClient, String upImagePath, String downImagePath, float x, float y, float width, float height) {
        Button.ButtonStyle bs = new Button.ButtonStyle();
        bs.up = new TextureRegionDrawable(new TextureRegion((Texture)xlpooLsionClient.getAssetManager().get(upImagePath)));
        bs.down = new TextureRegionDrawable(new TextureRegion((Texture)xlpooLsionClient.getAssetManager().get(downImagePath)));
        Button b = new Button(bs);
        b.setWidth(width);
        b.setHeight(height);
        b.setPosition(x, y, Align.center);
        return b;
    }
}
