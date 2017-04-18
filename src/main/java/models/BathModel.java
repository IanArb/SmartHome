package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Created by Fran Firmino on 17/04/2017.
 */
public class BathModel {
    @SerializedName("request")
    @Expose
    private String request;

    @SerializedName("lights")
    @Expose
    private String lightsSwitch;

    @SerializedName("boiler")
    @Expose
    private String heatWater;

    @SerializedName("tap")
    @Expose
    private String tapSwitch;

    public BathModel(){

    }

    public BathModel(String request, String lightsSwitch, String heatWater, String tapSwitch) {
        this.request = request;
        this.lightsSwitch = lightsSwitch;
        this.heatWater = heatWater;
        this.tapSwitch = tapSwitch;
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

    public String getHeatWater() {
        return heatWater;
    }

    public void setHeatWater(String heatWater) {
        this.heatWater = heatWater;
    }

    public String getTapSwitch() {
        return tapSwitch;
    }

    public void setTapSwitch(String tapSwitch) {
        this.tapSwitch = tapSwitch;
    }
}
