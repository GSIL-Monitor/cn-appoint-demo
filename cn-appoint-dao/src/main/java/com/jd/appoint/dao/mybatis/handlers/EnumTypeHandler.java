package com.jd.appoint.dao.mybatis.handlers;

import com.jd.air.common.enums.IntEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 处理使用mybatis时候不能查出enmu类和插入时候不能插入titint
 * Created by lijianzhen1 on 2017/3/16.
 */
public class EnumTypeHandler<E extends Enum<E> & IntEnum<E>> extends BaseTypeHandler<IntEnum> {
    private Class<E> clazz;

    public EnumTypeHandler(Class<E> enumType) {
        if (enumType == null)
            throw new IllegalArgumentException("enumType argument cannot be null");
        this.clazz = enumType;
    }

    /**
     * 将数据库映射的值转化为对应的枚举值
     *
     * @param code
     * @return
     */
    private IntEnum convert(int code) {
        IntEnum[] enumConstants = clazz.getEnumConstants();
        for (IntEnum im : enumConstants) {
            if (im.getIntValue() == code)
                return im;
        }
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, IntEnum intEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, intEnum.getIntValue());
    }

    @Override
    public IntEnum getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return convert(resultSet.getInt(columnName));
    }

    @Override
    public IntEnum getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return convert(resultSet.getInt(columnIndex));
    }

    @Override
    public IntEnum getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return convert(callableStatement.getInt(columnIndex));
    }
}
