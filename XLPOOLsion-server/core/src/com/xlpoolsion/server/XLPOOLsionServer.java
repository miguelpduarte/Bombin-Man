package com.xlpoolsion.server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xlpoolsion.server.networking.NetworkRouter;
import com.xlpoolsion.server.view.TextureManager;
import com.xlpoolsion.server.view.screens.GameScreen;
import com.xlpoolsion.server.view.screens.LobbyScreen;

public class XLPOOLsionServer extends Game {
	SpriteBatch batch;
	AssetManager assetManager;
	TextureManager textureManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		textureManager = new TextureManager(assetManager);

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
