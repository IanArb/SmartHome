package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author ianarbuckle on 13/04/2017.
 */
public class BedModel {

    @SerializedName("request")
    @Expose
    private String request;

    @SerializedName("lights")
    @Expose
    private String lightsSwitch;

    @SerializedName("heater")
    @Expose
    private String warmRoom;

    @SerializedName("lamp")
    @Expose
    private String lampSwitch;

    public BedModel() {

    }

    public BedModel(String request, String lightsSwitch, String warmRoom, String lampSwitch) {
        this.request = request;
        this.lightsSwitch = lightsSwitch;
        this.warmRoom = warmRoom;
        this.lampSwitch = lampSwitch;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getLightsSwitch() {
        return lightsSwitch;
    }

    public void setLightsSwitch(String lightsSwitch) {
        this.lightsSwitch = lightsSwitch;
    }

    public String getWarmRoom() {
        return warmRoom;
    }

    public void setWarmRoom(String warmRoom) {
        this.warmRoom = warmRoom;
    }

    public String getLampSwitch() {
        return lampSwitch;
    }

    public void setLampSwitch(String lampSwitch) {
        this.lampSwitch = lampSwitch;
    }

}
