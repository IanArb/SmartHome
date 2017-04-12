package services;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import serviceui.ServiceUI;
import utils.Constants;

/**
 * Created by Fran Firmino on 11/04/2017.
 */
public class KitchenService extends Service {
    private boolean isON;
    private final Timer timer;
    private int percentBoiled;

    public KitchenService(String name) {
        super(name, Constants.UDP_SOCKET_KITCHEN);
        timer = new Timer();
        percentBoiled = 0;
        isON = false;
        ui = new ServiceUI(this, name);
    }

    @Override
    protected void performAction(String action) {
        switch (action) {
            case Constants.STATUS_REQUEST:
                sendBack(getStatus());
                break;
            case Constants.KETTLE_BOIL_REQUEST:
                timer.schedule(new RemindTask(), 0, 1000);
                sendBack(Constants.REQUEST_OK);
                ui.updateArea("Boiling Kettle");
                break;
            case Constants.KETTLE_STATUS:
                sendBack(getKettleStatus());
                break;
            case Constants.OVEN_ON_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isON = true;
                ui.updateArea("Turning on Oven");
                break;
            case Constants.OVEN_OFF_REQUEST:
                sendBack(Constants.REQUEST_OK);
                isON = false;
                ui.updateArea("Turning off Oven");
                break;
            case Constants.OVEN_STATUS:
                sendBack(getOvenStatus());
                break;
            default:
                sendBack(Constants.BAD_COMMAND + " - " + action);
                break;
        }

    }

    class RemindTask extends TimerTask {

        @Override
        public void run() {
            if (percentBoiled < 100) {
                percentBoiled += 10;
            }
        }
    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public String getLightsStatus() {return null;}


    public String getOvenStatus() {
        String status ="";
        if(isON){
            status = "ON";
        }else if(!isON){
             status = "OFF";

        }
        return "Oven is "+status;
    }

    @Override
    public String getCurtainStatus() {
        return null;
    }

    public String getKettleStatus() {
        return "Kettle is "+percentBoiled + "% boiled. ";
    }

    public static void main(String[] args) throws IOException {
        new KitchenService(Constants.KITCHEN_SERVICE_NAME);
    }
}
