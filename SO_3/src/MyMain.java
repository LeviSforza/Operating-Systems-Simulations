public class MyMain {
    public static void main(String[] args) {

        SystemGenerator maker = new SystemGenerator(10, 200, 20);
        ProcessesList list = maker.generate();

        ProcessesList l = new ProcessesList();
        for(int i=0; i<list.size(); i++) {
            ReferencesList r = new ReferencesList();
            for(int j=0; j<list.get(i).size(); j++) {
                r.add(list.get(i).get(j));
            }
            r.setRange(list.get(i).getRange());
            l.add(r);
        }

        System.out.println("\nPrzydział równy:");
        BaseAllocation pRowny = new EqualAllocation(list, 50);
        pRowny.run();

        System.out.println("\nPrzydział proporcjonalny:");
        BaseAllocation pProp = new ProportionalAllocation(list, 50);
        pProp.run();

        System.out.println("\nModel strefowy:");
        BaseAllocation model = new ZoneModel(list, 50, 25);
        model.run();

        System.out.println("\nSterowanie częstością błędów strony:");
        BaseAllocation ster = new ErrorFrequencyController(l, 50, 0.2, 0.8, 20);
        ster.run();
    }
}
