public class Process {

    int arrivalTime;
    int waitingTime;
    boolean ifRealTime;
    int realTime;
    int cylinderNumber;
    int n;

    public Process(int number, int arrivalTime, int cylinderNumber, boolean ifRealTime, int realTime) {
        this.n = number;
        this.arrivalTime = arrivalTime;
        this.waitingTime = 0;
        this.ifRealTime = ifRealTime;
        this.realTime = realTime;
        this.cylinderNumber = cylinderNumber;
    }

    public int getRealTime() {
        return realTime;
    }

    public boolean isIfRealTime() {
        return ifRealTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getCylinderNumber() {
        return cylinderNumber;
    }

    public void updateWaitTime(int update) {
        waitingTime += update;
    }

    @Override
    public String toString() {
        return "Process{" +
                "n=" + n +
                ", cylinderNumber=" + cylinderNumber +
                ", arrivalTime=" + arrivalTime +
                ", waitingTime=" + waitingTime +
                ", ifRealTime=" + ifRealTime +
                ", realTime=" + realTime +
                '}';
    }
}
