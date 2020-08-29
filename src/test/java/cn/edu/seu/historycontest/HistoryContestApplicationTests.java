package cn.edu.seu.historycontest;

import cn.edu.seu.historycontest.controller.ChoiceQuestionController;
import cn.edu.seu.historycontest.entity.ChoiceQuestion;
import cn.edu.seu.historycontest.mapper.ChoiceQuestionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Random;

@SpringBootTest
class HistoryContestApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private ChoiceQuestionMapper choiceQuestionMapper;

    @Test
    void addRandomChoiceQuestion() {
        for (int i = 0; i < 100; i++) {
            ChoiceQuestion choiceQuestion = new ChoiceQuestion();

            int numA = getRandomInteger(4, 15);
            int numB = getRandomInteger(1, 10);

            choiceQuestion.setQuestion(numA + "+" + numB + "=?");
            int[] answers = new int[4];
            int answerIndex = getRandomInteger(0, 4);
            answers[answerIndex] = numA + numB;
            for (int j = 0; j < 4; j++)
                if (answers[j] == 0)
                    answers[j] = getRandomInteger(5, 25);

            choiceQuestion.setChoiceA(String.valueOf(answers[0]));
            choiceQuestion.setChoiceB(String.valueOf(answers[1]));
            choiceQuestion.setChoiceC(String.valueOf(answers[2]));
            choiceQuestion.setChoiceD(String.valueOf(answers[3]));

            choiceQuestion.setAnswer(answerIndex);

            choiceQuestionMapper.insert(choiceQuestion);
        }
    }

    int getRandomInteger(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

}
