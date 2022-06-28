package Point;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class AbstractPoint implements Comparable<AbstractPoint> {
    protected Double[] vector;

    public AbstractPoint(double[] vector) {
        this.vector = Arrays.stream(vector).boxed().toArray(Double[]::new);
    }

    public abstract AbstractPoint translate(Double[] translateVector);
    public abstract AbstractPoint rotate(Double[][] rotationMatrix);
    public abstract AbstractPoint divide(Double divider);
    public abstract AbstractPoint multiply(Double multiplier);
    public abstract AbstractPoint add(Double adder);

    @Override
    public String toString() {
        return Arrays.stream(vector)
                .map(v -> String.valueOf(Math.round(v)))
                .collect(Collectors.joining("; "));
    }

    @Override
    public boolean equals(Object o) {
        Double[] abstractVector = ((AbstractPoint)o).vector;
        for (int i = 0; i < vector.length; ++i) {
            if (Math.round(vector[i]) - Math.round(abstractVector[i]) != 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public int compareTo(AbstractPoint other) {
        Double[] abstractVector = other.vector;
        for (int i = 0; i < vector.length; ++i) {
            int comparison = vector[i].compareTo(abstractVector[i]);
            if (comparison != 0) {
                return comparison;
            }
        }
        return 0;
    }
}
