package com.iqiyi.vr.assistant.api.bean.app;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by wangyancong on 2017/8/23.
 */

@Getter
@Setter
public final class AppModel implements Serializable {

    private long qipu_id;

    private String app_package_name;

    private String app_name;

    private byte app_type;

    private String developer_name;

    private int latest_version;

    private long total_download;
    /**
     * VR交互方式 1:鼠标 2:头部控制 3:游戏手柄
     */
    private String vr_interactive_ways;
    /**
     * app版本号名称（字符串，例如：1.7.14）
     */
    private String app_ver_name;
    /**
     * 应用图标
     */
    private String app_logo;
    /**
     * 应用包大小
     */
    private long app_package_size;
    /**
     * 下载地址
     */
    private String app_download_url;
    /**
     * 应用价格
     */
    private double app_price;
    /**
     * 应用简介
     */
    private String app_desc;

    private String app_desc_detail;

    //以下自定义元素
    /**
     * 0 下载, 1 更新, 2 安装, 3 卸载
     */
    private int app_state;
}
