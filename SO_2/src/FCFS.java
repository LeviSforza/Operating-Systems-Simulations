import java.util.ArrayList;
import java.util.Comparator;

public class FCFS extends Base{

    public FCFS(ListOfProcesses processList, Disc d) {
        this.queue = processList.makeCopy();
        this.processesInWait = new ArrayList<>();
        this.doneProcesses = new ArrayList<>();
        this.undoneProcesses = new ArrayList<>();
        this.coveredDistance = 0;
        this.currentCylinder = 0;
        this.dysk = d;
    }

    @Override
    public void run() {
        while (!queue.isEmpty() || !processesInWait.isEmpty()) {
            findReadyProcesses();
            if (processesInWait.size() > 0) {
                sortReadyProcesses();
                Process currentProcess = processesInWait.get(0);

                coveredDistance += Math.abs(currentCylinder - currentProcess.getCylinderNumber());
                currentProcess.updateWaitTime(coveredDistance - currentProcess.getArrivalTime());
                currentCylinder = currentProcess.getCylinderNumber();
                doneProcesses.add(currentProcess);
                processesInWait.remove(currentProcess);
            } else {
                coveredDistance++;
                if(currentCylinder < dysk.getLength())
                    currentCylinder++;
                else
                    currentCylinder = 0;
            }
        }
      //  printDoneProcesses();
        this.sumUp();
    }

    @Override
    public void sortReadyProcesses() {
        processesInWait.sort(Comparator.comparing(Process::getArrivalTime));
    }


}

