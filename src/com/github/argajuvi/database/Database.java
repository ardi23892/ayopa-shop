package com.github.argajuvi.database;

import java.sql.*;

public class Database {

    private static Database instance;

    private final String host, dbName, username, password;
    private final int port;

    private Connection conn;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    private Database() {
        this.host = "localhost";
        this.port = 3306;
        this.dbName = "ayopa_shop";
        this.username = "root";
        this.password = "";
    }

    public void connect() throws SQLException {
        String urlFormat = "jdbc:mysql://%s:%d/%s";
        String connUrl = String.format(urlFormat, host, port, dbName);

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find MySQL driver");
        }

        this.conn = DriverManager.getConnection(connUrl, username, password);
    }

    public void disconnect() throws SQLException {
        if (conn == null) {
            return;
        }

        conn.close();
    }

    public ResultSet getResults(String sql, Object ...args) throws SQLException {
        PreparedStatement statement = conn.prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }

        return statement.executeQuery();
    }

    public void execute(String sql, Object ...args) {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                statement.setObject(i + 1, args[i]);
            }

            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
