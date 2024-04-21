import controller.Tester;

import java.util.ArrayList;

public class PrototypeMain {
    public static void main(String[] args){
        Tester tester = new Tester();
        ArrayList<String> expectedCommandOutputs = tester.readExpectedOutputFile("C:\\Git\\projlab_logarlec\\Logarlec\\Tests\\Test16\\Expected.txt");

        for(String str : expectedCommandOutputs){
            System.out.println(str);
            System.out.println("--------------");
        }
    }
}
