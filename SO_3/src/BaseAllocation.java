import java.util.ArrayList;

public abstract class BaseAllocation {


    ArrayList<ReferencesList> procQueue, doneReferences, spareCopy;
    int workingTime, pageFaultsForProcess, pageFaultsForSystem, framesNumber;
    private double pageFaultsForS;

    public double getNumberPageFaults() {
        return pageFaultsForS;
    }

    public abstract void run();

    public void sumUp() {
        pageFaultsForS = pageFaultsForSystem;
        double pageFaultsForP = (double)pageFaultsForSystem / spareCopy.size();
        System.out.println("Liczba błędów strony dla systemu: " + pageFaultsForS);
        System.out.println("Liczba błędów strony dla procesu: " + pageFaultsForP);
        pageFaultsForProcess = 0;
        pageFaultsForSystem = 0;
        workingTime = 0;
    }

    public void printQueue(){
        System.out.println("Queue:");
        for (ReferencesList t : procQueue) {
            for(int i=0; i<t.getList().size(); i++)
                System.out.println(t.toString());
        }
    }

}
