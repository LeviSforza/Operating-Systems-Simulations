import java.util.ArrayList;
import java.util.Comparator;

public class C_SCAN extends Base{

    int firstAhead = 0;

    public C_SCAN(ListOfProcesses processList, Disc d) {
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
                sortReadyProcesses();

                for(int i = 0; i< processesInWait.size(); i++){
                    if(currentCylinder > processesInWait.get(i).getCylinderNumber())
                        firstAhead++;
                }
                if(firstAhead < processesInWait.size()) {
                    Process currentProcess = processesInWait.get(firstAhead);
                    if (currentProcess.getCylinderNumber() == currentCylinder) {
                        doneProcesses.add(currentProcess);
                        processesInWait.remove(currentProcess);
                    }
                }

                for(int j = 0; j< processesInWait.size(); j++)
                    processesInWait.get(j).updateWaitTime(1);
            }
            coveredDistance++;
            firstAhead = 0;
            if(currentCylinder < dysk.getLength())
                currentCylinder++;
            else
                currentCylinder = 0;

        }
       // printDoneProcesses();
        this.sumUp();
    }

    @Override
    public void sortReadyProcesses() {
        processesInWait.sort((Comparator.comparing(Process::getCylinderNumber)));
    }


}
