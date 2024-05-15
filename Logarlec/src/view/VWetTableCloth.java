package view;

import viewmodel.IVWetTableCloth;

public class VWetTableCloth extends VItem {
    IVWetTableCloth ivWetTableCloth;

    public VWetTableCloth(IVWetTableCloth ivWetTableCloth) {
        this.ivWetTableCloth = ivWetTableCloth;
        ivWetTableCloth.SetIVItemUpdate(this);
    }
}
