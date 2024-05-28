package ltsa.lts;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LTSResultManager {
    public static LTSResult data = null;
    public static final ZoneId JST = ZoneId.of("Asia/Tokyo");
    public static final String BASE_RESULT_DATA_DIR = "../../resultdata";

    public static void init(String command, String inputFilePath, String targetController) {
        data = new LTSResult(command, inputFilePath, targetController);
    }

    public static void delete() {
        data = null;
    }

    public static void start() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.setStartedAt(now);
    }

    public static void finish() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.setFinishedAt(now);
    }

    private static String getResultFilePath() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String prefix = formatter.format(data.getStartedAt());

        return String.format("%s/%s_%s_%s_%s.json", BASE_RESULT_DATA_DIR, prefix, data.getLts(), data.getTargetController(), data.getCommand());
    }

    public static void dump() {
        try (
                BufferedWriter bw = Files.newBufferedWriter(Paths.get(getResultFilePath()), StandardCharsets.UTF_8);
                PrintWriter pw = new PrintWriter(bw);
        ) {
            pw.println(data.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
