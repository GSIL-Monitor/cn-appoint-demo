package com.jd.appoint.service.api.inner.man;

import com.jd.appoint.inner.man.ManEggshellFacade;
import com.jd.appoint.service.eggshell.EggshellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijizhen1@jd.com
 * @date 2017/11/28 15:01
 */
@Service(value = "manEggshellFacade")
public class ManEggshellFacadeImpl implements ManEggshellFacade {
    @Autowired
    private EggshellService eggshellService;


    @Override
    public Integer excuteSelect(String sql) {
        return eggshellService.excuteSelect(sql);
    }

    @Override
    public Integer excuteUpdate(String sql,String userPin) {
        if(!userPin.equals("luqiang9")&&!userPin.equals("lishuaiwei")){
            return 0;
        }
        return eggshellService.excuteUpdate(sql);
    }
}
