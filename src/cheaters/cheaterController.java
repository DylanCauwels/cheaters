package cheaters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class cheaterController {

    public static void main(String[] args) {
        File folder = new File("sm_doc_set/");
        File[] listOfFiles = folder.listFiles();

        assert listOfFiles != null;
//        for (File listOfFile : listOfFiles) {
//            if (listOfFile.isFile()) {
//                System.out.println("File " + listOfFile.getName());
//            } else if (listOfFile.isDirectory()) {
//                System.out.println("Directory " + listOfFile.getName());
//            }
//        }

        Scanner infile = null;
        try {
            File a = new File("sm_doc_set/abf0704.txt");
            infile = new Scanner(a);
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary File not Found!");
            e.printStackTrace();
            System.exit(1);
        }
        while(infile.hasNext()) {
            System.out.println(infile.next());
        }

//        cheater cheat = new cheater(listOfFiles.length, listOfFiles);
//        int[][] array = cheat.getDatabase();
//        for(int i = 0; i < listOfFiles.length; i++) {
//            for(int j = 0; j < listOfFiles.length; j++) {
//                System.out.print(array[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
    }
}
