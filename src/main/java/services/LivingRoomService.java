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
                sendBack(getStatus() + getLightsStatus() + getCurtainStatus() + getTVStatus());
                break;
            case Constants.WARM_REQUEST:
                timer.schedule(new WarmTask(), 0, 2000);
                model.setRequest(Constants.REQUEST_OK);
                model.setWarmRoom(Constants.WARM_REQUEST);
                String warmJson = Util.getJson(model);
                sendBack(warmJson);
                ui.updateArea(warmJson);
                break;
            case Constants.LIGHTS_ON_REQUEST:
                model.setLights(Constants.LIGHTS_ON_REQUEST);
                model.setRequest(Constants.REQUEST_OK);
                String lightsOnJson = Util.getJson(model);
                sendBack(lightsOnJson);
                isLightsOn = true;
                ui.updateArea(lightsOnJson);
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setLights(Constants.LIGHTS_OFF_REQUEST);
                String lightsOffJson = Util.getJson(model);
                sendBack(lightsOffJson);
                isLightsOn = false;
                ui.updateArea(lightsOffJson);
                break;
            case Constants.CURTAIN_OPEN_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setCurtains(Constants.CURTAIN_OPEN_REQUEST);
                String curtainsOpenJson = Util.getJson(model);
                sendBack(curtainsOpenJson);
                ui.updateArea(curtainsOpenJson);
                isOpen = true;
                break;
            case Constants.CURTAIN_CLOSE_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setCurtains(Constants.CURTAIN_CLOSE_REQUEST);
                String curtainsCloseJson = Util.getJson(model);
                sendBack(curtainsCloseJson);
                ui.updateArea(curtainsCloseJson);
                isOpen = false;
                break;
            case Constants.TV_ON_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setTelevision(Constants.TV_ON_REQUEST);
                String tvOnJson = Util.getJson(model);
                sendBack(tvOnJson);
                ui.updateArea(tvOnJson);
                isOn = true;
                break;
            case Constants.TV_OFF_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setTelevision(Constants.TV_OFF_REQUEST);
                String tvOffJson = Util.getJson(model);
                sendBack(tvOffJson);
                ui.updateArea(tvOffJson);
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
        return "Fireplace is " + warmPercentage + "% warm ";
    }

    private String getLightsStatus() {
        String message;
        if(isLightsOn) {
            message = "Lights ON ";
        } else {
            message = "Lights OFF ";
        }
        return message;
    }

    private String getCurtainStatus() {
        String message;
        if(isOpen) {
            message = "Curtains OPEN ";
        } else {
            message = "Curtains CLOSED ";
        }
        return message;
    }

    private String getTVStatus() {
        String message;
        if(isOn) {
            message = "TV ON ";
        } else {
            message = "TV OFF ";
        }
        return message;
    }

    public static void main(String[] args) throws IOException {
        new LivingRoomService(Constants.LIVING_ROOM_SERVICE_NAME);
    }
}
