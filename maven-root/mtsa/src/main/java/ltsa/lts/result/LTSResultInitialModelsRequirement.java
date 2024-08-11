package ltsa.lts.result;

import lombok.Data;

@Data
public class LTSResultInitialModelsRequirement {
    private String name;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    private Integer numberOfControllableActions;
    private Integer numberOfUncontrollableActions;

    public LTSResultInitialModelsRequirement(String name) {
        this.name = name;
    }

    public LTSResultInitialModelsRequirement(String name, Integer numberOfStates, Integer numberOfTransitions, Integer numberOfControllableActions, Integer numberOfUncontrollableActions) {
        this.name = name;
        this.numberOfStates = numberOfStates;
        this.numberOfTransitions = numberOfTransitions;
        this.numberOfControllableActions = numberOfControllableActions;
        this.numberOfUncontrollableActions = numberOfUncontrollableActions;
    }
}
