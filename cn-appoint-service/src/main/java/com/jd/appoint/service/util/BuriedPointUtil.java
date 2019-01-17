package com.jd.appoint.service.util;

import com.jd.appoint.common.utils.AppointDateUtils;
import com.jd.appoint.domain.enums.AppointUmpAlarmEnum;
import com.jd.appoint.domain.enums.BuriedRecordEnum;
import com.jd.appoint.domain.enums.ProcessTypeEnum;
import com.jd.appoint.domain.rpc.BuriedInfo;
import com.jd.fastjson.JSON;
import com.jd.pop.configcenter.client.StringUtils;
import com.jd.travel.monitor.alerts.UmpAlarmUtils;
import com.jd.xn.footprint.client.FootPrint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 预约信息埋点
 * Created by gaoxiaoqing on 2018/5/17.
 */
public class BuriedPointUtil {

    private static final Logger logger = LoggerFactory.getLogger(BuriedPointUtil.class);
    private static final Pattern pattern = Pattern.compile("\\{(.*?)\\}");
    private static Matcher matcher;
    private static final String START_APPOINT_FORMAT = "yyyy-MM-dd HH:mm";
    private static final String END_APPOINGT_FORMAT = "HH:mm";

    /**
     * 获取指定流程埋点内容
     * @param processType
     * @param param
     * @return
     */
    private static String getContent(int processType,Map<String,String> param){
        String content = ProcessTypeEnum.getRecordContent(processType);
        //无参数情况
        if(null == param || 0 == param.size()){
            return content;
        }
        matcher = pattern.matcher(content);
        while (matcher.find()){
            try {
                //获取占位项的名称
                String option = matcher.group();
                String optionName = option.substring(1,option.length() - 1).trim();
                //根据占位项名称获取参数对应内容
                Object value = param.get(optionName);
                if(null != value){
                    //替换占位项内容
                    content = content.replace(option,value.toString());
                }
            }catch (Exception e){
                logger.error("预约信息埋点异常,参数 = {}",param.toString(),e);
                UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_BURIED_POINT);
                return null;
            }
        }
        return content;
    }

    /**
     * 获取埋点内容
     * @param processType
     * @param param
     * @return
     */
    public static String getBurryContent(int processType,Map<String,String> param){
        //获取指定流程的埋点内容
        String buriedContent = getContent(processType,param);
        if(StringUtils.isEmpty(buriedContent)){
            logger.error("埋点内容为空，请核查!");
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_BURIED_POINT);
        }
        return buriedContent;
    }


    /**
     * 预约时间
     * @return
     */
    public static String getAppointTime(Date appointStartTime,Date appointEndTime){
        if(null == appointStartTime || null == appointEndTime){
            logger.error("埋点内容：预约时间不可为空!");
            UmpAlarmUtils.alarm(AppointUmpAlarmEnum.APPOINT_BURIED_POINT);
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AppointDateUtils.getDate2Str(START_APPOINT_FORMAT,appointStartTime));
        stringBuilder.append("-");
        stringBuilder.append(AppointDateUtils.getDate2Str(END_APPOINGT_FORMAT,appointEndTime));
        return stringBuilder.toString();
    }
}
