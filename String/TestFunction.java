class TestFunction {
    public String testFunction() {
        String result = "";
        char c1 = 'a' + 125 % 50 - 1;
        char c2 = 'a' + 0b0101 - 1; // 0b == binary literal
        char c3 = 't' - 1;
        result += c1;
        result += c2;
        result += c3;
        return Character.toUpperCase(c2) + result;
    }

    public static void main(String[] args) {
        TestFunction newfFunction = new TestFunction();
        System.out.println("\n" + newfFunction.testFunction());
    }
}
