package Skeleton;

import modul.*;

import java.util.Scanner;

public class SkeletonMain {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Tesztek listazasa
        System.out.println("1. Oktatót megvédi FFP2 maszk");
        System.out.println("14. Hallgató használt tárgyakat használna");
        System.out.println("15. Oktató használt, vagy nem használható tárgyat használna");
        // ..


        int input = scanner.nextInt();
        switch (input){
            case 1:
                InstructorSavedByFFP2Mask.Run();
                break;
            case 14:
                StudentTriesUsingUsedItems.Run();
                break;
            case 15:
                InstructorTriesUsingUsedOrNotAllowedItems.Run();
                break;
            default:
                System.out.println("Kérlek adj meg egy olyan sorszámot ami létezik a listában!");
        }
    }
}
