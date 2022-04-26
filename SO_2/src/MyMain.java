
public class MyMain {
    public static void main(String[] args) {

        Disc d = new Disc(100);
        TestSet test = new TestSet(10, 200, 100, 5000, 70, d);
        test.runAll();
        System.out.println("\n\n");
        test.runBadCaseFCFS();
        test.runBadCaseSSTF();
        test.runBadCaseSCAN();
        test.runBadCaseC_SCAN();
        test.runBadCaseEDF();
        test.runBadCaseFD_SCAN();

    }
}
