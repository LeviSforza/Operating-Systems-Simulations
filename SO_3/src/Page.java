public class Page {

    int idNumber, timeOutOfUse;

    @Override
    public String toString() {
        return "Page{" +
                "idNumber=" + idNumber +
                ", timeOutOfUse=" + timeOutOfUse +
                ", bit=" + bit +
                '}';
    }

    boolean bit;

    public Page(int idNumber){
        this.idNumber = idNumber;
        this.timeOutOfUse = 0;
        this.bit = true;
    }

    public boolean isBit() {
        return bit;
    }

    public void setBit(boolean bit) {
        this.bit = bit;
    }

    public int getIdNumber() {
        return idNumber;
    }

    public int getTimeOutOfUse() {
        return timeOutOfUse;
    }

    public void setTimeOutOfUse(int timeOutOfUse) {
        this.timeOutOfUse = timeOutOfUse;
    }

    public void updateUnusedTime(int update) {
        timeOutOfUse += update;
    }

}
