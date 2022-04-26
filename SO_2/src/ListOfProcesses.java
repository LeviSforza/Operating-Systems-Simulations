import java.util.ArrayList;

public class ListOfProcesses {

    ArrayList<Process> list;

    public ListOfProcesses() {
        this.list = new ArrayList<>();
    }

    public void add(Process process) {
        list.add(process);
    }

    public void clear(){
        list.clear();
    }

    public void resetWaitTime() {
        for (Process p : list){
            p.setWaitingTime(0);
        }
    }

    public ArrayList<Process> makeCopy() {
        ArrayList<Process> temp = new ArrayList();
        for(int i=0; i<list.size(); i++)
            temp.add(list.get(i));
        return temp;
    }

    public void printAll() {
        System.out.println("\nProcesses: ");
        for (Process t : list) {
            System.out.println(t.toString());
        }
    }


}
