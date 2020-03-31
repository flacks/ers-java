package com.revature.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionUtil {
    private static Connection connection = null;
    private static Logger logger = LogManager.getLogger(ConnectionUtil.class);

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

                logger.info("Loaded database credentials");
            } catch (IOException e) {
                logger.error("Error trying to load database credentials: " + e);
                e.printStackTrace();
            }

            connection = DriverManager.getConnection(
                    credentials[0],
                    credentials[1],
                    credentials[2]
            );

            logger.info("Connected to database");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error getting connection to database: " + e);
            e.printStackTrace();
        }

        return connection;
    }
}
