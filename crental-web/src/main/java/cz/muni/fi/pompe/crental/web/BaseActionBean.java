/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pompe.crental.web;

import java.util.Date;
import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import org.apache.taglibs.standard.functions.Functions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jozef
 */
public class BaseActionBean implements ActionBean{
    private ActionBeanContext context;
    
    protected Logger log;

    public BaseActionBean() {
        log = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void setContext(ActionBeanContext context) {
        this.context = context;
    }

    @Override
    public ActionBeanContext getContext() {
        return context;
    }

    public static String escapeHTML(String s) {
        return Functions.escapeXml(s);
    }
    
    /**
     * @return Date todays midnight timestamp
     */
    public Date calcToday() {
        Long time = new Date().getTime();
        return new Date(time - time % (24 * 60 * 60 * 1000 - 1));
    }
}
