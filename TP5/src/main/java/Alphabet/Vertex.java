package Alphabet;

import java.util.HashSet;

public class Vertex<ValueType> {
    HashSet<Vertex<ValueType>> toVertex;
    Integer indegree;
    ValueType label;

    Vertex(ValueType label) {
        this.indegree = 0;
        this.toVertex = new HashSet<>();
        this.label = label;
    }

    /**
     * Creates an oriented Edge from the current vertex to the destination vertex
     * @param dst Vertex to which the oriented Edge points to
     */
    public void connect(Vertex dst) {
        if (this.toVertex.add(dst))
            dst.indegree += 1;
    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }
}
