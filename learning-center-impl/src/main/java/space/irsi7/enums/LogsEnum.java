package space.irsi7.enums;

public enum LogsEnum {

    INSERT_SUCCESS("Операция прошла успешно"),
    INSERT_FAIL("Ошибка в введённых данных");

    private final String message;

    LogsEnum (String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
