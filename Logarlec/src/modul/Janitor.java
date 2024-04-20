package modul;

import controller.Game;

import java.util.UUID;


//TODO: The whole class
public class Janitor extends Person{
    private Game game;
    public Janitor(String id) {
        super(id);
    }

    public Janitor() {
        super(UUID.randomUUID().toString());
    }


    @Override
    public void AppearInRoom(Room r) {

    }

    @Override
    public void Move(DoorSide d) {

    }

    @Override
    public boolean Pickup(Item i) {
        return false;
    }

    @Override
    public void UseItem(Usable u) {

    }

    @Override
    public void StartTurn() {

    }

    @Override
    public void EndTurn() {

    }

    @Override
    public boolean GetIsFainted() {
        return false;
    }

    @Override
    public boolean GetIsStunned() {
        return false;
    }

    @Override
    public boolean GetIsActiveTurn() {
        return false;
    }
}
