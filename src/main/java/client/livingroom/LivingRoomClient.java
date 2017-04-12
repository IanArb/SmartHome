package client.livingroom;

import client.Client;
import clientui.ClientUI;
import clientui.LivingRoomUI;
import utils.Constants;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public class LivingRoomClient extends Client implements LivingRoomView {

    private LivingRoomPresenterImpl presenter;

    public LivingRoomClient() {
        serviceType = Constants.UDP_SOCKET_LIVING;
        ui = new LivingRoomUI(this);
        name = "Living Room";
        presenter = new LivingRoomPresenterImpl(this);
    }

    @Override
    public void updatePoll(String msg) {
        super.updatePoll(msg);
        presenter.updateLivingPoll(msg);
    }

    public void lights() {
        presenter.lights();
    }

    public void warm() {
        presenter.warm();
    }

    public void curtains() {
        presenter.curtains();
    }

    public void tvRemote() {
        presenter.tvRemote();
    }

    @Override
    public void disable() {
        super.disable();
        ui = new LivingRoomUI(this);
        presenter.disableLivingClient();
    }

    @Override
    public void setWarmUIMessage(ClientUI clientUI, String message) {
        clientUI = ui;
        clientUI.updateArea(message);
    }

    @Override
    public void setLightsUIMessage(ClientUI clientUI, String message) {
        clientUI = ui;
        clientUI.updateArea(message);
    }

    @Override
    public String sendWarmRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public void setCurtainUIMessage(ClientUI clientUI, String message) {
        clientUI = ui;
        clientUI.updateArea(message);
    }

    @Override
    public String sendCurtainOpenRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public String sendCurtainCloseRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public String sendLightsOnRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public String sendLightsOffRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public void setTVUIMessage(ClientUI clientUI, String message) {
        clientUI = ui;
        clientUI.updateArea(message);
    }

    @Override
    public String sendTVOnRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public String sendTVOffRequest(String request) {
        return sendMessage(request);
    }
}
