import java.util.ArrayList;

public class ProcessesList {

    ArrayList<ReferencesList> list;

    public ProcessesList() {
        this.list = new ArrayList<>();
    }

    public void add(ReferencesList s){
        list.add(s);
    }

    public ArrayList<ReferencesList> getList() {
        return list;
    }

    public int size(){
        return list.size();
    }

    public ArrayList<ReferencesList> makeCopy() {
        return new ArrayList<>(list);
    }

    public void remove(ReferencesList l){
        list.remove(l);
    }

    public void remove(int i){
        list.remove(i);
    }

    public void set(int i, ReferencesList l){
        list.set(i, l);
    }

    public ReferencesList get(int i){
        return list.get(i);
    }

}
