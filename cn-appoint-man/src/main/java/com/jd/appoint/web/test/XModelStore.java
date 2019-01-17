package com.jd.appoint.web.test;

import com.jd.adminlte4j.support.store.BaseStore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bjliuyong on 2018/4/2.
 */
public class XModelStore implements BaseStore {

    private static final String XModel_FILE = "xmodel.s" ;

    private static XModelStore xModelStore = new XModelStore() ;

    private static List<XModel> xModels  ;

    static {
        try {
            xModels = getAll() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static synchronized void add(XModel xModel) throws IOException {
        xModel.setId(xModels.size() + 1);
        xModels.add(xModel) ;
        xModelStore.writeObject(xModels , XModel_FILE);
    }

    public static synchronized void  update(XModel xModel) throws Exception {
        int i = 0 ;
        for(XModel m : xModels ) {
            if(m.getId() == xModel.getId()) {
                xModels.set(i , xModel) ;
                xModelStore.writeObject(xModels , XModel_FILE);
                return;
            }
            i++ ;
        }
    }

    public static synchronized List<XModel> getAll() throws Exception {
        if(xModels == null) {
            List<XModel> loads = xModelStore.readListObject(XModel_FILE , XModel.class);
            if(loads != null) {
                xModels = loads ;
            } else {
                xModels = new ArrayList<>() ;
            }
        }
        return xModels ;
    }

    public static synchronized XModel findOne() {
        if(xModels.isEmpty())
            return  null ;
        return xModels.get(xModels.size() - 1 ) ;
    }

}
