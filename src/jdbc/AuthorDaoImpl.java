package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuthorDaoImpl implements AuthorDao {
    private Connection getConnection() throws SQLException {
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
    public int insert(AuthorVo vo) {
        int count = 0;
        try {
            Connection conn = getConnection();
            String query = "INSERT INTO AUTHOR VALUES (seq_author_id.nextval, ?, ? )";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, vo.getAuthor_name());
            ps.setString(2, vo.getAuthor_desc());

            count = ps.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return count;
    }

    @Override
    public List<AuthorVo> getList() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        //데이터 전송을 위한 리스트
        List<AuthorVo> list = new ArrayList<>();

        try {
            conn = getConnection();
            stmt = conn.createStatement();
            String sql = " SELECT a.AUTHOR_ID ,   \n" +
                    "        a.AUTHOR_NAME , \n" +
                    "        a.AUTHOR_DESC   \n" +
                    " FROM AUTHOR a";

            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                AuthorVo vo = new AuthorVo(
                        rs.getInt("AUTHOR_ID"),
                        rs.getString("AUTHOR_NAME"),
                        rs.getString("AUTHOR_DESC")
                );
                list.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("ERROR:" + e.getMessage());
        }
        return list;
    }



    @Override
    public int delete(int authorId) {
        int count = 0;
        try {
            Connection conn = getConnection();
            String query = "DELETE FROM AUTHOR WHERE AUTHOR_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setLong(1, authorId);

            count = ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int update(AuthorVo vo) {
        int count = 0;
        try {
            Connection conn = getConnection();
            String query = " UPDATE AUTHOR a        \n"
                        + " SET a.AUTHOR_NAME = ?, \n"
                        + "     a.AUTHOR_DESC = ?  \n"
                        + " WHERE a.AUTHOR_ID = ?    ";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, vo.getAuthor_name());
            ps.setString(2, vo.getAuthor_desc());
            ps.setLong(3, vo.getAuthor_id());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

}
