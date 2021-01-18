/**
 * Print out total number of babies born, as well as for each gender, in a given CSV file of baby name data.
 * 
 * @author Duke Software Team 
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class BabyBirths {
    /*public void printNames () {
        FileResource fr = new FileResource();
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            if (numBorn <= 100) {
                System.out.println("Name " + rec.get(0) +
                           " Gender " + rec.get(1) +
                           " Num Born " + rec.get(2));
            }
        }
    } */

    public void totalBirths (FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        int gNames = 0;
        int bNames = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
                ++bNames;
            }
            else {
                totalGirls += numBorn;
                ++gNames;
            }
        }
        System.out.println("total births = " + totalBirths);
        System.out.println("No. of F births = " + totalGirls);
        System.out.println("No. of F names = " + gNames);
        System.out.println("No. of M births = " + totalBoys);
        System.out.println("No. of M names = " + bNames);
    }
    
    public int getRank(int year, String name, String g){
        FileResource fr = new FileResource("./by_year/yob"+year+".csv");
        int rank = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(g)){
                ++rank;
                if (rec.get(0).equals(name))
                    return rank;
            }
        }
        return -1;
    }
    
    public String getName(int year, int rank, String g){
        FileResource fr = new FileResource("./by_year/yob"+year+".csv");
        int rows = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(g)){
                ++rows;
                if (rows == rank)
                    return rec.get(0);
            }
        }
        return "NO NAME";
    }
    
    public void whatIsNameInYear(String name, int year, int nYear, String g){
        int rank = getRank(year,name,g);
        String newName = getName(nYear,rank,g);
        System.out.println(name + " born in "+year+" would be "+newName+" if she was born in "+nYear+".");
    }
    
    public int yearOfHighestRank(String name, String g){
         int hrank = 99999999;
         int yRank = -1;
         DirectoryResource dr = new DirectoryResource();
         for (File f : dr.selectedFiles()){
             String fName = f.getName();
             String year = fName.substring(3,7);
             int yr = Integer.parseInt(year);
             int rank = getRank(yr,name,g);
             if ((rank < hrank) && (rank!= -1)){
                hrank = rank;
                yRank = yr;
            }
         }
         return yRank;
    }
    
    public double getAverageRank(String name, String g){
         double avg = -1;
         double c = 0;
         double sum = 0;
         DirectoryResource dr = new DirectoryResource();
         for (File f : dr.selectedFiles()){
             ++c;
             String fName = f.getName();
             String year = fName.substring(3,7);
             int yr = Integer.parseInt(year);
             double rank = getRank(yr,name,g);
             sum += rank;
             avg = sum/c;
             
         }
      return avg;
    }
    
    public int getTotalBirthsRankedHigher(String name, String g, int year){
        FileResource fr = new FileResource("./by_year/yob"+year+".csv");
        int totalB = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(g)){
                if (rec.get(0).equals(name))
                    return totalB;
                totalB += Integer.parseInt(rec.get(2));
            }
        }
        return -1;
    }
    
    public void test () {
        FileResource fr = new FileResource();
        totalBirths(fr);
        //System.out.println("Rank: " + getRank(2012,"Jacob","M"));
        //whatIsNameInYear("Isabella", 2012, 2014, "F");
        
    }
   
}
