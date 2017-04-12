package services;

import serviceui.ServiceUI;
import utils.Constants;

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


    public BathService(String name) {
        super(name, Constants.UDP_SOCKET_BATH);
        timer = new Timer();
        percentHot = 0;
        lightsOn = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    protected void performAction(String action) {
        switch (action){
            case Constants.STATUS_REQUEST:
                sendBack(getStatus());
                break;
            case Constants.BOILER_REQUEST:
                timer.schedule(new RemindTask(), 0, 1000);
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Heating Water");
                break;
            case Constants.LIGHTS_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                lightsOn = true;
                ui.updateArea("Turning on lights");
                break;
            case Constants.LIGHTS_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                lightsOn = false;
                ui.updateArea("Turning off lights");
                break;
            case Constants.LIGHTS_STATUS:
                sendBack(getLightsStatus());
                break;
            case Constants.TAP_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                TapOn = true;
                ui.updateArea("Turning on tap");
                break;
            case Constants.TAP_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                TapOn = false;
                ui.updateArea("Turning off tap");
                break;
            case Constants.TAP_STATUS:
                sendBack(getTapStatus());
                break;
            case Constants.BOILER_STATUS:
                sendBack(getBoilerStatus());
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
       return getBoilerStatus()+" "+getLightsStatus()+ " "+ getTapStatus();
    }

    @Override
    public String getLightsStatus() {
        if(lightsOn) {
            return "Lights are on.";
        } else {
            return "Lights are off.";
        }
    }

    public String getTapStatus(){
        if(TapOn) {
            return "Taps are on.";
        } else {
            return "Taps are off.";
        }
    }

    public String getBoilerStatus() {
        return "Water is " + percentHot + "% hot.";
    }

    public static void main(String[] args) throws IOException {
        new BathService(Constants.BATH_SERVICE_NAME);
    }


}
