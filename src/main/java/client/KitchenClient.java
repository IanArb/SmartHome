package client;

import clientui.KitchenUI;
import models.KitchenModel;
import utils.Constants;

/**
 * Created by Fran Firmino on 11/04/2017.
 */
public class KitchenClient extends Client {
    private boolean isBoiling = false;
    private boolean isOvenOn = false;
    private boolean isTapOn = false;
    private KitchenModel model;

    public KitchenClient() {
        super();
        serviceType = Constants.UDP_SOCKET_KITCHEN;
        ui = new KitchenUI(this);
        name = "Kitchen";
        model = new KitchenModel();
    }

    /**
     * sends a message to boil kettle.
     */
    public void boil() {
        if (!isBoiling) {
            model.setRequest(Constants.REQUEST_OK);
            model.setBoilKettle(Constants.KETTLE_BOIL_REQUEST);
            sendMessage(model.getBoilKettle());
            ui.updateArea("Kettle is boiling");
            isBoiling = true;

        } else {
            ui.updateArea("Kettle is already boiling");
        }
    }

    /**
     * sends a message to turn oven on/off.
     */

    public void oven() {
        if (!isOvenOn) {
            model.setRequest(Constants.REQUEST_OK);
            model.setOvenSwitch(Constants.OVEN_ON_REQUEST);
            sendMessage(model.getOvenSwitch());
            isOvenOn = true;
            ui.updateArea("Oven is on");
        } else {
            model.setRequest(Constants.REQUEST_OK);
            model.setOvenSwitch(Constants.OVEN_OFF_REQUEST);
            sendMessage(model.getOvenSwitch());
            isOvenOn = false;
            ui.updateArea("Oven is off");
        }
    }

    /**
     * sends a message to turn tap on/off.
     */

    public void tap() {
        if (!isTapOn) {
            model.setTapSwitch(Constants.TAP_ON_REQUEST);
            model.setRequest(Constants.REQUEST_OK);
            sendMessage(model.getTapSwitch());
            isTapOn = true;
            ui.updateArea("Tap is on");

        } else {
            model.setTapSwitch(Constants.LAMP_OFF_REQUEST);
            model.setRequest(Constants.REQUEST_OK);
            sendMessage(model.getTapSwitch());
            isTapOn = false;
            ui.updateArea("Tap is off");
        }
    }

    @Override
    public void updatePoll(String msg) {
        switch (msg) {
            case "Kettle is 100% boiled.":
                isBoiling = false;
                break;
            default:
                isBoiling = false;
                isOvenOn = false;
                isTapOn = false;
                break;
        }
    }

    @Override
    public void disable() {
        super.disable();
        ui = new KitchenUI(this);
        isBoiling = false;
        isOvenOn = false;
        isTapOn = false;

    }
}



