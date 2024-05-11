package model;

import controller.Game;
import util.Logger;

import java.util.*;



public class Janitor extends Person{
    /**
     * Game referencia
     */
    private Game game;

    /**
     * Konstruktor
     * @param id - azonosíto teszteléshez
     */
    public Janitor(String id) {
        super(id);
    }

    /**
     * Nem teszteléshez használt konstruktor
     */
    public Janitor() {
        super(UUID.randomUUID().toString());
    }

    /**
     * Megjelenik a takarító a megadott szobában
     * @param r  Az a szoba, amelyben a Person meg szeretne jelenni
     */
    @Override
    public boolean AppearInRoom(Room r) {
        Logger.started(this, "AppearInRoom", r);
        int currentC = r.GetCurrentCapacity();
        int maxC = r.GetMaxCapacity();
        if(currentC < maxC) {
            Room oldRoom = room;
            room = r; // atlepes a masik szobaba
            oldRoom.RemoveJanitor(this);
            room.AddJanitor(this);

            //kitessékelés
            MakeThemLeave();

            //takarítás szellőztetés
            this.GetRoom().SetPoisonDuration(0);
            this.GetRoom().SetIsSticky(false);
        } else{
            return false;
        }
        Logger.finished(this, "AppearInRoom", r);
        return true;
    }

    /**
     * Oktatók és hallgatók kitesékelése amenyiben ez lehetséges
     */
    private void MakeThemLeave(){
        Random random = new Random();
        if(game!=null) {
            if (!game.GetIsDeterministic()){
                List<DoorSide> doorsCopy = new ArrayList<>(this.GetRoom().GetDoors());
                Collections.shuffle(doorsCopy);
                    for(Student st : this.GetRoom().GetStudents()){
                        for (DoorSide dr : doorsCopy){
                            if (random.nextBoolean())
                                if (dr.IsDoorUseable()){
                                    st.Move(dr);//a keresett ajtón átmegy
                                break;
                                }
                        }
                    }
                    for(Instructor ins : this.GetRoom().GetInstructors()){
                        for (DoorSide dr : doorsCopy){
                            if (random.nextBoolean())
                                if (dr.IsDoorUseable()){
                                    ins.Move(dr);//a keresett ajtón átmegy
                                    break;
                                }
                        }
                    }
            }else{ //determinisztikus mozgás az első lehetséges szomszéd
                for(Student st : this.GetRoom().GetStudents()){
                    for (DoorSide dr : this.GetRoom().GetDoors()){
                        if (dr.IsDoorUseable()){
                            st.Move(dr);//a keresett ajtón átmegy
                            break;
                        }
                    }
                }
                for(Instructor ins : this.GetRoom().GetInstructors()){
                    for (DoorSide dr : this.GetRoom().GetDoors()){
                        if (dr.IsDoorUseable()){
                            ins.Move(dr);//a keresett ajtón átmegy
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Mozgatási függvény
     * @param d  Egy ajtó, amelyen a Person megpróbál átlépni egy másik szobába
     */
    @Override
    public boolean Move(DoorSide d) {
        Logger.started(this, "Move", d);
        if (!room.GetDoors().contains(d)) return false;
        DoorSide d2 = d.GetPair();
        Room r2 = d2.GetRoom();
        boolean isAppeared = AppearInRoom(r2);
        Logger.finished(this, "Move", d);
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
        //return false;
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
        Logger.started(this, "StartTurn");
        activeTurn = true;

        //Mozgató logika
        Random random = new Random();
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
        Logger.finished(this, "StartTurn");
    }

    /**
     * Kör vége függvény
     */
    @Override
    public void EndTurn() {
        Logger.started(this,"EndTurn");
        this.activeTurn = false;
        game.NextTurn();
        Logger.finished(this, "EndTurn");
    }

    /**
     *  Elájult?
     * @return false
     */
    @Override
    public boolean GetIsFainted() {
        return false;
    }

    /**
     * Megbénult?
     * @return false
     */
    @Override
    public boolean GetIsStunned() {
        return false;
    }

    /**
     * Active e a köre?
     * @return ha aktiv true egyebkent false
     */
    @Override
    public boolean GetIsActiveTurn() {
        return this.activeTurn;
    }

    @Override
    public boolean GetIsAlive() {
        return true;
    }

    @Override
    public void ConnectTransistors(Transistor t1, Transistor t2) {

    }
}
