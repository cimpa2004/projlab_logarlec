package viewmodel;

import view.VJanitor;

public interface IVJanitor {
    public String GetID();
    public IVRoom GetIVRoom();
    public void SetIVJanitorUpdate(IVJanitorUpdate ivJanitorUpdate);
    public VJanitor GetIVJanitorUpdate();
}
