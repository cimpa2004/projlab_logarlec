import controller.Tester;
import util.Reader;

import java.util.ArrayList;
import java.util.Scanner;

public class PrototypeMain {


    public static void main(String[] args) {
        Tester tester = new Tester();

        while (true) {
            System.out.print("\n> ");
            String input = Reader.GetStringInput();

            String[] tokens = input.split(" ");
            if (tokens.length == 0) {
                continue;
            }

            String command = tokens[0];

            switch (command) {
                case "run":
                    if (tokens.length == 3 && tokens[1].equals("manual")) {
                        int testNumber = Integer.parseInt(tokens[2]);
                        tester.TestManual(testNumber);
                    } else if (tokens.length == 3 && tokens[1].equals("auto")) {
                        int testNumber = Integer.parseInt(tokens[2]);
                        tester.TestAuto(testNumber);
                    } else if (tokens.length == 2 && tokens[1].equals("all")) {
                        tester.TestRunAll();
                    } else {
                        System.out.println("Invalid command. Use 'run manual <test number>', 'run auto <test number>', or 'run all'.");
                    }
                    break;

                case "exit":
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid command. Use 'run manual <test number>', 'run auto <test number>', 'run all', or 'exit'.");
            }
        }
    }
}
