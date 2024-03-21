package util;

import java.util.Scanner;

public class Reader {
    static Scanner scanner = new Scanner(System.in);
    static public boolean GetBooleanInput(String message){
        System.out.println(message + ": 'true' / 'false'");
        return scanner.nextBoolean();
    }
    static public int GetIntInput(String message){
        System.out.println(message + ": Integer: ");
        return scanner.nextInt();
    }
}
