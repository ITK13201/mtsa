package ltsa.lts.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Duration;
import java.time.ZonedDateTime;
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

    private ZonedDateTime startedAt;
    private ZonedDateTime finishedAt;
    @SerializedName("duration [ms]")
    private Duration duration;
    @SerializedName("maxMemoryUsage [KB]")
    private Long maxMemoryUsage;

    public LTSResultComposeStepSolvingProblem() {
        this.sourceModels = new ArrayList<>();
    }

    public void calculateDuration() {
        this.duration = Duration.between(this.startedAt, this.finishedAt);
    }
}
