package ltsa.lts.result;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.Duration;

// Duration is output in millisecond [ms]
public class DurationSerializer implements JsonSerializer<Duration> {
    @Override
    public JsonElement serialize(Duration src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(src.toMillis());
    }
}
