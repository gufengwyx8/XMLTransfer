/*
 * @(#)BaseEntity.java 2013-3-1 ����07:07:03 XML2SQL
 */
package com.xml2sql.model;

import java.util.ArrayList;
import java.util.List;

/**
 * ʵ����
 * BaseEntity
 * @author wang
 * @version 1.0
 *
 */
public class BaseEntity {
    
    /** ���� */
    private String key;

    /** ���߼��� */
    private List<Author> authorList = new ArrayList<Author>();

    private String paper;

    private String year;

    private String conference;

    /** �������µ��������� */
    private List<String> citeList = new ArrayList<String>();

    /**
     * ����  key
     * @return key
     */
    public String getKey() {
        return key;
    }

    /**
     * ���� key
     * @param key key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * ����  authorList
     * @return authorList
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * ���� authorList
     * @param authorList authorList
     */
    public void setAuthorList(List<Author> authorList) {
        this.authorList = authorList;
    }

    /**
     * ����  paper
     * @return paper
     */
    public String getPaper() {
        return paper;
    }

    /**
     * ���� paper
     * @param paper paper
     */
    public void setPaper(String paper) {
        this.paper = paper;
    }

    /**
     * ����  year
     * @return year
     */
    public String getYear() {
        return year;
    }

    /**
     * ���� year
     * @param year year
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * ����  conference
     * @return conference
     */
    public String getConference() {
        return conference;
    }

    /**
     * ���� conference
     * @param conference conference
     */
    public void setConference(String conference) {
        this.conference = conference;
    }

    /**
     * ����  citeList
     * @return citeList
     */
    public List<String> getCiteList() {
        return citeList;
    }

    /**
     * ���� citeList
     * @param citeList citeList
     */
    public void setCiteList(List<String> citeList) {
        this.citeList = citeList;
    }
}
