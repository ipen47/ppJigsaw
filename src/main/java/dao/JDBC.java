package dao;

import Enity.User;

import java.awt.print.Book;
import java.sql.*;

public class JDBC {
    Connection con; // 声明Connection对象
    String dbDriver = "com.mysql.jdbc.Driver"; // 注册驱动
    // 获取连接
    String url= "jdbc:mysql://localhost:3306/jigsaw_user?characterEncoding=utf-8"; // 3306:mysql端口号。 z:数据库名字。
    String user = "root"; // user 字段存储了数据库的用户名。
    String password = "2404772289lp"; // password 字段存储了数据库的密码。


    public void getConnection() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(dbDriver); // 1.加载驱动程序
            System.out.println("成功加载Mysql驱动程序!");
        } catch (ClassNotFoundException e) {
            System.out.print("加载Mysql驱动程序时出错!");
            e.printStackTrace();
        }
        try {
            con = DriverManager.getConnection(url, user, password); // 2.建立连接对象
            System.out.println("成功连接Mysql服务器!");
        } catch (SQLException e) {
            System.out.print("建立连接时出错!");
            e.printStackTrace();
        }
    }

    //查询数据库是否有用户信息数据
    public boolean queryuser(String username,String password) throws SQLException {

        //3.定义sql
        String sql = "select * from user where username= ? and password= ?";
        //4.创建获取执行sql的对象statement
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1,username);
        statement.setString(2, password);

        //5.执行sql
        ResultSet resultSet = statement.executeQuery();
        //如果查询到用户数据则将用户数据保存到User类
        if (resultSet.next()) {
            System.out.println("查询到用户数据!");
            return true;
        }else{
            //如果没有查询到用户信息则返回false
            System.out.println("没有查到用户数据");
            return false;
        }


    }
    //注册新用户数据
    public void insert(String username,String password) throws SQLException {
        String sql = "insert into user(username,password) values(?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        preparedStatement.setString(1,username);
        preparedStatement.setString(2, password);

        preparedStatement.executeUpdate();
        System.out.println("注册成功，成功向数据库添加了一条数据");
    }



}
