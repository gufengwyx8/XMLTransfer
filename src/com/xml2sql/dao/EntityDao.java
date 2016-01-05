/*
 * @(#)EntityDao.java 2013-3-14 下午03:48:20 XML2SQL
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
 * 数据库访问对象
 * @author wang
 * @version 1.0
 *
 */
public class EntityDao {

    /** 项目配置 */
    private Map<String, String> config;

    /** 数据库连接 */
    private Connection conn;

    /** 预编译指令对象，每个操作一个表(发送SQL) */
    private PreparedStatement psAuthor, psPaper, psYear, psConference,
            psAuthorPaper, psPaperPaper, psPaperYear, psYearConference;

    /** 缓冲区中SQL指令条数 */
    private int batchCount;

    /** 缓冲区最大SQL指令 */
    private int maxCount;

    /** 提交指令次数 */
    private int count;

    public EntityDao() {
        //读取配置文件
        this.config = PropertiesUtil
                .readProperties(Resource.PROPERTIES_FILE_PATH);
        //获取数据源对象
        DataSource dataSource = this.getDataSource();
        //初始化数据库连接对象
        this.initConnection(dataSource);
        //初始化预编译指令对象
        this.initPreparedStatement();
        //读取缓冲区最大空间大小
        maxCount = Integer.parseInt(config.get(Resource.MAX_BATCH_COUNT_KEY));
        count = 0;
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

    /**
     * 初始化预编译指令对象
     */
    private void initPreparedStatement() {
        //初始化SQL缓冲区大小
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
     * 根据实体对象添加一组SQL指令，当SQL指令缓冲区中条数多余预定义最大值时，提交数据
     * @param entity 实体对象
     */
    public void save(BaseEntity entity) {
        //添加SQL指令
        this.saveToAuthor(entity);
        this.saveToAuthorPaper(entity);
        this.saveToConference(entity);
        this.saveToPaper(entity);
        this.saveToPaperPaper(entity);
        this.saveToPaperYear(entity);
        this.saveToYear(entity);
        this.saveToYearConference(entity);
        //当SQL指令缓冲区中条数多余预定义最大值时，提交数据
        batchCount++;
        if (batchCount > maxCount) {
            System.out.println("开始插入第" + (count + 1) + "次("
                    + ((long) count + 1) * maxCount + "条)数据");
            this.executeBatch();
            System.out.println("完成插入第" + (++count) + "次(" + ((long) count)
                    * maxCount + "条)数据");
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
     * 添加一条SQL指令
     * @param ps 预编译指令对象
     * @param value1 第一个参数值
     * @param value2 第二个参数值
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
     * 将所有缓冲区中SQL指令提交到数据库中
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
        //重新初始化预编译指令
        this.initPreparedStatement();
    }

    /**
     * 将目标预编译指令对象中SQL指令提交到数据库中
     * @param ps 预编译指令对象
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
