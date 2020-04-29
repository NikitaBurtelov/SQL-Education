package main;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.List;

public class Main {
    private static String userName = "root";
    private static String password = "root";
    private static String connectionUrl = "jdbc:mysql://localhost:3306/test?useSSL=false";

    public static void addDataBase(Statement statement) throws ParserConfigurationException, IOException, SQLException, ClassNotFoundException, MySQLSyntaxErrorException {
        System.out.println("Connection\n");
        statement.executeUpdate("drop table Users");
        statement.executeUpdate("create table if not exists Users (id MEDIUMINT NOT NULL AUTO_INCREMENT, name CHAR(30) NOT NULL, password CHAR(30) NOT NULL, PRIMARY KEY (id))");
        //statement.executeUpdate("insert into Users (name) values ('Никита')");
        statement.executeUpdate("insert into Users set name = 'user1', password = 'pas1'");
        statement.executeUpdate("insert into Users set name = 'user1', password = 'pas2'");
    }


    public static void allDataOutput(Statement statement) throws ParserConfigurationException, IOException, SQLException, ClassNotFoundException, MySQLSyntaxErrorException  {
        ResultSet resultSet = statement.executeQuery("select * from Users");
        printOut(resultSet);
    }

    public static void userDataOutput(Connection connection, String userId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("select * from Users where id = ?");
        preparedStatement.setString(1, userId);
        System.out.println("User:");
        ResultSet resultSet = preparedStatement.executeQuery();
        printOut(resultSet);
        System.out.println();
    }

    public static <T extends ResultSet> void printOut(T resultSet) throws SQLException {
        while (resultSet.next()) {
            System.out.println("id = " + resultSet.getInt("id"));
            System.out.println("userName = " + resultSet.getString("name"));
            System.out.println("userPassword = " + resultSet.getString("password"));
            System.out.println("***************\n");
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SQLException, ClassNotFoundException, MySQLSyntaxErrorException {
        Class.forName("com.mysql.jdbc.Driver");

        try (Connection connection = DriverManager.getConnection(connectionUrl, userName, password);
             Statement statement = connection.createStatement()) {
            System.out.print("Input userId: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String userId = reader.readLine();

            addDataBase(statement);

            userDataOutput(connection, userId);

            allDataOutput(statement);
        }
    }

}
