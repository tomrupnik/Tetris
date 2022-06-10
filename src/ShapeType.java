import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum ShapeType {
    L,
    Z,
    S,
    O,
    T,
    I;

    private static final List<ShapeType> SHAPES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = SHAPES.size();
    private static final Random RANDOM = new Random();

    public static ShapeType randomShape() {
        return SHAPES.get(RANDOM.nextInt(SIZE));
    }
}
