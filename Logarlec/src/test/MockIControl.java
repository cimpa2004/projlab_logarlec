package test;

import viewmodel.IControl;
import viewmodel.IVInstructor;

public class MockIControl implements IControl {

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

    @Override
    public void InstructorKills(IVInstructor instructor) {

    }
}
