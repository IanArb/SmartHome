package client;

import clientui.BedUI;
import utils.Constants;

/**
 * Bed Client.
 *
 * @author dominic
 */
public class BedClient extends Client {

    private boolean isWarming = false;
    private boolean isLightsOn = false;
    private boolean isLampOn = false;

    /**
     * Bed Client Constructor.
     */
    public BedClient() {
        super();
        serviceType = Constants.UDP_SOCKET_BED;
        ui = new BedUI(this);
        name = "Bedroom";
    }

    /**
     * sends a message to warm the bed.
     */
    public void warm() {
        if (!isWarming) {
            String action = sendMessage(Constants.WARM_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isWarming = true;
                ui.updateArea("Bedroom is warming");
            }
        } else {
            ui.updateArea("Bedroom is already warming");
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

    public void lamp() {
        if(!isLampOn) {
            String action = sendMessage(Constants.LAMP_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isLampOn = true;
                ui.updateArea("Lamp is turned on");
            }
        } else {
            sendMessage(Constants.LAMP_OFF_REQUEST);
            isLampOn = false;
            ui.updateArea("Lamp is turned off");
        }
    }

    @Override
    public void updatePoll(String msg) {
        switch (msg) {
            case "Bed is 100% warmed.":
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
