package util;

import java.util.Scanner;

/**
 * Beolvasást végző osztály
 */
public class Reader {
    /**
     * Függvény a boolean beolvasásokhoz
     * @param message a kiirandó kérdés
     * @return válasz a felhasználtótól
     */
    static public boolean GetBooleanInput(String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message + ": 'true' / 'false'");
        boolean canAdd = scanner.nextBoolean();
        scanner.close();
        return canAdd;
    }

    /**
     *Függvény az int beolvasásokhoz
     * @param message a kiirandó kérdés
     * @return válasz a felhasználtótól
     */
    static public int GetIntInput(String message){
        Scanner scanner = new Scanner(System.in);
        System.out.println(message + ": Integer: ");
        int gotInt = scanner.nextInt();
        scanner.close();
        return gotInt;
    }
}
