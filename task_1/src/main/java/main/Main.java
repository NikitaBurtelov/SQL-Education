package main;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.*;

public class Main {
    private static String userName = "root";
    private static String password = "root";
    private static String connectionUrl = "jdbc:mysql://localhost:3306/test?useSSL=false";

    public static void addDataBase(Statement statement) throws ParserConfigurationException, IOException, SQLException, ClassNotFoundException, MySQLSyntaxErrorException {
        System.out.println("Connection");
        statement.executeUpdate("drop table Books");
        statement.executeUpdate("create table if not exists Books (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, PRIMARY KEY (id))");
        statement.executeUpdate("insert into Books (name) values ('1984')");
        statement.executeUpdate("insert into Books set name = 'О дивный новый мир'");
    }


    public static void dataOutput(Statement statement) throws ParserConfigurationException, IOException, SQLException, ClassNotFoundException, MySQLSyntaxErrorException  {
        ResultSet resultSet = statement.executeQuery("select * from Books");

        while (resultSet.next()) {
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
            System.out.println("***************");
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SQLException, ClassNotFoundException, MySQLSyntaxErrorException {
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {

            addDataBase(statement);

            dataOutput(statement); //
        }
    }

}
