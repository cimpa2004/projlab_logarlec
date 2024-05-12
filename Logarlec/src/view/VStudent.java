package view;

import viewmodel.IVStudent;

import java.util.List;

public class VStudent {
    private IVStudent ivStudent;

    public List<VItem> getItems(){
        return null;
    }
    public boolean getIsActiveTurn(){
        return ivStudent.isActiveTurn();
    }
    public void EndTurn(){
        ivStudent.EndTurn();
    }
}
