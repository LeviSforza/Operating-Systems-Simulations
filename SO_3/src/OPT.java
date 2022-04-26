import java.util.ArrayList;

public class OPT extends Base{

    boolean ifElementIsInSet = false;

    public OPT(ReferencesList referencesList, int setSize) {
        this.queue = referencesList.makeCopy();
        this.doneReferences = new ArrayList<>();
        this.set = new Page[setSize];
        this.numbPageFaults = 0;
        this.workingTime = set.length;
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
                setTimeBetweenReferences();
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


    public void setTimeBetweenReferences(){
        for (Page page : set) {
            page.setTimeOutOfUse(Integer.MAX_VALUE);
        }
        for (Page p : set) {
            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i).getIdNumber() == p.getIdNumber()) {
                    p.setTimeOutOfUse(i);
                    i = queue.size();
                }
            }
        }
    }


}
