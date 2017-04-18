package services;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import models.KitchenModel;
import serviceui.ServiceUI;
import utils.Constants;
import utils.Util;

/**
 * Created by Fran Firmino on 11/04/2017.
 */
public class KitchenService extends Service {
    private boolean ovenOn;
    private boolean tapOn;
    private final Timer timer;
    private int percentBoiled;
    private KitchenModel model;

    public KitchenService(String name) {
        super(name, Constants.UDP_SOCKET_KITCHEN);
        timer = new Timer();
        percentBoiled = 0;
        ovenOn = false;
        tapOn = false;
        model = new KitchenModel();
        ui = new ServiceUI(this, name);
    }

    @Override
    protected void performAction(String action) {
        switch (action) {
            case Constants.STATUS_REQUEST:
                model.setBoilKettle(getStatus());
                model.setTapSwitch(getTapStatus());
                model.setOvenSwitch(getOvenStatus());
                sendBack(getTapStatus() + getOvenStatus()+ getOvenStatus());
                break;
            case Constants.KETTLE_BOIL_REQUEST:
                timer.schedule(new RemindTask(), 0, 1000);
                model.setRequest(Constants.REQUEST_OK);
                model.setBoilKettle(Constants.KETTLE_BOIL_REQUEST);
                String boilJson = Util.getJson(model);
                sendBack(boilJson);
                ui.updateArea(boilJson);
                break;
            case Constants.OVEN_ON_REQUEST:
                model.setOvenSwitch(Constants.OVEN_ON_REQUEST);
                model.setRequest(Constants.REQUEST_OK);
                String ovenOnJson = Util.getJson(model);
                sendBack(ovenOnJson);
                ovenOn = true;
                ui.updateArea(ovenOnJson);
                break;
            case Constants.OVEN_OFF_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setOvenSwitch(Constants.OVEN_OFF_REQUEST);
                String ovenOffJson = Util.getJson(model);
                sendBack(ovenOffJson);
                ovenOn = false;
                ui.updateArea(ovenOffJson);
                break;
            case Constants.TAP_ON_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setTapSwitch(Constants.TAP_ON_REQUEST);
                String tapOnJSon = Util.getJson(model);
                sendBack(tapOnJSon);
                ui.updateArea(tapOnJSon);
                break;
            case Constants.TAP_OFF_REQUEST:
                model.setRequest(Constants.REQUEST_OK);
                model.setTapSwitch(Constants.TAP_OFF_REQUEST);
                String tapOffJson = Util.getJson(model);
                sendBack(tapOffJson);
                ui.updateArea(tapOffJson);
                break;
            case Constants.TAP_STATUS:
                sendBack(getTapStatus());
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
        return getKettleStatus();
    }

    public String getTapStatus() {
        if (tapOn) {
            return "Tap ON ";
        } else {
            return "Tap OFF ";
        }
    }


    public String getOvenStatus() {
        if (ovenOn) {
            return "Oven ON ";
        } else {
            return "Oven OFF ";
        }
    }

    public String getKettleStatus() {
        return "Kettle is " + percentBoiled + "% boiled. ";
    }

    public static void main(String[] args) throws IOException {
        new KitchenService(Constants.KITCHEN_SERVICE_NAME);
    }
}
