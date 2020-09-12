package cn.edu.seu.historycontest.excel.listener;

import cn.edu.seu.historycontest.entity.ChoiceQuestion;
import cn.edu.seu.historycontest.excel.entity.ChoiceQuestionImportEntity;
import cn.edu.seu.historycontest.service.ChoiceQuestionService;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

public class ImportChoiceQuestionListener extends AnalysisEventListener<ChoiceQuestionImportEntity> {

    private final ChoiceQuestionService choiceQuestionService;

    public ImportChoiceQuestionListener(ChoiceQuestionService choiceQuestionService) {
        this.choiceQuestionService = choiceQuestionService;
    }

    @Override
    public void invoke(ChoiceQuestionImportEntity data, AnalysisContext context) {
        ChoiceQuestion choiceQuestion = new ChoiceQuestion();
        choiceQuestion.setQuestion(data.getQuestion());
        choiceQuestion.setChoiceA(data.getChoiceA());
        choiceQuestion.setChoiceB(data.getChoiceB());
        choiceQuestion.setChoiceC(data.getChoiceC());
        choiceQuestion.setChoiceD(data.getChoiceD());
        choiceQuestion.setAnswer((int) Character.toUpperCase(data.getAnswer().charAt(0)) - (int) 'A');
        choiceQuestionService.save(choiceQuestion);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
