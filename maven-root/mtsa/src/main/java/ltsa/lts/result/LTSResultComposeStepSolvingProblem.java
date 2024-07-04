package ltsa.lts.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;

@Data
public class LTSResultComposeStepSolvingProblem {
    private Integer numberOfMaxStates;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    private Integer numberOfControllableActions;
    private Integer numberOfUncontrollableActions;
    @SerializedName("composeDuration [ms]")
    private Duration composeDuration;
    @SerializedName("solvingDuration [ms]")
    private Duration solvingDuration;
    public ArrayList<String> sourceModels;

    public LTSResultComposeStepSolvingProblem() {
        this.sourceModels = new ArrayList<>();
    }
}
