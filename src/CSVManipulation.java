import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVManipulation {

    public CSVManipulation() {
    }

    // CSV file encoding MUST be UTF-8 ***without BOM***, otherwise errors will happen
    public ArrayList<ArrayList<Integer>> openDeckFromCSVFile(String path) { //For now, it will be static, so I can try to use it directly on the Deck Object/Class

        String line;
        ArrayList<String> readDeckFromFileString_CardRank = new ArrayList<>();
        ArrayList<String> readDeckFromFileString_CardSuit = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                readDeckFromFileString_CardRank.add(values[0]);
                readDeckFromFileString_CardSuit.add(values[1]);
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }

        // Calls the function stringArrListToIntArrList to convert the String ArrayList to an Integer ArrayList and saves into an ArrayList
        ArrayList<Integer> readDeckFromFile_CardRank = new ArrayList<>(stringArrListToIntArrList(readDeckFromFileString_CardRank));
        ArrayList<Integer> readDeckFromFile_CardSuit = new ArrayList<>(stringArrListToIntArrList(readDeckFromFileString_CardSuit));

        ArrayList<ArrayList<Integer>> toExport = new ArrayList<>();
        toExport.add(readDeckFromFile_CardRank);
        toExport.add(readDeckFromFile_CardSuit);

        return toExport;

    }


    //Converts ArrayList<String> to ArrayList<Integer> and returns it
    private ArrayList<Integer> stringArrListToIntArrList(ArrayList<String> stringArrayList){

        ArrayList<Integer> tempList = new ArrayList<>(stringArrayList.size());

        for (String myInt : stringArrayList) {
            tempList.add(Integer.valueOf(myInt));
        }
        return tempList;
    }


}
