package test;

import viewmodel.*;

public class MockICInit implements ICInit {
    @Override
    public void CreateVInstructor(IVInstructor ivInstructor) {
        System.out.println("CreateVInstructor");
    }

    @Override
    public void CreateVDoorSide(IVDoorSide ivDoorSide) {
        System.out.println("CreateVDoorSide");
    }

    @Override
    public void CreateVAirFreshener(IVAirFreshener ivAirFreshener) {
        System.out.println("CreateVAirFreshener");
    }

    @Override
    public void CreateVTVSZ(IVTVSZ ivTVSZ) {
        System.out.println("CreateVTVSZ");
    }

    @Override
    public void CreateVHolyBeerCup(IVHolyBeerCup ivHolyBeerCup) {
        System.out.println("CreateVHolyBeerCup");
    }

    @Override
    public void CreateVWetTableCloth(IVWetTableCloth ivWetTableCloth) {
        System.out.println("CreateVWetTableCloth");
    }

    @Override
    public void CreateVCamembert(IVCamembert ivCamembert) {
        System.out.println("CreateVCamembert");
    }

    @Override
    public void CreateVFFP2Mask(IVFFP2Mask ivFFP2Mask) {
        System.out.println("CreateVFFP2Mask");
    }

    @Override
    public void CreateVSlideRule(IVSlideRule ivSlideRule) {
        System.out.println("CreateVSlideRule");
    }

    @Override
    public void CreateVRoom(IVRoom ivRoom) {
        System.out.println("CreateVRoom");
    }

    @Override
    public void CreateVTransistor(IVTransistor ivTransistor) {
        System.out.println("CreateVTransistor");
    }

    @Override
    public void CreateVJanitor(IVJanitor ivJanitor) {
        System.out.println("CreateVJanitor");
    }

    @Override
    public void CreateVStudent(IVStudent ivStudent, ICInput icInput) {
        CreateVStudent(ivStudent);
    }

    //TODO: Megoldani hogy jó legyen
    public void CreateVStudent(IVStudent ivStudent) {
       System.out.println("CreateVStudent");
    }
}
