package com.xpoolsion.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.xpoolsion.server.networking.NetworkManager;

import java.io.IOException;

public class XPOOLsionServer extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	NetworkManager networkManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		try {
			networkManager = new NetworkManager(9021);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();

		try {
			networkManager.readInput();
		} catch (IOException e) {
			System.out.println("Server read error");
			e.printStackTrace();
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.K)) {
			try {
				networkManager.acceptClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		networkManager.closeServer();
	}
}
