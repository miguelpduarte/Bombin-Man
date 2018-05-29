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
                //TODO: Close connection on this message please
                //NetworkRouter.getInstance().endConnection();
                break;
            case LOST_CONNECTION:
                System.out.println("Lost connection to server!!");
                xlpooLsionClient.setScreen(new MainMenuScreen(xlpooLsionClient));
                break;
        }
    }
}
