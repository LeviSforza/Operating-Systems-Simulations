import java.util.ArrayList;

public class ReferencesList {

    ArrayList<Page> list;
    int idNumber;
    int range;

    public ReferencesList(){
        list = new ArrayList<>();
        idNumber = 0;
        range = 0;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void add(Page s){
        list.add(s);
    }

    public int size(){
        return list.size();
    }

    public int getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(int idNumber) {
        this.idNumber = idNumber;
    }

    public ArrayList<Page> makeCopy() {
        return new ArrayList<>(list);
    }


    public ArrayList<Page> getList() {
        return list;
    }

    public void setList(ArrayList<Page> list) {
        this.list = list;
    }

    public void resetOutOfUseTime() {
        for (Page p : list){
            p.setTimeOutOfUse(0);
        }
    }

    public Page get(int i) {
        return  list.get(i);
    }

    public void remove(int i) {
        list.remove(i);
    }

    public void remove(Page p) {
        list.remove(p);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }
}
