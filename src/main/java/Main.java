import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("$ ");
            String command = sc.nextLine();
            String [] commandsParts = command.split(" ",2);

            if (command.equalsIgnoreCase("exit"))
                break;

            switch(commandsParts[0]) {
                case "echo":
                    if (commandsParts.length > 1) {
                        System.out.println(commandsParts[1]);
                    }
                    break;
                case "type":
                    if (commandsParts.length > 1) {
                        handleType(commandsParts[1]);
                    }
                    break;
                default:
                    System.out.printf("%s: command not found\n", command);
            }

        }
    }

    public static void handleType(String command) {
        List<String> allowedCommands = Arrays.asList("echo", "type", "exit");
        if (allowedCommands.contains(command)) {
            System.out.printf("%s is a shell builtin\n", command);
        } else {
            System.out.printf("%s: not found\n", command);
        }
    }

}
