package cn.edu.seu.historycontest.service.impl;

import cn.edu.seu.historycontest.Constants;
import cn.edu.seu.historycontest.entity.ChoiceQuestion;
import cn.edu.seu.historycontest.entity.JudgeQuestion;
import cn.edu.seu.historycontest.entity.Paper;
import cn.edu.seu.historycontest.entity.User;
import cn.edu.seu.historycontest.exception.ForbiddenException;
import cn.edu.seu.historycontest.exception.ResourceNotFoundException;
import cn.edu.seu.historycontest.mapper.PaperMapper;
import cn.edu.seu.historycontest.payload.DetailedPaper;
import cn.edu.seu.historycontest.security.UserPrincipal;
import cn.edu.seu.historycontest.service.ChoiceQuestionService;
import cn.edu.seu.historycontest.service.JudgeQuestionService;
import cn.edu.seu.historycontest.service.PaperService;
import cn.edu.seu.historycontest.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperService {

    @Autowired
    private UserService userService;

    @Autowired
    private ChoiceQuestionService choiceQuestionService;

    @Autowired
    private JudgeQuestionService judgeQuestionService;

    @Override
    public DetailedPaper generatePaper(UserPrincipal userPrincipal) {
        User user = new User();
        user.setId(userPrincipal.getId());
        user.setStatus(Constants.STATUS_GENERATED);
        userService.updateById(user);

        Paper paper = new Paper();
        paper.setUid(userPrincipal.getId());
        List<ChoiceQuestion> choiceQuestions = generateChoiceQuestion();
        List<JudgeQuestion> judgeQuestions = generateJudgeQuestion();

        paper.setChoiceQuestion(choiceQuestions.stream().map(q -> q.getId().toString())
                .collect(Collectors.joining(",")));
        paper.setJudgeQuestion(judgeQuestions.stream().map(q -> q.getId().toString())
                .collect(Collectors.joining(",")));

        StringBuilder answerBuilder = new StringBuilder();
        for (int i = 0; i < Constants.TOTAL_CHOICE_QUESTION; i++) {
            if (i != 0)
                answerBuilder.append(',');
            answerBuilder.append("-1");
        }
        paper.setChoiceAnswer(answerBuilder.toString());

        answerBuilder = new StringBuilder();
        for (int i = 0; i < Constants.TOTAL_JUDGE_QUESTION; i++) {
            if (i != 0)
                answerBuilder.append(',');
            answerBuilder.append("-1");
        }
        paper.setJudgeAnswer(answerBuilder.toString());

        save(paper);

        DetailedPaper detailedPaper = new DetailedPaper();
        detailedPaper.setId(paper.getId());
        detailedPaper.setChoiceQuestions(choiceQuestions);
        detailedPaper.setJudgeQuestions(judgeQuestions);
        return detailedPaper;
    }

    @Override
    public void calibrateTime(Long userId, Date time) {
        User user = new User();
        user.setId(userId);
        user.setStatus(Constants.STATUS_STARTED);
        userService.updateById(user);

        Paper paper = getPaperFromUid(userId);
        paper.setStartTime(time);
        updateById(paper);
    }

    @Override
    public DetailedPaper getDetailedPaper(Long userId) {
        Paper paper = getPaperFromUid(userId);

        DetailedPaper detailedPaper = new DetailedPaper();
        detailedPaper.setId(paper.getId());


        if (paper.getChoiceQuestion() != null) {
            List<ChoiceQuestion> choiceQuestions = Arrays.stream(paper.getChoiceQuestion().split(","))
                    .map(s -> {
                        ChoiceQuestion question = choiceQuestionService.getById(s);
                        question.setAnswer(null);
                        return question;
                    }).collect(Collectors.toList());
            detailedPaper.setChoiceQuestions(choiceQuestions);
        }

        if (paper.getJudgeQuestion() != null) {
            List<JudgeQuestion> judgeQuestions = Arrays.stream(paper.getJudgeQuestion().split(","))
                    .map(s -> {
                        JudgeQuestion question = judgeQuestionService.getById(s);
                        question.setAnswer(null);
                        return question;
                    }).collect(Collectors.toList());
            detailedPaper.setJudgeQuestions(judgeQuestions);
        }

        if (paper.getChoiceAnswer() != null) {
            List<Integer> choiceAnswerSheet = Arrays.stream(paper.getChoiceAnswer().split(","))
                    .map(Integer::valueOf).collect(Collectors.toList());
            detailedPaper.setChoiceAnswerSheet(choiceAnswerSheet);
        }


        if (paper.getJudgeAnswer() != null) {
            List<Integer> judgeAnswerSheet = Arrays.stream(paper.getJudgeAnswer().split(","))
                    .map(Integer::valueOf).collect(Collectors.toList());
            detailedPaper.setJudgeAnswerSheet(judgeAnswerSheet);
        }

        detailedPaper.setStartTime(paper.getStartTime().getTime());

        return detailedPaper;
    }

    private Paper getPaperFromUid(Long userId) {
        QueryWrapper<Paper> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", userId);
        return getOne(queryWrapper);
    }

    private List<ChoiceQuestion> generateChoiceQuestion() {
        long total = choiceQuestionService.count();
        long pageSize = total / Constants.TOTAL_CHOICE_QUESTION;

        List<ChoiceQuestion> selectedQuestions = new LinkedList<>();
        for (int i = 0; i < Constants.TOTAL_CHOICE_QUESTION; i++) {

            Page<ChoiceQuestion> page = new Page<>(i + 1, pageSize);
            choiceQuestionService.page(page);
            List<ChoiceQuestion> tmpQuestions = new LinkedList<>(page.getRecords());

            if (i == Constants.TOTAL_CHOICE_QUESTION - 1) {
                page = new Page<>(i + 2, pageSize);
                choiceQuestionService.page(page);
                tmpQuestions.addAll(page.getRecords());
            }

            ChoiceQuestion selectedQuestion = tmpQuestions.get((int) (Math.random() * tmpQuestions.size()));
            selectedQuestion.setAnswer(null);
            selectedQuestions.add(selectedQuestion);
        }

        return selectedQuestions;
    }

    private List<JudgeQuestion> generateJudgeQuestion() {
        long total = judgeQuestionService.count();
        long pageSize = total / Constants.TOTAL_JUDGE_QUESTION;

        List<JudgeQuestion> selectedQuestions = new LinkedList<>();
        for (int i = 0; i < Constants.TOTAL_JUDGE_QUESTION; i++) {

            Page<JudgeQuestion> page = new Page<>(i + 1, pageSize);
            judgeQuestionService.page(page);
            List<JudgeQuestion> tmpQuestions = new LinkedList<>(page.getRecords());

            if (i == Constants.TOTAL_JUDGE_QUESTION - 1) {
                page = new Page<>(i + 2, pageSize);
                judgeQuestionService.page(page);
                tmpQuestions.addAll(page.getRecords());
            }

            JudgeQuestion selectedQuestion = tmpQuestions.get((int) (Math.random() * tmpQuestions.size()));
            selectedQuestion.setAnswer(null);
            selectedQuestions.add(selectedQuestion);
        }

        return selectedQuestions;
    }
}
