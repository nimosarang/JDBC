package com.kosta.sjk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class FindBook {
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String user = "eddy";
    private String pwd = "oarcle";
    private String driver = "oracle.jdbc.driver.OracleDriver";

    public List<BookVo> getList() throws ClassNotFoundException, SQLException {

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String sql = "SELECT b.BOOK_ID ,b.TITLE ,b.PUBS ,b.PUB_DATE ,b.AUTHOR_ID  FROM BOOK b ,AUTHOR a\n" +
                "WHERE b.AUTHOR_ID = a.AUTHOR_ID";

        Class.forName("oracle.jdbc.driver.OracleDriver");

        Connection con = DriverManager.getConnection(url, "eddy", "oracle");
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(sql);

        List<BookVo> list = new ArrayList<>();

        while (rs.next()) {
            int bookId = rs.getInt("book_id");
            String title = rs.getString("title");
            String pubs = rs.getString("pubs");
            String pubDate = rs.getString("pub_date");
            int authorId = rs.getInt("author_id");

            BookVo bookVo = new BookVo(
                    bookId,
                    title,
                    pubs,
                    pubDate,
                    authorId
            );
            list.add(bookVo);
        }

        rs.close();
        st.close();
        con.close();

        return list;
    }

    public int insert(BookVo bookVo) throws SQLException, ClassNotFoundException {

        String title = bookVo.getTitle();
        String pubs = bookVo.getPubs();
        String pubDate = bookVo.getPub_date();
        int authorId = bookVo.getAuthor_id();

        String sql = "INSERT INTO BOOK VALUES (  " +
                "seq_book_id.nextval, ?, ?, TO_DATE(?, 'YYYY/MM/DD'), ? )";

        Class.forName(driver);
        Connection con = DriverManager.getConnection(url, user, pwd);
        PreparedStatement st = con.prepareStatement(sql);

        st.setString(1, title);
        st.setString(2, pubs);
        st.setString(3, pubDate);
        st.setInt(4, authorId);

        int result = st.executeUpdate();
        System.out.println(result);

        st.close();
        con.close();

        return result;
    }

    public int update() throws ClassNotFoundException, SQLException, IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String str = bf.readLine();
        StringTokenizer stringTokenizer = new StringTokenizer(str, " ");

        String title = stringTokenizer.nextToken();
        String pubs = stringTokenizer.nextToken();
        String pubDate = stringTokenizer.nextToken();
        int authorId = Integer.parseInt(stringTokenizer.nextToken());
        int bookId = Integer.parseInt(stringTokenizer.nextToken());

        String sql = "UPDATE BOOK SET TITLE = ?, PUBS = ?,PUB_DATE = ?,AUTHOR_ID = ? \n" +
                "WHERE BOOK_ID = ?";

        Class.forName(driver);
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        Connection con = DriverManager.getConnection(url, "eddy", "oracle");
        PreparedStatement st = con.prepareStatement(sql);
        st.setString(1, title);
        st.setString(2, pubs);
        st.setString(3, pubDate);
        st.setInt(4, authorId);
        st.setInt(5,bookId);


        int result = st.executeUpdate();
        System.out.println(result);

        st.close();
        con.close();

        return result;
    }

    public int delete(int bookId) throws ClassNotFoundException, SQLException {

        String sql = "DELETE FROM BOOK b WHERE BOOK_ID = ?";

        Class.forName(driver);

        Connection con = DriverManager.getConnection(url, user, pwd);
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1, bookId);


        int result = st.executeUpdate();
        System.out.println(result);

        st.close();
        con.close();

        return result;
    }
}

