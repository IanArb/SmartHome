package services;

import models.LivingModel;
import serviceui.ServiceUI;
import utils.Constants;
import utils.Util;

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
    private LivingModel model;

    private int warmPercentage;

    public LivingRoomService(String name) {
        super(name, Constants.UDP_SOCKET_LIVING);
        timer = new Timer();
        isLightsOn = false;
        isOpen = false;
        isOn = false;
        warmPercentage = 0;
        model = new LivingModel();
        ui = new ServiceUI(this, name);
    }

    @Override
    protected void performAction(String action) {
        switch (action) {
            case Constants.STATUS_REQUEST:
                model.setWarmRoom(getStatus());
                model.setTelevision(getTVStatus());
                model.setCurtains(getCurtainStatus());
                model.setLights(getLightsStatus());
                String statusJson = Util.getJson(model);
                sendBack(statusJson);
                break;
            case Constants.WARM_REQUEST:
                timer.schedule(new WarmTask(), 0, 2000);
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Fireplace is heating");
                break;
            case Constants.LIGHTS_ON_REQUEST:
                model.setLights(Constants.REQUEST_OK);
                String lightsOnJson = Util.getJson(model);
                sendBack(lightsOnJson);
                isLightsOn = true;
                ui.updateArea("Turning on lights");
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                model.setLights(Constants.REQUEST_OK);
                String lightsOffJson = Util.getJson(model);
                sendBack(lightsOffJson);
                isLightsOn = false;
                ui.updateArea("Turning off lights");
                break;
            case Constants.CURTAIN_OPEN_REQUEST:
                model.setCurtains(Constants.REQUEST_OK);
                String curtainsOpenJson = Util.getJson(model);
                sendBack(curtainsOpenJson);
                ui.updateArea("Opening curtains");
                isOpen = true;
                break;
            case Constants.CURTAIN_CLOSE_REQUEST:
                model.setCurtains(Constants.REQUEST_OK);
                String curtainsCloseJson = Util.getJson(model);
                sendBack(curtainsCloseJson);
                ui.updateArea("Closing curtains");
                isOpen = false;
                break;
            case Constants.TV_ON_REQUEST:
                model.setTelevision(Constants.REQUEST_OK);
                String tvOnJson = Util.getJson(model);
                sendBack(tvOnJson);
                ui.updateArea("Turning on TV");
                isOn = true;
                break;
            case Constants.TV_OFF_REQUEST:
                model.setTelevision(Constants.REQUEST_OK);
                String tvOff = Util.getJson(model);
                sendBack(tvOff);
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
        return  getFireplaceStatus();
    }

    private String getFireplaceStatus() {
        return "Fireplace is " + warmPercentage + "% warm";
    }

    private String getLightsStatus() {
        String message;
        if(isLightsOn) {
            message = "Lights are on";
        } else {
            message = "Lights are off";
        }
        return message;
    }

    private String getCurtainStatus() {
        String message;
        if(isOpen) {
            message = "Curtains are open";
        } else {
            message = "Curtains are closed";
        }
        return message;
    }

    private String getTVStatus() {
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
