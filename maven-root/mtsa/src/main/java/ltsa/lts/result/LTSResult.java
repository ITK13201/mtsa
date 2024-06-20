package ltsa.lts.result;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.time.ZonedDateTime;


@Data
public class LTSResult {
    // initial parameters
    private String mode;
    private String command;
    private String ltsFilePath;
    private String target;
    // automatically set up by ltsFilePath
    private String lts;

    // additional parameters
    private LTSResultCompileStep compileStep;
    private LTSResultComposeStep composeStep;

    private ZonedDateTime startedAt;
    private ZonedDateTime finishedAt;
    @SerializedName("duration [ms]")
    private Duration duration;
    @SerializedName("maxMemoryUsage [KiB]")
    private Long maxMemoryUsage;

    public LTSResult(String mode, String command, String ltsFilePath, String target) {
        this.mode = mode;
        this.command = command;
        this.ltsFilePath = ltsFilePath;
        this.target = target;
        this.lts = this.getLTSNameFromInputFilePath(ltsFilePath);
        this.compileStep = new LTSResultCompileStep();
        this.composeStep = new LTSResultComposeStep();
    }

    private String getLTSNameFromInputFilePath(String ltsFilePath) {
        File file = new File(ltsFilePath);
        String basename = file.getName();
        return basename.substring(0, basename.lastIndexOf('.'));
    }

    public void calculateDuration() {
        this.duration = Duration.between(this.startedAt, this.finishedAt);
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
