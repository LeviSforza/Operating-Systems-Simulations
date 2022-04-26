public class ListMaker {

    private int processesQuantity, numberOfRT, maxArrivalTime, maxRealTime;
    int n = 1;
    int RTCounter = 0;
    Disc dysk;

    public ListMaker(int processesQuantity, int numberOfRT, int maxArrivalTime, int maxRealTime, Disc dysk){
        this.processesQuantity = processesQuantity;
        this.numberOfRT = numberOfRT;
        this.maxArrivalTime = maxArrivalTime;
        this.maxRealTime = maxRealTime;
        this.dysk = dysk;
    }

    public ListOfProcesses generate(){
        int entry, cylinder, RT;
        boolean ifRT;
        ListOfProcesses list = new ListOfProcesses();
        for(int i = 0; i < processesQuantity; i++) {
            n = i+1;
            entry = (int) (Math.random() * maxArrivalTime)+1;
            cylinder = (int) (Math.random() * dysk.getLength())+1;
            if(RTCounter < numberOfRT) {
                ifRT = true;
                RT = (int) (Math.random() * maxRealTime + 1);
            }
            else{
                ifRT = false;
                RT = 0;
            }
            list.add(new Process(n, entry, cylinder, ifRT, RT));
            RTCounter++;
        }
        RTCounter = 0;
        return list;
    }


}
