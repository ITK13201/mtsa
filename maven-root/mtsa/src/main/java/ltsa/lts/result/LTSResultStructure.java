package ltsa.lts.result;

import lombok.Data;

import java.util.ArrayList;

@Data
public class LTSResultStructure {
    private ArrayList<ArrayList<String>> transitions;

    public LTSResultStructure() {
        this.transitions = new ArrayList<>();
    }

    public void addTransition(LTSResultStructureTransition transition) {
        transitions.add(transition.getArrayList());
    }
}
