package ceyhun.erturk.util;

import com.google.gson.Gson;

public class GsonProvider {
    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static void setGson(Gson gson) {
        GsonProvider.gson = gson;
    }
}
