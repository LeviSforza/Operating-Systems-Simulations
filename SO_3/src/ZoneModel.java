import java.util.ArrayList;
import java.util.Arrays;

public class ZoneModel extends BaseAllocation {

    boolean ifElementIsInSet = false;
    ArrayList<ReferencesList> stoppedProcesses = new ArrayList<>();
    int availableFrames = 0;
    int framesNumber, deltaT;
    int D = 0;
    int timer = 0;
    ArrayList<Integer> framesList = new ArrayList<>();
    ArrayList<Integer> faultsList = new ArrayList<>();
    ArrayList<Integer> stoppedFaults = new ArrayList<>();
    ArrayList<Integer> finishedFaults = new ArrayList<>();
    ArrayList<ArrayList<Integer>> WSSInDeltaT = new ArrayList<>();
    ArrayList<Page[]> setsList = new ArrayList<>();
    ArrayList<Page> doneReferences;
    int numbPageFaults;
    int freedFrames = 0;

    public ZoneModel(ProcessesList processesList, int framesNumber, int deltaT) {
        this.procQueue = processesList.makeCopy();
        this.spareCopy = processesList.makeCopy();
        this.framesNumber = framesNumber;
        this.deltaT = deltaT;
        this.doneReferences = new ArrayList<>();
        this.numbPageFaults = 0;
        this.pageFaultsForProcess = 0;
        this.pageFaultsForSystem = 0;
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
            for(int g=0; g<deltaT; g++){
                array.add(0);
            }
            WSSInDeltaT.add(array);
        }

        //poczatkowy przydzial
        for(int j = 0; j< procQueue.size(); j++) {
            //  System.out.println("Liczba odwołań: " + procQueue.get(j).size());
            ReferencesList currentList = procQueue.get(j);
            Page[] set = new Page[framesList.get(j)];
            for (int i = 0; i < set.length; i++) {
                set[i] = currentList.get(0);
                WSSInDeltaT.get(j).add(1);
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


               if(timer == deltaT/3) {

                   ArrayList<Integer> WSSs = new ArrayList<>();
                   int counter = 0;
                   for (int i = 0; i < procQueue.size(); i++) {
                       for (int h = WSSInDeltaT.get(i).size() - 1; h >= WSSInDeltaT.get(i).size() - deltaT - 1; h--) {
                           counter += WSSInDeltaT.get(i).get(h);
                       }
                       if(counter == 0){
                           counter = 5;
                       }
                       WSSs.add(counter);
                       counter = 0;
                   }

                   for (int i = 0; i < procQueue.size(); i++) {
                       D += WSSs.get(i);
                   }

                   if (D <= framesNumber) {
                       //wyrównać sets do wss
                       for (int i = 0; i < procQueue.size(); i++) {
                           Page[] temp = new Page[WSSs.get(i)];
                           if (setsList.get(i).length <= WSSs.get(i)) {
                               System.arraycopy(setsList.get(i), 0, temp, 0, setsList.get(i).length);
                               Page page = new Page(99);
                               page.setTimeOutOfUse(Integer.MAX_VALUE);
                               for (int j = setsList.get(i).length; j < temp.length; j++) {
                                   temp[j] = page;
                               }
                               setsList.set(i, temp);
                           } else {
                               //odwrotny przypadek
                               System.arraycopy(setsList.get(i), 0, temp, 0, temp.length);
                               setsList.set(i, temp);
                           }
                       }
                       availableFrames = framesNumber - D;

                   } else {
                       //zatrzymać proces, rozdzielić powstałe ramki D > framesNumber
                       int maksWSS = 0;
                       int i = 0;
                       for (int f = 0; f < WSSs.size(); f++) {
                           if (maksWSS < WSSs.get(f)) {
                               i = f;
                               maksWSS = WSSs.get(f);
                           }
                       }

                       ReferencesList currentList = procQueue.get(i);
                       stoppedFaults.add(faultsList.get(i));
                       faultsList.remove(faultsList.get(i));
                       stoppedProcesses.add(currentList);
                       procQueue.remove(currentList);
                       freedFrames = setsList.get(i).length;
                       setsList.remove(setsList.get(i));

                       //przydział uwolninych ramek
                       ArrayList<Integer> ramkiDoDopisania = new ArrayList<>();
                       int framesPerProcess = freedFrames/ procQueue.size();
                       for(int g = 0; g< procQueue.size(); g++){
                           ramkiDoDopisania.add(framesPerProcess);
                       }
                       if(freedFrames % procQueue.size() != 0){
                           for(int g = 0; g < freedFrames % procQueue.size(); g++){
                               ramkiDoDopisania.set(g, framesPerProcess + 1);
                           }
                       }

                       //wyrównanie dopisanych ramek
                       for (int p = 0; p < procQueue.size(); p++) {
                           Page[] temp = new Page[setsList.get(p).length + ramkiDoDopisania.get(p)];

                           System.arraycopy(setsList.get(p), 0, temp, 0, setsList.get(p).length);
                           Page page = new Page(99);
                           page.setTimeOutOfUse(Integer.MAX_VALUE);
                           for (int j = setsList.get(p).length; j < temp.length; j++) {
                               temp[j] = page;
                           }
                           setsList.set(p, temp);
                       }
                       availableFrames = 0;
                   }
                   D = 0;
                   timer = 0;
               }

               //aktywowanie zatrzymanego procesu jeśli jest wystarczająco ramek
                   if (!stoppedProcesses.isEmpty()) {
                       if (availableFrames > stoppedProcesses.get(0).getRange() / 2) {
                          // System.out.println("Aktywowanie procesu ");

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


                   //wykonanie LRU
                   for (int i = 0; i < procQueue.size(); i++) {
                       ReferencesList currentList = doLRUAlgorithm(i);
                       if (currentList.isEmpty()) {
                           availableFrames += setsList.get(i).length;
                           setsList.remove(setsList.get(i));
                           finishedFaults.add(faultsList.get(i));
                           faultsList.remove(faultsList.get(i));
                           procQueue.remove(currentList);
                           i--;
                       }
                   }
             timer++;
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
                WSSInDeltaT.get(i).add(0);
                page.setTimeOutOfUse(0);
                ifElementIsInSet = true;
                break;
            }
        }

        if(!ifElementIsInSet){
            int place = findLongestOutOfUse(set);
            set[place] = currentPage;
            faultsList.set(i, faultsList.get(i)+1);
            WSSInDeltaT.get(i).add(1);
        }
        doneReferences.add(currentPage);
        currentList.remove(currentPage);
        ifElementIsInSet = false;
        return currentList;
    }




}
