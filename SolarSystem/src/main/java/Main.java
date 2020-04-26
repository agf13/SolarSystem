
import console.UIConsole;
import tests.ServiceTests;


public class Main {

    public static void main(String[] args){
//        ServiceTests serviceTests = new ServiceTests("testConfiguration.txt");
//        serviceTests.allTests();

        String fileName = "SpaceConfiguration.txt";
        UIConsole uiConsole = new UIConsole(fileName);
        uiConsole.run();
    }
}
