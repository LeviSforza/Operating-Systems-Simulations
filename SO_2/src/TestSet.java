import java.util.ArrayList;

public class TestSet {

    ArrayList<ListOfProcesses> set;
    ListOfProcesses badCase;
    private int processesQuantity, numberOfRT, maxArrivalTime, maxRealTime, listsNumber;
    Disc d;

    public TestSet(int listsNumber, int processesQuantity, int numberOfRT, int maxArrivalTime, int maxRealTime, Disc d) {
        this.listsNumber = listsNumber;
        this.processesQuantity = processesQuantity;
        this.numberOfRT = numberOfRT;
        this.maxArrivalTime = maxArrivalTime;
        this.maxRealTime = maxRealTime;
        this.d = d;
        set = new ArrayList<>();
        badCase = new ListOfProcesses();
    }

    public ArrayList<ListOfProcesses> generteSet() {
        ListMaker maker = new ListMaker(processesQuantity, numberOfRT, maxArrivalTime, maxRealTime, d);
        for(int i=0; i< listsNumber; i++)
            set.add(maker.generate());
        return set;
    }

    public void resetSet() {
        for(int i=0; i< listsNumber; i++)
            set.get(i).resetWaitTime();
    }

    public void runAll() {
        set = new ArrayList<>();
        generteSet();
        double AWT_FCFS = 0;
        double LWT_FCFS = 0;
        double AWT_SSTF = 0;
        double LWT_SSTF = 0;
        double AWT_SCAN = 0;
        double LWT_SCAN = 0;
        double AWT_C_SCAN = 0;
        double LWT_C_SCAN = 0;
        double AWT_EDF = 0;
        double LWT_EDF = 0;
        double AWT_FD_SCAN = 0;
        double LWT_FD_SCAN = 0;
        double UndoneN_FD_SCAN =0;
        double UndoneN_EDF = 0;
        double CD_FCFS = 0;
        double CD_SSTF = 0;
        double CD_SCAN = 0;
        double CD_C_SCAN = 0;
        double CD_EDF = 0;
        double CD_FD_SCAN = 0;

        for(int i=0; i<listsNumber; i++){
           // System.out.println("\n\nFCFS:");
            FCFS f1 = new FCFS(set.get(i), d);
            f1.run();
            AWT_FCFS += f1.getAverageWaitingTime();
            LWT_FCFS += f1.getLongestWaitTime();
            CD_FCFS += f1.getCovDistance();
            resetSet();

           // System.out.println("\n\nSSTF:");
            SSTF s1 = new SSTF(set.get(i), d);
            s1.run();
            AWT_SSTF += s1.getAverageWaitingTime();
            LWT_SSTF += s1.getLongestWaitTime();
            CD_SSTF += s1.getCovDistance();
            resetSet();

           // System.out.println("\n\nC_SCAN:");
            C_SCAN c1 = new C_SCAN(set.get(i), d);
            c1.run();
            AWT_C_SCAN += c1.getAverageWaitingTime();
            LWT_C_SCAN += c1.getLongestWaitTime();
            CD_C_SCAN += c1.getCovDistance();
            resetSet();

           // System.out.println("\n\nSCAN:");
            SCAN sc1 = new SCAN(set.get(i), d);
            sc1.run();
            AWT_SCAN += sc1.getAverageWaitingTime();
            LWT_SCAN += sc1.getLongestWaitTime();
            CD_SCAN += sc1.getCovDistance();
            resetSet();

           // System.out.println("\n\nEDF:");
            EDF e1 = new EDF(set.get(i), d);
            e1.run();
            AWT_EDF += e1.getAverageWaitingTime();
            LWT_EDF += e1.getLongestWaitTime();
            CD_EDF += e1.getCovDistance();
            UndoneN_EDF += e1.getNumberOfUndone();
            resetSet();

           // System.out.println("\n\nFD_SCAN:");
            FD_SCAN d1 = new FD_SCAN(set.get(i), d);
            d1.run();
            AWT_FD_SCAN += d1.getAverageWaitingTime();
            LWT_FD_SCAN += d1.getLongestWaitTime();
            CD_FD_SCAN += d1.getCovDistance();
            UndoneN_FD_SCAN += d1.getNumberOfUndone();
            resetSet();

        }

        AWT_FCFS = AWT_FCFS/listsNumber;
        LWT_FCFS = LWT_FCFS/listsNumber;
        CD_FCFS = CD_FCFS/listsNumber;
        System.out.println("\nFCFS: ");
        System.out.println("Average waiting time for FCFS: " + AWT_FCFS);
        System.out.println("Average longest waiting time for FCFS: " + LWT_FCFS);
        System.out.println("Average covered distance for FCFS: " + CD_FCFS);
        System.out.println("\nFCFS to najgorzej funkcjonuj??ca strategia. Wymusza ona wyj??tkowo du???? liczb?? \nprze????cze?? g??owicy oraz d??ugie czasy oczekiwania dla poszczeg??lnych ????da??," +
                " \nzw??aszcza gdy kolejne ????dania pojawiaj?? si?? po przeciwnych stronach dysku, \nco zmusza g??owic?? do biegania w t?? i z powrotem. \nFCFS zwi??ksza swoj?? " +
                "wydajno???? gdy kolejne ????dania pojawiaj?? si?? blisko siebie. \nJest to ma??o op??acalna metoda.");
        AWT_SSTF = AWT_SSTF/listsNumber;
        LWT_SSTF = LWT_SSTF/listsNumber;
        CD_SSTF = CD_SSTF/listsNumber;
        System.out.println("\nSSTF: ");
        System.out.println("Average waiting time for SSTF: " + AWT_SSTF);
        System.out.println("Average longest waiting time for SSTF: " + LWT_SSTF);
        System.out.println("Average covered distance for SSTF: " + CD_SSTF);
        System.out.println("\nSSTF dzia??a znacz??co lepiej od prymitywnego FCFS. Problemy mog?? pojawi?? si?? \nw przypadku gdy jedno z serii ????da?? pojawi si?? znacz??co dalej ni?? pozosta??e, mo??e zosta?? " +
                "\nwtedy zag??odzone, poniewa?? algorytm skupi si?? na bliskich ????daniach. SSTF jest bardzo wydajny \nje??li chodzi o ograniczanie potrzebnych do wykonania prze????cze?? g??owicy.");
        AWT_SCAN = AWT_SCAN/listsNumber;
        LWT_SCAN = LWT_SCAN/listsNumber;
        CD_SCAN = CD_SCAN/listsNumber;
        System.out.println("\nSCAN: ");
        System.out.println("Average waiting time for SCAN: " + AWT_SCAN);
        System.out.println("Average longest waiting time for SCAN: " + LWT_SCAN);
        System.out.println("Average covered distance for SCAN: " + CD_SCAN);
        System.out.println("\nSCAN wypada niewiele gorzej od C_SCAN-u. Zwi??ksza on swoj?? wydajno???? gdy wiele ????da?? \npojawia si?? blisko ??rodka dysku, a znacz??co zminiejsza, " +
                "gdy ????dania pojawiaj?? si?? blisko brzeg??w.");
        AWT_C_SCAN = AWT_C_SCAN/listsNumber;
        LWT_C_SCAN = LWT_C_SCAN/listsNumber;
        CD_C_SCAN = CD_C_SCAN/listsNumber;
        System.out.println("\nC_SCAN: ");
        System.out.println("Average waiting time for C_SCAN: " + AWT_C_SCAN);
        System.out.println("Average longest waiting time for C_SCAN: " + LWT_C_SCAN);
        System.out.println("Average covered distance for C_SCAN: " + CD_C_SCAN);
        System.out.println("\nC_SCAN jest najbardziej wydajnym i r??wnym w dzia??aniu algorytmem. \nJego ??redni czas oczekiwania jest najni??szy z wszytkich tu przedstawionych. Nawet je??li " +
                "nowe ????danie \npojawi si?? tu?? za g??owic??, musi czeka?? jedynie okres przej??cia g??owicy po jednej d??ugo??ci dysku. \nDodatkowo ??rednia liczba prze????cze?? g??owicy, czyli " +
                "przebyty dystans jest niewiele wi??ksza od SSTF.");
        AWT_EDF = AWT_EDF/listsNumber;
        LWT_EDF = LWT_EDF/listsNumber;
        CD_EDF = CD_EDF/listsNumber;
        UndoneN_EDF = UndoneN_EDF/listsNumber;
        System.out.println("\nEDF: ");
        System.out.println("Average waiting time for EDF: " + AWT_EDF);
        System.out.println("Average longest waiting time for EDF: " + LWT_EDF);
        System.out.println("Average covered distance for EDF: " + CD_EDF);
        System.out.println("Average number of undone requests for EDF: " + UndoneN_EDF);
        System.out.println("\nEDF jest ma??o skutecznym algorytmem. Zwi??kszona liczba ????da?? real-time o kr??tkim deadlinie \n" +
                "pogarsza jego skuteczno????, poniewa?? g??owica zaczyna " +
                "biec do ????da??, \ndo kt??rych nie jest w stanie zd????y??, nie wykonuj??cych innych le????cych po drodze. " +
                "\nEDF osi??ga te?? stosunkowo z??e wyniki je??lli chodzi " +
                "\no ??redni czas oczekiwania.");
        AWT_FD_SCAN = AWT_FD_SCAN/listsNumber;
        LWT_FD_SCAN = LWT_FD_SCAN/listsNumber;
        CD_FD_SCAN = CD_FD_SCAN/listsNumber;
        UndoneN_FD_SCAN = UndoneN_FD_SCAN/listsNumber;
        System.out.println("\nFD_SCAN: ");
        System.out.println("Average waiting time for FD_SCAN: " + AWT_FD_SCAN);
        System.out.println("Average longest waiting time for FD-SCAN: " + LWT_FD_SCAN);
        System.out.println("Average covered distance for FD_SCAN: " + CD_FD_SCAN);
        System.out.println("Average number of undone requests for FD_SCAN: " + UndoneN_FD_SCAN);
        System.out.println("\nFD_SCAN jest znacznie wydajniejszy ni?? EDF. Dzi??ki u??yciu SCAN \njako bazy, dobrze razi sobie " +
                "nawet z du???? liczb?? zg??osze?? real-time. \nDodatkowo osi??ga znacznie lepsze ni?? EDF wyniki," +
                " je??li chodzi o liczb?? niewykonanych ????da??, \nponiewa?? nie reaguje w przypadkach, w kt??rych nie m??g??by zd????y??.");
    }

