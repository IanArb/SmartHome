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
                sendBack(getStatus() + getLampStatus() + getLightsStatus());
                break;
            case Constants.WARM_REQUEST:
                timer.schedule(new RemindTask(), 0, 1000);
                model.setRequest(Constants.REQUEST_OK);
                model.setWarmRoom(Constants.WARM_REQUEST);
                String warmJson = Util.getJson(model);
                sendBack(warmJson);
                ui.updateArea(warmJson);
                break;
            case Constants.LIGHTS_ON_REQUEST:
                model.setLightsSwitch(Constants.LIGHTS_ON_REQUEST);
                model.setRequest(Constants.REQUEST_OK);
                String lightsOnJson = Util.getJson(model);
                sendBack(lightsOnJson);
                isOn = true;
                ui.updateArea(lightsOnJson);
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                model.setLightsSwitch(Constants.LIGHTS_OFF_REQUEST);
                model.setRequest(Constants.REQUEST_OK);
                String lightsOffJson = Util.getJson(model);
                sendBack(lightsOffJson);
                isOn = false;
                ui.updateArea(lightsOffJson);
                break;
            case Constants.LAMP_ON_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setLampSwitch(Constants.LAMP_ON_REQUEST);
                String lampOnJson = Util.getJson(model);
                sendBack(lampOnJson);
                isLampOn = true;
                ui.updateArea(lampOnJson);
                break;
            case Constants.LAMP_OFF_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setLampSwitch(Constants.LAMP_OFF_REQUEST);
                String lampOffJson = Util.getJson(model);
                sendBack(lampOffJson);
                isLampOn = false;
                ui.updateArea(lampOffJson);
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
        return "Bedroom is " + percentHot + "% warmed. ";
    }

    private String getLightsStatus() {
        String message;
        if(isOn) {
            message = "Lights ON ";
        } else {
            message = "Lights OFF ";
        }
        return message;
    }

    private String getLampStatus() {
        String message;
        if(isLampOn) {
            message = "Lamp ON ";
        } else {
            message = "Lamp OFF ";
        }
        return message;
    }

    public static void main(String[] args) throws IOException {
        new BedService(Constants.BED_SERVICE_NAME);
    }
}
