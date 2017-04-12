package client.bedroom;

/**
 * Created by ianarbuckle on 11/04/2017.
 *
 */
public interface BedPresenter {
    void warm();
    void lights();
    void lamp();
    void updateBedPoll(String message);
    void disableBedClient();
}
