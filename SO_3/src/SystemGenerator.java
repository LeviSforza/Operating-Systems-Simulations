import java.util.Random;

public class SystemGenerator {

    int processesNumber, maksReferencesNumber, maksRange;
    Random rnd = new Random();

    public SystemGenerator(int processesNumber, int maksReferencesNumber, int maksRange) {
        this.processesNumber = processesNumber;
        this.maksReferencesNumber = maksReferencesNumber;
        this.maksRange = maksRange;
    }

    public ProcessesList generate(){
        ProcessesList list = new ProcessesList();
        ListMaker maker;
        ReferencesList refList;
        int idNumb = 0;
        int referencesNumber, range;

        for(int i = 0; i < processesNumber; i++) {
           range = maksRange/2 + rnd.nextInt(maksRange/2)+1;
           referencesNumber = rnd.nextInt(maksReferencesNumber-maksRange)+1+maksRange;
           maker = new ListMaker(referencesNumber, range);
           refList = maker.generate();
           refList.setIdNumber(idNumb);
           refList.setRange(range);
           list.add(refList);
           idNumb++;
        }

        return list;
    }
}
