package com.zgq.wokaofree.model.search;

import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.zgq.wokaofree.model.CascadeDeleteable;

/**
 * Created by zgq on 2017/2/15.
 */

public class HistorySuggestion implements SearchSuggestion {

    private String content;

    public HistorySuggestion(String content) {
        this.content = content;
    }

    public HistorySuggestion(Parcel in) {
        this.content = in.readString();
    }

    @Override
    public String getBody() {
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HistorySuggestion> CREATOR = new Creator<HistorySuggestion>() {
        @Override
        public HistorySuggestion createFromParcel(Parcel parcel) {
            return new HistorySuggestion(parcel);
        }

        @Override
        public HistorySuggestion[] newArray(int i) {
            return new HistorySuggestion[0];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
    }

}
