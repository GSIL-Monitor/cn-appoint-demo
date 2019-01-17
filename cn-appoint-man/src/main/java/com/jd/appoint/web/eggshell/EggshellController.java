package com.jd.appoint.web.eggshell;

import com.google.common.collect.Lists;
import com.jd.adminlte4j.model.UIModel;
import com.jd.adminlte4j.web.springmvc.ApiAdminController;
import com.jd.appoint.inner.man.ManEggshellFacade;
import com.jd.common.util.StringUtils;
import com.jd.common.web.LoginContext;
import com.jd.uim.annotation.Authorization;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by luqiang3 on 2018/5/28.
 */
@Controller
@RequestMapping(value = "eggshell")
public class EggshellController extends ApiAdminController {

    @Resource
    private ManEggshellFacade manEggshellFacade;

    @RequestMapping("model")
    @ResponseBody
    @Authorization("eggshell_model")
    public UIModel getEggshellModel(){
        return UIModel.success().formData(new EggshellModel(), EggshellModel.class);
    }

    /**
     * 后台彩蛋
     * @return
     */
    @RequestMapping(value = {"hitIt", "excute"})
    @ResponseBody
    @Authorization("eggshell_model_excute")
    public UIModel excute(@RequestBody EggshellModel eggshellModel) {
        String hasConfirm = eggshellModel.getHasConfirm();
        eggshellModel.setHasConfirm(null);
        if (StringUtils.isEmpty(eggshellModel.getCommand())) {
            return UIModel.success().formData(eggshellModel, EggshellModel.class);
        }
        String lowerCaseCommand = eggshellModel.getCommand().toLowerCase();
        //判断是否是指定的某个人
        if (lowerCaseCommand.startsWith("delete")) {
            eggshellModel.setError("不能执行删除权限！");
            return UIModel.success().formData(eggshellModel, EggshellModel.class);
        }
        List<String> tables = getTableNamesBySql(eggshellModel.getCommand());
        //如果操作的是关联的表不做处理
        if (lowerCaseCommand.startsWith("update") && tables.size() != 1) {
            eggshellModel.setError("检查您执行的语句是否正确！");
            return UIModel.success().formData(eggshellModel, EggshellModel.class);
        }


        //执行后显示执行影响的数据条数
        if (StringUtils.isBlank(hasConfirm)) {
            if(lowerCaseCommand.startsWith("update")) {
                //获得sql的条件
                String whereSql = eggshellModel.getCommand().substring(lowerCaseCommand.indexOf("where"));
                //执行查询语句影响范围让执行人员确认
                StringBuffer sb = new StringBuffer();
                sb.append("select count(*) from ");
                sb.append(tables.get(0));
                sb.append(" ");
                sb.append(whereSql);
                //要执行的数据
                eggshellModel.setInfo("将影响" + manEggshellFacade.excuteSelect(sb.toString()) + "条线上数据，再次点击提交完成执行");
            }
            eggshellModel.setHasConfirm("true");
        }
        //执行更新操作
        if (StringUtils.isNotBlank(hasConfirm) && (lowerCaseCommand.startsWith("update") ||
                lowerCaseCommand.startsWith("insert"))) {
            //执行原始的语句
            String[] commands = eggshellModel.getCommand().split(";");
            int total = 0;
            for(int i = 0; i < commands.length; i++){
                total += manEggshellFacade.excuteUpdate(commands[i], LoginContext.getLoginContext().getPin());
            }
            eggshellModel.setInfo("执行完成，影响" + total + "条线上数据");
        }
        return UIModel.success().formData(eggshellModel, EggshellModel.class);
    }

    /**
     * 通过传递的sql获得对应的的表名
     *
     * @param sql
     * @return
     */
    private List<String> getTableNamesBySql(String sql) {
        Pattern p = Pattern
                .compile(
                        "(?i)(?<=(?:from|into|update|join)\\s{1,1000}"
                                + "(?:w\\*[\\.]?\\w\\*(?:\\s{0,1000},\\s{0,1000})?)?" // 重复这里, 可以多个from后面的表
                                + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                + "(?:\\w{1,1000}(?:\\s{0,1000},\\s{0,1000})?)?"
                                + ")(\\w+)"
                );
        Matcher m = p.matcher(sql);
        List<String> tables = Lists.newArrayList();
        while (m.find()) {
            tables.add(m.group());
        }
        return tables;
    }

}
