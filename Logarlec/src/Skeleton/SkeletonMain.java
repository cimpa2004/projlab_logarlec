package Skeleton;

import modul.*;

import java.util.Scanner;

public class SkeletonMain {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Tesztek listazasa
        System.out.println("1. Oktatót megvédi FFP2 maszk");
        System.out.println("6. Hallgatót megvédi a Szent Sörös pohár");
        System.out.println("9. Az oktató inventorya megtelik a játék véget ér, mert nincs több hallgató");
        System.out.println("10. Hallgató tárgyfelvétel");
        System.out.println("11. Hallgató önelgázosítás");
        System.out.println("12. Gázos és Átkozott szoba egyesül");
        System.out.println("13. Egy hallgató beragad egy szobába, amit elátkozódik");
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
                case 6:
                    StudentSavedByHolyBeerCup.Run();
                    validInput = true;
                    break;
                case 9:
                    InstructosInventoryGetsFullTheGameEnds.Run();
                    validInput = true;
                    break;
                case 10:
                    StudentItemPickup.Run();
                    validInput = true;
                    break;
                case 11:
                    StudentSelfGas.Run();
                    validInput = true;
                    break;
                case 12:
                    GasAndCursedRoomMerge.Run();
                    validInput = true;
                    break;
                case 13:
                    StudentGetsStuckInARoom.Run();
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
