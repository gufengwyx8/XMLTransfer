/*
 * @(#)Author.java 2013-3-14 ����04:28:31 XML2SQL
 */
package com.xml2sql.model;

/**
 * ����ʵ����
 * @author wang
 * @version 1.0
 *
 */
public class Author {
    
    /** ����(ͨ����ϣ������) */
    private String id;

    /** �������� */
    private String name;

    /**
     * ����  id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * ���� id
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ����  name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * ���� name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
}
