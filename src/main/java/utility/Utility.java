package utility;

import java.util.List;
import java.util.Random;

public class Utility {
    public static Object get_random(List<Object> list) {
        Object random_object = null;

        if(list != null && list.size() > 0) {
            random_object = list.get((new Random()).nextInt(list.size()));
        }

        return random_object;
    }
}
