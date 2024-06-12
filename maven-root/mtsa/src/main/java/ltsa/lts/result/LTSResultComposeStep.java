package ltsa.lts.result;

public class LTSResultComposeStep {
    public LTSResultComposeStepComposition composition;
    public LTSResultComposeStepSolvingProblem solvingProblem;

    LTSResultComposeStep() {
        this.composition = new LTSResultComposeStepComposition();
        this.solvingProblem = new LTSResultComposeStepSolvingProblem();
    }
}