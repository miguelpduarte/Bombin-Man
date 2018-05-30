package com.xlpoolsion.server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.xlpoolsion.server.networking.NetworkRouter;
import com.xlpoolsion.server.view.TextureManager;
import com.xlpoolsion.server.view.screens.LobbyScreen;

public class XLPOOLsionServer extends Game {
	SpriteBatch batch;
	AssetManager assetManager;
	TextureManager textureManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();

		// set the loaders for the generator and the fonts themselves
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		textureManager = new TextureManager(assetManager);

		//Video configs
		Gdx.graphics.setWindowedMode(1920, 1080);

		initView();
	}

	private void initView() {
		setScreen(new LobbyScreen(this));
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public TextureManager getTextureManager() {
		return textureManager;
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
		NetworkRouter.getInstance().closeServer();
	}
}
