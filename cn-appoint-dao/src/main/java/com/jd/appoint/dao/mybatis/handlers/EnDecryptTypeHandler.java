package com.jd.appoint.dao.mybatis.handlers;

import com.jd.appoint.common.security.LocalSecurityClient;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 这里在上线跑时候如何判断是加密后的数据还是解密后的数据,对应的不同的类型需要不同的handle目前只支持String的加解密
 * -思路1 可以使用解密异常。
 * Created by lijianzhen1 on 2017/3/27.
 */
public class EnDecryptTypeHandler extends BaseTypeHandler {
    private static Logger logger = LoggerFactory.getLogger(EnDecryptTypeHandler.class);
    private LocalSecurityClient localSecurityClient;

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        //这里处理加密操作
        try {
            ps.setString(i, this.localSecurityClient.encrypt(parameter.toString()));
        } catch (Exception e) {
            logger.warn("加密出现问题parameter={}，具体异常e={}", parameter, e);
        }
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String str = rs.getString(columnName);
        return decryptStr(str);
    }

    private String decryptStr(String str) {
        return this.localSecurityClient.decrypt(str);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String str = rs.getString(columnIndex);
        return decryptStr(str);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String str = cs.getString(columnIndex);
        return decryptStr(str);
    }

    public void setLocalSecurityClient(LocalSecurityClient localSecurityClient) {
        this.localSecurityClient = localSecurityClient;
    }
}
