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
    private boolean isOn = false;

    public BedService(String name) {
        super(name, Constants.UDP_SOCKET);
        timer = new Timer();
        percentHot = 0;
        isOn = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String a) {
        switch (a) {
            case Constants.STATUS_REQUEST:
                sendBack(getStatus());
                break;
            case Constants.WARM_REQUEST:
                timer.schedule(new RemindTask(), 0, 2000);
                sendBack("OK");
                ui.updateArea("Warming Bed");
                break;
            case "lights_status":
                sendBack(getLightsStatus());
                break;
            case Constants.LIGHTS_REQUEST:
                timer.schedule(new RemindTask(), 0, 100);
                if (isOn) {
                    sendBack("OK");
                    ui.updateArea("Lights are off");
                } else {
                    sendBack("OK");
                    ui.updateArea("Lights are on");
                }
            default:
                sendBack(BAD_COMMAND + " - " + a);
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
        return "Bed is " + percentHot + "% warmed.";
    }

    public String getLightsStatus() {
        if (isOn) {
            return "Lights are off";
        } else {
            return "Lights are on";
        }
    }

    public static void main(String[] args) throws IOException {
        new BedService("Dominic's");
    }
}
