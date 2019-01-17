package com.jd.appoint.inner.man.enums;

import com.jd.adminlte4j.model.Dict;
import com.jd.air.common.enums.IntEnum;
import com.jd.appoint.common.utils.EnumDisplay;
import com.jd.appoint.domain.enums.AppointStatusEnum;
import com.jd.appoint.domain.enums.EncryptTypeEnum;
import com.jd.appoint.domain.enums.ItemTypeEnum;
import com.jd.appoint.domain.enums.ServerTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luqiang3 on 2018/5/18.
 * 用于将domain定义的枚举，转换为man端页面可使用的枚举
 */
public class EnumUtils {

    /**
     * 订单状态转换
     * @return
     */
    public static List<Dict> getOrderStatus(){
        List<Dict> dicts = new ArrayList<>();
        for (AppointStatusEnum statusEnum : AppointStatusEnum.values()){
            dicts.add(Dict.build(statusEnum.getIntValue() + "", statusEnum.getAlias()));
        }
        return dicts;
    }

    /**
     * 服务类型转换
     * @return
     */
    public static List<Dict> getServerType(){
        List<Dict> dicts = new ArrayList<>();
        for(ServerTypeEnum typeEnum : ServerTypeEnum.values()){
            dicts.add(Dict.build(typeEnum.getIntValue() + "", typeEnum.getAlias()));
        }
        return dicts;
    }

    public static List<Dict> getEncrytType(){
        return getEnumMap(EncryptTypeEnum.class);
    }

    public static List<Dict> getItemType(){
        return getEnumMap(ItemTypeEnum.class);
    }

    public static <T extends IntEnum & EnumDisplay>  List<Dict> getEnumMap(Class<T> t){
        List<Dict> dicts = new ArrayList<>();
        for(T typeEnum : t.getEnumConstants()){
            dicts.add(Dict.build(typeEnum.getIntValue() + "", typeEnum.getName()));
        }
        return dicts;
    }
}
