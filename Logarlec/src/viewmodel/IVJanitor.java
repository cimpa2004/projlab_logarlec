package viewmodel;

import view.VJanitor;

public interface IVJanitor extends IVPerson {
    public String GetID();
    public IVRoom GetIVRoom();
    public void SetIVJanitorUpdate(IVJanitorUpdate ivJanitorUpdate);
    public VJanitor GetIVJanitorUpdate();
}
