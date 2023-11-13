package space.irsi7.models;

import java.util.List;

public record StudentRest(int id, String name, int course, int group, List<Theme> themes, List<Mark> marks, List<Test> tests) {
}
