package ltsa.lts.result;


import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class LTSResultManager {
    public static LTSResult data = null;
    public static final ZoneId JST = ZoneId.of("Asia/Tokyo");
    public static final String DEFAULT_OUTPUT_DIR = "../../resultdata";

    public static LTSResultStep currentStep = LTSResultStep.INIT;
    public static HashSet<String> controllableActions;
    public static String outputDir = null;
    public static LTSResultMode mode = LTSResultMode.ENABLED;


    public static void init(String mode, String command, String inputFilePath, String target) {
        data = new LTSResult(mode, command, inputFilePath, target);
        controllableActions = new HashSet<>();
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
        data.calculateDuration();
    }

    public static void startCompile() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.getCompileStep().setStartedAt(now);
    }

    public static void finishCompile() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.getCompileStep().setFinishedAt(now);
        data.getCompileStep().calculateDuration();
    }

    public static void startCreatingGameSpace() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.getComposeStep().creatingGameSpace.setStartedAt(now);
    }

    public static void finishCreatingGameSpace() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.getComposeStep().creatingGameSpace.setFinishedAt(now);
        data.getComposeStep().creatingGameSpace.calculateDuration();
    }

    public static void startSolvingProblem() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.getComposeStep().solvingProblem.setStartedAt(now);
    }

    public static void finishSolvingProblem() {
        ZonedDateTime now = ZonedDateTime.now(JST);
        data.getComposeStep().solvingProblem.setFinishedAt(now);
        data.getComposeStep().solvingProblem.calculateDuration();
    }

    private static String getResultFilePath() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String prefix = formatter.format(data.getStartedAt());
        Path path = Paths.get(outputDir, String.format("%s_%s_%s_%s.json", prefix, data.getLts(), data.getTarget(), data.getCommand()));

        return path.toString();
    }

    private static String getInputModelsResultFilePath() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String prefix = formatter.format(data.getStartedAt());
        Path path = Paths.get(outputDir, "input-models", String.format("%s_%s_%s_%s.json", prefix, data.getLts(), data.getTarget(), data.getCommand()));

        return path.toString();
    }

    public static void setControllableActions(Set<String> controllableActions) {
        LTSResultManager.controllableActions.addAll(controllableActions);
    }

    public static void dump_input_models() {
        try (
                BufferedWriter bw = Files.newBufferedWriter(Paths.get(getInputModelsResultFilePath()), StandardCharsets.UTF_8);
                PrintWriter pw = new PrintWriter(bw);
        ) {
            pw.println(data.toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
