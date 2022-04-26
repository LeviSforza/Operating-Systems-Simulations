import java.util.Random;

public class ListMaker {

    private int referencesNumber, range;
    Random rnd = new Random();

    public ListMaker(int referencesNumber, int range){
       this.referencesNumber = referencesNumber;
       this.range = range;
    }

    public ReferencesList generate(){
        ReferencesList list = new ReferencesList();
        int idNumb;
        double randomNumber;
        double probability = 0.3;

        for(int i = 0; i < referencesNumber; i++) {
            randomNumber = rnd.nextDouble();
            if(randomNumber < probability) {
                probability = 0.2;
                int start = rnd.nextInt(range/3+1);
                for(int j = 0; j< range/3; j++){
                    idNumb = rnd.nextInt(range/3+1) + start;
                    list.add(new Page(idNumb));
                }
                i += range/3;
            }
            else {
                idNumb = rnd.nextInt(range);
                probability += 0.005;
                list.add(new Page(idNumb));
            }
        }
        return list;
    }



}
