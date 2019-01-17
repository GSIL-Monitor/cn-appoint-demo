package com.jd.appoint.service.api.outer;

import com.jd.appoint.domain.enums.CheckCardResultEnum;
import com.jd.appoint.rpc.card.dto.CardDTO;
import com.jd.appoint.rpc.context.RpcContextService;
import com.jd.appoint.service.card.CardService;
import com.jd.appoint.service.card.convent.CardSkuConverter;
import com.jd.appoint.service.card.dto.SkuRequestDto;
import com.jd.travel.monitor.LogCollector;
import com.jd.travel.monitor.UmpMonitor;
import com.jd.travel.monitor.aspects.ServerEnum;
import com.jd.virtual.appoint.ValidateCodeGwService;
import com.jd.virtual.appoint.common.CommonRequest;
import com.jd.virtual.appoint.common.CommonResponse;
import com.jd.virtual.appoint.enums.StatusCode;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * TODO 实现对shop端校验的接口
 *
 * @author lijianzhen1
 * @date 2018/6/15
 */
@Service("validateCodeGwService")
public class ValidateCodeGwServiceImpl implements ValidateCodeGwService {
    private static final Logger logger = LoggerFactory.getLogger(ValidateCodeGwServiceImpl.class);
    @Resource
    private CardService cardService;
    @Resource
    private RpcContextService rpcContextService;

    /**
     * 卡密校验相关的操作
     *
     * @param commonRequest
     * @return
     */
    @Override
    @UmpMonitor(serverType = ServerEnum.NONE, logCollector =
    @LogCollector(description = "卡密校验接口", classify = ValidateCodeGwServiceImpl.class))
    public CommonResponse<Boolean> validateCode(CommonRequest commonRequest) {
        Map<String, String> maps = rpcContextService.getContext(commonRequest.getContextId());
        SkuRequestDto request = new SkuRequestDto();
        //业务代码不能为空
        //调用对应的商家进行返回校验结果
        CardDTO cardDTO = null;
        try {
            BeanUtils.populate(request, maps);
            //业务代码不能为空
            if (null == request.getBusinessCode()) {
                logger.warn("网关卡密校验businessCode不能为空");
                return new CommonResponse<>(StatusCode.BUSINESS_CODE_NIL_ERROR, true);
            }
            //商家id不能为空
            if (null == request.getVenderId()) {
                logger.warn("网关卡密校验venderId不能为空");
                return new CommonResponse<>(StatusCode.VENDERID_NIL_ERROR, true);
            }
            //校验卡号不能为空
            if (null == request.getCardNo()) {
                logger.warn("网关卡密校验校验卡号不能为空");
                return new CommonResponse<>(StatusCode.CODE_PWD_NIL_ERROR, true);
            }
            //转换卡密信息
            cardDTO = CardSkuConverter.convert(request);
            CheckCardResultEnum checked = cardService.checkCard(cardDTO);
            return new CommonResponse<>(convert(checked), false);
        } catch (Exception e) {
            logger.error("校验卡密sku的信息异常，e={}", e);
            new CommonResponse<>(StatusCode.EXTERNAL_SERVER_ERROR, false);
        }
        return new CommonResponse<>(StatusCode.EXTERNAL_SERVER_ERROR, false);
    }

    /**
     * 状态转换
     * @param result
     * @return
     */
    private StatusCode convert(CheckCardResultEnum result){
        switch (result){
            case SUCCESS : return StatusCode.SUCCESS;
            case FAIL : return StatusCode.CODE_INVALID_OR_ALREADY_USED;
            case CODE_PASSWORD_ERROR : return StatusCode.PASSWORD_NOT_EXISTS;
            case CODE_NOT_EXISTS : return StatusCode.CODE_NOT_EXISTS;
            case APPOINTED : return StatusCode.CODE_ALREADY_USED;
            case DISABLE : return StatusCode.CODE_INVALID;
            case EXPIRED : return StatusCode.CODE_OR_PASSWORD_EXPIRED;
            default : return StatusCode.CODE_INVALID_OR_ALREADY_USED;
        }
    }
}
