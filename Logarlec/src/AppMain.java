import controller.Tester;
import view.Window;

public class AppMain {
    public static void main(String[] args) {
        Tester test = new Tester();
        test.TestRunAll();
        Window appWindow = new Window();
    }

}
