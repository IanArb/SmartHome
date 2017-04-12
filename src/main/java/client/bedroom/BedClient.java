package client.bedroom;

import client.Client;
import clientui.BedUI;
import clientui.ClientUI;
import utils.Constants;

/**
 * Bed Client.
 *
 * @author dominic
 */
public class BedClient extends Client implements BedView {

    private BedPresenterImpl presenter;

    /**
     * Bed Client Constructor.
     */
    public BedClient() {
        super();
        serviceType = Constants.UDP_SOCKET_BED;
        ui = new BedUI(this);
        name = "Bedroom";
        presenter = new BedPresenterImpl(this);
    }

    /**
     * sends a message to warm the bed.
     */
    public void warm() {
        presenter.warm();
    }

    public void lights() {
        presenter.lights();
    }

    public void lamp() {
        presenter.lamp();
    }

    @Override
    public void updatePoll(String msg) {
        presenter.updateBedPoll(msg);
    }

    @Override
    public void setWarmUIMessage(ClientUI clientUI, String message) {
        clientUI = ui;
        clientUI.updateArea(message);
    }

    @Override
    public String sendWarmRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public void setLightsUIMessage(ClientUI clientUI, String message) {
        clientUI = ui;
        clientUI.updateArea(message);
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
    public void setLampUIMessage(ClientUI clientUI, String message) {
        clientUI = ui;
        clientUI.updateArea(message);
    }

    @Override
    public String sendLampOnRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public String sendLampOffRequest(String request) {
        return sendMessage(request);
    }

    @Override
    public void disable() {
        super.disable();
        ui = new BedUI(this);
        presenter.disableBedClient();
    }
}
