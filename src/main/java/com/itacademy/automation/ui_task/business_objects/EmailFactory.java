package com.itacademy.automation.ui_task.business_objects;

import static com.itacademy.automation.ui_task.services.RandomGenerator.*;

public class EmailFactory {

    public static Email getCorrectEmail() {
        return new Email()
                .withLogin("itacademyselenium")
                .withDomain("@mail.ru");
    }

    public static Email getIncorrectLogin() {
        return new Email()
                .withLogin("!#$%^&")
                .withDomain("@mail.ru");
    }

    public static Email getIncorrectDomain() {
        return new Email()
                .withLogin("itacademyselenium")
                .withDomain(generateRandomIncorrectDomain());
    }

    public static Email getEmptyLogin() {
        return new Email()
                .withLogin("")
                .withDomain(generateRandomDomain());
    }
}
