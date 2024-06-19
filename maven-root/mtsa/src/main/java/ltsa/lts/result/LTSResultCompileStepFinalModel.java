package ltsa.lts.result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.ZonedDateTime;

@Data
public class LTSResultCompileStepFinalModel {
    private String name;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    private Integer numberOfControllableActions;
    private Integer numberOfUncontrollableActions;

    public LTSResultCompileStepFinalModel(String name) {
        this.name = name;
    }

    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
                .registerTypeAdapter(Duration.class, new DurationSerializer())
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
                .setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
