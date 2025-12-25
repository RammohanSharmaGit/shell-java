import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Evaluator {

    List<String> builtInCommands = Arrays.asList("echo", "type", "exit");

    public String evaluate(String command) {
        String [] commandsParts = command.split(" ",2);
        String evaluated = null;
        switch(commandsParts[0]) {
            case "echo":
                if (commandsParts.length > 1) {
                    evaluated = String.format("%s\n", commandsParts[1]);
                }
                break;
            case "type":
                if (commandsParts.length > 1) {
                    evaluated = handleType(commandsParts[1]);
                }
                break;
            default:
                evaluated = String.format("%s: command not found\n", command);
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
