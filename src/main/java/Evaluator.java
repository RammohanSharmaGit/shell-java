import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Evaluator {

    List<String> builtInCommands = Arrays.asList("echo", "type", "exit", "pwd", "cd");
    String currPath = System.getProperty("user.dir");

    public String evaluate(String command) {
        String [] commandsParts = command.split(" ");
        String evaluated = null;
        switch(commandsParts[0]) {
            case "echo":
                if (commandsParts.length > 1) {
                    evaluated = String.format("%s\n", command.split(" ",2)[1]);
                }
                break;
            case "type":
                if (commandsParts.length > 1) {
                    evaluated = handleType(commandsParts[1]);
                }
                break;
            case "pwd":
                evaluated = String.format("%s\n", currPath);
                break;
            case "cd":
                evaluated = handleCd(commandsParts[1], evaluated);
                break;
            default:
                String path = null;
                if ((path = searchInPath(commandsParts[0])) != null) {
                List<String> arguments = new ArrayList<>(Arrays.asList(Arrays.copyOfRange(commandsParts,1,commandsParts.length)));
                arguments.add(0,commandsParts[0]);
                    try {
                        ProcessBuilder pb = new ProcessBuilder(arguments);
                        pb.inheritIO();
                        Process process = pb.start();
                        process.waitFor();
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                }
                evaluated = String.format("%s: command not found\n", command);
        }
        return evaluated;
    }

    private String handleCd(String path, String evaluated) {
        if (Files.isDirectory(Paths.get(path))) {
            currPath = path;
        } else {
            evaluated = String.format("cd: %s: No such file or directory\n", path);
        }
        return evaluated;
    }

    public String handleType(String command) {
        String path = null;
        if (builtInCommands.contains(command)) {
            return String.format("%s is a shell builtin\n", command);
        } else if ((path = searchInPath(command)) != null) {
            return String.format("%s is %s", command, path);
        } else {
            return String.format("%s: not found\n", command);
        }
    }

    public String searchInPath(String command) {
        String pathEnv = System.getenv("PATH");
        String [] pathsList = pathEnv.split(":");
        for (String path : pathsList) {
            Path pathToFile = Paths.get(path,command);
            if (Files.exists(pathToFile) && Files.isRegularFile(pathToFile) && Files.isExecutable(pathToFile)) {
                return String.format("%s\n",pathToFile);
            }
        }
        return null;
    }
}
