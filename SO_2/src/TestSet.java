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
        System.out.println("\nFCFS to najgorzej funkcjonująca strategia. Wymusza ona wyjątkowo dużą liczbę \nprzełączeń głowicy oraz długie czasy oczekiwania dla poszczególnych żądań," +
                " \nzwłaszcza gdy kolejne żądania pojawiają się po przeciwnych stronach dysku, \nco zmusza głowicę do biegania w tą i z powrotem. \nFCFS zwiększa swoją " +
                "wydajność gdy kolejne żądania pojawiają się blisko siebie. \nJest to mało opłacalna metoda.");
        AWT_SSTF = AWT_SSTF/listsNumber;
        LWT_SSTF = LWT_SSTF/listsNumber;
        CD_SSTF = CD_SSTF/listsNumber;
        System.out.println("\nSSTF: ");
        System.out.println("Average waiting time for SSTF: " + AWT_SSTF);
        System.out.println("Average longest waiting time for SSTF: " + LWT_SSTF);
        System.out.println("Average covered distance for SSTF: " + CD_SSTF);
        System.out.println("\nSSTF działa znacząco lepiej od prymitywnego FCFS. Problemy mogą pojawić się \nw przypadku gdy jedno z serii żądań pojawi się znacząco dalej niż pozostałe, może zostać " +
                "\nwtedy zagłodzone, ponieważ algorytm skupi się na bliskich żądaniach. SSTF jest bardzo wydajny \njeśli chodzi o ograniczanie potrzebnych do wykonania przełączeń głowicy.");
        AWT_SCAN = AWT_SCAN/listsNumber;
        LWT_SCAN = LWT_SCAN/listsNumber;
        CD_SCAN = CD_SCAN/listsNumber;
        System.out.println("\nSCAN: ");
        System.out.println("Average waiting time for SCAN: " + AWT_SCAN);
        System.out.println("Average longest waiting time for SCAN: " + LWT_SCAN);
        System.out.println("Average covered distance for SCAN: " + CD_SCAN);
        System.out.println("\nSCAN wypada niewiele gorzej od C_SCAN-u. Zwiększa on swoją wydajność gdy wiele żądań \npojawia się blisko środka dysku, a znacząco zminiejsza, " +
                "gdy żądania pojawiają się blisko brzegów.");
        AWT_C_SCAN = AWT_C_SCAN/listsNumber;
        LWT_C_SCAN = LWT_C_SCAN/listsNumber;
        CD_C_SCAN = CD_C_SCAN/listsNumber;
        System.out.println("\nC_SCAN: ");
        System.out.println("Average waiting time for C_SCAN: " + AWT_C_SCAN);
        System.out.println("Average longest waiting time for C_SCAN: " + LWT_C_SCAN);
        System.out.println("Average covered distance for C_SCAN: " + CD_C_SCAN);
        System.out.println("\nC_SCAN jest najbardziej wydajnym i równym w działaniu algorytmem. \nJego średni czas oczekiwania jest najniższy z wszytkich tu przedstawionych. Nawet jeśli " +
                "nowe żądanie \npojawi się tuż za głowicą, musi czekać jedynie okres przejścia głowicy po jednej długości dysku. \nDodatkowo średnia liczba przełączeń głowicy, czyli " +
                "przebyty dystans jest niewiele większa od SSTF.");
        AWT_EDF = AWT_EDF/listsNumber;
        LWT_EDF = LWT_EDF/listsNumber;
        CD_EDF = CD_EDF/listsNumber;
        UndoneN_EDF = UndoneN_EDF/listsNumber;
        System.out.println("\nEDF: ");
        System.out.println("Average waiting time for EDF: " + AWT_EDF);
        System.out.println("Average longest waiting time for EDF: " + LWT_EDF);
        System.out.println("Average covered distance for EDF: " + CD_EDF);
        System.out.println("Average number of undone requests for EDF: " + UndoneN_EDF);
        System.out.println("\nEDF jest mało skutecznym algorytmem. Zwiększona liczba żądań real-time o krótkim deadlinie \n" +
                "pogarsza jego skuteczność, ponieważ głowica zaczyna " +
                "biec do żądań, \ndo których nie jest w stanie zdążyć, nie wykonujących innych leżących po drodze. " +
                "\nEDF osiąga też stosunkowo złe wyniki jeślli chodzi " +
                "\no średni czas oczekiwania.");
        AWT_FD_SCAN = AWT_FD_SCAN/listsNumber;
        LWT_FD_SCAN = LWT_FD_SCAN/listsNumber;
        CD_FD_SCAN = CD_FD_SCAN/listsNumber;
        UndoneN_FD_SCAN = UndoneN_FD_SCAN/listsNumber;
        System.out.println("\nFD_SCAN: ");
        System.out.println("Average waiting time for FD_SCAN: " + AWT_FD_SCAN);
        System.out.println("Average longest waiting time for FD-SCAN: " + LWT_FD_SCAN);
        System.out.println("Average covered distance for FD_SCAN: " + CD_FD_SCAN);
        System.out.println("Average number of undone requests for FD_SCAN: " + UndoneN_FD_SCAN);
        System.out.println("\nFD_SCAN jest znacznie wydajniejszy niż EDF. Dzięki użyciu SCAN \njako bazy, dobrze razi sobie " +
                "nawet z dużą liczbą zgłoszeń real-time. \nDodatkowo osiąga znacznie lepsze niż EDF wyniki," +
                " jeśli chodzi o liczbę niewykonanych żądań, \nponieważ nie reaguje w przypadkach, w których nie mógłby zdążyć.");
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
        System.out.println("\nProblemem jest tutaj fakt, że procesy pojawiają się po przeciwnych stronach cylindra. \n" +
                "FCFS musi biegać z jednego końca dysku na drugi, nie wykonując mijanych po drodze żądań, \nktóre pojawiły się później.\n");
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
        System.out.println("\nProblemem jest tutaj żądanie które pojawia się jako trzecie (n=3). Musi ono czekać \n" +
                "bardzo długo na swoją kolej ponieważ jest na znacznie dalszym niż reszta cylindrze.\n");
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
        System.out.println("\nProblemem są tutaj żądania, które pojawiły się tuż za głowicą, \n" +
                "muszą one czekać prawie dwie długości dysku na swoją kolej.\n");
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
        System.out.println("\nPodobna sytuacja jak przy SCAN-ie. Najdłuższy czas oczekiwania otrzymujemy, \n" +
                "gdy wiele żądań pojawia się jak tutaj tuż za głowicą.");
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
        System.out.println("\nProblemy przy EDF pojawiają się, gdy wygenerowane zostaje wiele żądań \n" +
                "real-time, do których głowica nie jest w stanie zdążyć. Zmusza to pozostałe to bezcelowego czekania.\n");
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
        System.out.println("\nDla FD_SCAN-u problemem może być sytuacja, gdy w jednej części dysku pojawi się \n" +
                "wiele żądań real-time, na których skupi się algorytm, podczas gdy czekające w drugiej częsci \n" +
                "zwykłe żądania muszą długo czekać.");
        badCase.clear();
    }


}
