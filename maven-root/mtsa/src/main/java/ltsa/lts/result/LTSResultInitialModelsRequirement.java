package ltsa.lts.result;

import MTSSynthesis.controller.model.ControllerGoal;
import MTSTools.ac.ic.doc.commons.relations.BinaryRelation;
import MTSTools.ac.ic.doc.commons.relations.Pair;
import MTSTools.ac.ic.doc.mtstools.model.MTS;
import lombok.Data;
import ltsa.control.util.ControllerUtils;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static MTSTools.ac.ic.doc.mtstools.model.MTS.TransitionType.REQUIRED;

@Data
public class LTSResultInitialModelsRequirement {
    private String name;
    private Integer numberOfStates;
    private Integer numberOfTransitions;
    private Integer numberOfControllableActions;
    private Integer numberOfUncontrollableActions;
    public ArrayList<ArrayList<String>> structure;

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

    public void setStructure(MTS<Long, String> mts, ControllerGoal<String> goal) {
        Set<String> allControllableActions = goal.getControllableActions();
        LTSResultStructure structure = new LTSResultStructure();

        MTS<Long, String> env = ControllerUtils.removeTopStates(mts, goal.getFluents());
        Map<Long, BinaryRelation<String, Long>> transitions = env.getTransitions(REQUIRED);
        for (Map.Entry<Long, BinaryRelation<String, Long>> entry : transitions.entrySet()) {
            Long srcStateNumber = entry.getKey();
            for (Pair<String, Long> actionPair : entry.getValue()) {
                String actionName = actionPair.getFirst();
                Long destStateNumber = actionPair.getSecond();
                Boolean isControllableAction = allControllableActions.contains(actionName);
                LTSResultStructureTransition transitionResult = new LTSResultStructureTransition(srcStateNumber, destStateNumber, actionName, isControllableAction);
                structure.addTransition(transitionResult);
            }
        }
        this.structure = structure.getTransitions();
    }
}
