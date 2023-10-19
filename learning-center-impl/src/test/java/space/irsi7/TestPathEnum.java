package space.irsi7;

enum TestPathEnum {

    STUDENTS("data/test_students.yaml"),
    CONFIG("data/test_properties.yaml");

    public String getPath() {
        return path;
    }

    private final String path;

    TestPathEnum(String path) {
        this.path = path;
    }
}
