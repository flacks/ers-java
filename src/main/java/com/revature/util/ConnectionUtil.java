package com.revature.util;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static Connection connection = null;

    private ConnectionUtil() {
        super();
    }

    public static Connection getConnection() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            String[] credentials = new String[3];

//            System.out.println(new java.io.File( "." ).getCanonicalPath());

            // TODO: Use a cleaner method for passing database credentials
            try (FileReader reader = new FileReader(
                    "C:/Program Files/Apache Tomcat/webapps/ERS/WEB-INF/classes/db.properties")
            ) {
                Properties properties = new Properties();
                properties.load(reader);

                credentials[0] = properties.getProperty("database");
                credentials[1] = properties.getProperty("username");
                credentials[2] = properties.getProperty("password");
            } catch (IOException e) {
                e.printStackTrace();
            }

            connection = DriverManager.getConnection(
                    credentials[0],
                    credentials[1],
                    credentials[2]
            );
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
