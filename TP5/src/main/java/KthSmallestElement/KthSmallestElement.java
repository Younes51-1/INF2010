package KthSmallestElement;

import java.util.*;

public class KthSmallestElement {
    /**
     * Explication de votre complexité temporelle
     * O(k log max(m, n)) car O(max(m, n) + k * log max(m, n))
     * max(m, n) car construction de la priorityQueue
     * k * log max(m, n) car boucle for en O(k)
     * et operations de retrait et d'insertion en O(log max(m, n))
     * O(1) pour les autres opérations.
     *
     * Explication de votre complexité spatiale
     * O(max(m,n)) car O(1 + 1 + 1 + max(m, n))
     * max(m, n) dépend de la variable direction
     * et de la boucle while (chaque fois qu'on dépile, on ajoute au max 1 node).
     */
    /** TODO Worst case
     *      Time complexity : O ( k log max(m,n) )
     *      Space complexity : O ( max(m,n) )
     *
     * Returns the `k`th smallest element in `matrix`
     * @param matrix 2D table of shape M x N respecting the following rules
     *               matrix[i][j] <= matrix[i+1][j]
     *               matrix[i][j] <= matrix[i][j + 1]
     * @param k Index of the smallest element we want
     * @return `K`th smallest element in `matrix`, null if non-existent
     */
    static public <T extends Comparable<T>> T findKthSmallestElement(final T[][] matrix, final int k) {
        if (matrix == null || matrix.length == 0)
            return null;

        final int ROWS = matrix.length;
        final int COLS = matrix[0].length;

        if (k < 0 || k >= ROWS * COLS)
            return null;

        String direction = ROWS > COLS ? "ROWS" : "COLS";
        PriorityQueue<Node<T>> queue = new PriorityQueue<>();

        if (direction.equals("COLS")) {
            for (int i = 0; i < ROWS; i++)
                queue.add(new Node<>(matrix[i][0], i, 0));
        }
        else {
            for (int i = 0; i < COLS; i++)
                queue.add(new Node<>(matrix[0][i], 0, i));
        }

        for (int i = 0; i < k; i++) {
            Node<T> current = queue.poll();
            int row = current == null ? 0 : current.row;
            int col = current == null ? 0 : current.col;
            if (direction.equals("COLS") && col < COLS - 1)
                    queue.add(new Node<>(matrix[row][col + 1], row, col + 1));
            else if (direction.equals("ROWS") && row < ROWS - 1)
                    queue.add(new Node<>(matrix[row + 1][col], row + 1, col));
        }

        return queue.size() == 0 ? null : queue.peek().value;
    }
    private static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
        final T value;
        final int row;
        final int col;

        Node(T value, int row, int col) {
            this.value = value;
            this.col = col;
            this.row = row;
        }

        @Override
        public int compareTo(Node<T> other) {
            return Integer.compare(value.compareTo(other.value), 0);
        }
    }
}
