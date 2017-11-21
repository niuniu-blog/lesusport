package com.haichuang.lesusport.service.login;

/**
 * 远程session提供类
 */
public interface SessionProvider {
    /**
     * 获取session
     *
     * @param key
     * @return
     */
    String getAttributeForUsername(String key);

    /**
     * 设置session
     *
     * @param key
     * @param value
     */
    void setAttributeForUsername(String key, String value);
}
