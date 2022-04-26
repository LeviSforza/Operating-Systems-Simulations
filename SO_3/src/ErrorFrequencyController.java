import java.util.ArrayList;
import java.util.Arrays;

public class ErrorFrequencyController extends BaseAllocation {

    boolean ifElementIsInSet = false;
    ArrayList<ReferencesList> stoppedProcesses = new ArrayList<>();
    int availableFrames = 0;
    int framesNumber;
    double l, u;
    double PPF = 0;
    int timer = 0;
    ArrayList<Integer> framesList = new ArrayList<>();
    ArrayList<Integer> faultsList = new ArrayList<>();
    ArrayList<Integer> stoppedFaults = new ArrayList<>();
    ArrayList<Integer> finishedFaults = new ArrayList<>();
    ArrayList<ArrayList<Integer>> faultsInDeltaT = new ArrayList<>();
    ArrayList<Page[]> setsList = new ArrayList<>();
    ArrayList<Page> doneReferences;
    int workingTime, numbPageFaults;

    //ArrayList<ReferencesList> procQueue;

    public ErrorFrequencyController(ProcessesList processesList, int framesNumber, double l, double u, int deltaT) {
        this.procQueue = processesList.makeCopy();
        this.spareCopy = processesList.makeCopy();
        this.framesNumber = framesNumber;
        this.l = l;
        this.u = u;
        this.timer = deltaT;
        this.doneReferences = new ArrayList<>();
        this.numbPageFaults = 0;
        this.pageFaultsForProcess = 0;
        this.pageFaultsForSystem = 0;
        this.workingTime = 0;
    }

    @Override
    public void run() {

        //poczatek przydzialu proporcjonalnego
        int sumOfRanges = 0;
        int usedFrames = 0;
        double wskaznik;

        for (ReferencesList list : procQueue) {
            sumOfRanges += list.getRange();
        }
        wskaznik = (double) framesNumber/sumOfRanges;

        for (ReferencesList value : procQueue) {
            int framesPerProcess = (int) (value.getRange() * wskaznik);
            framesList.add(framesPerProcess);
            usedFrames += framesPerProcess;
        }
        if(usedFrames < framesNumber){
            for(int i=0; i< framesNumber - usedFrames; i++){
                framesList.set(i, framesList.get(i) + 1);
            }
        }
        if(usedFrames > framesNumber){
            for(int i=0; i< usedFrames - framesNumber; i++){
                framesList.set(i, framesList.get(i) - 1);
            }
        }

        for(int i=0; i<procQueue.size(); i++){
            ArrayList<Integer> array = new ArrayList<>();
            for(int g=0; g<timer; g++){
                array.add(0);
            }
            faultsInDeltaT.add(array);
        }

        //poczatkowy przydzial
        for(int j = 0; j< procQueue.size(); j++) {
          //  System.out.println("Liczba odwołań: " + procQueue.get(j).size());
            ReferencesList currentList = procQueue.get(j);
            Page[] set = new Page[framesList.get(j)];
            for (int i = 0; i < set.length; i++) {
                set[i] = currentList.get(0);
                faultsInDeltaT.get(j).add(1);
                doneReferences.add(currentList.get(0));
                currentList.remove(0);
            }
            numbPageFaults = set.length;
            faultsList.add(numbPageFaults);

            int initialOutOfUseTime = set.length - 1;
            for (Page value : set) {
                value.setTimeOutOfUse(initialOutOfUseTime);
                initialOutOfUseTime--;
            }
            setsList.add(set);
        }

        //konkretny system
        while (!procQueue.isEmpty()) {

                for(int i = 0; i< procQueue.size(); i++){

                    int faultsInDelta = 0;
                    for(int h=faultsInDeltaT.get(i).size()-1; h >= faultsInDeltaT.get(i).size() - timer-1; h--) {
                        faultsInDelta += faultsInDeltaT.get(i).get(h);
                    }
                    PPF = (double) faultsInDelta / timer;

                    //zabieram procesowi jedną ramkę
                    if(PPF < l){
                        Page[] temp = new Page[setsList.get(i).length - 1];
                        System.arraycopy(setsList.get(i), 0, temp, 0, setsList.get(i).length-1);
                        setsList.set(i, temp);
                        availableFrames++;
                    }

                    if(PPF > u){
                        //jeśli nie ma ramek zatrzymuję proces
                        if(availableFrames == 0){
                            ReferencesList currentList = procQueue.get(i);
                            stoppedFaults.add(faultsList.get(i));
                            faultsList.remove(faultsList.get(i));
                            stoppedProcesses.add(currentList);
                            procQueue.remove(currentList);
                            availableFrames += setsList.get(i).length;
                            setsList.remove(setsList.get(i));
                            i--;
                        } else {
                            //jeśli są ramki dołożyć jedną
                            Page[] temp = new Page[setsList.get(i).length + 1];
                            System.arraycopy(setsList.get(i), 0, temp, 0, setsList.get(i).length);
                            Page page = new Page(99);
                            page.setTimeOutOfUse(Integer.MAX_VALUE);
                            temp[setsList.get(i).length] = page;
                            setsList.set(i, temp);
                            availableFrames--;
                        }
                    }
                }

                //aktywowanie procesu
            if(!stoppedProcesses.isEmpty()){
                if( availableFrames > stoppedProcesses.get(0).getRange() / 2) {
                    ReferencesList currentList = stoppedProcesses.get(0);
                    Page[] temp = new Page[currentList.getRange() / 2];
                    Page page = new Page(99);
                    page.setTimeOutOfUse(Integer.MAX_VALUE);
                    Arrays.fill(temp, page);
                    setsList.add(temp);
                    procQueue.add(currentList);
                    availableFrames -= currentList.getRange() / 2;
                    stoppedProcesses.remove(currentList);
                    faultsList.add(stoppedFaults.get(0));
                    stoppedFaults.remove(faultsList.get(0));
                }
            }

            for (int i = 0; i < procQueue.size(); i++) {
                ReferencesList currentList = doLRUAlgorithm(i);
                if(currentList.isEmpty()){
                    availableFrames += setsList.get(i).length;
                    setsList.remove(setsList.get(i));
                    finishedFaults.add(faultsList.get(i));
                    faultsList.remove(faultsList.get(i));
                    procQueue.remove(currentList);
                    i--;
                }
            }
        }

        for (Integer integer : finishedFaults) {
            pageFaultsForSystem += integer;
        }
        this.sumUp();
    }

    public int findLongestOutOfUse(Page[] set){
        int pageInSet = 0;
        for(int i=1; i<set.length; i++) {
            if(set[pageInSet].getTimeOutOfUse() < set[i].getTimeOutOfUse())
                pageInSet = i;
        }
        return pageInSet;
    }

    public ReferencesList doLRUAlgorithm(int i){

        ReferencesList currentList = procQueue.get(i);
        Page[] set = setsList.get(i);
        for (Page page : set) page.updateUnusedTime(1);
        Page currentPage = currentList.get(0);
        for (Page page : set) {
            if (currentPage.getIdNumber() == page.getIdNumber()) {
                faultsInDeltaT.get(i).add(0);
                page.setTimeOutOfUse(0);
                ifElementIsInSet = true;
                break;
            }
        }

        if(!ifElementIsInSet){
            int place = findLongestOutOfUse(set);
            set[place] = currentPage;
            faultsList.set(i, faultsList.get(i)+1);
            faultsInDeltaT.get(i).add(1);
        }
        doneReferences.add(currentPage);
        currentList.remove(currentPage);
        ifElementIsInSet = false;
        return currentList;
    }

}