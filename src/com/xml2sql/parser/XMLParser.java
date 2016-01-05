/*
 * @(#)XMLParser.java 2013-3-1 下午05:00:44 XML2SQL
 */
package com.xml2sql.parser;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;

import com.xml2sql.Resource;
import com.xml2sql.dao.EntityDao;
import com.xml2sql.model.BaseEntity;
import com.xml2sql.parser.dom.EntityParser;
import com.xml2sql.util.FileSplitter;
import com.xml2sql.util.PropertiesUtil;

/**
 * XMLParser
 * @author wang
 * @version 1.0
 *
 */
public class XMLParser {

    //proceedings article inproceedings phdthesis mastersthesis www book incollection

    private Map<String, String> config;

    private SAXReader reader;

    private EntityParser entityParser = new EntityParser();

    private EntityDao entityDao = new EntityDao();

    public XMLParser() {
        this.config = PropertiesUtil
                .readProperties(Resource.PROPERTIES_FILE_PATH);
        this.initXMLReader();
    }

    /**
     * 初始化XML解析器
     */
    private void initXMLReader() {
        reader = new SAXReader();
        try {
            reader.setFeature(Resource.LOAD_DTD_URL, false);
        } catch (SAXException e) {
            e.printStackTrace();
        }
        if (Resource.SAX_STYLE
                .equals(config.get(Resource.XML_READER_STYLE_KEY))) {
            this.initElementHandler();
        }
    }

    /**
     * 初始化SAX事件委托对象
     */
    private void initElementHandler() {

    }

    /**
     * 开始数据迁移
     */
    public void migrate() {
        long start = System.currentTimeMillis();
        System.out.println("开始拆分文件");
        List<File> fileList = this.splitFile();
        for (File file : fileList) {
            System.out.println("开始解析文件" + file.getName());
            Document doc = null;
            try {
                doc = reader.read(file);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            if (Resource.DOM_STYLE.equals(config
                    .get(Resource.XML_READER_STYLE_KEY))) {
                //DOM解析方式
                this.parseEntityFromDocument(doc);
            } else if (Resource.SAX_STYLE.equals(config
                    .get(Resource.XML_READER_STYLE_KEY))) {
                //SAX解析方式
            }
        }
        entityDao.executeBatch();
        long end = System.currentTimeMillis();
        if (Boolean.TRUE.toString().equals(config.get(Resource.DEBUG_KEY))) {
            System.out.println("cast " + (end - start) + "ms");
        }
    }

    /**
     * 拆分XML文件
     * @return 拆分完的文件对象集合
     */
    private List<File> splitFile() {
        FileSplitter splitter = new FileSplitter(Integer.parseInt(config
                .get(Resource.MAX_LINE_KEY)));
        return splitter.splitFile(config.get(Resource.XML_FOLDER_PATH_KEY),
            config.get(Resource.XML_FILE_PATH_KEY));
    }

    /**
     * 从XML文档对象中解析出实体集合
     * @param doc XMl文档对象
     * @return 实体集合 
     */
    @SuppressWarnings("unchecked")
    private void parseEntityFromDocument(Document doc) {
        for (Element e : (List<Element>) doc.getRootElement().elements()) {
            BaseEntity entity = entityParser.parseEntity(e);
            entityDao.save(entity);
        }
    }

    public static void main(String[] args) {
        new XMLParser().migrate();
    }
}
