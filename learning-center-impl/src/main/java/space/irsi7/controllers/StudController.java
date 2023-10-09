package space.irsi7.controllers;

import space.irsi7.enums.MenuEnum;
import space.irsi7.services.StudentServiceImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudController {

    //TODO: Выбрасывать свой Exception возможно унаследованный от RunTimeException
    static StudentServiceImpl studentServiceImpl;
    static Scanner in;
    static MultipleChoiceMenu multipleChoiceMenu;

    static {
        in = new Scanner(System.in);
        studentServiceImpl = new StudentServiceImpl();
    }

//    public StudController(StudentServiceImpl service, Scanner scanner, MultipleChoiceMenu menu){
//        studentServiceImpl = service;
//        in = scanner;
//        multipleChoiceMenu = menu;
//    }

    public static boolean start() {

        multipleChoiceMenu = new MultipleChoiceMenu(new MenuEnum[]{
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
        studentServiceImpl.addStudent(name, eduId);
    }

    public static void remove(){
        int studentId = getStudId();
        studentServiceImpl.removeStudent(studentId);
    }

    public static void rate(){
        int mark;
        int studentId = getStudId();
        System.out.println("Введите оценку за последний тест");
        mark = in.nextInt();
        studentServiceImpl.rateStudent(studentId, mark);
    }

    public static void time(){
        int studentId = getStudId();
        System.out.println("Выбранному студенту осталось учиться "
                + studentServiceImpl.getEduTimeLeft(studentId) + " дней");
    }

    public static void report(){
        int studentId = getStudId();
        System.out.println(studentServiceImpl.getReportStudent(studentId));
    }

    public static void possibility(){
        int studentId = getStudId();
        if (studentServiceImpl.getDropChance(studentId)) {
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
            studentServiceImpl.getAllReport(sort, order, filter).forEach(System.out::println);
        }
        if(write == MenuEnum.WRITE_TXT.ordinal()) {
            writeToTxt(studentServiceImpl.getAllReport(sort, order, filter));
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
        } while (!studentServiceImpl.validateId(studentId));
        return studentId;
    }

    public static void writeToTxt(List<String> strings){
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
}
