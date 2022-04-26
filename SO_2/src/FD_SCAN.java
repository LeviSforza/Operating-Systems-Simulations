import java.util.ArrayList;
import java.util.Comparator;

public class FD_SCAN extends Base{

    int firstLoop = 0;

    public FD_SCAN(ListOfProcesses processList, Disc d) {
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
        while (!queue.isEmpty() || !processesInWait.isEmpty()) {
            findReadyProcesses();
            findProcessesRT();
            throwOutImpossible();

            if (processesRT.size() > 0) {
                processesRT.sort(Comparator.comparing(Process::getRealTime));
                Process currentProcess = processesRT.get(0);

                for(int j = 0; j< processesInWait.size(); j++) {
                    Process z = processesInWait.get(j);
                    if((z.getCylinderNumber()>currentCylinder && z.getCylinderNumber()<=currentProcess.getCylinderNumber())
                    || (z.getCylinderNumber()<currentCylinder && z.getCylinderNumber()>=currentProcess.getCylinderNumber())){
                        z.updateWaitTime(coveredDistance-z.getArrivalTime()+Math.abs(currentProcess.getCylinderNumber()-currentCylinder));
                        doneProcesses.add(z);
                        processesInWait.remove(z);
                    }
                }
                for(int j=0; j<processesRT.size(); j++) {
                    Process z = processesRT.get(j);
                    if((z.getCylinderNumber()>currentCylinder && z.getCylinderNumber()<currentProcess.getCylinderNumber())
                            || (z.getCylinderNumber()<currentCylinder && z.getCylinderNumber()>currentProcess.getCylinderNumber())){
                        if(z.getRealTime() < Math.abs(z.getCylinderNumber()-currentCylinder))
                            undoneProcesses.add(z);
                        else
                            z.updateWaitTime(coveredDistance-z.getArrivalTime()+Math.abs(currentProcess.getCylinderNumber()-currentCylinder));
                            doneProcesses.add(z);
                        processesRT.remove(z);
                    }
                }
                coveredDistance += Math.abs(currentCylinder - currentProcess.getCylinderNumber());
                currentProcess.updateWaitTime(coveredDistance - currentProcess.getArrivalTime());
                currentCylinder = currentProcess.getCylinderNumber();
                doneProcesses.add(currentProcess);
                processesRT.remove(currentProcess);
                firstLoop = 1;
            }
            if (processesInWait.size() > 0 && firstLoop == 0) {
                sortReadyProcesses();
                Process currentProcess = processesInWait.get(0);

                int x = coveredDistance + Math.abs(currentProcess.getCylinderNumber()-currentCylinder);
                Process p = findEarliestProcess();
                if(x > p.getArrivalTime()) {
                    int m = (p.getArrivalTime()-coveredDistance);
                    if(currentProcess.getCylinderNumber() < currentCylinder)
                        currentCylinder -= m;
                    else
                        currentCylinder += m;
                    coveredDistance += m;
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
     //   printDoneProcesses();
     //   printUndoneProcesses();
        this.sumUp();
    }

    @Override
    public void sortReadyProcesses() {
        processesInWait.sort((Comparator.comparing(Process::getArrivalTime)));
    }

    public void findProcessesRT() {
        for(int i = 0; i< processesInWait.size(); i++){
            if(processesInWait.get(i).isIfRealTime() == true){
                processesRT.add(processesInWait.get(i));
                processesInWait.remove(i);
            }
        }
    }

    public void throwOutImpossible() {
        for(int i=0; i<processesRT.size(); i++){
            if(processesRT.get(i).getRealTime() < Math.abs(processesRT.get(i).getCylinderNumber()-currentCylinder)) {
                undoneProcesses.add(processesRT.get(i));
                processesRT.remove(i);
            }
        }
    }

    public Process findEarliestProcess() {
        Process p = new Process(1,99999,1,true,0);
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
