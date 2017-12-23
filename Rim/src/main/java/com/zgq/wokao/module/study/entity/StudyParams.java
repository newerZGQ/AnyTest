package com.zgq.wokao.module.study.entity;

import com.zgq.wokao.entity.paper.question.QuestionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudyParams {
    private String paperId;
    private QuestionType questionType;
    private String questionId;
}
