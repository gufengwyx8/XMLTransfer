package com.xml2sql.dao.jdbc;

/**
 * DataSource 数据源对象
 * 
 * @author zfz
 * @version 1.0
 * 
 */
public class DataSource {
    /** 驱动名 */
    private String driverName;
    /** 连接字符串 */
    private String connectionString;
    /** 用户名 */
    private String username;
    /** 密码 */
    private String password;

    /**
     * 返回 driverName
     * 
     * @return driverName
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * 设置 driverName
     * 
     * @param driverName
     *            driverName
     */
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    /**
     * 返回 connectionString
     * 
     * @return connectionString
     */
    public String getConnectionString() {
        return connectionString;
    }

    /**
     * 设置 connectionString
     * 
     * @param connectionString
     *            connectionString
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    /**
     * 返回 username
     * 
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置 username
     * 
     * @param username
     *            username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 返回 password
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置 password
     * 
     * @param password
     *            password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
