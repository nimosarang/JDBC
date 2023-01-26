package com.kosta.jdbc;

import jdbc.AuthorVo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {

    public Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
            conn = DriverManager.getConnection(dburl, "eddy", "oracle");
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC 드라이버 로드 실패!");
        }
        return conn;
    }

    @Override
    public List<BookVo> getList() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<BookVo> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = "SELECT b.BOOK_ID ,b.TITLE ,b.PUBS ,b.PUB_DATE,a.AUTHOR_NAME FROM BOOK b ,AUTHOR a \n"
                    + "WHERE b.AUTHOR_ID = a.AUTHOR_ID";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                BookVo vo = new BookVo(
                        rs.getLong("book_id"),
                        rs.getString("title"),
                        rs.getString("pubs"),
                        rs.getString("pub_date"),
                        rs.getString("author_name")
                );
                list.add(vo);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public List<BookVo> getSearchList(String keyWord) {

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<BookVo> list = new ArrayList<>();

        try {
            conn = getConnection();
            String sql = "SELECT b.BOOK_ID ,b.TITLE ,b.PUBS ,TO_CHAR(b.PUB_DATE,'YYYY/MM/DD')PUB_DATE ,a.AUTHOR_NAME  FROM BOOK b ,AUTHOR a\n" +
                        " WHERE b.AUTHOR_ID = a.AUTHOR_ID\n" +
                        " AND b.TITLE || b.PUBS ||a.AUTHOR_NAME LIKE ?";

            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + keyWord + "%");

            rs = ps.executeQuery();

            while (rs.next()) {
                BookVo vo = new BookVo(
                        rs.getLong("book_id"),
                        rs.getString("title"),
                        rs.getString("pubs"),
                        rs.getString("pub_date"),
                        rs.getString("author_name")
                );
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


}
