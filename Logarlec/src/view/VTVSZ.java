package view;

import viewmodel.IVTVSZ;

public class VTVSZ extends VItem {
    IVTVSZ ivTVSZ;

    public VTVSZ(IVTVSZ ivTVSZ) {
        this.ivTVSZ = ivTVSZ;
        this.ivTVSZ.SetIVItemUpdate(this);
    }
}
