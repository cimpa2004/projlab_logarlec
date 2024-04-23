import controller.Tester;

import java.util.ArrayList;

public class PrototypeMain {


    public static void main(String[] args){
        Tester tester = new Tester();

        // 26os teszt lefuttatasa automata modon (input fajlbol olvas parancsokat)
        tester.TestAuto(1);

        // 26os teszt lefuttatasa automata modon (input fajlbol olvas parancsokat)
        tester.TestManual(26);

        // osszes tesz automata lefuttatasa
        //tester.TestRunAll();
    }
}
