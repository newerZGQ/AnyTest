package com.zgq.wokao.module.search.entity;

import com.zgq.wokao.entity.paper.info.ExamPaperInfo;

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
public class SearchInfoItem implements Searchable {
    private String paperId;
    private ExamPaperInfo info;
}
