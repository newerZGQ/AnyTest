package com.iqiyi.vr.assistant.api.bean.app;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangyancong on 2017/9/4.
 */

@Getter
@Setter
public class CaptureModel implements Parcelable {
    private String media_url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.media_url);
    }

    protected CaptureModel(Parcel in) {
        this.media_url = in.readString();
    }

    public static final Creator<CaptureModel> CREATOR = new Creator<CaptureModel>() {
        @Override
        public CaptureModel createFromParcel(Parcel source) {
            return new CaptureModel(source);
        }

        @Override
        public CaptureModel[] newArray(int size) {
            return new CaptureModel[size];
        }
    };
}
