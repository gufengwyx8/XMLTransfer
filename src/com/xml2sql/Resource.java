/*
 * @(#)Resource.java 2013-3-1 ÏÂÎç04:58:36 XML2SQL
 */
package com.xml2sql;

/**
 * Resource
 * @author wang
 * @version 1.0
 *
 */
public interface Resource {

    String LOAD_DTD_URL = "http://apache.org/xml/features/nonvalidating/load-external-dtd";

    String PROPERTIES_FILE_PATH = "config.properties";

    String XML_FOLDER_PATH_KEY = "XML_FOLDER_PATH";

    String XML_FILE_PATH_KEY = "XML_FILE_PATH";

    String CONNECTION_STRING_KEY = "CONNECTION_STRING";

    String DRIVER_NAME_KEY = "DRIVER_NAME";

    String DATABASE_NAME_KEY = "DATABASE_NAME";

    String USER_NAME_KEY = "USER_NAME";

    String USER_PASSWORD_KEY = "USER_PASSWORD";

    String XML_READER_STYLE_KEY = "XML_READER_STYLE";

    String MAX_LINE_KEY = "MAX_LINE";

    String SAX_STYLE = "sax";

    String DOM_STYLE = "dom";

    String DEBUG_KEY = "DEBUG";

    String MAX_BATCH_COUNT_KEY = "MAX_BATCH_COUNT";

}
