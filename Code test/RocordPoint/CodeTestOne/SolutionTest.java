import static org.junit.Assert.*;
import org.junit.Test;

public class SolutionTest {
    Solution solution = new Solution();

    @Test
    public void testNormalCases() {
        assertEquals(132, solution.MathChallenge(123));
        assertEquals(12534, solution.MathChallenge(12453));
        assertEquals(21534, solution.MathChallenge(21354));
        assertEquals(51243, solution.MathChallenge(51234));
    }

    @Test
    public void testEdgeCases() {
        assertEquals(-1, solution.MathChallenge(999)); // No greater permutation
        assertEquals(-1, solution.MathChallenge(54321)); // Already highest permutation
        assertEquals(-1, solution.MathChallenge(4321)); // Decreasing order

        assertEquals(120, solution.MathChallenge(102)); // Next permutation when zero is involved
        assertEquals(201, solution.MathChallenge(120)); // Smallest value change
    }

    @Test
    public void testSingleDigitNumbers() {
        assertEquals(-1, solution.MathChallenge(1)); // Single-digit numbers have no higher permutations
        assertEquals(-1, solution.MathChallenge(9));
    }

    @Test
    public void testNumbersWithRepeatingDigits() {
        assertEquals(211, solution.MathChallenge(121)); // Next permutation with repeating digits
        assertEquals(121, solution.MathChallenge(112));
        assertEquals(-1, solution.MathChallenge(222)); // No next permutation
    }
}
