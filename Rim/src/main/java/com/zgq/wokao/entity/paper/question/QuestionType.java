package com.zgq.wokao.entity.paper.question;

import android.os.Parcel;
import android.os.Parcelable;

public enum QuestionType implements Parcelable {
    NOTQUESTION(0),
    FILLIN(1),
    TF(2),
    SINGLECHOOSE(3),
    MUTTICHOOSE(4),
    DISCUSS(5);
    private int value;

    private QuestionType(int value) {
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
            switch (value) {
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

    public static QuestionType parseFromValue(int value) {
        switch (value) {
            case 0:
                return NOTQUESTION;
            case 1:
                return FILLIN;
            case 2:
                return TF;
            case 3:
                return SINGLECHOOSE;
            case 4:
                return MUTTICHOOSE;
            case 5:
                return DISCUSS;
            default:
                return NOTQUESTION;
        }
    }
}
