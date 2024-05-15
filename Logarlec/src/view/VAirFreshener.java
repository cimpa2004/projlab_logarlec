package view;

import viewmodel.IVAirFreshener;

public class VAirFreshener extends VItem {
    IVAirFreshener ivAirFreshener;

    public VAirFreshener(IVAirFreshener ivAirFreshener) {
        this.ivAirFreshener = ivAirFreshener;
        this.ivAirFreshener.SetIVItemUpdate(this);
    }
}
