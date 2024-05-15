package view;

import viewmodel.IVSlideRule;

public class VSlideRule extends VItem {
    IVSlideRule ivSlideRule;

    //ezt a konstruktort használd Roomban megírtam onnan lehet példát venni
    public VSlideRule(IVSlideRule ivSlideRule){
        this.ivSlideRule = ivSlideRule;
        this.ivSlideRule.SetIVItemUpdate(this);
    }
}
