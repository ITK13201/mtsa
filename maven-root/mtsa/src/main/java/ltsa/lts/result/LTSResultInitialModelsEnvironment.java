package ltsa.lts.result;

import MTSSynthesis.controller.model.ControllerGoal;
import MTSTools.ac.ic.doc.commons.relations.BinaryRelation;
import MTSTools.ac.ic.doc.commons.relations.Pair;
import MTSTools.ac.ic.doc.mtstools.model.MTS;
import lombok.Data;
import ltsa.ac.ic.doc.mtstools.util.fsp.AutomataToMTSConverter;
import ltsa.control.util.ControllerUtils;
import ltsa.lts.CompactState;
import ltsa.lts.LTSOutput;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static MTSTools.ac.ic.doc.mtstools.model.MTS.TransitionType.REQUIRED;

@Data
public class LTSResultInitialModelsEnvironment {
    private transient CompactState compactState;

    private String name;
    private Integer numberOfStates;
    private Long numberOfTransitions;
    private Long numberOfControllableActions;
    private Long numberOfUncontrollableActions;
    public ArrayList<ArrayList<String>> structure;

    public LTSResultInitialModelsEnvironment(CompactState state) {
        this.compactState = state;
        this.name = state.name;
    }

    private ArrayList<ArrayList<String>> getStructure(MTS<Long, String> mts, ControllerGoal<String> goal) {
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
        return structure.getTransitions();
    }

    public void initialize(ControllerGoal<String> goal, LTSOutput output, Boolean isEnabledStructure) {
        MTS<Long, String> mts = AutomataToMTSConverter.getInstance().convert(this.compactState);
        this.numberOfStates = mts.getStates().size();
        this.numberOfTransitions = ControllerUtils.getNumberOfTransitions(mts);
        this.numberOfControllableActions = ControllerUtils.getNumberOfControllableActions(mts, goal, output);
        this.numberOfUncontrollableActions = numberOfTransitions - numberOfControllableActions;

        if (isEnabledStructure) {
            // get structure
            this.structure = this.getStructure(mts, goal);
        }
        // reset memory
        this.compactState = null;
    }
}
