/*
 * @(#)BaseEntity.java 2013-3-1 下午07:07:03 XML2SQL
 */
package com.xml2sql.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 实体类
 * BaseEntity
 * @author wang
 * @version 1.0
 *
 */
public class BaseEntity {
    
    /** 主键 */
    private String key;

    /** 作者集合 */
    private List<Author> authorList = new ArrayList<Author>();

    private String paper;

    private String year;

    private String conference;

    /** 引用文章的主键集合 */
    private List<String> citeList = new ArrayList<String>();

    /**
     * 返回  key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置 key
     * @param key key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 返回  authorList
     * @return authorList
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * 设置 authorList
     * @param authorList authorList
     */
    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    /**
     * 返回  paper
     * @return paper
     */
    public String getPaper() {
        return paper;
    }

    /**
     * 设置 paper
     * @param paper paper
     */
    public void setPaper(String paper) {
        this.paper = paper;
    }

    /**
     * 返回  year
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * 设置 year
     * @param year year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * 返回  conference
     * @return conference
     */
    public String getConference() {
        return conference;
    }

    /**
     * 设置 conference
     * @param conference conference
     */
    public void setConference(String conference) {
        this.conference = conference;
    }

    /**
     * 返回  citeList
     * @return citeList
     */
    public List<String> getCiteList() {
        return citeList;
    }

    /**
     * 设置 citeList
     * @param citeList citeList
     */
    public void setCiteList(List<String> citeList) {
        this.citeList = citeList;
    }
}
