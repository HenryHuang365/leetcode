import java.util.HashSet;
import java.util.Set;

public class findLowestInteger {
    public int solution(int[] A) {
        Set<Integer> positiveSet = new HashSet<>();
        for (int num : A) {
            if (num > 0 && !positiveSet.contains(num)) {
                positiveSet.add(num);
            }
        }

        int i = 1;
        while (positiveSet.contains(i)) {
            i++;
        }

        return i;
    }
}
