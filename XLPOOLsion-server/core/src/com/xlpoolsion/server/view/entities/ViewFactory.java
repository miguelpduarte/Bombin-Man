package com.xlpoolsion.server.view.entities;

import com.xlpoolsion.server.XLPOOLsionServer;
import com.xlpoolsion.server.model.entities.*;

import java.util.HashMap;
import java.util.Map;

public class ViewFactory {
    private static Map<EntityModel, EntityView> viewMap = new HashMap<EntityModel, EntityView>();

    public static EntityView getView(XLPOOLsionServer xlpooLsionServer, EntityModel model) {
        if(!viewMap.containsKey(model)) {
            createView(xlpooLsionServer, model);
        }
        return viewMap.get(model);
    }

    private static void createView(XLPOOLsionServer xlpooLsionServer, EntityModel model) {
        if (model instanceof BombModel) {
            viewMap.put(model, new BombView(xlpooLsionServer));
        } else if (model instanceof BreakableBrickModel) {
            viewMap.put(model, new BreakableBrickView(xlpooLsionServer));
        } else if (model instanceof BrickModel) {
            viewMap.put(model, new BrickView(xlpooLsionServer));
        } else if (model instanceof ExplosionModel) {
            viewMap.put(model, new ExplosionView(xlpooLsionServer));
        } else if (model instanceof PlayerModel) {
            createPlayerView(xlpooLsionServer, model);
        } else if (model instanceof PowerUpModel) {
            createPowerupView(xlpooLsionServer, model);
        } else if (model instanceof PowerDownModel) {
            createPowerDownView(xlpooLsionServer, model);
        }
    }

    private static void createPlayerView(XLPOOLsionServer xlpooLsionServer, EntityModel model) {
        switch(((PlayerModel) model).getId()) {
            case 1:
                viewMap.put(model, new PlayerBlueView(xlpooLsionServer));
                break;
            case 2:
                viewMap.put(model, new PlayerBlackView(xlpooLsionServer));
                break;
            case 3:
                viewMap.put(model, new PlayerRedView(xlpooLsionServer));
                break;
            default:
                //Player 0 is the default sprite, and so this is future proofed for the case that more players are added
                viewMap.put(model, new PlayerRedView(xlpooLsionServer));
                break;
        }
    }

    private static void createPowerupView(XLPOOLsionServer xlpooLsionServer, EntityModel model) {
        switch(((PowerUpModel)model).getType()) {
            case SpeedUp:
                viewMap.put(model, new SpeedUpView(xlpooLsionServer));
                break;
            case BombRadUp:
                viewMap.put(model, new RadiusUpView(xlpooLsionServer));
                break;
            case BombsUp:
                viewMap.put(model, new BombUpView(xlpooLsionServer));
                break;
        }
    }

    private static void createPowerDownView(XLPOOLsionServer xlpooLsionServer, EntityModel model) {
        switch(((PowerDownModel)model).getType()) {
            case SpeedDown:
                viewMap.put(model, new SpeedDownView(xlpooLsionServer));
                break;
            case BombRadDown:
                viewMap.put(model, new RadiusDownView(xlpooLsionServer));
                break;
            case BombsDown:
                viewMap.put(model, new BombDownView(xlpooLsionServer));
                break;
        }
    }

    public static void destroyView(EntityModel model) {
        viewMap.remove(model);
    }
}
