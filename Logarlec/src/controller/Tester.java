package controller;


/**
 * Ez lehet elvégezni a játékban teszteket. Képest fájlból, vagy általános bementről beolvasni teszt inputot és
 * ellenörizni, hogy a megadott kimenettel megegyezik a teszt kimenete.
 */
public class Tester {
    /**
     * Egy inputHandler ami vegzi a parancsok beolvasasat, illetve a parancsok vegrehajtasat a megadott jatekon
     */
    InputHandler inputHandler;
    /**
     * Egy jatek peldany, melyet minden teszt futasanal ujbol inicializal, es ezen vegzi a teszt futasat
     */
    Game game;

    public Tester(){
        inputHandler = new InputHandler();
    }

    /**
     * Ezzel a metodussal futtathato egy teszt. Az inputFilePathban megadott utvonalban talalhato fajlt olvassa be es
     * vegzi el az ebben megadott parancsokat. Majd a kimenetet osszehasonlitja a parameterkent megadott outputFilePath
     * utvonalon talalhato fajl tartalmaval.
     * @param inputFilePath Az bemeneti parancsokat tartalmazo fajl utvonala
     * @param expectedOutputFilePath Az elvart kimenetet tartalmazo fajlt utvonala
     */
    public void runTestWithInputFile(String inputFilePath, String expectedOutputFilePath){
    }

    /**
     * Ezzel a metodussal futtathatok a tesztek. A fuggveny meghivasa utana az altalanos bemenetre adhatok meg a parancsok.
     * Miutan minden parancsot megadott a felhasznalo akkor az End paranccsal allithato le a teszt. Majd az End parancs elott
     * generalodott kimenetet osszehasonlitja a parameterkent megadott outputFilePath utvonalon talalhato fajl tartalmaval.
     * @param expectedOutputFilePath Az elvart kimenetet tartalmazo fajlt utvonala
     */
    public void runTest(String expectedOutputFilePath){
    }

}
