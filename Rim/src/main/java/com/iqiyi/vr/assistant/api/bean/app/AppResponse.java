package com.iqiyi.vr.assistant.api.bean.app;

import com.iqiyi.vr.assistant.api.bean.BaseResponse;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangyancong on 2017/9/1.
 */
@Getter
@Setter
public class AppResponse extends BaseResponse<AppResponse.AppList> {

    @Getter
    @Setter
    public class AppList implements Serializable {
        private List<AppModel> list;
    }
}
