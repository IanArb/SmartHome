package client.bedroom;

import clientui.ClientUI;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public interface BedView {
    void setWarmUIMessage(ClientUI clientUI, String message);
    void setLightsUIMessage(ClientUI clientUI, String message);
    void setLampUIMessage(ClientUI clientUI, String message);
    String sendWarmRequest(String request);
    String sendLightsOnRequest(String request);
    String sendLightsOffRequest(String request);
    String sendLampOnRequest(String request);
    String sendLampOffRequest(String request);
}
