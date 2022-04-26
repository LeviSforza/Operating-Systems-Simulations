import java.util.ArrayList;

public abstract class Base {

    ArrayList<Process> queue, processesInWait, doneProcesses, processesRT, undoneProcesses;
    int coveredDistance, currentCylinder;
    private double sumWaitingTime = 0;
    private double averageWaitingTime = 0;
    private double longestWaitTime = 0;
    private double numberOfUndone = 0;
    private double covDistance = 0;
    Disc dysk;

    public abstract void run();

    public double getLongestWaitTime() {
        return longestWaitTime;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getNumberOfUndone() {
        return numberOfUndone;
    }

    public double getCovDistance() {
        return covDistance;
    }

    public void sumUp() {
        for (int i = 0; i < doneProcesses.size(); i++) {
            sumWaitingTime += doneProcesses.get(i).getWaitingTime();
            if(longestWaitTime < doneProcesses.get(i).getWaitingTime())
                longestWaitTime = doneProcesses.get(i).getWaitingTime();
        }
        averageWaitingTime = sumWaitingTime/doneProcesses.size();
      //  System.out.println("Sredni czas oczekiwania: " + averageWaitingTime);
       // System.out.println("Najdłuższy czas oczekiwania: " + longestWaitTime);
       // System.out.println("Covered distance: " + coveredDistance);
       // System.out.println("Liczba niewykonanych: " + undoneProcesses.size());
        numberOfUndone = undoneProcesses.size();
        covDistance = coveredDistance;
        coveredDistance=0;
    }

    public void findReadyProcesses() {
        for(int i=0; i<queue.size(); i++){
            if(queue.get(i).getArrivalTime() <= coveredDistance){
                processesInWait.add(queue.get(i));
                queue.remove(i);
            }
        }
    }

    public abstract void sortReadyProcesses();

    public void printAll(){
        for (Process t : processesInWait) {
            System.out.println(t.toString());
        }
    }

    public void printDoneProcesses() {
        System.out.println("Done processes:");
        for (Process t : doneProcesses) {
            System.out.println(t.toString());
        }
    }

}
