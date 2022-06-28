import java.util.*;

public final class Interview {
    /**
     * Expliquez votre complexité temporelle et spatiale à l'aide de commentaire dans le code
     * Indiquez les équivalences telles que O(n + 1) => O(n) et O(2n) => O(n)
     *
     * * TODO Time Complexity  : O(n * logn + n * (3 logn) + 1 + logn) => O(nlogn)
     * * TODO Space Complexity : O(n + 1 + 1) => O(n)
     *
     * @param boxes The array that contains the weight of each box.
     * @return The weight of the final box if it is applicable.
     */
    static public int lastBox(int[] boxes) {
        if (boxes.length == 1)
            return boxes[0];

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder()); // Space complexity : O(n)

        for (int boxe: boxes) // Time complexity : O(n)
            priorityQueue.add(boxe); // Time complexity : O(logn)

        while (priorityQueue.size() > 1) { // Time complexity : O(n)
            int poidsBoites1 = priorityQueue.remove(); // Space complexity : O(1), Time complexity : O(logn)
            int poidsBoites2 = priorityQueue.remove(); // Space complexity : O(1), Time complexity : O(logn)
            if (poidsBoites1 != poidsBoites2) {
                poidsBoites1 -= poidsBoites2;
                priorityQueue.add(poidsBoites1); // Time complexity : O(logn)
            }
        }

        return priorityQueue.size() == 0 ? 0 : priorityQueue.remove(); // Time complexity : O(1) et O(logn)
    }
}
