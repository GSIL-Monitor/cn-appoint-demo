package com.jd.appoint.web.sys.converts;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jd.appoint.inner.man.dto.VenderConfigDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/28 20:09
 */
public class VenderConfigPO2VenderConfigDTO {
    private List<VenderConfigDTO> venderConfigPOs;
    private Map<String, List<VenderConfigDTO>> configInfoDTOS;
    //获得头标题
    List<String> header = new ArrayList<String>();

    public VenderConfigPO2VenderConfigDTO(List<VenderConfigDTO> venderConfigPOs) {
        this.venderConfigPOs = venderConfigPOs;
        this.configInfoDTOS = venderConfigPOs.stream()
                .collect(groupingBy(VenderConfigDTO::getBusinessCode));
        header.add("businessCode");
        header.add("venderId");
        this.header.addAll(venderConfigPOs.stream().map(v -> v.getCfgKey()).distinct().collect(Collectors.toList()));
    }

    public ManVenderConfigDTO convert() {
        ManVenderConfigDTO venderConfigDTOs = new ManVenderConfigDTO();
        venderConfigDTOs.setHeader(header);
        List<Map<String, Object>> content = Lists.newArrayList();
        configInfoDTOS.forEach((k, v) -> {
            //建立关键值
            List<VenderConfigDTO> venderConfigPOList = v;
            boolean hasRowSpan = false;
            //迭代放入数据
            for (VenderConfigDTO vcp : venderConfigPOList) {
                Map<String, Object> datas = Maps.newHashMap();
                //行号
                if (!hasRowSpan) {
                    hasRowSpan = true;
                    datas.put("rowspan", v.size());
                }
                datas.put("id", vcp.getId());
                datas.put("businessCode", k);
                datas.put("venderId", vcp.getVenderId());
                datas.put(vcp.getCfgKey(), vcp.getValue());
                content.add(datas);
            }
            //将解析的数据放入
            venderConfigDTOs.setContent(content);
        });
        return venderConfigDTOs;
    }
}
