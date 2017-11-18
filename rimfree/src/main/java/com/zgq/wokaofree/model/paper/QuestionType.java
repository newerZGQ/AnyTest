package com.zgq.wokaofree.model.paper;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
        dest.writeInt(getValue());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QuestionType> CREATOR = new Creator<QuestionType>() {
        @Override
        public QuestionType createFromParcel(Parcel in) {
            int value = in.readInt();
            QuestionType type = NOTQUESTION;
            switch (value){
                case 1:
                    type = FILLIN;
                    break;
                case 2:
                    type = TF;
                    break;
                case 3:
                    type = SINGLECHOOSE;
                    break;
                case 4:
                    type = MUTTICHOOSE;
                    break;
                case 5:
                    type = DISCUSS;
                    break;
                default:
                    break;
            }
            return type;
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
