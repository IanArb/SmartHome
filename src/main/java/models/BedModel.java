package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author ianarbuckle on 13/04/2017.
 */
public class BedModel {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("Lights")
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

    public BedModel(String status, String lightsSwitch, String warmRoom, String lampSwitch) {
        this.status = status;
        this.lightsSwitch = lightsSwitch;
        this.warmRoom = warmRoom;
        this.lampSwitch = lampSwitch;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
