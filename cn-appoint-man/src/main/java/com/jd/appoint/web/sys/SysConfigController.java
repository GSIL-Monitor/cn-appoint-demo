package com.jd.appoint.web.sys;

import com.alibaba.fastjson.JSONArray;
import com.jd.adminlte4j.model.UIModel;
import com.jd.appoint.inner.man.ManVenderConfigFacade;
import com.jd.appoint.inner.man.dto.InvokeMethod;
import com.jd.appoint.inner.man.dto.VenderConfigDTO;
import com.jd.appoint.inner.man.dto.VenderConfigFormDTO;
import com.jd.appoint.web.BaseController;
import com.jd.appoint.web.sys.converts.ManVenderConfigDTO;
import com.jd.appoint.web.sys.converts.VenderConfigPO2VenderConfigDTO;
import com.jd.appoint.web.sys.query.SysConfigModel;
import com.jd.appoint.web.sys.query.SysConfigQuery;
import com.jd.travel.base.soa.SoaRequest;
import com.jd.travel.base.soa.SoaResponse;
import com.jd.uim.annotation.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 10:06
 */
@Controller
@RequestMapping(value = "sysconfig")
public class SysConfigController extends BaseController {

    @Resource(name = "manVenderConfigFacade")
    private ManVenderConfigFacade manVenderConfigFacade;

    /**
     * 获得配置的查询模型
     *
     * @return
     */
    @RequestMapping("model")
    @ResponseBody
    public UIModel getOrderListModel() {
        return UIModel.success().formData(new SysConfigQuery(), SysConfigQuery.class);
    }

    /**
     * @return
     */
    @RequestMapping(path = {"", "list"})
    @ResponseBody
    @Authorization("sysconfig_list")
    public ManVenderConfigDTO list(SysConfigQuery sysConfigQuery) {
        VenderConfigFormDTO venderConfigFormDTO = new VenderConfigFormDTO();
        venderConfigFormDTO.setBusinessCode(sysConfigQuery.getBusinessCode());
        venderConfigFormDTO.setKey(sysConfigQuery.getKey());
        venderConfigFormDTO.setVenderId(sysConfigQuery.getVenderId());
        SoaResponse<List<VenderConfigDTO>> soaResponse =
                manVenderConfigFacade.getVenderConfig(new SoaRequest<>(venderConfigFormDTO));
        ManVenderConfigDTO manVenderConfigDTO =
                new VenderConfigPO2VenderConfigDTO(soaResponse.getResult()).convert();
        return manVenderConfigDTO;
    }


    /**
     * 到填单页面
     *
     * @return
     */
    @RequestMapping("/toAdd")
    @ResponseBody
    @Authorization("sysconfig_add")
    public UIModel toAdd() {
        return UIModel.success().formData(new SysConfigModel(), SysConfigModel.class);
    }

    /**
     * 添加操作
     *
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    @Authorization("sysconfig_add")
    public UIModel add(@RequestBody SysConfigModel sysConfigQueryModel) {
        //添加操作
        SoaRequest soaRequest = new SoaRequest<VenderConfigDTO>();
        VenderConfigFormDTO venderConfigDTO = new VenderConfigFormDTO();
        venderConfigDTO.setValue(sysConfigQueryModel.getValue());
        venderConfigDTO.setKey(sysConfigQueryModel.getKey());
        venderConfigDTO.setVenderId(sysConfigQueryModel.getVenderId());
        venderConfigDTO.setBusinessCode(sysConfigQueryModel.getBusinessCode());
        soaRequest.setData(venderConfigDTO);
        //执行添加操作
        SoaResponse soaResponse = manVenderConfigFacade.invoke(soaRequest, InvokeMethod.INVOKE_ADD);
        if (soaResponse.isSuccess()) {
            return UIModel.success().setMsg("添加成功");
        }
        return UIModel.success().setMsg("添加失败");
    }

    /**
     * 编辑操作
     *
     * @return
     */
    @RequestMapping("/toModify")
    @ResponseBody
    @Authorization("sysconfig_modify")
    public UIModel toModify(Long id) {
        SoaRequest soaRequest = new SoaRequest<VenderConfigDTO>();
        VenderConfigFormDTO venderConfigDTO = new VenderConfigFormDTO();
        venderConfigDTO.setId(id);
        soaRequest.setData(venderConfigDTO);
        SoaResponse<VenderConfigFormDTO> soaResponse = manVenderConfigFacade.invoke(soaRequest, InvokeMethod.INVOKE_SELECT);
        if (soaResponse.isSuccess()) {
            VenderConfigFormDTO vcDto = soaResponse.getResult();
            SysConfigModel sysConfigModel = new SysConfigModel();
            sysConfigModel.setBusinessCode(vcDto.getBusinessCode());
            sysConfigModel.setId(vcDto.getId());
            sysConfigModel.setKey(vcDto.getKey());
            sysConfigModel.setValue(vcDto.getValue());
            sysConfigModel.setVenderId(vcDto.getVenderId());
            sysConfigModel.setValue(vcDto.getValue());
            //编辑操作绑定数据
            return UIModel.success().formData(sysConfigModel, SysConfigModel.class);
        }
        //编辑操作绑定数据
        return UIModel.success().setMsg("没有找到该配置");
    }

    /**
     * 编辑操作
     *
     * @return
     */
    @RequestMapping("/modify")
    @ResponseBody
    @Authorization("sysconfig_modify")
    public UIModel modify(@RequestBody SysConfigModel sysConfigQueryModel) {
        //添加操作
        SoaRequest soaRequest = new SoaRequest<VenderConfigDTO>();
        VenderConfigFormDTO venderConfigDTO = new VenderConfigFormDTO();
        venderConfigDTO.setId(sysConfigQueryModel.getId());
        venderConfigDTO.setValue(sysConfigQueryModel.getValue());
        venderConfigDTO.setKey(sysConfigQueryModel.getKey());
        venderConfigDTO.setVenderId(sysConfigQueryModel.getVenderId());
        venderConfigDTO.setBusinessCode(sysConfigQueryModel.getBusinessCode());
        soaRequest.setData(venderConfigDTO);
        //执行添加操作
        SoaResponse soaResponse = manVenderConfigFacade.invoke(soaRequest, InvokeMethod.INVOKE_UPDATE);
        if (!soaResponse.isSuccess()) {
            logger.error("修改配置失败具体返回参数 soaResponse={}", JSONArray.toJSONString(soaRequest));
            return UIModel.success().setMsg("修改失败");
        }
        //修改操作
        return UIModel.success().setMsg("修改成功");
    }


    /**
     * 生成查询表单
     *
     * @return
     */
    @RequestMapping("/getQuery")
    @ResponseBody
    public UIModel getQuery() {
        return UIModel.success().formData(null, SysConfigQuery.class);
    }

    /**
     * @return
     */
    @RequestMapping("/submitQuery")
    @ResponseBody
    public UIModel submit() {
        return UIModel.success().formData(null, SysConfigQuery.class);
    }
}
