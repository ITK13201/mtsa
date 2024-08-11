package ltsa.lts.result;

import MTSSynthesis.controller.model.ControllerGoal;
import MTSTools.ac.ic.doc.mtstools.model.MTS;
import lombok.Data;
import ltsa.ac.ic.doc.mtstools.util.fsp.AutomataToMTSConverter;
import ltsa.control.util.ControllerUtils;
import ltsa.lts.CompactState;
import ltsa.lts.LTSOutput;

@Data
public class LTSResultInitialModelsEnvironment {
    private transient CompactState compactState;

    private String name;
    private Integer numberOfStates;
    private Long numberOfTransitions;
    private Long numberOfControllableActions;
    private Long numberOfUncontrollableActions;

    public LTSResultInitialModelsEnvironment(CompactState state) {
        this.compactState = state;
        this.name = state.name;
    }

    public void initialize(ControllerGoal<String> goal, LTSOutput output) {
        MTS<Long, String> mts = AutomataToMTSConverter.getInstance().convert(this.compactState);
        this.numberOfStates = mts.getStates().size();
        this.numberOfTransitions = ControllerUtils.getNumberOfTransitions(mts);
        this.numberOfControllableActions = ControllerUtils.getNumberOfControllableActions(mts, goal, output);
        this.numberOfUncontrollableActions = numberOfTransitions - numberOfControllableActions;
        // reset memory
        this.compactState = null;
    }
}
