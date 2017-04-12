package client;

import clientui.KitchenUI;
import utils.Constants;

/**
 * Created by Fran Firmino on 11/04/2017.
 */
public class KitchenClient extends Client {
    private boolean isBoiling= false;
    private boolean isOvenOn = false;

    public KitchenClient() {
        super();
        serviceType = Constants.UDP_SOCKET_KITCHEN;
        ui = new KitchenUI(this);
        name = "Kitchen";
    }

    /**
     * sends a message to boil kettle.
     */
    public void boil() {
        if (!isBoiling) {
            String action = sendMessage(Constants.KETTLE_BOIL_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isBoiling = true;
                ui.updateArea("Kettle is boiling");
            }
        } else {
            ui.updateArea("Kettle is already boiling");
        }
    }

    /**
     * sends a message to turn oven on/off.
     */

    public void oven() {
        if (!isOvenOn) {
            String action = sendMessage(Constants.OVEN_ON_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isOvenOn = true;
                ui.updateArea("Oven is on");
            }
        } else {
            sendMessage(Constants.OVEN_OFF_REQUEST);
            isOvenOn = false;
            ui.updateArea("Oven is off");
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
                    break;
            }
        }

        @Override
        public void disable() {
            super.disable();
            ui = new KitchenUI(this);
            isBoiling = false;
            isOvenOn = false;

        }
    }



