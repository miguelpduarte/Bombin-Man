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
            viewMap.put(model, new PlayerView(xlpooLsionServer));
        } else if (model instanceof PowerUpModel) {
            viewMap.put(model, new PowerUpView(xlpooLsionServer));
        }
    }

    public static void destroyView(EntityModel model) {
        viewMap.remove(model);
    }
}
