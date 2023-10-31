package space.irsi7.enums;

public enum PathsEnum {
    STUDENTS("learning-center-impl/src/main/resources/data/students.yaml"),
    CONFIG("learning-center-impl/src/main/resources/data/properties.yaml");

    public String getPath() {
        return path;
    }

    private final String path;

    PathsEnum(String path) {
        this.path = path;
    }
}
