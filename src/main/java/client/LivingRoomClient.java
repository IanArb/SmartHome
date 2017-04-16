package client;

import clientui.LivingRoomUI;
import models.LivingModel;
import utils.Constants;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public class LivingRoomClient extends Client {

    private boolean isLightsOn;
    private boolean isCurtainOpen;
    private boolean isWarming;
    private boolean isTvOn;
    private LivingModel model;

    public LivingRoomClient() {
        serviceType = Constants.UDP_SOCKET_LIVING;
        ui = new LivingRoomUI(this);
        name = "Living Room";
        model = new LivingModel();
    }

    @Override
    public void updatePoll(String message) {
        super.updatePoll(message);
        switch (message) {
            case "Fireplace is 100% warmed":
                isWarming = false;
                break;
            default:
                isWarming = false;
                isCurtainOpen = false;
                isLightsOn = false;
                isTvOn = false;
                break;
        }
    }

    public void lights() {
        if(!isLightsOn) {
            model.setRequest(Constants.REQUEST_OK);
            model.setLights(Constants.LIGHTS_ON_REQUEST);
            sendMessage(model.getLights());
            isLightsOn = true;
            ui.updateArea("Living room lights are turned on");
        } else {
            model.setRequest(Constants.REQUEST_OK);
            model.setLights(Constants.LIGHTS_OFF_REQUEST);
            sendMessage(Constants.LIGHTS_OFF_REQUEST);
            isLightsOn = false;
            ui.updateArea("Living room lights are turned off");
        }
    }

    public void warm() {
        if (!isWarming) {
            model.setRequest(Constants.REQUEST_OK);
            model.setWarmRoom(Constants.WARM_REQUEST);
            sendMessage(model.getWarmRoom());
            ui.updateArea("Fireplace is warming");
            isWarming = true;
        } else {
            ui.updateArea("Fireplace is already warming");
        }
    }

    public void curtains() {
        if(!isCurtainOpen) {
            model.setRequest(Constants.REQUEST_OK);
            model.setCurtains(Constants.CURTAIN_OPEN_REQUEST);
            sendMessage(model.getCurtains());
            isCurtainOpen = true;
            ui.updateArea("Curtains are open");
        } else {
            model.setRequest(Constants.REQUEST_OK);
            model.setCurtains(Constants.CURTAIN_CLOSE_REQUEST);
            sendMessage(Constants.CURTAIN_CLOSE_REQUEST);
            isCurtainOpen = false;
            ui.updateArea("Curtains are closed");
        }
    }

    public void tvRemote() {
        if(!isTvOn) {
            model.setRequest(Constants.REQUEST_OK);
            model.setTelevision(Constants.TV_ON_REQUEST);
            sendMessage(model.getTelevision());
            isTvOn = true;
            ui.updateArea("TV is turned on");
        } else {
            model.setRequest(Constants.REQUEST_OK);
            model.setTelevision(Constants.TV_OFF_REQUEST);
            sendMessage(Constants.TV_OFF_REQUEST);
            isTvOn = false;
            ui.updateArea("TV is turned off");
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new LivingRoomUI(this);
        isLightsOn = false;
        isCurtainOpen = false;
        isWarming = false;
        isTvOn = false;
    }
}
