/*
 * @(#)XMLBuilder.java 2013-5-2 ����11:15:28 XML2SQL
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

    /** ��Ŀ���� */
    private Map<String, String> config;

    /** ���ݿ����� */
    private Connection conn;

    public XMLRebuilder() {
        //��ȡ�����ļ�
        this.config = PropertiesUtil
                .readProperties(Resource.PROPERTIES_FILE_PATH);
        //��ȡ����Դ����
        DataSource dataSource = this.getDataSource();
        //��ʼ�����ݿ����Ӷ���
        this.initConnection(dataSource);
    }

    /**
     * ���������ļ���������Դ����
     * @return ����Դ����
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
     * ��ʼ�����ݿ����Ӷ���
     * @param dataSource ����Դ���� 
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
            //���ò��Զ��ύSQLָ��(�����ύ)
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
