package client.bedroom;

import clientui.ClientUI;
import utils.Constants;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public class BedPresenterImpl implements BedPresenter {

    private boolean isWarming;
    private boolean isLightsOn;
    private boolean isLampOn;

    private BedView view;
    ClientUI clientUI;


    public BedPresenterImpl(BedView view) {
        this.view = view;
        isLampOn = false;
        isLightsOn = false;
        isWarming = false;
    }

    @Override
    public void warm() {
        if (!isWarming) {
            String action = view.sendWarmRequest(Constants.WARM_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isWarming = true;
                view.setWarmUIMessage(clientUI, "Bedroom is warming");
            }
        } else {
            view.setWarmUIMessage(clientUI, "Bedroom is already warming");
        }
    }

    @Override
    public void lights() {
        if(!isLightsOn) {
            String action = view.sendLightsOnRequest(Constants.LIGHTS_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isLightsOn = true;
                view.setLightsUIMessage(clientUI, "Bedroom lights are turned on");
            }
        } else {
            view.sendLightsOffRequest(Constants.LIGHTS_OFF_REQUEST);
            isLightsOn = false;
            view.setLightsUIMessage(clientUI, "Bedroom lights are turned off");
        }
    }

    @Override
    public void lamp() {
        if(!isLampOn) {
            String action = view.sendLampOnRequest(Constants.LAMP_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isLampOn = true;
                view.setLampUIMessage(clientUI, "Bedroom lamp is turned on");
            }
        } else {
            view.sendLampOffRequest(Constants.LAMP_OFF_REQUEST);
            isLampOn = false;
            view.setLampUIMessage(clientUI, "Bedroom lamp is turned off");
        }
    }


    @Override
    public void updateBedPoll(String message) {
        switch (message) {
            case "Bedroom is 100% warmed.":
                isWarming = false;
                break;
            default:
                isWarming = false;
                isLightsOn = false;
                isLampOn = false;
                break;
        }
    }

    @Override
    public void disableBedClient() {
        isWarming = false;
        isLightsOn = false;
        isLampOn = false;
    }
}
