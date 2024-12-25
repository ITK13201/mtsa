package ltsa.lts.result;

public class LTSResultComposeStep {
    // [Modified Parallel Composition, Error State Abstraction]
    public LTSResultComposeStepCreatingGameSpace creatingGameSpace;
    // [Safety Game Solving]
    public LTSResultComposeStepSolvingProblem solvingProblem;

    LTSResultComposeStep() {
        this.creatingGameSpace = new LTSResultComposeStepCreatingGameSpace();
        this.solvingProblem = new LTSResultComposeStepSolvingProblem();
    }
}