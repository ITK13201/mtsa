package ltsa.lts.result;

import lombok.Data;

@Data
public class LTSResultCompileStepFinalModel {
    private String name;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    private Integer numberOfControllableActions;
    private Integer numberOfUncontrollableActions;

    public LTSResultCompileStepFinalModel(String name) {
        this.name = name;
    }
}
