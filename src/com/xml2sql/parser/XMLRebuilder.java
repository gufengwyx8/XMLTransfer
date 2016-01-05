/*
 * @(#)XMLBuilder.java 2013-5-2 下午11:15:28 XML2SQL
 */
package com.xml2sql.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.xml2sql.Resource;
import com.xml2sql.dao.jdbc.DataSource;
import com.xml2sql.util.PropertiesUtil;

/**
 * XMLBuilder
 * @author wang
 * @version 1.0
 *
 */
public class XMLRebuilder {

    /** 项目配置 */
    private Map<String, String> config;

    /** 数据库连接 */
    private Connection conn;

    public XMLRebuilder() {
        //读取配置文件
        this.config = PropertiesUtil
                .readProperties(Resource.PROPERTIES_FILE_PATH);
        //获取数据源对象
        DataSource dataSource = this.getDataSource();
        //初始化数据库连接对象
        this.initConnection(dataSource);
    }

    /**
     * 根据配置文件构造数据源对象
     * @return 数据源对象
     */
    private DataSource getDataSource() {
        DataSource dataSource = new DataSource();
        dataSource.setConnectionString(config
                .get(Resource.CONNECTION_STRING_KEY)
                + config.get(Resource.DATABASE_NAME_KEY));
        dataSource.setDriverName(config.get(Resource.DRIVER_NAME_KEY));
        dataSource.setUsername(config.get(Resource.USER_NAME_KEY));
        dataSource.setPassword(config.get(Resource.USER_PASSWORD_KEY));
        return dataSource;
    }

    /**
     * 初始化数据库连接对象
     * @param dataSource 数据源对象 
     */
    private void initConnection(DataSource dataSource) {
        try {
            Class.forName(dataSource.getDriverName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn = DriverManager.getConnection(
                dataSource.getConnectionString(), dataSource.getUsername(),
                dataSource.getPassword());
            //设置不自动提交SQL指令(批量提交)
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public void rebuild(){
        Set<Integer> set=new HashSet<Integer>();
        PreparedStatement ps=null;
    }
}
