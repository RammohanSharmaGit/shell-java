import java.util.Arrays;
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
                    System.out.println(commandsParts[1]);
                    break;
                default:
                    System.out.printf("%s: command not found\n", command);
            }


        }
    }
}
