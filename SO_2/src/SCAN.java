import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SCAN extends Base{
    int firstAhead = 0;
    int prevCylinder = -1;

    public SCAN(ListOfProcesses processList, Disc d) {
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
            queue.sort(Comparator.comparing(Process::getArrivalTime));

            if (processesInWait.size() > 0) {
                if (prevCylinder < currentCylinder) {
                    sortReadyProcesses();
                    for (int i = 0; i < processesInWait.size(); i++) {
                        if (currentCylinder > processesInWait.get(i).getCylinderNumber()) {
                            firstAhead++;
                        }
                    }
                    doSCANAlgorithm();
                } else {
                    sortReadyProcesses();
                    Collections.reverse(processesInWait);
                    for (int i = 0; i < processesInWait.size(); i++) {
                        if (currentCylinder < processesInWait.get(i).getCylinderNumber()) {
                            firstAhead++;
                        }
                    }
                    doSCANAlgorithm();
                }
            }
            coveredDistance++;
            firstAhead = 0;

            if(currentCylinder == dysk.getLength() && currentCylinder > prevCylinder) {
                currentCylinder--;
                prevCylinder = dysk.getLength();
            }
            if (currentCylinder == 0 && currentCylinder < prevCylinder) {
                currentCylinder++;
                prevCylinder = 0;
            }
            if(currentCylinder < dysk.getLength() && currentCylinder > prevCylinder) {
                currentCylinder++;
                prevCylinder++;
            }
            if(currentCylinder < dysk.getLength() && currentCylinder < prevCylinder){
                currentCylinder--;
                prevCylinder--;
            }
        }
      //  printDoneProcesses();
        this.sumUp();
    }

    @Override
    public void sortReadyProcesses() {
        processesInWait.sort((Comparator.comparing(Process::getCylinderNumber)));
    }

    public void doSCANAlgorithm() {
        if (firstAhead < processesInWait.size()) {
            Process currentProcess = processesInWait.get(firstAhead);
            if (currentProcess.getCylinderNumber() == currentCylinder) {
                doneProcesses.add(currentProcess);
                processesInWait.remove(currentProcess);
            }
        }
        for (int j = 0; j < processesInWait.size(); j++) {
            processesInWait.get(j).updateWaitTime(1);
        }
    }


}
