package ltsa.lts;


public class CUIOutput implements LTSOutput {
    public void clearOutput() {
    }

    public void out(String str) {
        System.out.print(str);
    }

    public void outln(String str) {
        System.out.println(str);
    }
}
