package controller;


import util.Reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Ez lehet elvégezni a játékban teszteket. Képest fájlból, vagy általános bementről beolvasni teszt inputot és
 * ellenörizni, hogy a megadott kimenettel megegyezik a teszt kimenete.
 */
public class Tester {
    /**
     * Egy inputHandler ami vegzi a parancsok beolvasasat, illetve a parancsok vegrehajtasat a megadott jatekon
     */
    InputHandler inputHandler;
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";

    /**
     * Egy jatek peldany, melyet minden teszt futasanal ujbol inicializal, es ezen vegzi a teszt futasat
     */
    Game game;

    /**
     * Ezzel a fuggvenynel elindithato egy teszt mely soran a bemenetrol olvasodnak be a parancsok. End parancs utan
     * leall a teszt. Futas vegen kiirja az eredmenyeket.
     * @param testNumber A teszt szama
     */
    public void TestManual(int testNumber){
        String path = "Tests/Test"+testNumber;
        System.out.println("--- RUN Test"+testNumber+ " ---");
        runTest(path + "/Expected.txt");
        System.out.println("--- END ---");
    }

    /**
     * Ezzel a fuggvenynel elindithato egy teszt mely soran az input.txt-bol olvasodnak be a parancsok. Futas vegen kiirja az eredmenyeket.
     * @param testNumber A teszt szama
     */
    public void TestAuto(int testNumber){
        String path = "Tests/Test"+ testNumber;
        System.out.println("--- RUN Test"+testNumber+ " ---");
        runTestWithInputFile(path+"/input.txt", path + "/Expected.txt");
        System.out.println("--- END ---");
    }

    /**
     * Ez a fuggveny lefuttatja az osszes tesztet oly modon, hogy beolvassa az inputokat a megfelelo teszt fajlbol.
     */
    public void TestRunAll(){
        for (int i = 1; i <= 33; i++) {
            String path = "Tests/Test"+ i;
            System.out.println("--- RUN Test"+i+ " ---");
            runTestWithInputFile(path+"/input.txt", path + "/Expected.txt");
            System.out.println("--- END ---");
        }
    }

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
        ArrayList<String> expectedCommandOutputs = readExpectedOutputFile(expectedOutputFilePath);
        ArrayList<String> actualCommandOutputs = new ArrayList<>();

        // Read input commands from inputFilePath
        ArrayList<String> inputCommands = new ArrayList<>();
        File file = new File(inputFilePath);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                inputCommands.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (String command : inputCommands) {
            String commandOutput = inputHandler.handleCommand(command);
            actualCommandOutputs.add(commandOutput);
        }

        logOutput(expectedCommandOutputs, actualCommandOutputs, inputCommands);
    }

    /**
     * Ezzel a metodussal futtathatok a tesztek. A fuggveny meghivasa utana az altalanos bemenetre adhatok meg a parancsok.
     * Miutan minden parancsot megadott a felhasznalo akkor az End paranccsal allithato le a teszt. Majd az End parancs elott
     * generalodott kimenetet osszehasonlitja a parameterkent megadott outputFilePath utvonalon talalhato fajl tartalmaval.
     * @param expectedOutputFilePath Az elvart kimenetet tartalmazo fajlt utvonala
     */
    public void runTest(String expectedOutputFilePath){
        ArrayList<String> expectedCommandOutputs = readExpectedOutputFile(expectedOutputFilePath);
        ArrayList<String> actualCommandOutputs = new ArrayList<>();
        String input;

        ArrayList<String> inputCommands = new ArrayList<>();
        System.out.println("Add meg a parancsokat: \n");
        while (true) {
            input = Reader.GetStringInput();
            inputCommands.add(input);
            if ("End".equalsIgnoreCase(input)) {
                break;
            }
            String commandOutput = inputHandler.handleCommand(input);
            actualCommandOutputs.add(commandOutput);
            System.out.println(commandOutput+"\n");
        }

        logOutput(expectedCommandOutputs, actualCommandOutputs, inputCommands);
    }

    private void logOutput(ArrayList<String> expectedCommandOutputs, ArrayList<String> actualCommandOutputs, ArrayList<String> inputCommands){
        StringBuilder str = new StringBuilder();
        if (actualCommandOutputs.size() != expectedCommandOutputs.size()){
            str.append("\n--- FAIL: Az elvart es aktualis parancsok szama elter.\n");
            str.append("EXPECTED:\n").append(expectedCommandOutputs.size()).append("\n");
            str.append("ACTUAL:\n").append(actualCommandOutputs.size());
            System.out.println(RED + str + RESET);
            str.setLength(0);
        }

        int n = Math.min(actualCommandOutputs.size(), expectedCommandOutputs.size());
        for (int i = 0; i < n; i++) {
            String result = compareOutputs(expectedCommandOutputs.get(i), actualCommandOutputs.get(i), inputCommands.get(i));
            if (result.contains("SUCCESS")) System.out.println(GREEN + result + RESET + "\n\n");
            else System.out.println(RED + result + RESET + "\n\n");
        }
    }

    private String compareOutputs(String expected, String actual, String command){
        if (!actual.equals(expected)){
            StringBuilder str = new StringBuilder();
            str.append("--- FAIL: ").append(command).append("\n");
            str.append("EXPECTED:\n");
            str.append(expected).append("\n\n");
            str.append("ACTUAL:\n");
            str.append(actual);
            return str.toString();
        }
        return "-- SUCCESS: " + command;
    }

    private ArrayList<String> readExpectedOutputFile(String expectedOutputFilePath){
        ArrayList<String> result = new ArrayList<>();
        File file = new File(expectedOutputFilePath);

        try (Scanner scanner = new Scanner(file)) {
            StringBuilder commandOutput = new StringBuilder();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.isEmpty() || !scanner.hasNextLine()){
                    commandOutput.deleteCharAt(commandOutput.length()-1);
                    result.add(commandOutput.toString());
                    commandOutput = new StringBuilder();
                } else{
                    commandOutput.append(line).append("\n");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }


}
