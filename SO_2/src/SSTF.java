import java.util.ArrayList;
import java.util.Comparator;

public class SSTF extends Base{

    public SSTF(ListOfProcesses processList, Disc d) {
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
       // printDoneProcesses();
        this.sumUp();
    }

    public void sortReadyProcesses() {
        Process p;
        for(int i = 0; i< processesInWait.size()-1; i++) {
            for(int j = 0; j< processesInWait.size()-1; j++) {
                if (Math.abs(currentCylinder - processesInWait.get(j).getCylinderNumber())
                        >= Math.abs(currentCylinder - processesInWait.get(j + 1).getCylinderNumber())) {
                    //BubbleSort
                    p = processesInWait.get(j);
                    processesInWait.set(j, processesInWait.get(j + 1));
                    processesInWait.set(j + 1, p);
                }
            }
        }
    }


}
