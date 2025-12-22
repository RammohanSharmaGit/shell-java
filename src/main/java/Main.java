import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("$ ");
            String command = sc.next();
            if (command.equalsIgnoreCase("exit"))
                break;
            System.out.printf("%s: command not found\n", command);
        }
    }
}
