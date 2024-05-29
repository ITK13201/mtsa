package ltsa.ui;

import ltsa.dispatcher.TransitionSystemDispatcher;
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
    String targetName;
    Integer sleepTime;

    public CUIManager(String command, String inputFilePath, String targetName, Integer sleepTime) {
        this.command = command;
        this.inputFilePath = inputFilePath;
        this.targetName = targetName;
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

    // === [START] PARTIAL COPY FROM HPWindow ===

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

    private void doComposition(
            LTSInputString ltsInputString,
            LTSOutput ltsOutput,
            String currentDirectory,
            String targetName
    ) {
        long maxMemoryUsage = 0;
        long maxStates = 0;
        long maxTransitions = 0;

        long startTime = System.currentTimeMillis();

        CompositeState cs = this.doCompile(ltsInputString, ltsOutput, currentDirectory, targetName);
        ltsOutput.outln("Compile is Complete!");
        ltsOutput.outln("");
        ltsOutput.outln("");
        ltsOutput.outln("");
        ltsOutput.outln("===================================================");
        ltsOutput.outln("                    Composition                    ");
        ltsOutput.outln("===================================================");
        TransitionSystemDispatcher.applyComposition(cs, ltsOutput);

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime; //ms

        /* When reusing other results */
        // ltsOutput.clearOutput();
        // compileIfChange();
        // if (current != null) {
        //     try {
        //         TransitionSystemDispatcher.applyComposition(current, ltsOutput);

        //     } catch (LTSCompositionException e) {
        //         return;
        //     }

        //     boolean isControllable = current.composition != null;
        //     if (!isControllable) {
        //         return;
        //         //throw new LTSException("Composition not controllable.");

        //     }

        //     postState(current);
        //     int[] current_states = new int[current.machines.size() + 1];
        //     for (int i = 0; i < current.machines.size() + 1; i++)
        //         current_states[i] = 0;
        //     layouts.setCurrentState(current_states);
        // }

        ltsOutput.outln("");
        ltsOutput.outln("");
        ltsOutput.outln("[info] Composition is Complete!");
        ltsOutput.outln("[info] Maximum State       : " + maxStates);
        ltsOutput.outln("[info] Maximum Transition  : " + maxTransitions);
        ltsOutput.outln("[info] Maximum Memory (KB) : " + maxMemoryUsage);
        ltsOutput.outln("[info] Execution Time (ms) : " + executionTime);
        ltsOutput.outln("");
    }

    // === [END] PARTIAL COPY FROM HPWindow ===

    private void runCompile(String input, String targetName) {
        LTSInputString ltsInput = new LTSInputString(input);
        CUIOutput ltsOutput = new CUIOutput();
        String currentDirectory = ".";

        LTSResultManager.start();
        doCompile(ltsInput, ltsOutput, currentDirectory, targetName);
        LTSResultManager.finish();

        System.out.println(ltsOutput.toString());
        LTSResultManager.dump();
    }

    private void runCompose(String input, String targetName) {
        LTSInputString ltsInput = new LTSInputString(input);
        CUIOutput ltsOutput = new CUIOutput();
        String currentDirectory = ".";

        LTSResultManager.start();
        doCompile(ltsInput, ltsOutput, currentDirectory, targetName);
        doComposition(ltsInput, ltsOutput, currentDirectory, targetName);
        LTSResultManager.finish();

        System.out.println(ltsOutput.toString());
        LTSResultManager.dump();
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

        LTSResultManager.init("CUI", this.command, this.inputFilePath, this.targetName);

        String input = readFile(this.inputFilePath);
        switch (this.command) {
            case "compile":
                System.out.println("Run Compile");
                this.runCompile(input, this.targetName);
                break;
            case "compose":
                System.out.println("Run Compose");
                this.runCompose(input, this.targetName);
                break;
            default:
                System.out.println("Unknown command: " + this.command);
        }
    }
}