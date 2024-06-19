package ltsa.lts.result;

public class LTSResultComposeStep {
    public LTSResultComposeStepCreatingGameSpace creatingGameSpace;
    public LTSResultComposeStepSolvingProblem solvingProblem;

    LTSResultComposeStep() {
        this.creatingGameSpace = new LTSResultComposeStepCreatingGameSpace();
        this.solvingProblem = new LTSResultComposeStepSolvingProblem();
    }
}