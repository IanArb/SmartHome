package client.livingroom;

import clientui.ClientUI;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public interface LivingRoomView {
    void setWarmUIMessage(ClientUI clientUI, String message);
    void setLightsUIMessage(ClientUI clientUI, String message);
    void setCurtainUIMessage(ClientUI clientUI, String message);
    void setTVUIMessage(ClientUI clientUI, String message);
    String sendWarmRequest(String request);
    String sendCurtainOpenRequest(String request);
    String sendCurtainCloseRequest(String request);
    String sendLightsOnRequest(String request);
    String sendLightsOffRequest(String request);
    String sendTVOnRequest(String request);
    String sendTVOffRequest(String request);
}
