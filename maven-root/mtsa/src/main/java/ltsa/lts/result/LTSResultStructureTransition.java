package ltsa.lts.result;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;

@Data
@AllArgsConstructor
public class LTSResultStructureTransition {
    private Long srcStateNumber;
    private Long destStateNumber;
    private String actionName;
    private Boolean IsControllable;

    public ArrayList<String> getArrayList() {
        String srcStateNumberStr = String.valueOf(this.srcStateNumber);
        String destStateNumberStr = String.valueOf(this.destStateNumber);
        String convertedIsControllable = this.IsControllable ? "1" : "0";
        return new ArrayList<>(Arrays.asList(srcStateNumberStr, destStateNumberStr, this.actionName, convertedIsControllable));
    }
}
