
import console.UIConsole;
import tests.AstronomicObjectTests;
import tests.ServiceTests;


public class Main {

    public static void main(String[] args){
        ServiceTests serviceTests = new ServiceTests("testConfiguration.txt");
        AstronomicObjectTests astronomicTests = new AstronomicObjectTests();

        serviceTests.allTests();
        astronomicTests.allTests();

        String fileName = "SpaceConfiguration.txt";
        UIConsole uiConsole = new UIConsole(fileName);
        uiConsole.run();
    }
}
