import java.util.ArrayList;

public abstract class Base {

    ArrayList<Page> queue, doneReferences;
    int workingTime, numbPageFaults;
    Page[] set;
    private double numberPageFaults = 0;

    public double getNumberPageFaults() {
        return numberPageFaults;
    }

    public abstract void run();

    public void sumUp() {
        numberPageFaults = numbPageFaults;
       // System.out.println("Liczba błędów strony: " + numberPageFaults);
        numbPageFaults = 0;
        workingTime = 0;
    }

}
