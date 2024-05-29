package ltsa.lts;

import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {
    @Override
    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}

// Duration is output in millisecond [ms]
class DurationSerializer implements JsonSerializer<Duration> {
    @Override
    public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.toMillis());
    }
}

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
    private ZonedDateTime startedAt;
    private ZonedDateTime finishedAt;
    @SerializedName("duration [ms]")
    private Duration duration;

    public LTSResult(String mode, String command, String ltsFilePath, String target) {
        this.mode = mode;
        this.command = command;
        this.ltsFilePath = ltsFilePath;
        this.target = target;
        this.lts = this.getLTSNameFromInputFilePath(ltsFilePath);
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
