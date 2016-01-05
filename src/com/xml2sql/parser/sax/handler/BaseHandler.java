/*
 * @(#)BaseHandler.java 2013-3-1 обнГ05:01:18 XML2SQL
 */
package com.xml2sql.parser.sax.handler;

import org.dom4j.ElementHandler;
import org.dom4j.ElementPath;

/**
 * BaseHandler
 * @author wang
 * @version 1.0
 *
 */
public class BaseHandler implements ElementHandler {

    @Override
    public void onEnd(ElementPath arg0) {
        
    }

    @Override
    public void onStart(ElementPath arg0) {
        
    }

    protected void insertToSql(Object entity) {
        
    }

}
