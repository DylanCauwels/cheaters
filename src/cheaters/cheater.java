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

    public int[][] getDatabase() {
        return database;
    }

    class fileComparator {
        HashMap<Integer, String> similarities = new HashMap<>();

        public int compare(File fileA, File fileB) {
            parseFile(fileA);
            return parseFile(fileB);

        }

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

        private boolean moreThan6(String string) {
            int counter = 0;
            for(int i = 0; i < string.length(); i++) {
                if(string.charAt(i) == ' ') {
                    counter++;
                }
            }
            return counter > 5;
        }

        private int firstSpace(String string) {
            int index = 0;
            char indexedChar = string.charAt(index);
            while(indexedChar != ' ') {
                index++;
                indexedChar = string.charAt(index);
            }
            return index;
        }

        private int stringToKey(String string) {
            int sum = 0;
            for(int i = 0; i < string.length(); i++) {
                sum += (int)string.charAt(i);
            }
            return sum;
        }
    }
}
