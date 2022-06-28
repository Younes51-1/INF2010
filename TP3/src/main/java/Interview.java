import java.util.*;
public class Interview {

    /** Expliquez votre complexité temporelle et spatiale en cas moyen et en pire cas à l'aide de commentaire dans le code
     *  Indiquez les équivalences telles que O(n + 1) => O(n) et O(2n) => O(n)
     *
     ** TODO Time Complexity : Worst Case O(n), explain Worst and Average Case
     ** TODO Space Complexity : Determine and Explain Worst and Average Case in comments
     ** TODO HAS TO BE ITERATIVE, , NOT RECURSIVE
     * @param numbers List of numbers sorted in ascending order containing 1 non-duplicate
     * @return non-duplicate number
     */
    public static Integer findNonDuplicateIterativeLinear(Integer[] numbers) {
        if (numbers.length == 0)
            return null;
        if (numbers.length == 1)
            return numbers[0];
        HashMap<Integer, Integer> map = new HashMap<>(); // Complexité spatiale average et worst case : O(n) assignation
        for (Integer number: numbers) { // Complexité temporelle average et worst case : O(n) boucle for #1
            int count = map.getOrDefault(number, 0);
            map.put(number, ++count);
        }
        for (Integer number: numbers) { // Complexité temporelle average et worst case : O(n) boucle for #2
            if (map.get(number).equals(1)) {
                return number;
            }
        }
        return null;
        // Complexité temporelle totale average et worst case : O(n) boucle for #1 + O(n) boucle for #2 = O(n)
    }

    /** Expliquez votre complexité temporelle et spatiale à l'aide de commentaire dans le code
     *  Indiquez les équivalences telles que O(n + 1) => O(n) et O(2n) => O(n)
     *
     ** TODO Time Complexity : Worst Case O(log(n)), explain Worst and Average Case
     ** TODO Space Complexity : Determine and Explain Worst and Average Case in comments
     ** TODO HAS TO BE ITERATIVE, NOT RECURSIVE
     * @param numbers List of numbers sorted in ascending order containing 1 duplicate
     * @return non-duplicate number
     */
    public static Integer findNonDuplicateIterative(Integer[] numbers) {
        if (numbers.length == 0)
            return null;
        if (numbers.length == 1)
            return numbers[0];
        int start = 0, end = numbers.length - 1, mid; // Complexité spatiale average et worst case : O(1) assignations
        if (!numbers[start].equals(numbers[start + 1]))
            return numbers[start];
        if (!numbers[end].equals(numbers[end - 1]))
            return numbers[end];
        while (start <= end) { // Complexité temporelle average et worst case : O(logn) car division de l'array en 2 à chaque itération
            mid = start + (end - start) / 2;
            if (!numbers[mid].equals(numbers[mid - 1]) && !numbers[mid].equals(numbers[mid + 1]))
                return numbers[mid];
            else if ((numbers[mid].equals(numbers[mid + 1]) && mid % 2 == 0)
                    || (numbers[mid].equals(numbers[mid - 1]) && mid % 2 != 0))
                start = mid + 1;
            else
                end = mid - 1;
        }
        return null;
    }

    /** Expliquez votre complexité temporelle et spatiale à l'aide de commentaire dans le code
     *  Indiquez les équivalences telles que O(n + 1) => O(n) et O(2n) => O(n)
     *
     ** TODO Time Complexity : Worst Case O(log(n)), explain Worst and Average Case
     ** TODO Space Complexity : Determine and Explain Worst and Average Case in comments
     ** TODO HAS TO BE RECURSIVE, NOT ITERATIVE
     * Complexité spatiale average et worst case : O(logn) car empilement des fonctions où l'array est divisé chaque fois par 2
     * Complexité temporelle average et worst case : O(logn) car division de l'array en 2 à chaque itération
     * @param numbers List of numbers sorted in ascending order containing 1 non-duplicate
     * @return non-duplicate number
     */
    public static Integer findNonDuplicateRecursive(Integer[] numbers) {
        if (numbers.length == 0)
            return null;
        if (numbers.length == 1)
            return numbers[0];
        int mid = (numbers.length -1) / 2;
        if (mid % 2 == 0) {
            if (numbers[mid].equals(numbers[mid + 1]))
                 return findNonDuplicateRecursive(Arrays.copyOfRange(numbers, mid + 2, numbers.length));
            else
                return findNonDuplicateRecursive(Arrays.copyOfRange(numbers, 0, mid + 1));
        }
        else {
            if (numbers[mid].equals(numbers[mid - 1]))
                return findNonDuplicateRecursive(Arrays.copyOfRange(numbers, mid + 1, numbers.length));
            else
                return findNonDuplicateRecursive(Arrays.copyOfRange(numbers, 0, mid));
        }
    }
}