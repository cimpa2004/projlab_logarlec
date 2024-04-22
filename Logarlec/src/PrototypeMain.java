import controller.Tester;

import java.util.ArrayList;

public class PrototypeMain {

    public static void Test26_Manual(Tester tester){
        tester.runTest("C:\\Git\\projlab_logarlec\\Logarlec\\Tests\\Test26\\Expected.txt");
        //
    }


    public static void main(String[] args){
        Tester tester = new Tester();

        tester.runTest("C:\\Git\\projlab_logarlec\\Logarlec\\Tests\\Test26\\Expected.txt");
    }
}
