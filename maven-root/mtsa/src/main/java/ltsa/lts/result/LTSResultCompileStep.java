package ltsa.lts.result;

import java.util.ArrayList;

public class LTSResultCompileStep {
    public ArrayList<LTSResultCompileStepEnvironment> environments;
    public ArrayList<LTSResultCompileStepRequirement> requirements;
    public ArrayList<LTSResultCompileStepFinalModel> finalModels;

    LTSResultCompileStep() {
        this.environments = new ArrayList<>();
        this.requirements = new ArrayList<>();
        this.finalModels = new ArrayList<>();
    }
}