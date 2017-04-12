package client;

import clientui.BathUI;
import utils.Constants;

/**
 * Created by Fran Firmino on 12/04/2017.
 */
public class BathClient extends Client{

    private boolean isHeating = false;
    private boolean isLightsOn = false;
    private boolean isTapOn = false;

    public BathClient() {
        super();
        serviceType = Constants.UDP_SOCKET_BATH;
        ui = new BathUI(this);
        name = "Bathroom";
    }

    /**
     * sends a message to heat the water
     */
    public void heat() {
        if (!isHeating) {
            String action = sendMessage(Constants.BOILER_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isHeating = true;
                ui.updateArea("Water is heating");
            }
        } else {
            ui.updateArea("Water is already heating");
        }
    }

    public void lights() {
        if(!isLightsOn) {
            String action = sendMessage(Constants.LIGHTS_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isLightsOn = true;
                ui.updateArea("Lights are turned on");
            }
        } else {
            sendMessage(Constants.LIGHTS_OFF_REQUEST);
            isLightsOn = false;
            ui.updateArea("Lights are turned off");
        }
    }

    public void tap() {
        if(!isTapOn) {
            String action = sendMessage(Constants.TAP_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isTapOn = true;
                ui.updateArea("Tap is turned on");
            }
        } else {
            sendMessage(Constants.TAP_OFF_REQUEST);
            isTapOn = false;
            ui.updateArea("Tap is turned off");
        }
    }

    @Override
    public void updatePoll(String msg) {
        switch (msg) {
            case "Water is 100% hot.":
                isHeating = false;
                break;
            default:
                isHeating = false;
                isLightsOn = false;
                isTapOn = false;
                break;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new BathUI(this);
        isHeating = false;
        isLightsOn = false;
        isTapOn = false;
    }
}
