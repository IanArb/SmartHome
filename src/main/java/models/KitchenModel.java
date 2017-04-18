package models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Fran Firmino on 17/04/2017.
 */
public class KitchenModel {

    @SerializedName("request")
    @Expose
    private String request;

    @SerializedName("oven")
    @Expose
    private String ovenSwitch;

    @SerializedName("kettle")
    @Expose
    private String boilKettle;

    @SerializedName("tap")
    @Expose
    private String tapSwitch;

    public KitchenModel(){

    }

    public KitchenModel(String request, String tapSwitch, String boilKettle, String ovenSwitch) {
        this.request = request;
        this.ovenSwitch = ovenSwitch;
        this.boilKettle = boilKettle;
        this.tapSwitch = tapSwitch;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
    public String getBoilKettle() {
        return boilKettle;
    }

    public void setBoilKettle(String boilKettle) {
        this.boilKettle = boilKettle;
    }

    public String getTapSwitch() {
        return tapSwitch;
    }

    public void setTapSwitch(String tapSwitch) {
        this.tapSwitch = tapSwitch;
    }

    public String getOvenSwitch() {
        return ovenSwitch;
    }

    public void setOvenSwitch(String ovenSwitch) {
        this.ovenSwitch = ovenSwitch;
    }
}
