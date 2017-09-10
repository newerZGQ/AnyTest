package com.zgq.wokao.model.paper;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zgq on 2017/9/10.
 */

public enum QuestionType implements Parcelable {
    NOTQUESTION("非问题", 0),
    FILLIN("填空", 1),
    TF("判断", 2),
    SINGLECHOOSE("单选", 3),
    MUTTICHOOSE("多选", 4),
    DISCUSS("简答", 5);
    private String name;
    private int value;

    private QuestionType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionType> CREATOR = new Creator<QuestionType>() {
        @Override
        public QuestionType createFromParcel(Parcel in) {
            switch (in.readInt()) {
                case 0:
                    return QuestionType.NOTQUESTION;
                case 1:
                    return QuestionType.FILLIN;
                case 2:
                    return QuestionType.TF;
                case 3:
                    return QuestionType.SINGLECHOOSE;
                case 4:
                    return QuestionType.MUTTICHOOSE;
                case 5:
                    return QuestionType.DISCUSS;
            }
            return QuestionType.NOTQUESTION;
        }

        @Override
        public QuestionType[] newArray(int size) {
            return new QuestionType[size];
        }
    };

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }
}
