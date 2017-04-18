package services;

import models.BathModel;
import serviceui.ServiceUI;
import utils.Constants;
import utils.Util;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Fran Firmino on 12/04/2017.
 */
public class BathService extends Service {
    private final Timer timer;
    private int percentHot;
    private boolean lightsOn;
    private boolean TapOn;
    private BathModel model;


    public BathService(String name) {
        super(name, Constants.UDP_SOCKET_BATH);
        timer = new Timer();
        percentHot = 0;
        lightsOn = false;
        ui = new ServiceUI(this, name);
        model = new BathModel();
    }

    @Override
    protected void performAction(String action) {
        switch (action){
            case Constants.STATUS_REQUEST:
                model.setHeatWater(getStatus());
                model.setTapSwitch(getTapStatus());
                model.setLightsSwitch(getLightsStatus());
                sendBack(getStatus() + getTapStatus() + getLightsStatus());
                break;
            case Constants.BOILER_REQUEST:
                timer.schedule(new RemindTask(), 0, 1000);
                model.setRequest(Constants.REQUEST_OK);
                model.setHeatWater(Constants.BOILER_REQUEST);
                String heatJson = Util.getJson(model);
                sendBack(heatJson);
                ui.updateArea(heatJson);
                break;
            case Constants.LIGHTS_ON_REQUEST:
                model.setLightsSwitch(Constants.LIGHTS_ON_REQUEST);
                model.setRequest(Constants.REQUEST_OK);
                String lightsOnJson = Util.getJson(model);
                sendBack(lightsOnJson);
                lightsOn = true;
                ui.updateArea(lightsOnJson);
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                model.setLightsSwitch(Constants.LIGHTS_OFF_REQUEST);
                model.setRequest(Constants.REQUEST_OK);
                String lightsOffJson = Util.getJson(model);
                sendBack(lightsOffJson);
                lightsOn = false;
                ui.updateArea(lightsOffJson);
                break;
             case Constants.TAP_ON_REQUEST:
                 model.setRequest(Constants.REQUEST_OK);
                 model.setTapSwitch(Constants.TAP_ON_REQUEST);
                 String tapOnJson = Util.getJson(model);
                 sendBack(tapOnJson);
                TapOn = true;
                ui.updateArea(tapOnJson);
                break;
            case Constants.TAP_OFF_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setTapSwitch(Constants.TAP_OFF_REQUEST);
                String tapOffJson = Util.getJson(model);
                sendBack(tapOffJson);
                TapOn = false;
                ui.updateArea(tapOffJson);
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
       return getBoilerStatus();
    }

    public String getLightsStatus() {
        if(lightsOn) {
            return "Lights ON ";
        } else {
            return "Lights OFF ";
        }
    }

    public String getTapStatus(){
        if(TapOn) {
            return "Taps are ON ";
        } else {
            return "Taps are OFF ";
        }
    }

    public String getBoilerStatus() {
        return "Water is " + percentHot + "% hot.";
    }

    public static void main(String[] args) throws IOException {
        new BathService(Constants.BATH_SERVICE_NAME);
    }


}
