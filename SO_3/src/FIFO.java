import java.util.ArrayList;

public class FIFO extends Base{

    boolean ifElementIsInSet = false;
    int oldestReference = 0;

    public FIFO(ReferencesList referencesList, int setSize) {
        this.queue = referencesList.makeCopy();
        this.doneReferences = new ArrayList<>();
        this.set = new Page[setSize];
        this.numbPageFaults = 0;
        this.workingTime = 0;
    }

    @Override
    public void run() {

        for(int i=0; i<set.length ; i++){
            set[i] = queue.get(0);
            doneReferences.add(queue.get(0));
            queue.remove(0);
        }
        numbPageFaults = set.length;

        while (!queue.isEmpty()) {

            Page currentPage = queue.get(0);

            for (Page page : set) {
                if (currentPage.getIdNumber() == page.getIdNumber()) {
                    ifElementIsInSet = true;
                    break;
                }
            }

            if(!ifElementIsInSet){
                set[oldestReference] = currentPage;
                numbPageFaults++;
                if(oldestReference < set.length-1)
                    oldestReference++;
                else
                    oldestReference = 0;
            }
            doneReferences.add(currentPage);
            queue.remove(currentPage);

            ifElementIsInSet = false;
            workingTime++;

        }
        this.sumUp();
    }


}
