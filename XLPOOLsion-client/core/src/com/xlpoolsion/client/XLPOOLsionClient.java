package com.xlpoolsion.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.xlpoolsion.client.networking.NetworkManager;

import java.io.IOException;
import java.net.ConnectException;

public class XLPOOLsionClient extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Button btn;
	Stage stage;
	float r = 0;
	float g = 0;
	float b = 0;
	private NetworkManager networkManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		try {
			networkManager = new NetworkManager("192.168.1.17", 9021);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();

		if(Gdx.input.justTouched()) {
			r = 1;
			g = 1;
			b = 1;
			networkManager.sendOutput();
		} else {
			r = 0;
			g = 0;
			b = 0;
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.VOLUME_DOWN)) {
			try {
				networkManager.readInput();
			} catch (IOException e) {
				System.out.println("Input reading failed");
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
