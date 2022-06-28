package Alphabet;
import java.util.*;

public class Alphabet {

    /**
     * TODO
     * From the words contained in the dictionary of a fictitious language, find the lexical order of
     * the symbols composing the language.
     *
     * @param dictionary Contains all the word of a language
     * @return The lexicalOrder of the symbols composing this language
     */
    public static ArrayList<Character> lexicalOrder(String[] dictionary) {
        Graph<Character> graph = new Graph<Character>();

        for (int i = 0; i < dictionary.length - 1; i++) {
            String mot1 = dictionary[i];
            String mot2 = dictionary[i+1];
            int taillePlusPetitMot = Math.min(mot1.length(), mot2.length());
            for (int j = 0; j < taillePlusPetitMot; j++) {
                char char1 = mot1.charAt(j);
                char char2 = mot2.charAt(j);
                if (char1 != char2) {
                    graph.connect(char1, char2);
                    break;
                }
            }
        }
        // La méthode topSort() a été ajoutée au fichier Graph.java.
        // Elle était inspirée du code fourni avec les notes du cours.
        return graph.topSort();
    }
}


