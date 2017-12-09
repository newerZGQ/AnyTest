package com.zgq.wokao.entity.search;

import com.zgq.wokao.entity.CascadeDeleteable;
import com.zgq.wokao.module.search.entity.Searchable;

import io.realm.RealmObject;
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
    private String content;
    private String date;
    private int count;

    @Override
    public void cascadeDelete() {

    }
}
