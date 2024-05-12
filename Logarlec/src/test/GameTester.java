package test;

import controller.Game;
import controller.InputHandler;
import util.Logger;
import util.Reader;

public class GameTester {
    public static void main(String[] args) {
        Game game = new Game(false, Logger.LogLevel.INPUT_HANDLER);
        MockICInit icInit = new MockICInit();
        MockIControl iControl = new MockIControl();
        MockICRoom icRoom = new MockICRoom();
        game.SetICInit(icInit);
        game.SetIControl(iControl);
        game.SetICRoom(icRoom);

        InputHandler inputHandler = game.GetInputHandler();
        game.CreateGame("Tests/Test17/Map.json");

        while (true) {
            System.out.print("\n> ");
            String command = Reader.GetStringInput();

            switch (command) {
                case "exit":
                    System.exit(0);
                    break;
                default:
                    inputHandler.handleCommand(command);
            }
        }
    }
}
