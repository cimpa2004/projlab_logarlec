package test;

import viewmodel.ICRoom;
import viewmodel.IVRoom;

public class MockICRoom implements ICRoom {
    @Override
    public void Split(IVRoom ivRoom, IVRoom _new) {
        System.out.println("Split");
    }

    @Override
    public void Merge(IVRoom ivRoom1, IVRoom ivRoom2) {
        System.out.println("Merge");
    }
}
