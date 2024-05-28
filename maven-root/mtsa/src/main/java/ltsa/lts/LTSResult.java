package ltsa.lts;

import com.google.gson.*;
import lombok.Data;

import java.io.File;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

class ZonedDateTimeSerializer implements JsonSerializer<ZonedDateTime> {
    @Override
    public JsonElement serialize(ZonedDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
    }
}

@Data
public class LTSResult {
    // initial parameters
    private String command;
    private String ltsFilePath;
    private String targetController;
    // automatically set up by ltsFilePath
    private String lts;

    // additional parameters
    private ZonedDateTime startedAt;
    private ZonedDateTime finishedAt;

    public LTSResult(String command, String ltsFilePath, String targetController) {
        this.command = command;
        this.ltsFilePath = ltsFilePath;
        this.targetController = targetController;
        this.lts = this.getLTSNameFromInputFilePath(ltsFilePath);
    }

    private String getLTSNameFromInputFilePath(String ltsFilePath) {
        File file = new File(ltsFilePath);
        String basename = file.getName();
        return basename.substring(0, basename.lastIndexOf('.'));
    }

    public String toJson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeSerializer())
                .excludeFieldsWithModifiers(Modifier.STATIC, Modifier.TRANSIENT, Modifier.VOLATILE)
                .setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
