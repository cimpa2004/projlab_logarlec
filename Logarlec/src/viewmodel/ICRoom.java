package viewmodel;


public interface ICRoom {
    public void Split(IVRoom ivRoom, IVRoom _new);
    public void Merge(IVRoom ivRoom1, IVRoom ivRoom2);
}
