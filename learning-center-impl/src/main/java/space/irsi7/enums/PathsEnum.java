package space.irsi7.enums;

public enum PathsEnum {
    STUDENTS("data/students.yaml"),
    CONFIG("data/properties.yaml");

    public String getPath() {
        return path;
    }

    private final String path;

    PathsEnum(String path) {
        this.path = path;
    }
}
