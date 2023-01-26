package jdbc;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1.JDBC 드라이버 (Oracle) 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");

            //2.Connection 얻어오기
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            connection = DriverManager.getConnection(url, "eddy", "oracle");
            System.out.println("접속 성공");

            //3.SQL 문 준비 (/ 바인딩 / 실행)
            String query = "select author_id, author_name name, author_desc from author";
            preparedStatement = connection.prepareStatement(query); //SQL 문 준비단계,객체 생성

            resultSet = preparedStatement.executeQuery(); //실행

            //4.결과처리
            while (resultSet.next()) { //resultSet.next() 데이터가 있어요, 데이터가 없을때까지 반복하기위함
                int authorId = resultSet.getInt("author_id"); //컬럼명을 인트값으로 받아오겠다
                String authorName = resultSet.getString("name"); //Alias 명으로도 꺼낼 수 있다
                String authorDese = resultSet.getString("author_desc");
                System.out.println(authorId + "\t" + authorName + "\t" + authorDese + "\t");
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


    }
}
