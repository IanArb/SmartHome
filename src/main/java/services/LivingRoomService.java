package services;

import serviceui.ServiceUI;
import utils.Constants;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public class LivingRoomService extends Service {

    private Timer timer;

    private boolean isLightsOn;
    private boolean isOpen;
    private boolean isOn;

    private int warmPercentage;

    public LivingRoomService(String name) {
        super(name, Constants.UDP_SOCKET_LIVING);
        timer = new Timer();
        isLightsOn = false;
        isOpen = false;
        isOn = false;
        warmPercentage = 0;
        ui = new ServiceUI(this, name);
    }

    @Override
    protected void performAction(String action) {
        switch (action) {
            case Constants.STATUS_REQUEST:
                sendBack(getStatus());
                break;
            case Constants.WARM_REQUEST:
                timer.schedule(new WarmTask(), 0, 2000);
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Fireplace is heating");
                break;
            case Constants.LIGHTS_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isLightsOn = true;
                ui.updateArea("Turning on lights");
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isLightsOn = false;
                ui.updateArea("Turning off lights");
                break;
            case Constants.LIGHTS_STATUS:
                sendBack(getLightsStatus());
                break;
            case Constants.CURTAIN_STATUS:
                sendBack(getCurtainStatus());
                break;
            case Constants.CURTAIN_OPEN_REQUEST:
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Opening curtains");
                isOpen = true;
                break;
            case Constants.CURTAIN_CLOSE_REQUEST:
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Closing curtains");
                isOpen = false;
                break;
            case Constants.TV_STATUS:
                sendBack(getTVStatus());
                break;
            case Constants.TV_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Turning on TV");
                isOn = true;
                break;
            case Constants.TV_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Turning off TV");
                isOn = false;
                break;
            default:
                sendBack(Constants.BAD_COMMAND + " - " + action);
                break;
        }
    }

    private class WarmTask extends TimerTask {

        @Override
        public void run() {
            if(warmPercentage < 100) {
                warmPercentage += 10;
            }
        }
    }

    @Override
    public String getStatus() {
        return "Fireplace is " + warmPercentage + "% warm";
    }

    @Override
    public String getLightsStatus() {
        String message;
        if(isLightsOn) {
            message = "Lights are on";
        } else {
            message = "Lights are off";
        }
        return message;
    }

    public String getCurtainStatus() {
        String message;
        if(isOpen) {
            message = "Curtains are open";
        } else {
            message = "Curtains are closed";
        }
        return message;
    }

    public String getTVStatus() {
        String message;
        if(isOn) {
            message = "TV is ON";
        } else {
            message = "TV is OFF";
        }
        return message;
    }

    public static void main(String[] args) throws IOException {
        new LivingRoomService(Constants.LIVING_ROOM_SERVICE_NAME);
    }
}