    public void runBadCaseFCFS() {
        badCase.add(new Process(1, 1, d.getLength(), false, 0));
        badCase.add(new Process(2, 20, 1, false, 0));
        badCase.add(new Process(3, 30, d.getLength()-5, false, 0));
        badCase.add(new Process(4, 150, 4, false, 0));
        badCase.add(new Process(5, 200, d.getLength()-3, false, 0));
        badCase.add(new Process(6, 250, 70, false, 0));
        badCase.add(new Process(7, 251, 40, false, 0));
        //badCase.printAll();
        FCFS f = new FCFS(badCase, d);
        f.run();
        System.out.println("FCFS: \n");
        f.printDoneProcesses();
        System.out.println("\nAverage waiting time for FCFS: " + f.getAverageWaitingTime());
        System.out.println("Covered distance for FCFS: " + f.getCovDistance());
        System.out.println("\nProblemem jest tutaj fakt, ??e procesy pojawiaj?? si?? po przeciwnych stronach cylindra. \n" +
                "FCFS musi biega?? z jednego ko??ca dysku na drugi, nie wykonuj??c mijanych po drodze ????da??, \nkt??re pojawi??y si?? p????niej.\n");
        badCase.clear();
    }

    public void runBadCaseSSTF() {
        badCase.add(new Process(1, 1, 4, false, 0));
        badCase.add(new Process(2, 2, 1, false, 0));
        badCase.add(new Process(3, 3, d.getLength()-5, false, 0));
        badCase.add(new Process(4, 4, 6, false, 0));
        badCase.add(new Process(5, 8, 3, false, 0));
        badCase.add(new Process(6, 9, 8, false, 0));
        badCase.add(new Process(7, 10, 9, false, 0));
        //badCase.printAll();
        SSTF f = new SSTF(badCase, d);
        f.run();
        System.out.println("SSTF: \n");
        f.printDoneProcesses();
        System.out.println("\nAverage waiting time for SSTF: " + f.getAverageWaitingTime());
        System.out.println("Covered distance for SSTF: " + f.getCovDistance());
        System.out.println("\nProblemem jest tutaj ????danie kt??re pojawia si?? jako trzecie (n=3). Musi ono czeka?? \n" +
                "bardzo d??ugo na swoj?? kolej poniewa?? jest na znacznie dalszym ni?? reszta cylindrze.\n");
        badCase.clear();
    }

