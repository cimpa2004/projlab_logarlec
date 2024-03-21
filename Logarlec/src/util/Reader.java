package util;

import java.util.Scanner;

public class Reader {
    static public boolean GetBooleanInput(String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message + ": 'true' / 'false'");
        boolean canAdd = scanner.nextBoolean();
        scanner.close();
        return canAdd;
    }
    static public int GetIntInput(String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message + ": Integer: ");
        int gotInt = scanner.nextInt();
        scanner.close();
        return gotInt;
    }
}
