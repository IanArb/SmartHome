package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author ianarbuckle on 13/04/2017.
 */
public class LivingModel {

    @SerializedName("heater")
    @Expose
    private String warmRoom;

    @SerializedName("lights")
    @Expose
    private String lights;

    @SerializedName("tv")
    @Expose
    private String television;

    @SerializedName("curtains")
    @Expose
    private String curtains;

    public LivingModel() {

    }

    public LivingModel(String warmRoom, String lights, String television, String curtains) {
        this.warmRoom = warmRoom;
        this.lights = lights;
        this.television = television;
        this.curtains = curtains;
    }

    public String getWarmRoom() {
        return warmRoom;
    }

    public void setWarmRoom(String warmRoom) {
        this.warmRoom = warmRoom;
    }

    public String getLights() {
        return lights;
    }

    public void setLights(String lights) {
        this.lights = lights;
    }

    public String getTelevision() {
        return television;
    }

    public void setTelevision(String television) {
        this.television = television;
    }

    public String getCurtains() {
        return curtains;
    }

    public void setCurtains(String curtains) {
        this.curtains = curtains;
    }
}
