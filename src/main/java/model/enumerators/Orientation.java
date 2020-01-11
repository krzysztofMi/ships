package model.enumerators;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Orientation {
    HORIZONTAL, VERTICAL;


    private static final List<Orientation> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Orientation getRandomOrientation(){
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
