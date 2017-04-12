package client.livingroom;

import clientui.ClientUI;
import utils.Constants;

/**
 * Created by ianarbuckle on 12/04/2017.
 *
 */
public class LivingRoomPresenterImpl implements LivingRoomPresenter {

    private LivingRoomView view;
    ClientUI clientUI;

    private boolean isLightsOn;
    private boolean isCurtainOpen;
    private boolean isWarming;
    private boolean isTvOn;

    public LivingRoomPresenterImpl(LivingRoomView view) {
        this.view = view;
        isLightsOn = false;
        isCurtainOpen = false;
        isWarming = false;
        isTvOn = false;
    }

    public void lights() {
        if(!isLightsOn) {
            String action = view.sendLightsOnRequest(Constants.LIGHTS_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isLightsOn = true;
                view.setLightsUIMessage(clientUI, "Living room lights are turned on");
            }
        } else {
            view.sendLightsOffRequest(Constants.LIGHTS_OFF_REQUEST);
            isLightsOn = false;
            view.setLightsUIMessage(clientUI, "Living room lights are turned off");
        }
    }

    public void curtains() {
        if(!isCurtainOpen) {
            String action = view.sendCurtainOpenRequest(Constants.CURTAIN_OPEN_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isCurtainOpen = true;
                view.setCurtainUIMessage(clientUI, "Curtains are opened");
            }
        } else {
            view.sendCurtainCloseRequest(Constants.CURTAIN_CLOSE_REQUEST);
            isCurtainOpen = false;
            view.setCurtainUIMessage(clientUI, "Curtains are closed");
        }
    }

    public void warm() {
        if (!isWarming) {
            String action = view.sendWarmRequest(Constants.WARM_REQUEST);
            if (action.equals(Constants.REQUEST_OK)) {
                isWarming = true;
                view.setWarmUIMessage(clientUI, "Fireplace is heating");
            }
        } else {
            view.setWarmUIMessage(clientUI, "Fireplace is already warming");
        }
    }

    @Override
    public void tvRemote() {
        if(!isTvOn) {
            String action = view.sendTVOnRequest(Constants.TV_ON_REQUEST);
            if(action.equals(Constants.REQUEST_OK)) {
                isTvOn = true;
                view.setTVUIMessage(clientUI, "TV is turned on");
            }
        } else {
            view.sendTVOffRequest(Constants.TV_OFF_REQUEST);
            isTvOn = false;
            view.setTVUIMessage(clientUI, "TV is turned off");
        }
    }

    @Override
    public void updateLivingPoll(String message) {
        switch (message) {
            case "Fireplace is 100% warmed":
                isWarming = false;
                break;
            default:
                isWarming = false;
                isCurtainOpen = false;
                isLightsOn = false;
                isTvOn = false;
                break;
        }
    }

    @Override
    public void disableLivingClient() {
        isLightsOn = false;
        isCurtainOpen = false;
        isWarming = false;
        isTvOn = false;
    }

}
