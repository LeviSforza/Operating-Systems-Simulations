import java.util.ArrayList;
import java.util.Comparator;

public class EDF extends Base{

    int firstLoop = 0;

    public EDF(ListOfProcesses processList, Disc d) {
        this.queue = processList.makeCopy();
        this.processesInWait = new ArrayList<>();
        this.doneProcesses = new ArrayList<>();
        this.processesRT = new ArrayList<>();
        this.undoneProcesses = new ArrayList<>();
        this.coveredDistance = 0;
        this.currentCylinder = 0;
        this.dysk = d;
    }

    @Override
    public void run() {
        while (!queue.isEmpty() || !processesInWait.isEmpty() || !processesRT.isEmpty()) {
            findReadyProcesses();
            findProcessesRT();

            if (processesRT.size() > 0) {
                processesRT.sort(Comparator.comparing(Process::getRealTime));
                Process currentProcess = processesRT.get(0);

                int l = Math.abs(currentProcess.getCylinderNumber()-currentCylinder) +coveredDistance -currentProcess.getArrivalTime();
                if(l > currentProcess.getRealTime()){
                    coveredDistance += currentProcess.getRealTime();
                    if(currentProcess.getCylinderNumber() < currentCylinder)
                        currentCylinder -= currentProcess.getRealTime();
                    else
                        currentCylinder += currentProcess.getRealTime();
                    undoneProcesses.add(currentProcess);
                    processesRT.remove(currentProcess);
                } else {
                    coveredDistance += Math.abs(currentCylinder - currentProcess.getCylinderNumber());
                    currentProcess.updateWaitTime(coveredDistance - currentProcess.getArrivalTime());
                    currentCylinder = currentProcess.getCylinderNumber();
                    doneProcesses.add(currentProcess);
                    processesRT.remove(currentProcess);
                }
                firstLoop = 1;
            }
            if (processesInWait.size() > 0 && firstLoop == 0) {

                sortReadyProcesses();
                Process currentProcess = processesInWait.get(0);

                int x = coveredDistance + Math.abs(currentProcess.getCylinderNumber()-currentCylinder);
                Process p = findEarliestProcess();
                if(x > p.getArrivalTime()) {
                    int m = (p.getArrivalTime()-coveredDistance);
                    coveredDistance += m;
                    if(currentProcess.getCylinderNumber() < currentCylinder)
                        currentCylinder -= m;
                    else
                        currentCylinder += m;
                } else {
                    coveredDistance += Math.abs(currentCylinder - currentProcess.getCylinderNumber());
                    currentProcess.updateWaitTime(coveredDistance - currentProcess.getArrivalTime());
                    currentCylinder = currentProcess.getCylinderNumber();
                    doneProcesses.add(currentProcess);
                    processesInWait.remove(currentProcess);
                }
            }

            if(processesInWait.isEmpty() && processesRT.isEmpty()) {
                coveredDistance++;
                if(currentCylinder < dysk.getLength())
                    currentCylinder++;
                else
                    currentCylinder = 0;
            }
            firstLoop = 0;
        }
      //  printDoneProcesses();
      //  printUndoneProcesses();
        this.sumUp();
    }

    @Override
    public void sortReadyProcesses() {
        processesInWait.sort(Comparator.comparing(Process::getArrivalTime));
    }

    public void findProcessesRT() {
        for(int i = 0; i< processesInWait.size(); i++){
            if(processesInWait.get(i).isIfRealTime() == true){
                processesRT.add(processesInWait.get(i));
                processesInWait.remove(i);
            }
        }
    }

    public Process findEarliestProcess() {
        Process p = new Process(1,999999,1,true,0);
        for(int i=0; i<queue.size(); i++){
            if(queue.get(i).isIfRealTime() == true){
                if(queue.get(i).getArrivalTime() < p.getArrivalTime())
                    p = queue.get(i);
            }
        }
        return p;
    }

    public void printUndoneProcesses(){
        System.out.println("\nUndone processes;");
        for (Process t : undoneProcesses) {
            System.out.println(t.toString());
        }
    }


}
