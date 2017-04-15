package client;

import clientui.LivingRoomUI;
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

    public LivingRoomClient() {
        serviceType = Constants.UDP_SOCKET_LIVING;
        ui = new LivingRoomUI(this);
        name = "Living Room";
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
        if (!isLightsOn) {
            String action = sendMessage(Constants.LIGHTS_ON_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isLightsOn = true;
                ui.updateArea("Living room lights are turned on");
            }
        } else {
            sendMessage(Constants.LIGHTS_OFF_REQUEST);
            isLightsOn = false;
            ui.updateArea("Living room lights are turned off");
        }
    }

    public void warm() {
        if (!isWarming) {
            String action = sendMessage(Constants.WARM_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isWarming = true;
                ui.updateArea("Fireplace is heating");
            }
        } else {
            ui.updateArea("Fireplace is already warming");
        }
    }

    public void curtains() {
        if(!isCurtainOpen) {
            String action = sendMessage(Constants.CURTAIN_OPEN_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isCurtainOpen = true;
                ui.updateArea("Curtains are opened");
            }
        } else {
            sendMessage(Constants.CURTAIN_CLOSE_REQUEST);
            isCurtainOpen = false;
            ui.updateArea("Curtains are closed");
        }
    }

    public void tvRemote() {
        if(!isTvOn) {
            String action = sendMessage(Constants.TV_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isTvOn = true;
                ui.updateArea("TV is turned on");
            }
        } else {
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
