package cn.edu.seu.historycontest.excel.listener;

import cn.edu.seu.historycontest.entity.JudgeQuestion;
import cn.edu.seu.historycontest.excel.entity.JudgeQuestionImportEntity;
import cn.edu.seu.historycontest.service.JudgeQuestionService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ImportJudgeQuestionListener extends AnalysisEventListener<JudgeQuestionImportEntity> {

    private final JudgeQuestionService judgeQuestionService;

    public ImportJudgeQuestionListener(JudgeQuestionService judgeQuestionService) {
        this.judgeQuestionService = judgeQuestionService;
    }

    @Override
    public void invoke(JudgeQuestionImportEntity data, AnalysisContext context) {
        JudgeQuestion judgeQuestion = new JudgeQuestion();
        judgeQuestion.setQuestion(data.getQuestion());
        judgeQuestion.setAnswer(data.getAnswer());
        judgeQuestionService.save(judgeQuestion);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
