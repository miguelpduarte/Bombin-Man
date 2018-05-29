package com.xlpoolsion.client.view.screens;

import com.xlpoolsion.client.XLPOOLsionClient;
import com.xlpoolsion.client.controller.GameController;

public class WaitingForServerScreen extends StageScreen {
    public WaitingForServerScreen(XLPOOLsionClient xlpooLsionClient) {
        super(xlpooLsionClient);
    }

    @Override
    protected void loadAssets() {

    }

    @Override
    protected void createGUI() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        switch (GameController.getInstance().getCurrentState()) {
            case PLAYING:
                xlpooLsionClient.setScreen(new ControlsScreen(xlpooLsionClient));
                break;
            case SERVER_FULL:
                System.out.println("Server full! Screen is WIP");
                xlpooLsionClient.setScreen(new MainMenuScreen(xlpooLsionClient));
                //Temporary because screen is not yet created (Should be done when pressing back button in server_full screen)
                GameController.getInstance().resetState();
                break;
            case LOST_CONNECTION:
                System.out.println("Lost connection to server!!");
                xlpooLsionClient.setScreen(new MainMenuScreen(xlpooLsionClient));
                break;
        }
    }
}
