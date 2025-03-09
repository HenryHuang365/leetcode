import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SolutionTest {

    @Test
    public void testExampleCases() {
        Solution solution = new Solution();

        assertEquals(3, solution.solution("^vv<")); // Example 1
        assertEquals(4, solution.solution("vv>>vv")); // Example 2
        assertEquals(0, solution.solution("<<<")); // Example 3
    }

    @Test
    public void testEdgeCases() {
        Solution solution = new Solution();

        assertEquals(0, solution.solution("^")); // Single arrow, no moves needed
        assertEquals(1, solution.solution("^>")); // One rotation needed
        assertEquals(2, solution.solution("^v")); // Two rotations needed
        assertEquals(1, solution.solution("^<")); // **Fixed: Needs 1 rotation**
        assertEquals(1, solution.solution(">>>v")); // Mixed case
        assertEquals(6, solution.solution("><><><")); // Alternating arrows
    }

    @Test
    public void testLargeInput() {
        Solution solution = new Solution();
        String largeInput = "^".repeat(1000) + "v".repeat(1000);
        assertEquals(2000, solution.solution(largeInput)); // 1000 arrows need to rotate
    }
}
