import java.util.ArrayList;

public class ProportionalAllocation extends BaseAllocation {

    public ProportionalAllocation(ProcessesList processesList, int framesNumber) {
        this.procQueue = processesList.makeCopy();
        this.spareCopy = processesList.makeCopy();
        this.doneReferences = new ArrayList<>();
        this.framesNumber = framesNumber;
        this.pageFaultsForProcess = 0;
        this.pageFaultsForSystem = 0;
        this.workingTime = 0;
    }


    @Override
    public void run() {
        LRU lru;
        int sumOfRanges = 0;
        int usedFrames = 0;
        double wskaznik;
        ArrayList<Integer> framesList = new ArrayList<>();

        for (ReferencesList list : procQueue) {
            sumOfRanges += list.getRange();
        }

       // System.out.println("Sum: " + sumOfRanges);
        wskaznik = (double) framesNumber/sumOfRanges;
       // System.out.println("Wskaznik: " + wskaznik);

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


        for (int i = 0; i < procQueue.size(); i++) {
            ReferencesList referencesList = procQueue.get(i);
            lru = new LRU(referencesList, framesList.get(i));
            lru.run();
            pageFaultsForSystem += lru.getNumberPageFaults();
            referencesList.resetOutOfUseTime();
        }
        sumUp();
    }
}
