package ltsa.lts.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;

@Data
public class LTSResultCompileStep {
    public ArrayList<LTSResultCompileStepEnvironment> environments;
    public ArrayList<LTSResultCompileStepRequirement> requirements;
    public ArrayList<LTSResultCompileStepFinalModel> finalModels;

    private ZonedDateTime startedAt;
    private ZonedDateTime finishedAt;
    @SerializedName("duration [ms]")
    private Duration duration;
    @SerializedName("maxMemoryUsage [KB]")
    private Long maxMemoryUsage = -1L;

    LTSResultCompileStep() {
        this.environments = new ArrayList<>();
        this.requirements = new ArrayList<>();
        this.finalModels = new ArrayList<>();
    }

    public void calculateDuration() {
        this.duration = Duration.between(this.startedAt, this.finishedAt);
    }
}