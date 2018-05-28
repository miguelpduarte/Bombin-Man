package com.xlpoolsion.client.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.xlpoolsion.client.XLPOOLsionClient;

public class ButtonFactory {
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
