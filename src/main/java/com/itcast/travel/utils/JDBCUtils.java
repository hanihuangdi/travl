package com.itcast.travel.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    private  static DataSource ds;
    private static String url;
    private static String username;
    private static String password;
    private static String diver;
    static {
        Properties pro = new Properties();
        InputStream in = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  static DataSource getDatasource(){
        return ds;
    }
    // 4. 定义得到连接对象的方法
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    // 5.定义关闭资源的方法
    public static void close(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {}
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {}
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {}
        }
    }

    // 6.重载关闭方法
    public static void close(Connection conn, Statement stmt) {
        close(conn, stmt, null);
    }
}
