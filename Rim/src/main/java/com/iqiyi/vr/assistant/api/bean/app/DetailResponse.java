package com.iqiyi.vr.assistant.api.bean.app;

import com.iqiyi.vr.assistant.api.bean.BaseResponse;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangyancong on 2017/9/2.
 */
@Getter
@Setter
public class DetailResponse extends BaseResponse<DetailResponse.DetailModel> {

    @Getter
    @Setter
    public class DetailModel implements Serializable {

        private List<CaptureModel> res;
        private AppModel basic;
    }
}
