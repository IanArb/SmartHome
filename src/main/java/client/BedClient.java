package client;

import clientui.BedUI;
import models.BedModel;
import utils.Constants;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public class BedClient extends Client {

    private boolean isWarming;
    private boolean isLightsOn;
    private boolean isLampOn;
    private BedModel model;

    /**
     * Bed Client Constructor.
     */
    public BedClient() {
        super();
        serviceType = Constants.UDP_SOCKET_BED;
        ui = new BedUI(this);
        name = "Bedroom";
        model = new BedModel();
    }

    public void warm() {
        if (!isWarming) {
            model.setRequest(Constants.REQUEST_OK);
            model.setWarmRoom(Constants.WARM_REQUEST);
            sendMessage(model.getWarmRoom());
            ui.updateArea("Bedroom is warming");
            isWarming = true;
        } else {
            ui.updateArea("Bedroom is already warming");
        }
    }

    public void lights() {
        if(!isLightsOn) {
            model.setRequest(Constants.REQUEST_OK);
            model.setLightsSwitch(Constants.LIGHTS_ON_REQUEST);
            sendMessage(model.getLightsSwitch());
            isLightsOn = true;
            ui.updateArea("Bedroom lights are turned on");
        } else {
            model.setRequest(Constants.REQUEST_OK);
            model.setLightsSwitch(Constants.LIGHTS_OFF_REQUEST);
            sendMessage(Constants.LIGHTS_OFF_REQUEST);
            isLightsOn = false;
            ui.updateArea("Bedroom lights are turned off");
        }
    }

    public void lamp() {
        if(!isLampOn) {
            model.setLampSwitch(Constants.LAMP_ON_REQUEST);
            model.setRequest(Constants.REQUEST_OK);
            sendMessage(model.getLampSwitch());
            isLampOn = true;
            ui.updateArea("Bedroom lamp is turned on");
        } else {
            model.setLampSwitch(Constants.LAMP_OFF_REQUEST);
            model.setRequest(Constants.REQUEST_OK);
            sendMessage(model.getLampSwitch());
            isLampOn = false;
            ui.updateArea("Bedroom lamp is turned off");
        }
    }

    @Override
    public void updatePoll(String message) {
        switch (message) {
            case "Bedroom is 100% warmed.":
                isWarming = false;
                break;
            default:
                isWarming = false;
                isLightsOn = false;
                isLampOn = false;
                break;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new BedUI(this);
        isWarming = false;
        isLightsOn = false;
        isLampOn = false;
    }
}
