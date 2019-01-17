package com.jd.appoint.web.order;

import com.jd.adminlte4j.model.UIModel;
import com.jd.adminlte4j.model.builder.TableBuilder;
import com.jd.adminlte4j.web.springmvc.ApiAdminController;
import com.jd.appoint.inner.man.ManAppointOrderFacade;
import com.jd.appoint.inner.man.dto.OrderDetailDTO;
import com.jd.appoint.inner.man.dto.OrderListDTO;
import com.jd.appoint.page.Page;
import com.jd.appoint.web.order.convert.OrderListConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;


/**
 * Created by luqiang3 on 2018/5/17.
 */
@Controller
@RequestMapping(value = "order")
public class OrderController extends ApiAdminController {

    private Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private ManAppointOrderFacade manAppointOrderFacade;
    private final String[] ORDER_LIST_SHOW_COLUMNS = new String[]{"created"};
    private final String[] ORDER_LIST_HIDDEN_COLUMNS = new String[]{"createdRange"};

    /**
     * 获得订单列表查询模型
     * @return
     */
    @RequestMapping("model")
    @ResponseBody
    public UIModel getOrderListModel(){
        return UIModel.success().formData(new OrderListDTO(), OrderListDTO.class);
    }

    /**
     * 订单列表查询接口
     * @param orderListDTO
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public UIModel getOrderList(OrderListDTO orderListDTO){
        //查询参数构建 start
        Map<String, String> searchMap = OrderListConvert.toSearchMap(orderListDTO);
        Page page = new Page();
        page.setSearchMap(searchMap);
        page.setPageSize(orderListDTO.getPageSize());
        page.setPageNumber(orderListDTO.getCurrentPage());
        //查询参数构建 end
        Page<OrderListDTO> response = manAppointOrderFacade.list(page);//调用jsf接口，查询订单列表结果
        TableBuilder builder = TableBuilder.newBuilder(OrderListDTO.class)
                                .isPage(true)
                                .pageSize(orderListDTO.getPageSize())
                                .data(response.getList())
                                .totalSize((int) response.getTotalCount())
                                .showColumn(ORDER_LIST_SHOW_COLUMNS)
                                .hiddenColumn(ORDER_LIST_HIDDEN_COLUMNS);
        return UIModel.success().tableData(builder.build());
    }

    /**
     * 通过预约订单号查询约单详情
     * @param id
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public UIModel getOrderDetail(Long id){
        OrderDetailDTO detail = manAppointOrderFacade.detail(id);
        return UIModel.success().formData(detail, OrderDetailDTO.class);
    }

}
