/*
 * @(#)EntityDao.java 2013-3-14 ����03:48:20 XML2SQL
 */
package com.xml2sql.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import com.xml2sql.Resource;
import com.xml2sql.dao.jdbc.DataSource;
import com.xml2sql.model.Author;
import com.xml2sql.model.BaseEntity;
import com.xml2sql.util.PropertiesUtil;

/**
 * ���ݿ���ʶ���
 * @author wang
 * @version 1.0
 *
 */
public class EntityDao {

    /** ��Ŀ���� */
    private Map<String, String> config;

    /** ���ݿ����� */
    private Connection conn;

    /** Ԥ����ָ�����ÿ������һ����(����SQL) */
    private PreparedStatement psAuthor, psPaper, psYear, psConference,
            psAuthorPaper, psPaperPaper, psPaperYear, psYearConference;

    /** ��������SQLָ������ */
    private int batchCount;

    /** ���������SQLָ�� */
    private int maxCount;

    /** �ύָ����� */
    private int count;

    public EntityDao() {
        //��ȡ�����ļ�
        this.config = PropertiesUtil
                .readProperties(Resource.PROPERTIES_FILE_PATH);
        //��ȡ����Դ����
        DataSource dataSource = this.getDataSource();
        //��ʼ�����ݿ����Ӷ���
        this.initConnection(dataSource);
        //��ʼ��Ԥ����ָ�����
        this.initPreparedStatement();
        //��ȡ���������ռ��С
        maxCount = Integer.parseInt(config.get(Resource.MAX_BATCH_COUNT_KEY));
        count = 0;
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

    /**
     * ��ʼ��Ԥ����ָ�����
     */
    private void initPreparedStatement() {
        //��ʼ��SQL��������С
        this.batchCount = 0;
        try {
            psAuthor = conn.prepareStatement("insert into Author values(?,?)");
            psPaper = conn.prepareStatement("insert into Paper values(?,?)");
            psYear = conn.prepareStatement("insert into Year values(?,?)");
            psConference = conn
                    .prepareStatement("insert into Conference values(?,?)");
            psAuthorPaper = conn
                    .prepareStatement("insert into Author_Paper values(?,?)");
            psPaperPaper = conn
                    .prepareStatement("insert into Paper_Paper values(?,?)");
            psPaperYear = conn
                    .prepareStatement("insert into Paper_Year values(?,?)");
            psYearConference = conn
                    .prepareStatement("insert into Year_Conference values(?,?)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * ����ʵ��������һ��SQLָ���SQLָ���������������Ԥ�������ֵʱ���ύ����
     * @param entity ʵ�����
     */
    public void save(BaseEntity entity) {
        //���SQLָ��
        this.saveToAuthor(entity);
        this.saveToAuthorPaper(entity);
        this.saveToConference(entity);
        this.saveToPaper(entity);
        this.saveToPaperPaper(entity);
        this.saveToPaperYear(entity);
        this.saveToYear(entity);
        this.saveToYearConference(entity);
        //��SQLָ���������������Ԥ�������ֵʱ���ύ����
        batchCount++;
        if (batchCount > maxCount) {
            System.out.println("��ʼ�����" + (count + 1) + "��("
                    + ((long) count + 1) * maxCount + "��)����");
            this.executeBatch();
            System.out.println("��ɲ����" + (++count) + "��(" + ((long) count)
                    * maxCount + "��)����");
        }
    }

    private void saveToAuthor(BaseEntity entity) {
        for (Author author : entity.getAuthorList()) {
            this.addBatch(psAuthor, author.getId(), author.getName());
        }
    }

    private void saveToPaper(BaseEntity entity) {
        this.addBatch(psPaper, entity.getKey(), entity.getPaper());
    }

    private void saveToYear(BaseEntity entity) {
        this.addBatch(psYear, entity.getYear(), null);
    }

    private void saveToConference(BaseEntity entity) {
        if (entity.getConference() != null) {
            this
                    .addBatch(psConference, entity.getKey(), entity
                            .getConference());
        }
    }

    private void saveToAuthorPaper(BaseEntity entity) {
        for (Author author : entity.getAuthorList()) {
            this.addBatch(psAuthorPaper, author.getId(), entity.getKey());
        }
    }

    private void saveToPaperPaper(BaseEntity entity) {
        for (String cite : entity.getCiteList()) {
            this.addBatch(psPaperPaper, entity.getKey(), cite);
        }
    }

    private void saveToPaperYear(BaseEntity entity) {
        this.addBatch(psPaperYear, entity.getKey(), entity.getYear());
    }

    private void saveToYearConference(BaseEntity entity) {
        if (entity.getConference() != null) {
            this.addBatch(psYearConference, entity.getYear(), entity
                    .getConference());
        }
    }

    /**
     * ���һ��SQLָ��
     * @param ps Ԥ����ָ�����
     * @param value1 ��һ������ֵ
     * @param value2 �ڶ�������ֵ
     */
    private void addBatch(PreparedStatement ps, String value1, String value2) {
        try {
            ps.setString(1, value1);
            ps.setString(2, value2);
            ps.addBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * �����л�������SQLָ���ύ�����ݿ���
     */
    public void executeBatch() {
        this.executeBatch(psAuthor);
        this.executeBatch(psAuthorPaper);
        this.executeBatch(psConference);
        this.executeBatch(psPaper);
        this.executeBatch(psPaperPaper);
        this.executeBatch(psPaperYear);
        this.executeBatch(psYear);
        this.executeBatch(psYearConference);
        try {
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //���³�ʼ��Ԥ����ָ��
        this.initPreparedStatement();
    }

    /**
     * ��Ŀ��Ԥ����ָ�������SQLָ���ύ�����ݿ���
     * @param ps Ԥ����ָ�����
     */
    private void executeBatch(PreparedStatement ps) {
        try {
            ps.executeBatch();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
