package com.zgq.wokao.module.search.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

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

    public static final Parcelable.Creator<HistorySuggestion> CREATOR = new Parcelable.Creator<HistorySuggestion>() {
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
