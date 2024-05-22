package ltsa.lts;


public class ConsoleOutput implements LTSOutput {
    private StringBuffer output = new StringBuffer();

    public void clearOutput() {}

    public void out(String str) {
        output.append(str);
        System.out.print(str);
    }

    public void outln(String str) {
        output.append(str).append("\r");
        System.out.println(str);
    }

    @Override
    public String toString() {
        return output.toString();
    }

    public boolean empty() {
        return (output.length() == 0);
    }
}
