package ltsa.lts.result;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LTSResultInitialModelsRequirement {
    private String name;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    private Integer numberOfControllableActions;
    private Integer numberOfUncontrollableActions;
    public ArrayList<ArrayList<Integer>> structure;

    public LTSResultInitialModelsRequirement(String name) {
        this.name = name;
        this.structure = new ArrayList<>();
    }

    public LTSResultInitialModelsRequirement(String name, Integer numberOfStates, Integer numberOfTransitions, Integer numberOfControllableActions, Integer numberOfUncontrollableActions) {
        this.name = name;
        this.numberOfStates = numberOfStates;
        this.numberOfTransitions = numberOfTransitions;
        this.numberOfControllableActions = numberOfControllableActions;
        this.numberOfUncontrollableActions = numberOfUncontrollableActions;
        this.structure = new ArrayList<>();
    }
}
