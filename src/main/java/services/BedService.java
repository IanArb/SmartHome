package services;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.BedModel;
import serviceui.ServiceUI;
import utils.Constants;
import utils.Util;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public class BedService extends Service {

    private final Timer timer;
    private int percentHot;
    private boolean isOn;
    private boolean isLampOn;
    private BedModel model;

    public BedService(String name) {
        super(name, Constants.UDP_SOCKET_BED);
        timer = new Timer();
        percentHot = 0;
        isOn = false;
        isLampOn = false;
        model = new BedModel();
        ui = new ServiceUI(this, name);
    }

    @Override
    public void performAction(String action) {

        switch (action) {
            case Constants.STATUS_REQUEST:
                model.setWarmRoom(getStatus());
                model.setLampSwitch(getLampStatus());
                model.setLightsSwitch(getLightsStatus());
                String toJson = Util.getJson(model);
                sendBack(toJson);
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
            case Constants.LAMP_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isLampOn = true;
                ui.updateArea("Turning on lamp");
                break;
            case Constants.LAMP_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isLampOn = false;
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
        return getBedRoomStatus();
    }

    private String getBedRoomStatus() {
        return "Bedroom is " + percentHot + "% warmed.";
    }

    private String getLightsStatus() {
        String message;
        if(isOn) {
            message = "Lights are on";
        } else {
            message = "Lights are off";
        }
        return message;
    }

    private String getLampStatus() {
        String message;
        if(isLampOn) {
            message = "Lamp is on";
        } else {
            message = "Lamp is off";
        }
        return message;
    }

    public static void main(String[] args) throws IOException {
        new BedService(Constants.BED_SERVICE_NAME);
    }
}
