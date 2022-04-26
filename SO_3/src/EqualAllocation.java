import java.util.ArrayList;

public class EqualAllocation extends BaseAllocation {

    public EqualAllocation(ProcessesList processesList, int framesNumber) {
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

        ArrayList<Integer> framesList = new ArrayList<>();
        LRU lru;
        int framesPerProcess = framesNumber/ procQueue.size();
        for(int i = 0; i< procQueue.size(); i++){
            framesList.add(framesPerProcess);
        }
        if(framesNumber % procQueue.size() != 0){
            for(int i = 0; i< framesNumber % procQueue.size(); i++){
                framesList.set(i, framesPerProcess + 1);
            }
        }

        for (int i = 0; i < procQueue.size(); i++) {
            ReferencesList referencesList = procQueue.get(i);
           // System.out.println("Liczba ramek: " + framesList.get(i));
            lru = new LRU(referencesList, framesList.get(i));
            lru.run();
            pageFaultsForSystem += lru.getNumberPageFaults();
            referencesList.resetOutOfUseTime();
        }
        sumUp();
    }



}
