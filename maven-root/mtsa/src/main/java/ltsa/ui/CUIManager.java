package ltsa.ui;

import ltsa.lts.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;


class CUIManager {
    String command;
    String inputFilePath;
    String targetControllerName;
    Integer sleepTime;

    public CUIManager(String command, String inputFilePath, String targetControllerName, Integer sleepTime) {
        this.command = command;
        this.inputFilePath = inputFilePath;
        this.targetControllerName = targetControllerName;
        this.sleepTime = sleepTime;
    }

    private String readFile(String path) {
        try {
            Path p = Paths.get(path);
            System.out.println("Reading inputted file: " + p.toRealPath(LinkOption.NOFOLLOW_LINKS));
            return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
                    .collect(Collectors.joining(System.getProperty("line.separator")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private CompositeState doCompile(
            LTSInputString ltsInputString,
            LTSOutput ltsOutput,
            String currentDirectory,
            String TargetControllerName
    ) {
        CompositeState cs = null;
        LTSCompiler comp = new LTSCompiler(ltsInputString, ltsOutput, currentDirectory);
        try {
            comp.compile();
            cs = comp.continueCompilation(TargetControllerName);
        } catch (LTSCompositionException x) {
            ltsOutput.outln("Construction of " + TargetControllerName + " aborted.");
            cs = null;
            return cs;
        } catch (LTSException x) {
            cs = null;
            ltsOutput.outln("ERROR: " + x.getMessage());
            return cs;
        }
        return cs;
    }

    private void runCompile(String input, String targetControllerName) {
        LTSInputString ltsInput = new LTSInputString(input);
        CUIOutput ltsOutput = new CUIOutput();
        String currentDirectory = ".";

        doCompile(ltsInput, ltsOutput, currentDirectory, targetControllerName);
        System.out.println(ltsOutput.toString());
    }

    private void runCompose() {
    }

    public void run() {
        // TO CONNECT JVM REMOTE DEBUG TOOL
        if (this.sleepTime != null) {
            try {
                Thread.sleep(this.sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String input = readFile(this.inputFilePath);
        switch (this.command) {
            case "compile":
                System.out.println("Run Compile");
                this.runCompile(input, targetControllerName);
                break;
            case "compose":
                System.out.println("Run Compose");
                this.runCompose();
                break;
            default:
                System.out.println("Unknown command: " + this.command);
        }
    }
}