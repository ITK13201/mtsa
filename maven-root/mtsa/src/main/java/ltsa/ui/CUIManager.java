package ltsa.ui;

import ltsa.lts.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;


class CUIManager {
    String inputFilePath;

    public CUIManager(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    private String readFile(String path) throws IOException {
        return Files.lines(Paths.get(path), Charset.forName("UTF-8"))
                .collect(Collectors.joining(System.getProperty("line.separator")));
    }

    private void parse(String input) {
        LTSInput ltsInput = new LTSInputString(input);
        ConsoleOutput output = new ConsoleOutput();

        LTSCompiler compiled = new LTSCompiler(ltsInput, output, ".");
        try {
            compiled.compile();
        } catch (LTSException e) {
            System.out.println("Syntax error in " + input + ": " + output.toString() + e.toString());
        }
        System.out.println("input: " + input);
        System.out.println("output: " + output.toString());
    }

    public void run(){
        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            String input = readFile(this.inputFilePath);
            parse(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}