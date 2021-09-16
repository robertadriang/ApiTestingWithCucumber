package util.database;


import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Slf4j
public class DbConnection {

  private Connection connection;


  public Connection connectToDatabase () {

    try {
      connection = DriverManager.getConnection(
          "jdbc:postgresql://127.0.0.1:5432/petclinic",
          "postgres",
          "qweasdzxc"
      );

    } catch (SQLException e) {
      log.error("Could not get database connection", e);
    }
    return connection;
  }
}
