import java.util.*;
import java.util.HashMap;

public final class Interview {

    /** Expliquez votre complexité temporelle et spatiale à l'aide de commentaire dans le code
     *  n représente le nombre de charactère de `phrase` et m le nombre de charactère de `stopwords`
     *  Indiquez les équivalences telles que O(n + 1 + m + 1) => O(n+m) et O(2n+3m) => O(n+m) lorsque possible
     *
     ** TODO Justify Time Complexity  : Average Case O(n+m)
     * O(n + n + n + m + m + m + m + n) => O(4n + 4m) => O(n+m)
     * Dans l'ordre : toLowerCase + split + for #1 + for #2 + toLowerCase + split + for #3 + for #4
     ** TODO Justify Space Complexity : Worst Case O(n+m)
     * O(n + n + m + m + n) => O(3n + 2m) => O(n + m)
     * Dans l'ordre : phraseList + for #1 + for #2 + for #3 + for #4
     *
     *
     * @param phrase String containing a sequence of words separated by a space
     * @param stopwords String array containing all the stop words
     * @return Pair containing two elements, first being the most common word not in the stop words,
     * second being the number of occurences of this word
     */
    public static Pair findMostCommonValidWord(String phrase, String[] stopwords) {
        if (phrase.isEmpty())
            return new Pair(null, null);

        String[] phraseList = phrase.toLowerCase(Locale.ROOT).split("\\s+");
        HashMap<String, Integer> hash = new HashMap<>();
        for (String word : phraseList) {
            if (hash.containsKey(word)) {
                int count = hash.get(word);
                hash.put(word, ++count);
            }
            else
                hash.put(word, 1);
        }

        StringBuilder temp = new StringBuilder();
        for (String stopword : stopwords) {
            temp.append(stopword);
            temp.append(" ");
        }
        String tempStr = temp.toString();
        tempStr = tempStr.toLowerCase();
        stopwords = tempStr.split("\\s+");
        for (String stop : stopwords)
            hash.remove(stop);

        Pair mostPopular = new Pair(null, null);
        for (String word : phraseList) {
            if (hash.containsKey(word)) {
                if (mostPopular.second == null || hash.get(word).compareTo(mostPopular.second) > 0) {
                    mostPopular.first = word;
                    mostPopular.second = hash.get(word);
                }
                else if (hash.get(word).equals(mostPopular.second) && word.compareTo(mostPopular.first) < 0) {
                    mostPopular.first = word;
                    mostPopular.second = hash.get(word);
                }
            }
        }
        return mostPopular;
    }
}
