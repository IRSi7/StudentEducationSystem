package space.irsi7.enums;

public enum MenuEnum {
    // Константы главного меню
    MAIN_ADD("Добавить студента"),
    MAIN_REMOVE("Отчислить студента"),
    MAIN_RATE("Проставить оценку"),
    MAIN_TIME_LEFT("Рассчитать время до выпуска"),
    MAIN_DROP_CHANCE("Рассчитать возможность отчисления"),
    MAIN_REPORT_ONE("Получить отчёт об успеваемости"),
    MAIN_REPORT_ALL("Вывести список студентов"),

    // Константы меню сортировки
    SORT_ID("Сортировать по ID"),
    SORT_NAME("Сортировать по Фамилии и Имени"),
    SORT_TESTS_PASSED("Сортировать по кол-ву сданных тестов"),
    SORT_DAYS_LEFT("Сортировать по кол-ву дней до конца обучения"),
    SORT_GPA("Сортировать по среднему баллу"),

    // Константы меню фильтра
    FILTER_NO("Не фильтровать"),
    FILTER_LOW("Показать только c низкой вероятностью быть отчисленными"),
    FILTER_HIGH("Показать только с высокой вероятностью быть отчисленными"),

    // Константы меню выбора порядка сортировки
    ORDER_DIRECT("По возрастанию"),
    ORDER_REVERSED("По убыванию"),

    // Константы меню вывода
    WRITE_CONSOLE("Вывести в консоль"),
    WRITE_TXT("Вывести в output.txt"),


    // Константы простого меню
    YES("Да"),
    NO("Нет");

    private final String message;

    MenuEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
