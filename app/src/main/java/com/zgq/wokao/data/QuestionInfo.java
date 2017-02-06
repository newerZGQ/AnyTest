package com.zgq.wokao.data;

/**
 * Created by zgq on 16-6-19.
 */
public interface QuestionInfo {
    public void setType(String type);
    public String getType();
    public void setId(int id);
    public int getId();
    public boolean isStared();
    public void setStared(boolean stared);
    public boolean isStudied();
    public void setStudied(boolean studied);
}