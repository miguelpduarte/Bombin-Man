package com.xlpoolsion.server;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xlpoolsion.server.view.GameView;
import com.xlpoolsion.server.view.LobbyView;

public class XLPOOLsionServer extends Game {
	SpriteBatch batch;
	AssetManager assetManager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		initView();
	}

	private void initView() {
		setScreen(new LobbyView(this));
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	@Override
	public void dispose () {
		batch.dispose();
		assetManager.dispose();
	}
}
