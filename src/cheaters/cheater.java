package cheaters;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class cheater {
    private int[][] database;
    private HashMap<Integer, List<String>> hits;
    static private HashMap<String, HashSet<String>> fileWords;

    /**
     * cheater runs through all the documents in the given fileset, checks them against each other and puts the amount
     * of concurrencies for each pairing into the database array which is sized at the start of the method.
     * @param documents the number of documents in the array
     * @param docs a File array of all the filenames of the documents located in the package
     */
    public cheater(int documents, File[] docs, int segmentSize) {
//        database = new int[documents][documents];
        hits = new HashMap<Integer, List<String>>();
        fileWords = new HashMap<String, HashSet<String>>();
        for(int i = 0; i < docs.length; i++) {
            for(int j = i + 1; j < docs.length; j++) {
//                database[i][j] = comparator.compare(docs[i], docs[j]);
                FileComparator comparator = new FileComparator(segmentSize, docs[i], docs[j]);
                int conflicts = comparator.compare();
                if (hits.containsKey(conflicts)){
                    hits.get(conflicts).add(""+i+" "+j+" ");
                }else{
                    ArrayList list = new ArrayList();
                    list.add(""+docs[i].getName()+","+docs[j].getName()+" ");
                    hits.put(conflicts, list);
                }

            }
        }
    }

    /**
     *
     * @return the 2D matrix of registered 6-word similarities
     */
    public HashMap<Integer, List<String>> getDatabase() {
        return hits;
    }


    class FileComparator {
        //TODO multiple compare calls will require that similarities be reallocated every run to prevent saturation from other file calls

        HashSet<String> fileA_words = null;
        HashSet<String> fileB_words = null;
        int segmentSize;

        public FileComparator(int N, File fileA, File fileB){
            segmentSize = N;

            if(fileWords.containsKey(fileA.getName())){
                fileA_words = fileWords.get(fileA.getName());
            }else{
                //Construct hash set for this file
                fileA_words = parseFile(fileA);
                fileWords.put(fileA.getName(), fileA_words);
            }
            if(fileWords.containsKey(fileB.getName())){
                fileB_words = fileWords.get(fileB.getName());
            }else{
                //Construct hash set for this file
                fileB_words = parseFile(fileB);
                fileWords.put(fileB.getName(), fileB_words);
            }
        }

        /**
         * takes two files and runs them. The first run of parseFile puts the keys and values into the similarities
         * HashMap, and the second returns the collisions that occurred in the hashmap when the second file was put in
         * @param fileA first file to be run
         * @param fileB second file to be run
         * @return amount of collisions in the second run with the first run
         */
        public int compare() {
            int conflicts = 0;

            if(fileA_words.size()<fileB_words.size()){
                for(String s: fileA_words){
                    if(fileB_words.contains(s)){
                        conflicts++;
                    }
                }
            }else{
                for(String s: fileB_words){
                    if(fileA_words.contains(s)){
                        conflicts++;
                    }ei
                }
            }
            return conflicts;
        }

        /**
         * parseFile uses a string builder to construct all 6-word segments of a given file and puts them into the collisions
         * HashMap. If there are any collisions it represents them in the returned conflicts variable. If only one file is run
         * through the method then conflicts will always return 0.
         * @param file the file to be parsed and inserted into the hashmap
         * @return the number of conflicts in the hashmap
         */
        private HashSet<String> parseFile(File file) {
            try {
                HashSet<String> set = new HashSet<String>();
                Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)));
                ArrayList<String> segment = new ArrayList<String>();

                while(scanner.hasNext()) {
                    segment.add(scanner.next());
                    if(segment.size()>=segmentSize) {
                        if(segment.size() > segmentSize){
                            segment.remove(0);
                        }
                        String parsed = String.join("", segment).toLowerCase().replaceAll("\\W", "");
                        set.add(parsed);
                    }
                }
                return set;
            } catch (NullPointerException | IOException a) {
                a.printStackTrace();
            }
            return null;
        }

    }
}
