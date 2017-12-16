package com.zgq.wokao.module.search.entity;

import com.zgq.wokao.entity.paper.info.ExamPaperInfo;
import com.zgq.wokao.entity.paper.question.IQuestion;
import com.zgq.wokao.entity.paper.question.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SearchQuestionItem implements Searchable {
    private ExamPaperInfo info;
    private QuestionType questionType;
    private int questionId;
    private IQuestion question;
}
