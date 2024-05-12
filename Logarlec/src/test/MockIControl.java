package test;

import viewmodel.IControl;

public class MockIControl implements IControl {
    @Override
    public void Update() {
        System.out.println("Update");
    }

    @Override
    public void StudentStartedTurn()  {
        System.out.println("StudentStartedTurn");
    }

    @Override
    public void InstructorWin() {
        System.out.println("InstructorWin");
    }

    @Override
    public void StudentWin() {
        System.out.println("StudentWin");
    }
}