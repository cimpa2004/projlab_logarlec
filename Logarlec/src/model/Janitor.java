package model;

import controller.Game;
import util.Logger;
import viewmodel.IVJanitor;
import viewmodel.IVJanitorUpdate;
import viewmodel.IVRoom;

import java.util.*;



public class Janitor extends Person implements IVJanitor {
    /**
     * Konstruktor
     * @param id - azonosíto teszteléshez
     */
    public Janitor(String id) {

        super(id);
    }
    public Janitor(String id, Game game) {
        super(id);
        this.game = game;
    }

    /**
     * Nem teszteléshez használt konstruktor
     */
    public Janitor(Game game) {
        super(UUID.randomUUID().toString());
        this.game = game;
    }

    private IVJanitorUpdate ivJanitorUpdate;

    /**
     * Megjelenik a takarító a megadott szobában
     * @param r  Az a szoba, amelyben a Person meg szeretne jelenni
     */
    @Override
    public boolean AppearInRoom(Room r) {
        Logger.startedModel(this, "AppearInRoom", r);
        int currentC = r.GetCurrentCapacity();
        int maxC = r.GetMaxCapacity();
        if(currentC < maxC) {
            Room oldRoom = room;
            oldRoom.RemoveJanitor(this);
            r.AddJanitor(this);

            //kitessékelés
            MakeThemLeave();

            //takarítás szellőztetés
            this.GetRoom().SetPoisonDuration(0);
            this.GetRoom().SetIsSticky(false);
        } else{
            Logger.finishedModel(this, "AppearInRoom", r);
            return false;
        }
        Logger.finishedModel(this, "AppearInRoom", r);
        return true;
    }

    /**
     * Oktatók és hallgatók kitesékelése amenyiben ez lehetséges
     */
    private void MakeThemLeave(){
        Logger.startedModel(this, "MakeThemLeave");
        if(game!=null) {
            if (!game.GetIsDeterministic()){
                final List<DoorSide> doorsCopy = new ArrayList<>(this.GetRoom().GetDoors());
                Collections.shuffle(doorsCopy);
                    ArrayList<Student> studentsCopy = new ArrayList<>(this.GetRoom().GetStudents());
                    for(Student st : studentsCopy){
                        for (DoorSide dr : doorsCopy){
                                if (dr.IsDoorUseable()){
                                    st.Move(dr);//a keresett ajtón átmegy
                                break;
                                }
                        }
                    }
                    ArrayList<Instructor> instructorCopy = new ArrayList<>(this.GetRoom().GetInstructors());
                    for(Instructor ins : instructorCopy){
                        for (DoorSide dr : doorsCopy){
                                if (dr.IsDoorUseable()){
                                    ins.Move(dr);//a keresett ajtón átmegy
                                    break;
                                }
                        }
                    }
            }else{ //determinisztikus mozgás az első lehetséges szomszéd
                ArrayList<Student> studentsCopy = new ArrayList<>(this.GetRoom().GetStudents());
                for(Student st : studentsCopy){
                    for (DoorSide dr : this.GetRoom().GetDoors()){
                        if (dr.IsDoorUseable()){
                            st.Move(dr);//a keresett ajtón átmegy
                            break;
                        }
                    }
                }
                ArrayList<Instructor> instructorCopy = new ArrayList<>(this.GetRoom().GetInstructors());
                for(Instructor ins : instructorCopy){
                    for (DoorSide dr : this.GetRoom().GetDoors()){
                        if (dr.IsDoorUseable()){
                            ins.Move(dr);//a keresett ajtón átmegy
                            break;
                        }
                    }
                }
            }
        }
        Logger.finishedModel(this, "MakeThemLeave");
    }

