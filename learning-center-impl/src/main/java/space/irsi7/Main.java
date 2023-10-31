package space.irsi7;

import space.irsi7.controllers.StudController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {

        logger.info("Приложение запущено");
        boolean isStarted = true;
        while(isStarted){
            isStarted = StudController.start();
        }
        logger.info("Приложение завершило работу");
    }
}