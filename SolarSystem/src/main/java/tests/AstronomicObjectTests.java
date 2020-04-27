package tests;

import entities.AstronomicObject;

public class AstronomicObjectTests {

    public void allTests(){
        equalName_differentInput_returnFalse();
        equalName_sameInput_returnTrue();
    }

    public void equalName_sameInput_returnTrue(){
        AstronomicObject obj1 = new AstronomicObject();
        AstronomicObject obj2 = new AstronomicObject();
        obj1.setName("me");
        obj2.setName("me");
        assert (obj1.equalName(obj2));
    }

    public void equalName_differentInput_returnFalse(){
        AstronomicObject obj1 = new AstronomicObject();
        AstronomicObject obj2 = new AstronomicObject();
        obj1.setName("me");
        obj2.setName("not me");
        assert (obj1.equalName(obj2) == false);
    }
}
