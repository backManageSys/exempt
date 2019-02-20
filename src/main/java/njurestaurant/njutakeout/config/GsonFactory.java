package njurestaurant.njutakeout.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import njurestaurant.njutakeout.config.jsonAdapter.EventAdapter;
import njurestaurant.njutakeout.config.jsonAdapter.SpringfoxJsonToGsonAdapter;
import njurestaurant.njutakeout.parameters.event.EventAddParameters;
import springfox.documentation.spring.web.json.Json;

public class GsonFactory {
    public static Gson get() {
        return new GsonBuilder()
                .registerTypeAdapter(EventAddParameters.class, new EventAdapter())
                .registerTypeAdapter(Json.class, new SpringfoxJsonToGsonAdapter())
                .create();
    }
}
