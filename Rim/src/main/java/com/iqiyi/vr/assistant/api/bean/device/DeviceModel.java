package com.iqiyi.vr.assistant.api.bean.device;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangyancong on 2017/8/31.
 */
@Getter
@Setter
public class DeviceModel implements Serializable {
    /**
     * 设备名称
     */
    private String deviceName;
    /**
     * 设备IP
     */
    private String deviceIp;
    /**
     * 设备ID
     */
    private String deviceId;
}
