package viewmodel;


public interface IVInit {
    public void AddStudent(String personID);
    public void RemoveStudent(String personID);
    public void StartGame();
    public void CreateGame(String mapPathJSON);
}
