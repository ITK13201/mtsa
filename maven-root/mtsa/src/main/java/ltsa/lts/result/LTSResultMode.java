package ltsa.lts.result;

public enum LTSResultMode {
    ENABLED("enabled"),
    ONLY_PERFORMANCE("only-performance"),
    FOR_MACHINE_LEARNING("for-machine-learning"),
    FOR_MACHINE_LEARNING_EXTRA("for-machine-learning-extra"),
    DISABLED("disabled");

    private final String text;

    LTSResultMode(final String text) {
        this.text = text;
    }

    public String getString() {
        return this.text;
    }
}