    /**
     * Mozgatási függvény
     * @param d  Egy ajtó, amelyen a Person megpróbál átlépni egy másik szobába
     */
    @Override
    public boolean Move(DoorSide d) {
        Logger.startedModel(this, "Move", d);
        if (!room.GetDoors().contains(d)) return false;
        DoorSide d2 = d.GetPair();
        Room r2 = d2.GetRoom();
        boolean isAppeared = AppearInRoom(r2);
        Logger.finishedModel(this, "Move", d);
        return isAppeared;
    }


    /**
     * Tárgyfelvétel, Tilos!!!
     * @param i  Az az Item, amelyet a Person fel szeretne venni.
     * @return excepctiont dob mert tilos
     */
    @Override
    public boolean Pickup(Item i) {
        throw new RuntimeException("A takarito nem vehet fel targyakat");
    }

    /**
     * Tárgyhasználat, Tilos!!!
     * @param i
     *Kivételt dob
     */
    @Override
    public void UseItem(Item i) {
        throw new RuntimeException("A takarito nem hasznalhat targyakat");
    }

    /**
     *Takarító körét kezelő függvény
     */
    @Override
    public void StartTurn() {
        Logger.startedModel(this, "StartTurn");
        activeTurn = true;

        //Mozgató logika
        Random random = new Random();
        if(game != null)
        if (!game.GetIsDeterministic()){
            List<DoorSide> doorsCopy = new ArrayList<>(this.GetRoom().GetDoors());
            Collections.shuffle(doorsCopy);

            for (DoorSide dr : doorsCopy){
                if (random.nextBoolean())
                    if (dr.IsDoorUseable()){
                        this.Move(dr);//a keresett ajtón átmegy
                        break;
                    }
            }
        }else{ //determinisztikus mozgás az első lehetséges szomszéd
            for (DoorSide dr : this.GetRoom().GetDoors()){
                if (dr.IsDoorUseable()){
                    this.Move(dr);//a keresett ajtón átmegy
                    break;
                }
            }
        }

        EndTurn();
        Logger.finishedModel(this, "StartTurn");
    }

    /**
     * Kör vége függvény
     */
    @Override
    public void EndTurn() {
        Logger.startedModel(this,"EndTurn");
        this.activeTurn = false;
        game.NextTurn();
        Logger.finishedModel(this, "EndTurn");
    }

    /**
     *  Elájult?
     * @return false
     */
    @Override
    public boolean GetIsFainted() {
        Logger.startedModel(this, "GetIsFainted");
        Logger.finishedModel(this, "GetIsFainted");
        return false;
    }

    /**
     * Megbénult?
     * @return false
     */
    @Override
    public boolean GetIsStunned() {
        Logger.startedModel(this, "GetIsStunned");
        Logger.finishedModel(this, "GetIsStunned");
        return false;
    }

    /**
     * Active e a köre?
     * @return ha aktiv true egyebkent false
     */
    @Override
    public boolean GetIsActiveTurn() {
        Logger.startedModel(this, "GetIsActiveTurn");
        Logger.finishedModel(this, "GetIsActiveTurn");
        return this.activeTurn;
    }

    @Override
    public boolean GetIsAlive() {
        Logger.startedModel(this, "GetIsAlive");
        Logger.finishedModel(this, "GetIsAlive");
        return true;
    }

    @Override
    public void ConnectTransistors(Transistor t1, Transistor t2) {
        throw new RuntimeException("A janitor nem csatlakoztathat ossze tranzisztorokat.");
    }

    @Override
	public IVRoom GetIVRoom() {
        Logger.startedModel(this, "GetIVRoom");
        Logger.finishedModel(this, "GetIVRoom");
        return this.room;
    }

    @Override
    public void SetIVJanitorUpdate(IVJanitorUpdate ivJanitorUpdate) {
        Logger.startedModel(this, "SetIVJanitorUpdate", ivJanitorUpdate);
        this.ivJanitorUpdate = ivJanitorUpdate;
        Logger.finishedModel(this, "SetIVJanitorUpdate", ivJanitorUpdate);
    }
}
