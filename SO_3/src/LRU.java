import java.util.ArrayList;

public class LRU extends Base{

    boolean ifElementIsInSet = false;

    public LRU(ReferencesList referencesList, int setSize) {
        this.queue = referencesList.makeCopy();
        this.doneReferences = new ArrayList<>();
        this.set = new Page[setSize];
        this.numbPageFaults = 0;
        this.workingTime = 0;
    }

    @Override
    public void run() {

        for(int i=0; i < set.length ; i++){
            set[i] = queue.get(0);
            doneReferences.add(queue.get(0));
            queue.remove(0);
        }
        numbPageFaults = set.length;

        int initialOutOfUseTime = set.length - 1;
        for (Page value : set) {
            value.setTimeOutOfUse(initialOutOfUseTime);
            initialOutOfUseTime--;
        }

        while (!queue.isEmpty()) {

            for (Page page : set) page.updateUnusedTime(1);
            Page currentPage = queue.get(0);

            for (Page page : set) {
                if (currentPage.getIdNumber() == page.getIdNumber()) {
                    page.setTimeOutOfUse(0);
                    ifElementIsInSet = true;
                    break;
                }
            }

            if(!ifElementIsInSet){
                int place = findLongestOutOfUse();
                set[place] = currentPage;
                numbPageFaults++;
            }
            doneReferences.add(currentPage);
            queue.remove(currentPage);
            ifElementIsInSet = false;
            workingTime++;
        }
        this.sumUp();
    }


    public int findLongestOutOfUse(){
        int pageInSet = 0;
        for(int i=1; i<set.length; i++) {
            if(set[pageInSet].getTimeOutOfUse() < set[i].getTimeOutOfUse())
                pageInSet = i;
        }
        return pageInSet;
    }
}
