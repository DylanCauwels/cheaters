package cheaters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.SortedSet;
import java.util.TreeSet;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class cheaterController {

    public static void main(String[] args) {
        File folder = new File(args[0]);
        int segmentSize = Integer.parseInt(args[1]);
        int plagiagrismThreshold = Integer.parseInt(args[2]);

        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;

        PrintWriter writer = null;
        try{
            writer = new PrintWriter(new FileOutputStream("output.txt", false));
        }catch(Exception e){
            //die
        }

        cheater cheat = new cheater(listOfFiles.length, listOfFiles, segmentSize);
        HashMap<Integer, List<String>> map = cheat.getDatabase();
        SortedSet<Integer> keys = new TreeSet<>(map.keySet()).descendingSet();
        for (int key : keys) {
            if(key >= plagiagrismThreshold){
                writer.println(""+ key + ": "+ map.get(key));
            }else{
                break;
            }
        }
        writer.close();
    }
}
