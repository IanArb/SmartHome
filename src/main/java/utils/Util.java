package utils;

import com.google.gson.Gson;

/**
 * @author ianarbuckle on 13/04/2017.
 */
public class Util {

    public static String getJson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

}
