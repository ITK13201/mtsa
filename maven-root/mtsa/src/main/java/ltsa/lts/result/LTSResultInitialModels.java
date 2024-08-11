package ltsa.lts.result;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LTSResultInitialModels {
    public ArrayList<LTSResultInitialModelsEnvironment> environments;
    public ArrayList<LTSResultInitialModelsRequirement> requirements;

    LTSResultInitialModels() {
        this.environments = new ArrayList<>();
        this.requirements = new ArrayList<>();
    }
}
