package space.irsi7.controllers;

import org.yaml.snakeyaml.Yaml;
import space.irsi7.enums.MenuEnum;
import space.irsi7.models.Course;
import space.irsi7.models.Theme;
import space.irsi7.services.StudentService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudController {

    static StudentService studentService;

    //TODO: Выбрасывать свой Exception возможно унаследованный от RunTimeException
    static {
        studentService = new StudentService();
    }

    static Scanner in = new Scanner(System.in);

    public static boolean start() {

        MultipleChoiceMenu multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.MAIN_ADD, MenuEnum.MAIN_REMOVE, MenuEnum.MAIN_RATE, MenuEnum.MAIN_TIME_LEFT,
                MenuEnum.MAIN_DROP_CHANCE, MenuEnum.MAIN_REPORT_ONE, MenuEnum.MAIN_REPORT_ALL
        });

        int choice = multipleChoiceMenu.chooseOne();

        if(choice == MenuEnum.MAIN_ADD.ordinal()){
            add();
        }
        if(choice == MenuEnum.MAIN_REMOVE.ordinal()){
            remove();
        }
        if(choice == MenuEnum.MAIN_RATE.ordinal()){
            rate();
        }
        if(choice == MenuEnum.MAIN_TIME_LEFT.ordinal()){
            time();
        }
        if(choice == MenuEnum.MAIN_DROP_CHANCE.ordinal()){
            possibility();
        }
        if(choice == MenuEnum.MAIN_REPORT_ONE.ordinal()){
            report();
        }
        if(choice == MenuEnum.MAIN_REPORT_ALL.ordinal()){
            fullReport();
        }
        return restart();
    }

    public static void add(){
        String name;
        int eduId;
        System.out.println("Введите Имя и Фамилию студента");
        name = in.nextLine();
        System.out.println("Введите порядковый номер учебной программы");
        eduId = in.nextInt();
        studentService.addStudent(name, eduId);
    }

    public static void remove(){
        int studentId = getStudId();
        studentService.removeStudent(studentId);
    }

    public static void rate(){
        int mark;
        int studentId = getStudId();
        System.out.println("Введите оценку за последний тест");
        mark = in.nextInt();
        studentService.rateStudent(studentId, mark);
    }

    public static void time(){
        int studentId = getStudId();
        System.out.println("Выбранному студенту осталось учиться "
                + studentService.getEduTimeLeft(studentId) + " дней");
    }

    public static void report(){
        int studentId = getStudId();
        System.out.println(studentService.getReportStudent(studentId));
    }

    public static void possibility(){
        int studentId = getStudId();
        if (studentService.getDropChance(studentId)) {
            System.out.println("Низкая вероятность быть отчисленным");
        } else {
            System.out.println("Высокая вероятность быть отчисленным");
        }
    }

    public static void fullReport(){
        MultipleChoiceMenu multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.SORT_ID, MenuEnum.SORT_NAME, MenuEnum.SORT_TESTS_PASSED,
                MenuEnum.SORT_DAYS_LEFT, MenuEnum.SORT_GPA
        });

        int sort = multipleChoiceMenu.chooseOne();

        multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.ORDER_DIRECT, MenuEnum.ORDER_REVERSED
        });
        int order = multipleChoiceMenu.chooseOne();

        multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.FILTER_NO, MenuEnum.FILTER_LOW, MenuEnum.FILTER_HIGH
        });
        int filter = multipleChoiceMenu.chooseOne();

        multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.WRITE_CONSOLE, MenuEnum.WRITE_TXT
        });
        int write = multipleChoiceMenu.chooseOne();

        if(write == MenuEnum.WRITE_CONSOLE.ordinal()) {
            studentService.getAllReport(sort, order, filter).forEach(System.out::println);
        }
        if(write == MenuEnum.WRITE_TXT.ordinal()) {
            writeToTxt(studentService.getAllReport(sort, order, filter));
        }
    }

    public static boolean restart() {
        System.out.println("Хотите продолжить?");
        MultipleChoiceMenu yesNo = new MultipleChoiceMenu(new MenuEnum[]{
                MenuEnum.YES, MenuEnum.NO
        });
        return yesNo.chooseOne() == MenuEnum.YES.ordinal();
    }

    public static int getStudId(){
        int studentId;
        do {
            System.out.println("Введите ID студента");
            studentId = in.nextInt();
        } while (!studentService.validateId(studentId));
        return studentId;
    }

    public static void writeToTxt(ArrayList<String> strings){
        try(PrintStream stream = new PrintStream("output.txt")){
            System.setOut(stream);
            strings.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            System.out.println("Файл для записи не найден. Вывод не был сохранён.");
        } finally {
            PrintStream consoleStream = new PrintStream(
                    new FileOutputStream(FileDescriptor.out));
            System.setOut(consoleStream);
        }
    }

//    public static void initTest () {
//
//        ArrayList<Course> courses = new ArrayList<>(){{
//            add(new Course(1, new ArrayList<>(List.of(1, 2, 3 , 4, 5, 6, 7, 8, 9, 10))));
//            add(new Course(2, new ArrayList<>(List.of(10, 9, 8 , 7, 6, 5, 4, 3, 2, 1))));
//        }};
//        ArrayList<Theme> themes = new ArrayList<>(){{
//            add(new Theme(1, "Spring", 2));
//            add(new Theme(2, "Java", 3));
//            add(new Theme(3, "Kotlin", 1));
//            add(new Theme(4, "Maven", 4));
//            add(new Theme(5, "Gradle", 3));
//            add(new Theme(6, "Docker", 4));
//            add(new Theme(7, "KeyCloak", 2));
//            add(new Theme(8, "Jira", 1));
//            add(new Theme(9, "Confluence", 1));
//            add(new Theme(10, "Android", 4));
//            add(new Theme(11, "OpenSearch", 4));
//            add(new Theme(12, "Spring", 1));
//
//        }};
//
//        Config config = new Config(courses, themes);
//
//        Yaml yaml = new Yaml();
//        StringWriter writer = new StringWriter();
//        yaml.dump(config, writer);
//    }
}
