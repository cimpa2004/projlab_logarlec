package Skeleton;

import modul.*;

import java.util.Scanner;

public class SkeletonMain {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Tesztek listazasa
        System.out.println("1. Oktatót megvédi FFP2 maszk");
        System.out.println("5. Hallgatót megvédi a TVSZ");
        System.out.println("7. Kiürül a gáz egy szobából");
        System.out.println("8. Tranzisztor használat");
        System.out.println("14. Hallgató használt tárgyakat használna");
        System.out.println("15. Oktató használt, vagy nem használható tárgyat használna");
        // ..


        Scanner scanner = new Scanner(System.in);
        boolean validInput = false;

        while (!validInput) {
            System.out.println("Adj meg egy sorszámot a felsoroltak közül:");
            int input = scanner.nextInt();

            switch (input){
                case 1:
                    InstructorSavedByFFP2Mask.Run();
                    validInput = true;
                    break;
                case 5:
                    StudentSavedByTVSZ.Run();
                    validInput = true;
                    break;
                case 7:
                    GasClearsFromRoom.Run();
                    validInput = true;
                    break;
                case 8:
                    UseTransistor.Run();
                    validInput = true;
                    break;
                case 14:
                    StudentTriesUsingUsedItems.Run();
                    validInput = true;
                    break;
                case 15:
                    InstructorTriesUsingUsedOrNotAllowedItems.Run();
                    validInput = true;
                    break;
                default:
                    System.out.println("Kérlek adj meg egy olyan sorszámot ami létezik a listában!");
            }
        }
    }
}
