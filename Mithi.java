import java.io.*;
import java.util.*;

public class Mithi {
    public static void main(String[] args) {
        Mithi generator = new Mithi();
        generator.generateIndex();
    }

    public void generateIndex() {
        List<String> excludeWords = readWordsFromFile("E:\\JAVA\\WorkSpace2\\IS_Mithi\\extras\\exclude-words.txt");

        Map<String, Set<Integer>> wordIndex = new TreeMap<>();

        for (int i = 1; i <= 3; i++) {
            String filename = "E:\\JAVA\\WorkSpace2\\IS_Mithi\\extras\\Page" + i + ".txt";
            List<String> words = readWordsFromFile(filename);

            for (String word : words) {
                if (!excludeWords.contains(word)) {
                    Set<Integer> pages = wordIndex.getOrDefault(word, new HashSet<>());
                    pages.add(i);
                    wordIndex.put(word, pages);
                }
            }
        }

        writeIndexToFile(wordIndex, "E:\\JAVA\\WorkSpace2\\IS_Mithi\\extras\\indexAnswer.txt");
    }

    private List<String> readWordsFromFile(String filename) {
        List<String> words = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] lineWords = line.split("\\s+");
                words.addAll(Arrays.asList(lineWords));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

    private void writeIndexToFile(Map<String, Set<Integer>> wordIndex, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            for (Map.Entry<String, Set<Integer>> entry : wordIndex.entrySet()) {
                String word = entry.getKey();
                Set<Integer> pages = entry.getValue();
                writer.print(word + " : ");

                StringBuilder pageList = new StringBuilder();
                for (int page : pages) {
                    pageList.append(page).append(",");
                }
                pageList.deleteCharAt(pageList.length() - 1); // Remove the trailing comma

                writer.println(pageList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
