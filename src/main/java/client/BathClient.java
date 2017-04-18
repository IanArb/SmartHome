package client;

import clientui.BathUI;
import models.BathModel;
import utils.Constants;

/**
 * Created by Fran Firmino on 12/04/2017.
 */
public class BathClient extends Client{

    private boolean isHeating = false;
    private boolean isLightsOn = false;
    private boolean isTapOn = false;
    private BathModel model;

    public BathClient() {
        super();
        serviceType = Constants.UDP_SOCKET_BATH;
        ui = new BathUI(this);
        name = "Bathroom";
        model = new BathModel();
    }

    /**
     * sends a message to heat the water
     */
    public void heat() {
        if (!isHeating) {
            model.setRequest(Constants.REQUEST_OK);
            model.setHeatWater(Constants.BOILER_REQUEST);
            sendMessage(model.getHeatWater());
                isHeating = true;
                ui.updateArea("Water is heating");
        } else {
            ui.updateArea("Water is already heating");
        }
    }

    public void lights() {
        if(!isLightsOn) {
            model.setRequest(Constants.REQUEST_OK);
            model.setLightsSwitch(Constants.LIGHTS_ON_REQUEST);
            sendMessage(model.getLightsSwitch());
            isLightsOn = true;
            ui.updateArea("Lights are on");
        } else {
            model.setRequest(Constants.REQUEST_OK);
            model.setLightsSwitch(Constants.LIGHTS_OFF_REQUEST);
            sendMessage(model.getLightsSwitch());
            isLightsOn = false;
            ui.updateArea("Lights are off");
        }
    }

    public void tap() {
        if(!isTapOn) {
            model.setTapSwitch(Constants.TAP_ON_REQUEST);
            model.setRequest(Constants.REQUEST_OK);
            sendMessage(model.getTapSwitch());
                isTapOn = true;
                ui.updateArea("Tap is on");
        } else {
            model.setTapSwitch(Constants.TAP_OFF_REQUEST);
            model.setRequest(Constants.REQUEST_OK);
            sendMessage(model.getTapSwitch());
            isTapOn = false;
            ui.updateArea("Tap is off");
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
