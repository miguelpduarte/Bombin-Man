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
	//private NetworkManager networkManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		/*
		try {
			networkManager = new NetworkManager("192.168.1.17", 9021);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.end();

		/*
		if(Gdx.input.justTouched()) {
			r = 1;
			g = 1;
			b = 1;
			//networkManager.sendOutput();
		} else {
			r = 0;
			g = 0;
			b = 0;
		}
		*/



		if(isShakingPhone()) {
			Gdx.input.vibrate(200);
			//Gdx.app.getInput().vibrate(200);
			lastPaint = System.currentTimeMillis();
			r = 1;
			g = 0;
			b = 0;
		} else {
			if(System.currentTimeMillis() - lastPaint > 500) {
				r = 0;
				g = 1;
				b = 0;
			}
		}

		/*
		if(Gdx.input.isKeyJustPressed(Input.Keys.VOLUME_DOWN)) {
			try {
				networkManager.readInput();
			} catch (IOException e) {
				System.out.println("Input reading failed");
				e.printStackTrace();
			}
		}
		*/
	}

	private long lastPaint = System.currentTimeMillis();

	private static final int SHAKE_THRESHOLD = 900;
	private static final long SHAKE_TIMEOUT = 100;

	private long lastUpdate = System.currentTimeMillis();
	private float last_x = 0;
	private float last_y = 0;
	private float last_z = 0;

	private boolean isShakingPhone() {
		long curTime = System.currentTimeMillis();
		// only allow one update every 100ms.
		if ((curTime - lastUpdate) > SHAKE_TIMEOUT) {
			long diffTime = (curTime - lastUpdate);
			lastUpdate = curTime;

			float x = Gdx.input.getAccelerometerX();
			float y = Gdx.input.getAccelerometerY();
			float z = Gdx.input.getAccelerometerZ();

			float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

			if (speed > SHAKE_THRESHOLD) {
				System.out.println("shake detected w/ speed: " + speed);
				//Toast.makeText(this, "shake detected w/ speed: " + speed, Toast.LENGTH_SHORT).show();
				return true;
			}
			last_x = x;
			last_y = y;
			last_z = z;
		}
		return false;
	}

	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
