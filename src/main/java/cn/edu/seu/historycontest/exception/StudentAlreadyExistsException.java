package cn.edu.seu.historycontest.exception;

import org.springframework.util.StringUtils;

public class StudentAlreadyExistsException extends ForbiddenException {

    public static StudentAlreadyExistsException bySid(String sid) {
        return new StudentAlreadyExistsException(String.format("学生已存在，学号：%s", sid));
    }

    public static StudentAlreadyExistsException byCardId(String cardId) {
        return new StudentAlreadyExistsException(String.format("学生已存在，一卡通号：%s", cardId));
    }

    private StudentAlreadyExistsException(String message) {
        super(message);
    }
}
