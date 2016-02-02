public enum TestEnum {

    ONE("1", "ONE"), TWO("1", "TWO");

    public String value;
    public String message;

    private TestEnum(String value, String message) {
        this.value = value;
        this.message = message;
    }
}
