package hexlet.code;

import picocli.CommandLine;

import java.util.concurrent.Callable;

import static hexlet.code.Utils.checkFilesFormat;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @CommandLine.Option(names = {"-f", "--format"}, defaultValue = "stylish",
            description = "output format [default: stylish]")
    private static String format;

    @CommandLine.Parameters(description = "path to first file")
    private static String filepath1;
    @CommandLine.Parameters(description = "path to second file")
    private static String filepath2;

    @Override
    public Integer call() throws Exception {
        if (!checkFilesFormat(filepath1, filepath2)) {
            throw new UnsupportedOperationException("Comparison of not JSON or YAML file formats isn't supported");
        }
        System.out.println(Differ.generate(filepath1, filepath2, format));
        return 0;
    }
}
