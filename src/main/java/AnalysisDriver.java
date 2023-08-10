import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class AnalysisDriver {

    /**
     * read data to hashmap from the csv file
     * @param map the data struct
     * @param filePath file location
     */
    public int readFileToMap(HashMap<String, Data> map, String filePath){
//        String csvFile = "src/main/resources/data_7.csv";
        String line = "";
        String delimiter = ",";
        int total=0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(delimiter);
                // process the every line
                if (!map.containsKey(fields[0])){
                    Data newValue = new Data();
                    map.put(fields[0], newValue);
                }
                map.get(fields[0]).addField(fields[1], fields[2]);
                total++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return total;
    }

    /**
     * analysis processor
     * @param map dataset
     * @param total Set activity limits
     */
    public void analyse(HashMap<String, Data> map, int total){
        ArrayList<String> addList = new ArrayList<>();
        ArrayList<String> deleteList = new ArrayList<>();
        ArrayList<String> activeList = new ArrayList<>();
        ArrayList<String> badList = new ArrayList<>();

        for (String key : map.keySet()) {
            Data data = map.get(key);
            data.setScore();
            DecimalFormat df = new DecimalFormat("#.###");
            if (key.length() < 4)
                key = key + "  ";
            String scoreStr = df.format(data.getScore());
            if (scoreStr.length() < 5)
                scoreStr = scoreStr + "0";
            String cur = key + "   \t," + scoreStr + ", \t\t" + data;

            if (data.getScore() > 0.70 && data.getTotal() > 5) {
                addList.add(cur);
            } else if (data.getScore() < 0.3) {
                deleteList.add(cur);
            }
            if (((double)data.getTotal())/total >= 0.03) {
                activeList.add(cur);
            }
            if(data.getDeleteSome() != 0 || data.getSell() != 0)
                badList.add(cur);
        }

        addList.sort((o1, o2) -> {
            double o1_score = Double.parseDouble(o1.split(",")[1]);
            double o2_score = Double.parseDouble(o2.split(",")[1]);
            return o2_score - o1_score > 0 ? 1 : o2_score-o1_score==0 ? 0 : -1;
        });
        deleteList.sort((o1, o2) -> {
            double o1_score = Double.parseDouble(o1.split(",")[1]);
            double o2_score = Double.parseDouble(o2.split(",")[1]);
            return o1_score - o2_score > 0 ? 1 : o2_score-o1_score==0 ? 0 : -1;
        });
        activeList.sort((o1, o2) -> {
            double o1_score = Double.parseDouble(o1.split(",")[1]);
            double o2_score = Double.parseDouble(o2.split(",")[1]);
            return o2_score - o1_score > 0 ? 1 : o2_score-o1_score==0 ? 0 : -1;
        });

        System.out.println("-------ANALYSIS RESULT------");
        System.out.println("----------addList----------");
        for (String add : addList){
            System.out.println(add);
        }
        System.out.println("----------deleteList----------");
        for (String delete : deleteList){
            System.out.println(delete);
        }
        System.out.println("----------activeList----------");
        for (String ac : activeList){
            System.out.println(ac);
        }
        System.out.println("----------badList----------");
        for (String bd : badList){
            System.out.println(bd);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        AnalysisDriver driver = new AnalysisDriver();

        //Todo read file
        HashMap<String, Data> totalMap = new HashMap<>();
        HashMap<String, Data> weekMap = new HashMap<>();
        int total = driver.readFileToMap(totalMap, "D:\\软件所\\学习笔记\\python\\reptile_test\\data.csv");
        int subtotal = driver.readFileToMap(weekMap, "D:\\软件所\\学习笔记\\python\\reptile_test\\data_16.csv");

        //Todo analyse the datamap
        driver.analyse(totalMap, total);
        driver.analyse(weekMap, subtotal);
    }
}
