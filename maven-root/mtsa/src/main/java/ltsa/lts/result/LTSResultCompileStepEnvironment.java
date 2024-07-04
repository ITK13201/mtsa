package ltsa.lts.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;

@Data
public class LTSResultCompileStepEnvironment {
    private String name;
    private Integer numberOfMaxStates;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    @SerializedName("composeDuration [ms]")
    private Duration composeDuration;
    public ArrayList<String> sourceModels;

    public LTSResultCompileStepEnvironment(String name) {
        this.name = name;
        this.sourceModels = new ArrayList<>();
    }
}
