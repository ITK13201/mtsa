package ltsa.lts.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.time.Duration;

@Data
public class LTSResultCompileStepRequirement {
    private String name;
    @SerializedName("minimizeDuration [ms]")
    private Duration minimizeDuration;

    public LTSResultCompileStepRequirement(String name) {
        this.name = name;
    }
}
