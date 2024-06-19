package ltsa.lts.result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Data
public class LTSResultComposeStepCreatingGameSpace {
    private Integer numberOfMaxStates;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    private Integer numberOfControllableActions;
    private Integer numberOfUncontrollableActions;
    @SerializedName("composeDuration [ms]")
    private Duration composeDuration;
    public ArrayList<String> sourceModels;

    public LTSResultComposeStepCreatingGameSpace() {
        this.sourceModels = new ArrayList<>();
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
