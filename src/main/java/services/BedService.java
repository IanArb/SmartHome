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

    private final Timer tempTimer;
    private final Timer lightsTimer;
    private int percentHot;
    private int lightsPer;
    private int brightness;
    private boolean isOn;
    private boolean isOff;

    public BedService(String name) {
        super(name, Constants.UDP_SOCKET);
        tempTimer = new Timer();
        lightsTimer = new Timer();
        percentHot = 0;
        lightsPer = 0;
        brightness = 0;
        isOn = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        switch (a) {
            case Constants.STATUS_REQUEST:
                sendBackTemperature(getStatus());
                break;
            case Constants.WARM_REQUEST:
                tempTimer.schedule(new RemindTask(), 0, 2000);
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Warming Bed");
                break;
            default:
                sendBackTemperature(BAD_COMMAND + " - " + a);
                break;
            case Constants.LIGHTS_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Turning on lights");
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Turning off lights");
                break;
            case Constants.LIGHTS_STATUS:
                sendBack(getLightsStatus());
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
        if(isOff) {
            return "Lights are off";
        } else {
            return "Lights are on";
        }
    }

    public static void main(String[] args) throws IOException {
        new BedService("Bed Service");
    }
}
