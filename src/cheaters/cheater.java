package cheaters;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class cheater {
    private int[][] database;


    /**
     * cheater runs through all the documents in the given fileset, checks them against each other and puts the amount
     * of concurrencies for each pairing into the database array which is sized at the start of the method.
     * @param documents the number of documents in the array
     * @param docs a File array of all the filenames of the documents located in the package
     */
    public cheater(int documents, File[] docs) {
        database = new int[documents][documents];
        fileComparator comparator = new fileComparator();
        for(int i = 0; i < docs.length; i++) {
            for(int j = i + 1; j < docs.length; j++) {
                database[i][j] = comparator.compare(docs[i], docs[j]);
            }
        }
    }

    /**
     *
     * @return the 2D matrix of registered 6-word similarities
     */
    public int[][] getDatabase() {
        return database;
    }


    class fileComparator {
        //TODO multiple compare calls will require that similarities be reallocated every run to prevent saturation from other file calls
        HashMap<Integer, String> similarities = new HashMap<>();

        /**
         * takes two files and runs them. The first run of parseFile puts the keys and values into the similarities
         * HashMap, and the second returns the collisions that occurred in the hashmap when the second file was put in
         * @param fileA first file to be run
         * @param fileB second file to be run
         * @return amount of collisions in the second run with the first run
         */
        public int compare(File fileA, File fileB) {
            parseFile(fileA);
            return parseFile(fileB);

        }

        /**
         * parseFile uses a string builder to construct all 6-word segments of a given file and puts them into the collisions
         * HashMap. If there are any collisions it represents them in the returned conflicts variable. If only one file is run
         * through the method then conflicts will always return 0.
         * @param file the file to be parsed and inserted into the hashmap
         * @return the number of conflicts in the hashmap
         */
        private int parseFile(File file) {
            int conflicts = 0;
            try {
                Scanner scanner = new Scanner(file);
                StringBuilder segment = null;
                while(scanner.hasNext()) {
                    assert false;
                    segment.append(scanner.next());
                    if(moreThan6(segment.toString())) {
                        //TODO firstSpace may leave a space before the string so it may have to return index++;
                        segment.delete(0, firstSpace(segment.toString()));
                    }
                    if(similarities.get(stringToKey(segment.toString())) == null) {
                        similarities.put(stringToKey(segment.toString()), segment.toString());
                    } else {
                        conflicts++;
                    }
                }
                return conflicts;
            } catch (FileNotFoundException | NullPointerException a) {
                a.printStackTrace();
            }
            return -1;
        }

        /**
         * morethan6 counts the number of spaces in a string and returns true if it has more than 5 (meaning it has
         * more than 6 words
         * @param string the string to be tested
         * @return
         */
        private boolean moreThan6(String string) {
            int counter = 0;
            for(int i = 0; i < string.length(); i++) {
                if(string.charAt(i) == ' ') {
                    counter++;
                }
            }
            return counter > 5;
        }

        /**
         * first space finds the index value of the first space in the string so that the stringBuilder can remove the
         * first word from the string
         * @param string the string to be tested
         * @return the index of the first space in the string
         */
        private int firstSpace(String string) {
            int index = 0;
            char indexedChar = string.charAt(index);
            while(indexedChar != ' ') {
                index++;
                indexedChar = string.charAt(index);
            }
            return index;
        }

        /**
         * stringToKey calculates the key value for the hashmap by adding up all the characters in the string
         * @param string the string to be calculated
         * @return the key of the string to be entered into the hashmap
         */
        private int stringToKey(String string) {
            int sum = 0;
            for(int i = 0; i < string.length(); i++) {
                sum += (int)string.charAt(i);
            }
            return sum;
        }
    }
}
