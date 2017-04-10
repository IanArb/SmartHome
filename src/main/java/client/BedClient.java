/*
 * 
 */
package client;

import clientui.BedUI;
import utils.Constants;

/**
 * Bed Client.
 *
 * @author dominic
 */
public class BedClient extends Client {

    private final String WARM = "Warm";
    private final String LIGHTS_ON = "Lights On";
    private final String LIGHTS_OFF = "Lights Off";
    private boolean isWarming = false;
    private boolean isOn = false;
    private boolean isOff = false;

    /**
     * Bed Client Constructor.
     */
    public BedClient() {
        super();
        serviceType = Constants.UDP_SOCKET;
        ui = new BedUI(this);
        name = "Bedroom";
    }

    /**
     * sends a message to warm the bed.
     */
    public void warm() {
        if (!isWarming) {
            String action = sendMessage(WARM);
            if (action.equals(Constants.REQUEST_OK)) {
                isWarming = true;
                ui.updateArea("Bedroom is warming");
            }
        } else {
            ui.updateArea("Bedroom is already warming");
        }
    }

    public void lights() {
        if(!isOn) {
            String action = sendMessage(LIGHTS_ON);
            if(action.equals(Constants.REQUEST_OK)) {
                isOn = true;
                ui.updateArea("Lights are turned on");
            }
        } else {
            sendMessage(LIGHTS_OFF);
            isOn = false;
            ui.updateArea("Lights are turned off");
        }
    }

    public void lightsOff() {
        if(!isOff) {
            String action = sendMessage(LIGHTS_OFF);
            if(action.equals(Constants.REQUEST_OK)) {
                isOff = true;
                ui.updateArea("Lights are turned off");
            } else {
                isOff = false;
                ui.updateArea("Lights are on");
            }
        }
    }

    public void lamps() {

    }

    @Override
    public void updatePoll(String msg) {
        switch (msg) {
            case "Bed is 100% warmed.":
                isWarming = false;
                break;
            default:
                isWarming = false;
                isOn = false;
                isOff = false;
                break;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new BedUI(this);
        isWarming = false;
        isOn = false;
        isOff = false;
    }
}
