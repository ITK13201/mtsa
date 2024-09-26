package ltsa.lts.result;

public enum LTSResultMode {
    ENABLED("enabled"),
    ONLY_PERFORMANCE("only-performance"),
    DISABLED("disabled");

    private final String text;

    LTSResultMode(final String text) {
        this.text = text;
    }

    public String getString() {
        return this.text;
    }
}