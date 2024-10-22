package ltsa.ui;

import MTSTools.ac.ic.doc.mtstools.model.MTS;
import ltsa.ac.ic.doc.mtstools.util.fsp.AutomataToMTSConverter;
import ltsa.control.util.ControllerUtils;
import ltsa.dispatcher.TransitionSystemDispatcher;
import ltsa.lts.*;
import ltsa.lts.result.*;

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
            String targetControllerName
    ) {
        CompositeState cs = null;
        LTSCompiler comp = new LTSCompiler(ltsInputString, ltsOutput, currentDirectory);
        try {
            comp.compile();
            cs = comp.continueCompilation(targetControllerName);

            switch (LTSResultManager.mode) {
                case ENABLED:
                    LTSResultManager.setControllableActions(cs.goal.getControllableActions());


                    for (CompactState machine : cs.machines) {
                        MTS<Long, String> mts = AutomataToMTSConverter.getInstance().convert(machine);
                        LTSResultCompileStepFinalModel finalModel = new LTSResultCompileStepFinalModel(machine.name);
                        finalModel.setNumberOfStates(mts.getStates().size());
                        int numberOfTransitions = Math.toIntExact(ControllerUtils.getNumberOfTransitions(mts));
                        finalModel.setNumberOfTransitions(numberOfTransitions);
                        Long numberOfControllableActions = ControllerUtils.getNumberOfControllableActions(mts, cs.goal, ltsOutput);
                        finalModel.setNumberOfControllableActions(Math.toIntExact(numberOfControllableActions));
                        finalModel.setNumberOfUncontrollableActions(
                                numberOfTransitions - Math.toIntExact(numberOfControllableActions)
                        );
                        LTSResultManager.data.getCompileStep().finalModels.add(finalModel);
                    }

                    for (LTSResultInitialModelsEnvironment environment : LTSResultManager.data.getInitialModels().environments) {
                        environment.initialize(cs.goal, ltsOutput);
                    }

                    for (LTSResultCompileStepFinalModel finalModel : LTSResultManager.data.getCompileStep().finalModels) {
                        if (finalModel == LTSResultManager.data.getCompileStep().finalModels.get(0)) {
                            continue;
                        }

                        LTSResultInitialModelsRequirement requirement = new LTSResultInitialModelsRequirement(
                                finalModel.getName(),
                                finalModel.getNumberOfStates(),
                                finalModel.getNumberOfTransitions(),
                                finalModel.getNumberOfControllableActions(),
                                finalModel.getNumberOfUncontrollableActions()
                        );
                        LTSResultManager.data.getInitialModels().requirements.add(requirement);
                    }
                    break;
                case FOR_MACHINE_LEARNING:
                    LTSResultManager.setControllableActions(cs.goal.getControllableActions());

                    // environments
                    for (LTSResultInitialModelsEnvironment environment : LTSResultManager.data.getInitialModels().environments) {
                        environment.initialize(cs.goal, ltsOutput);
                    }

                    // requirements
                    for (CompactState machine : cs.machines) {
                        if (machine.name.equals("Environment")) {
                            continue;
                        }
                        MTS<Long, String> mts = AutomataToMTSConverter.getInstance().convert(machine);
                        LTSResultInitialModelsRequirement requirement = new LTSResultInitialModelsRequirement(machine.name);
                        requirement.setNumberOfStates(mts.getStates().size());
                        int numberOfTransitions = Math.toIntExact(ControllerUtils.getNumberOfTransitions(mts));
                        requirement.setNumberOfTransitions(numberOfTransitions);
                        Long numberOfControllableActions = ControllerUtils.getNumberOfControllableActions(mts, cs.goal, ltsOutput);
                        requirement.setNumberOfControllableActions(Math.toIntExact(numberOfControllableActions));
                        requirement.setNumberOfUncontrollableActions(
                                numberOfTransitions - Math.toIntExact(numberOfControllableActions)
                        );
                        LTSResultManager.data.getInitialModels().requirements.add(requirement);
                    }
                case ONLY_PERFORMANCE:
                case DISABLED:
                    break;
            }
        } catch (LTSCompositionException x) {
            ltsOutput.outln("Construction of " + targetControllerName + " aborted.");
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
        HPWindow.maxMemoryUsage = 0;
        HPWindow.maxStates = 0;
        HPWindow.maxTransitions = 0;

        long startTime = System.currentTimeMillis();

        switch (LTSResultManager.mode) {
            case ENABLED:
                LTSResultManager.currentStep = LTSResultStep.COMPILE;
                break;
            case ONLY_PERFORMANCE:
            case FOR_MACHINE_LEARNING:
            case DISABLED:
                break;
        }

        CompositeState cs = this.doCompile(ltsInputString, ltsOutput, currentDirectory, targetName);
        ltsOutput.outln("Compile is Complete!");
        ltsOutput.outln("");
        ltsOutput.outln("");
        ltsOutput.outln("");
        ltsOutput.outln("===================================================");
        ltsOutput.outln("                    Composition                    ");
        ltsOutput.outln("===================================================");

        switch (LTSResultManager.mode) {
            case ENABLED:
                LTSResultManager.currentStep = LTSResultStep.COMPOSE;
                break;
            case ONLY_PERFORMANCE:
            case FOR_MACHINE_LEARNING:
            case DISABLED:
                break;
        }

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
        ltsOutput.outln("[info] Maximum State       : " + HPWindow.maxStates);
        ltsOutput.outln("[info] Maximum Transition  : " + HPWindow.maxTransitions);
        ltsOutput.outln("[info] Maximum Memory (KB) : " + HPWindow.maxMemoryUsage);
        ltsOutput.outln("[info] Execution Time (ms) : " + executionTime);
        ltsOutput.outln("");

        switch (LTSResultManager.mode) {
            case ENABLED:
            case ONLY_PERFORMANCE:
            case FOR_MACHINE_LEARNING:
                LTSResultManager.data.setMaxMemoryUsage(HPWindow.maxMemoryUsage);
                break;
            case DISABLED:
                break;
        }
    }

    // === [END] PARTIAL COPY FROM HPWindow ===

    private void runCompile(String input, String targetName) {
        LTSInputString ltsInput = new LTSInputString(input);
        CUIOutput ltsOutput = new CUIOutput();
        String currentDirectory = ".";

        switch (LTSResultManager.mode) {
            case ENABLED:
            case ONLY_PERFORMANCE:
            case FOR_MACHINE_LEARNING:
                LTSResultManager.start();
                doCompile(ltsInput, ltsOutput, currentDirectory, targetName);
                LTSResultManager.finish();
                LTSResultManager.dump();
                break;
            case DISABLED:
                doCompile(ltsInput, ltsOutput, currentDirectory, targetName);
                break;
        }
    }

    private void runCompose(String input, String targetName) {
        LTSInputString ltsInput = new LTSInputString(input);
        CUIOutput ltsOutput = new CUIOutput();
        String currentDirectory = ".";

        switch (LTSResultManager.mode) {
            case ENABLED:
            case ONLY_PERFORMANCE:
            case FOR_MACHINE_LEARNING:
                LTSResultManager.start();
                doComposition(ltsInput, ltsOutput, currentDirectory, targetName);
                LTSResultManager.finish();
                LTSResultManager.dump();
                break;
            case DISABLED:
                doComposition(ltsInput, ltsOutput, currentDirectory, targetName);
                break;
        }
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

        switch (LTSResultManager.mode) {
            case ENABLED:
            case ONLY_PERFORMANCE:
            case FOR_MACHINE_LEARNING:
                LTSResultManager.init("CUI", this.command, this.inputFilePath, this.targetName);
                break;
            case DISABLED:
                break;
        }

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