    public void runBadCaseSCAN() {
        badCase.add(new Process(1, 1, 4, false, 0));
        badCase.add(new Process(2, 3, 1, false, 0));
        badCase.add(new Process(3, 3, d.getLength()-5, false, 0));
        badCase.add(new Process(4, d.getLength()+3, d.getLength(), false, 0));
        badCase.add(new Process(5, 50, 15, false, 0));
        badCase.add(new Process(6, 20, 25, false, 0));
        badCase.add(new Process(7, 10, 9, false, 0));
        //badCase.printAll();
        SCAN f = new SCAN(badCase, d);
        f.run();
        System.out.println("SCAN: \n");
        f.printDoneProcesses();
        System.out.println("\nAverage waiting time for SCAN: " + f.getAverageWaitingTime());
        System.out.println("Covered distance for SCAN: " + f.getCovDistance());
        System.out.println("\nProblemem s?? tutaj ????dania, kt??re pojawi??y si?? tu?? za g??owic??, \n" +
                "musz?? one czeka?? prawie dwie d??ugo??ci dysku na swoj?? kolej.\n");
        badCase.clear();
    }

    public void runBadCaseC_SCAN() {
        badCase.add(new Process(1, 1, 4, false, 0));
        badCase.add(new Process(2, 3, 1, false, 0));
        badCase.add(new Process(3, 3, d.getLength()-3, false, 0));
        badCase.add(new Process(4, 15, 30, false, 0));
        badCase.add(new Process(5, d.getLength()+5, 2, false, 0));
        badCase.add(new Process(6, 20, 25, false, 0));
        badCase.add(new Process(7, 10, 9, false, 0));
        //badCase.printAll();
        C_SCAN f = new C_SCAN(badCase, d);
        f.run();
        System.out.println("C_SCAN: \n");
        f.printDoneProcesses();
        System.out.println("\nAverage waiting time for C_SCAN: " + f.getAverageWaitingTime());
        System.out.println("Covered distance for C_SCAN: " + f.getCovDistance());
        System.out.println("\nPodobna sytuacja jak przy SCAN-ie. Najd??u??szy czas oczekiwania otrzymujemy, \n" +
                "gdy wiele ????da?? pojawia si?? jak tutaj tu?? za g??owic??.");
        badCase.clear();
    }

