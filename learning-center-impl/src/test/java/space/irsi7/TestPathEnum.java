package space.irsi7;

enum TestPathEnum {

    STUDENTS("src/test/resources/data/test_students.yaml"),
    CONFIG("src/test/resources/data/test_properties.yaml");

    public String getPath() {
        return path;
    }

    private final String path;

    TestPathEnum(String path) {
        this.path = path;
    }
}
