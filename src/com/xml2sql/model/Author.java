/*
 * @(#)Author.java 2013-3-14 下午04:28:31 XML2SQL
 */
package com.xml2sql.model;

/**
 * 作者实体类
 * @author wang
 * @version 1.0
 *
 */
public class Author {
    
    /** 主键(通过哈希码生成) */
    private String id;

    /** 作者名字 */
    private String name;

    /**
     * 返回  id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * 设置 id
     * @param id id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 返回  name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置 name
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }
}
