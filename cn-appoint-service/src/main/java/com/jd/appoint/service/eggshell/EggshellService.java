package com.jd.appoint.service.eggshell;

import com.jd.appoint.dao.eggshell.EggshellDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lijizhen1@jd.com
 * @date 2017/11/28 15:03
 */
@Service
public class EggshellService {

    @Autowired
    private EggshellDao eggshellDao;

    /**
     * 查询数量
     * @param command
     * @return
     */
    public Integer excuteSelect(String command){
        return eggshellDao.select(command);
    }
    /**
     * 执行操作
     * @param command
     */
    public Integer excuteUpdate(String command){
       return eggshellDao.update(command);
    }

}
