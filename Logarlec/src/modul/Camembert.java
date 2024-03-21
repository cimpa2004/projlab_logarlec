package modul;
import util.Logger;
import util.Reader;

/**
 * A camebert-t reprezentációja
 * Tárolja, hogy aktív e. Ha nem aktív akkor nem engedi a használatot.
 * Elhelyezi magát az őt felvevők inventoryába, és megvalósítja a használatot.
 * {@inheritDoc}
 */
public class Camembert extends Item implements Usable {
	/**
	 * Tárolja hogy aktív e
	 */
	private boolean isActivated;

	/**
	 * Aktiválja az Camembert-t
	 * @return True ha sikerült False, ha nem/ már használva volt
	 */
	@Override
	public boolean Activate() {
		Logger.started(this, "Activate");
		isActivated = Reader.GetBooleanInput("Sikerült aktiválni a Camembert?");
		Logger.finished(this, "Activate");
		return isActivated;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st A felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		Logger.finished(this, "PickedUpStudent", st);
		return returnValue;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i A felvevő oktató
	 * @return True, ha sikerült felvenni; False, ha nem
	 */
	@Override
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		Logger.finished(this, "PickedUpInstructor", i);
		return returnValue;
	}

	/**
	 * Kezeli az eldobást
	 * @param p Az eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Kezeli a használatot
	 * @param s Az Camembert-t használó hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		boolean isActivated = Activate();
		if(isActivated){
			Room roomToPosion = s.GetRoom();
			int duration = Reader.GetIntInput("Mennyi ideig legyen gázos a szoba?");
			roomToPosion.SetPoisonDuration(duration);
		}
		Logger.finished(this, "UsedByStudent", s);
	}

	/**
	 * Kezeli a használatot
	 * @param i A Camembert-t használó oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		boolean isActivated = Activate();
		if(isActivated){
			Room roomToPosion = i.GetRoom();
			int duration = Reader.GetIntInput("Mennyi ideig legyen gázos a szoba?");
			roomToPosion.SetPoisonDuration(duration);
		}
		Logger.finished(this, "UsedByInstructor", i);
	}
}
