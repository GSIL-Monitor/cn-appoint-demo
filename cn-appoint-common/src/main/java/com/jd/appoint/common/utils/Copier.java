package com.jd.appoint.common.utils;


import com.google.common.base.Preconditions;

/**
 * Created by yangyuan on 6/7/18.
 */
public class Copier {

    /**
     * 枚举拷贝，按枚举名equals来判断
     * @param source
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T extends Enum>  T enumCopy(Enum source, Class<T> targetClass) {
        Preconditions.checkNotNull(source);
        Enum[] targetArray = targetClass.getEnumConstants();
        for(Enum t:targetArray) {
            if(source.name().equals(t.name())){
                return (T)t;
            }
        }
        return null;//throw new RuntimeException("not found");
    }
}
