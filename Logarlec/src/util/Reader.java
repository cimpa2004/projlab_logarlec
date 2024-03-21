package util;

import java.util.Scanner;

/**
 * Beolvasást végző osztály
 */
public class Reader {
    /**
     * Ez a object végzi késöbb a felhasználótól kért inputok beolvasását
     */
    static Scanner scanner = new Scanner(System.in);
    /**
     * Függvény a boolean beolvasásokhoz
     * @param message a kiirandó kérdés
     * @return válasz a felhasználtótól
     */
    static public boolean GetBooleanInput(String message){
        System.out.println(message + ": 'true' / 'false'");
        return scanner.nextBoolean();
    }

    /**
     *Függvény az int beolvasásokhoz
     * @param message a kiirandó kérdés
     * @return válasz a felhasználtótól
     */
    static public int GetIntInput(String message){
        System.out.println(message + ": Integer: ");
        return scanner.nextInt();
    }
}