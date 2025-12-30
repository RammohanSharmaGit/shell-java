import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Evaluator e = new Evaluator();
        while(true) {
            System.out.print("$ ");
            String command = sc.nextLine();
            if (command.equalsIgnoreCase("exit"))
                break;

            String evaluated = e.evaluate(command);
            if (evaluated!=null)
                System.out.print(e.evaluate(command));

        }
    }

}
