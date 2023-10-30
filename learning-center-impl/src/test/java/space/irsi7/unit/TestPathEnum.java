package space.irsi7.unit;

import lombok.Getter;

@Getter
enum TestPathEnum {

    STUDENTS("data/students.yaml"),
    CONFIG("data/properties.yaml");

    private final String path;

    TestPathEnum(String path) {
        this.path = path;
    }
}
