package client.livingroom;

/**
 * Created by ianarbuckle on 12/04/2017.
 *
 */
public interface LivingRoomPresenter {
    void lights();
    void curtains();
    void warm();
    void tvRemote();
    void updateLivingPoll(String message);
    void disableLivingClient();
}
