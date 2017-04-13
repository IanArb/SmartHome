package services;

import utils.Constants;

/**
 * Created by ianarbuckle on 13/04/2017.
 *
 */
public class Services {

    public static void main(String[] args) {
        new BedService(Constants.BED_SERVICE_NAME);
        new BathService(Constants.BATH_SERVICE_NAME);
        new KitchenService(Constants.KITCHEN_SERVICE_NAME);
        new LivingRoomService(Constants.LIVING_ROOM_SERVICE_NAME);
    }
}
