package services;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;
import utils.Constants;

/**
 * The Class BedService.
 */
public class BedService extends Service {

    private final Timer timer;
    private int percentHot;
    private boolean isOn;

    public BedService(String name) {
        super(name, Constants.UDP_SOCKET_BED);
        timer = new Timer();
        percentHot = 0;
        isOn = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String action) {
        switch (action) {
            case Constants.STATUS_REQUEST:
                sendBack(getStatus());
                break;
            case Constants.WARM_REQUEST:
                timer.schedule(new RemindTask(), 0, 1000);
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Warming bedroom");
                break;
            case Constants.LIGHTS_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isOn = true;
                ui.updateArea("Turning on lights");
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isOn = false;
                ui.updateArea("Turning off lights");
                break;
            case Constants.LIGHTS_STATUS:
                sendBack(getLightsStatus());
                break;
            case Constants.LAMP_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isOn = false;
                ui.updateArea("Turning on lamp");
                break;
            case Constants.LAMP_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isOn = false;
                ui.updateArea("Turning off lamp");
                break;
            default:
                sendBack(Constants.BAD_COMMAND + " - " + action);
                break;
        }
    }

    private class RemindTask extends TimerTask {

        @Override
        public void run() {
            if (percentHot < 100) {
                percentHot += 10;
            }
        }
    }

    @Override
    public String getStatus() {
        return "Bedroom is " + percentHot + "% warmed.";
    }

    @Override
    public String getLightsStatus() {
        if(isOn) {
            return "Lights are on";
        } else {
            return "Lights are off";
        }
    }

    @Override
    public String getCurtainStatus() {
        //Stub method
        return null;
    }

    public static void main(String[] args) throws IOException {
        new BedService(Constants.BED_SERVICE_NAME);
    }
}
