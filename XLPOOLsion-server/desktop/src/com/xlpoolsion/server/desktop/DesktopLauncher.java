package com.xlpoolsion.server.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.xlpoolsion.server.XLPOOLsionServer;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bombin' Man Server";
		/*
		cfg.useGL20 = true;
		cfg.height = 640;
		cfg.width = 360;
		 */
		new LwjglApplication(new XLPOOLsionServer(), config);
	}
}
