package com.haichuang.lesusport.common.conveter;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 去除空格
 */
public class CustomerConveter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        if (StringUtils.isNotBlank(source)) {
            source = source.trim();
            return source;
        }
        return null;
    }
}
