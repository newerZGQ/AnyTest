package com.zgq.wokao.module.search.entity;

import com.zgq.wokao.entity.CascadeDeleteable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
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
public class SearchHistory extends RealmObject implements Searchable, CascadeDeleteable {
    @PrimaryKey
    private String id;
    private String content;
    private String date;
    private int count;

    @Override
    public void cascadeDelete() {

    }
}
