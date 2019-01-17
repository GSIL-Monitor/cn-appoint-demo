package com.jd.appoint.web.test;

import com.jd.adminlte4j.model.UIModel;
import com.jd.adminlte4j.model.builder.TableBuilder;
import com.jd.appoint.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author lijizhen1@jd.com
 * @date 2018/5/16 10:06
 */
@Controller
@RequestMapping(value = "test")
public class TestConfigController extends BaseController {
    /**
     * @return
     */
    @RequestMapping(path = {"info"})
    @ResponseBody
    public UIModel list(XModelQuery xModelQuery) throws Exception {
        TableBuilder builder = TableBuilder.newBuilder(XModel.class)
                .data(page(xModelQuery))
                .isPage(true)
                .totalSize(100);
        return UIModel.success().tableData(builder.build());
    }

    private List<XModel> page(XModelQuery xModelQuery) throws Exception {
        List<XModel> xModels = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            XModel xModel = new XModel();
            xModel.setId(1);
            xModel.setAge(new Random().nextInt(1));
            xModel.setBirthDay(new Date());
            xModel.setInputName("dddd");
            xModel.setContact("内存");
            xModel.setEducatoin(2);
            xModel.setLove("是");
            xModel.setMyIcon("i99");
            xModel.setName("Janle" + i);
            xModels.add(xModel);
        }
        return xModels.subList(0, xModels.size() >= 20 ? 20 : xModels.size());
    }

}
