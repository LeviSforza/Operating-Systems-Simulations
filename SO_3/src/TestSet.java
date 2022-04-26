import java.util.ArrayList;

public class TestSet {

    ArrayList<ReferencesList> testLists;
    private int listsNumb, referencesNumb, range, numberOfFrames;

    public TestSet(int listsNumber, int referencesNumb, int range, int numberOfFrames) {
        this.listsNumb = listsNumber;
        this.referencesNumb = referencesNumb;
        this.range = range;
        this.numberOfFrames = numberOfFrames;
        testLists = new ArrayList<>();
    }

    public void generteSet() {
        ListMaker maker = new ListMaker(referencesNumb, range);
        for(int i=0; i < listsNumb; i++)
            testLists.add(maker.generate());
    }

    public void resetSet() {
        for(int i=0; i< listsNumb; i++)
            testLists.get(i).resetOutOfUseTime();
    }

    public void runAll() {

        testLists = new ArrayList<>();
        generteSet();
        double PF_FIFO = 0;
        double PF_OPT = 0;
        double PF_LRU = 0;
        double PF_AproksLRU = 0;
        double PF_RAND = 0;

        for (int i = 0; i < listsNumb; i++) {

            Base f = new FIFO(testLists.get(i), numberOfFrames);
            f.run();
            PF_FIFO += f.getNumberPageFaults();
            resetSet();

            Base o = new OPT(testLists.get(i), numberOfFrames);
            o.run();
            PF_OPT += o.getNumberPageFaults();
            resetSet();

            Base l = new LRU(testLists.get(i), numberOfFrames);
            l.run();
            PF_LRU += l.getNumberPageFaults();
            resetSet();

            Base a = new ApproxLRU(testLists.get(i), numberOfFrames);
            a.run();
            PF_AproksLRU += a.getNumberPageFaults();
            resetSet();

            Base r = new RAND(testLists.get(i), numberOfFrames);
            r.run();
            PF_RAND += r.getNumberPageFaults();
            resetSet();
        }

        PF_FIFO = PF_FIFO / listsNumb;
        System.out.println("Liczba błędów strony FIFO: " + PF_FIFO);

        PF_OPT = PF_OPT / listsNumb;
        System.out.println("Liczba błędów strony OPT: " + PF_OPT);

        PF_LRU = PF_LRU / listsNumb;
        System.out.println("Liczba błędów strony LRU: " + PF_LRU);

        PF_AproksLRU = PF_AproksLRU / listsNumb;
        System.out.println("Liczba błędów strony AproksLRU: " + PF_AproksLRU);

        PF_RAND = PF_RAND / listsNumb;
        System.out.println("Liczba błędów strony RAND: " + PF_RAND);
    }
}