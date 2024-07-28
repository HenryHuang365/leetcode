public class arrowDirection {
    public int numberOfSteps(String S) {
        int countUp = 0;
        int countDown = 0;
        int countLeft = 0;
        int countRight = 0;

        // Count the number of each type of arrow
        for (char c : S.toCharArray()) {
            switch (c) {
                case '^': countUp++; break;
                case 'v': countDown++; break;
                case '<': countLeft++; break;
                case '>': countRight++; break;
            }
        }

        System.out.println("up: " + countUp);
        System.out.println("down: " + countDown);
        System.out.println("left: " + countLeft);
        System.out.println("right: " + countRight);

        // Calculate the moves needed to align all arrows in one direction
        int movesToUp = countDown * 2 + countLeft * 3 + countRight;
        int movesToDown = countUp * 2 + countLeft + countRight * 3;
        int movesToLeft = countUp + countDown * 2 + countRight * 3;
        int movesToRight = countUp * 3 + countDown + countLeft * 2;

        // Return the minimum moves required to align all arrows
        return Math.min(Math.min(movesToUp, movesToDown), Math.min(movesToLeft, movesToRight));
    }

    public static void main(String[] args) {
        arrowDirection newClass = new arrowDirection();
        int res = newClass.numberOfSteps(">>>");
        System.out.println(res);
    }
}
