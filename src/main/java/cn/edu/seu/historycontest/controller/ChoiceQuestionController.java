package cn.edu.seu.historycontest.controller;


import cn.edu.seu.historycontest.entity.ChoiceQuestion;
import cn.edu.seu.historycontest.service.ChoiceQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2020-08-28
 */
@RestController
@RequestMapping("/api/choice")
public class ChoiceQuestionController {

    @Autowired
    private ChoiceQuestionService choiceQuestionService;

    @GetMapping("list")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ChoiceQuestion> getList() {
        return choiceQuestionService.list();
    }

    @PutMapping("insert")
    @PreAuthorize("hasRole('ADMIN')")
    public void insert(@RequestBody ChoiceQuestion choiceQuestion) {
        choiceQuestionService.save(choiceQuestion);
    }

    @PutMapping("edit")
    @PreAuthorize("hasRole('ADMIN')")
    public void edit(@RequestBody ChoiceQuestion choiceQuestion) {
        choiceQuestionService.updateById(choiceQuestion);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        choiceQuestionService.removeById(id);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteMany(@RequestBody List<Long> ids) {
        choiceQuestionService.removeByIds(ids);
    }

}

