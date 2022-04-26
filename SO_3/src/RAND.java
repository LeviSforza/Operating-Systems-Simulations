import java.util.ArrayList;
import java.util.Random;

public class RAND extends Base{

    boolean ifElementIsInSet = false;
    Random r = new Random();

    public RAND(ReferencesList referencesList, int setSize) {
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
                int randPos = r.nextInt(set.length);
                set[randPos] = currentPage;
                numbPageFaults++;
            }
            doneReferences.add(currentPage);
            queue.remove(currentPage);

            ifElementIsInSet = false;
            workingTime++;
        }
        this.sumUp();
    }
}
