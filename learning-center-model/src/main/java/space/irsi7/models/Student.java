package space.irsi7.models;

public record Student(int id, String name, int course, int group)
{
    @Override
    public String toString() {
        return "";
    }
}
