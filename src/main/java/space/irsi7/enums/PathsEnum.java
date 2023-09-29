package space.irsi7.enums;

public enum PathsEnum {
    STUDENTS("src/main/resources/data/students.yaml"),
    CONFIG("src/main/resources/data/properties.yaml");

    public String getPath() {
        return path;
    }

    private final String path;

    PathsEnum(String path) {
        this.path = path;
    }
}
