package com.xlpoolsion.client;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.xlpoolsion.client.networking.NetworkManager;
import com.xlpoolsion.common.Message;

import java.io.IOException;
import java.net.ConnectException;

public class XLPOOLsionClient extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Viewport viewport;
	float r = 0;
	float g = 0;
	float b = 0;
	private Label infoLbl;
	private NetworkManager networkManager;
	
	@Override
	public void create() {
		viewport = new ExtendViewport(640, 320);
		//viewport.set
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

		try {
			networkManager = new NetworkManager("192.168.1.17", 9021);
		} catch (ConnectException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Label.LabelStyle style = new Label.LabelStyle();
		style.fontColor = Color.WHITE;
		//style.background = new SpriteDrawable(new Sprite(img));
		//style.background = ...; // Set the drawable you want to use
		style.font = new BitmapFont();

		infoLbl = new Label("Init text", style);
		infoLbl.setFontScale(4.0f);
		//infoLbl.setPosition(viewport.getScreenWidth()/2, viewport.getScreenHeight()/2, 0);
		//infoLbl.setPosition(100, 100, 0);

		//viewport.apply();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(r, g, b, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		infoLbl.draw(batch, 1.0f);
		batch.end();

		if(Gdx.input.justTouched()) {
			infoLbl.setPosition(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
		}


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
			Gdx.input.vibrate(250);
			lastPaint = System.currentTimeMillis();
			r = 1;
			g = 0;
			b = 0;
			try {
				networkManager.sendMessage(new Message(5, 6.9f, "wow what a message!"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if(System.currentTimeMillis() - lastPaint > 500) {
				r = 0;
				g = 1;
				b = 0;
			}
		}
	}

	private long lastPaint = System.currentTimeMillis();

	private static final float SHAKE_THRESHOLD = 10.0f;

	private boolean isShakingPhone() {

		infoLbl.setText(String.format("gyro X: %.2f    gyro Y: %.2f    gyro Z: %.2f",
				Gdx.input.getGyroscopeX(), Gdx.input.getGyroscopeY(), Gdx.input.getGyroscopeZ()));


		System.out.println(String.format("gyro X: %.2f    gyro Y: %.2f    gyro Z: %.2f",
				Gdx.input.getGyroscopeX(), Gdx.input.getGyroscopeY(), Gdx.input.getGyroscopeZ()));

		return Math.abs(Gdx.input.getGyroscopeY()) > SHAKE_THRESHOLD;
	}



	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
