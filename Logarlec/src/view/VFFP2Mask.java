package view;

import viewmodel.IVFFP2Mask;

public class VFFP2Mask extends VItem{
    IVFFP2Mask ivFFP2Mask;
    
    public VFFP2Mask(IVFFP2Mask ivffp2Mask){
        this.ivFFP2Mask = ivffp2Mask;
        this.ivFFP2Mask.SetIVItemUpdate(this);
    }
}