    public void runBadCaseEDF() {
        badCase.add(new Process(1, 1, d.getLength()-4, false, 0));
        badCase.add(new Process(2, 3, 20, true, 11));
        badCase.add(new Process(3, 3, 7, false, 0));
        badCase.add(new Process(4, 10, d.getLength(), false, 0));
        badCase.add(new Process(5, 13, 30, true, 15));
        badCase.add(new Process(6, 20, 5, true, 10));
        badCase.add(new Process(7, 12, 9, false, 0));
        //badCase.printAll();
        EDF f = new EDF(badCase, d);
        f.run();
        System.out.println("EDF: \n");
        f.printDoneProcesses();
        f.printUndoneProcesses();
        System.out.println("\nAverage waiting time for EDF: " + f.getAverageWaitingTime());
        System.out.println("Covered distance for EDF: " + f.getCovDistance());
        System.out.println("\nProblemy przy EDF pojawiaj?? si??, gdy wygenerowane zostaje wiele ????da?? \n" +
                "real-time, do kt??rych g??owica nie jest w stanie zd????y??. Zmusza to pozosta??e to bezcelowego czekania.\n");
        badCase.clear();
    }

    public void runBadCaseFD_SCAN() {
        badCase.add(new Process(1, 1, 20, true, 22));
        badCase.add(new Process(2, 5, 60, false, 0));
        badCase.add(new Process(3, 3, 15, true, 30));
        badCase.add(new Process(4, 24, 71, false, 0));
        badCase.add(new Process(5, 20, 13, true, 15));
        badCase.add(new Process(6, 4, 4, true, 11));
        badCase.add(new Process(7, 76, 77, false, 0));
        //badCase.printAll();
        FD_SCAN f = new FD_SCAN(badCase, d);
        f.run();
        System.out.println("FD_SCAN: \n");
        f.printDoneProcesses();
        f.printUndoneProcesses();
        System.out.println("\nAverage waiting time for FD_SCAN: " + f.getAverageWaitingTime());
        System.out.println("Covered distance for FD_SCAN: " + f.getCovDistance());
        System.out.println("\nDla FD_SCAN-u problemem mo??e by?? sytuacja, gdy w jednej cz????ci dysku pojawi si?? \n" +
                "wiele ????da?? real-time, na kt??rych skupi si?? algorytm, podczas gdy czekaj??ce w drugiej cz??sci \n" +
                "zwyk??e ????dania musz?? d??ugo czeka??.");
        badCase.clear();
    }


}
