/*
 * @(#)EntityParser.java 2013-3-2 下午01:03:53 XML2SQL
 */
package com.xml2sql.parser.dom;

import java.util.List;
import java.util.UUID;

import org.dom4j.Element;

import com.xml2sql.model.Author;
import com.xml2sql.model.BaseEntity;

/**
 * 实体对象解析器
 * @author wang
 * @version 1.0
 *
 */
public class EntityParser {

    /**
     * 从XML元素对象中解析出实体对象
     * @param e XML元素对象
     * @return 实体对象
     */
    public BaseEntity parseEntity(Element e) {
        BaseEntity entity = new BaseEntity();
        this.parseAttribtueAuthor(entity, e);
        this.parseAttribtueKey(entity, e);
        this.parseAttribtuePaper(entity, e);
        this.parseAttribtueConference(entity, e);
        this.parseAttributeYear(entity, e);
        this.parseAttributeCite(entity, e);
        return entity;
    }

    private void parseAttribtueKey(BaseEntity entity, Element e) {
        entity.setKey(e.attributeValue("key"));
    }

    private void parseAttribtuePaper(BaseEntity entity, Element e) {
        if (e.element("title") != null) {
            entity.setPaper(e.elementTextTrim("title"));
        } else if (e.element("booktitle") != null) {
            entity.setPaper(e.elementTextTrim("booktitle"));
        }
    }

    @SuppressWarnings("unchecked")
    private void parseAttribtueAuthor(BaseEntity entity, Element e) {
        List<Element> authorElementList = (List<Element>) e.elements("author");
        for (Element authorElement : authorElementList) {
            Author author = new Author();
            author.setId(UUID.randomUUID().toString());
            author.setName(authorElement.getTextTrim());
            entity.getAuthorList().add(author);
        }
    }

    private void parseAttribtueConference(BaseEntity entity, Element e) {
        if (e.element("journal") != null) {
            entity.setConference(e.elementTextTrim("journal"));
        } else if (e.element("ee") != null) {
            entity.setConference(e.elementTextTrim("ee"));
        } else if (e.element("school") != null) {
            entity.setConference(e.elementTextTrim("school"));
        }
    }

    @SuppressWarnings("unchecked")
    private void parseAttributeCite(BaseEntity entity, Element e) {
        List<Element> citeElementList = (List<Element>) e.elements("cite");
        if (citeElementList != null) {
            for (Element citeElement : citeElementList) {
                entity.getCiteList().add(citeElement.getTextTrim());
            }
        }
    }

    private void parseAttributeYear(BaseEntity entity, Element e) {
        entity.setYear(e.elementText("year"));
        if (entity.getYear() == null || entity.getYear().equals("")) {
            entity.setYear("2013");
        }
    }
}
