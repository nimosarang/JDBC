package com.kosta.jdbcDao.sjk;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {
  private Connection conn = null;
  private PreparedStatement pstmt = null;
  private ResultSet rs = null;

  public Connection getConnection() {
    Connection conn = null;
    try {
      Class.forName("oracle.jdbc.driver.OracleDriver");
      String dburl = "jdbc:oracle:thin:@localhost:1521:xe";
      conn = DriverManager.getConnection(dburl, "webdb", "1234");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return conn;
  }

  @Override
  public AccountVO getBalance() {
    AccountVO accountVO = new AccountVO();
    try {
      conn = getConnection();
      String sql = " select balance " + " from account \n" + " where seq_id = (select max(seq_id) from account)";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {

        accountVO.setBalance(rs.getInt(1));
      }
    } catch (SQLException e) {
      System.err.println("ERROR : " + e.getMessage());
    }
    return accountVO;
  }

  @Override
  public List<AccountVO> getListAll() {
    AccountVO accountVO = null;
    List<AccountVO> list = new ArrayList<>();

    try {
      conn = getConnection();
      String sql = " select seq_id, deposit, withdraw, " + " to_char(tr_date, 'YYYYMMDD'), balance \n"
          + " from account \n";
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        // AccountVO(int seq_id, int deposit, int withdraw, String tr_date, int balance)
        // {
        accountVO = new AccountVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
        list.add(accountVO);
      }
    } catch (SQLException e) {
      System.err.println("ERROR : " + e.getMessage());
    } finally {
      try {
        if (rs != null) { rs.close(); }
        if (pstmt != null) { pstmt.close(); }
        if (conn != null) { conn.close(); }
      } catch (Exception e) {
        System.err.println("ERROR : " + e.getMessage());
      }
    }
    return list;
  }

  @Override
  public List<AccountVO> getList(String searchStartDate, String searchEndDate) {
    AccountVO accountVO = null;
    List<AccountVO> list = new ArrayList<>();

    try {
      conn = getConnection();
      String sql = " select seq_id, deposit, withdraw, " 
                 + "        to_char(tr_date, 'YYYYMMDD'), balance \n"
                 + " from   account \n" 
                 + " WHERE  TR_DATE >= TO_DATE(?, 'YYYYMMDD') \n"
                 + "   and  TR_DATE <= TO_DATE(?, 'YYYYMMDD') ";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, searchStartDate);
      pstmt.setString(2, searchEndDate);
      
      rs = pstmt.executeQuery();
      while (rs.next()) {
        accountVO = new AccountVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
        list.add(accountVO);
      }
    } catch (SQLException e) {
      System.err.println("ERROR : " + e.getMessage());
    } finally {
      try {
        if (rs != null) { rs.close(); }
        if (pstmt != null) { pstmt.close(); }
        if (conn != null) { conn.close(); }
      } catch (Exception e) {
        System.err.println("ERROR : " + e.getMessage());
      }
    }
    return list;
  }

  @Override
  public List<AccountVO> getList(String TradingDate) {
    AccountVO accountVO = null;
    List<AccountVO> list = new ArrayList<>();

    try {
      conn = getConnection();
      String sql = " select seq_id, deposit, withdraw, " 
                 + "        to_char(tr_date, 'YYYYMMDD'), balance \n"
                 + " from   account \n" 
                 + " WHERE  tr_date = to_date(?, 'YYYYMMDD') \n";
      pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, TradingDate);
      rs = pstmt.executeQuery();
      while (rs.next()) {
        accountVO = new AccountVO(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5));
        list.add(accountVO);
      }
    } catch (SQLException e) {
      System.err.println("ERROR : " + e.getMessage());
    } finally {
      try {
        if (rs != null) { rs.close(); }
        if (pstmt != null) { pstmt.close(); }
        if (conn != null) { conn.close(); }
      } catch (Exception e) {
        System.err.println("ERROR : " + e.getMessage());
      }
    }
    return list;
  }

  @Override
  public int insertTradeInfo(String depositWithdraw, Long money) {
    int insertedCount = 0;

    try {
      if ("deposit".equals(depositWithdraw)) {
        conn = getConnection();
        String sql = " INSERT INTO account \n" + " (seq_id, deposit, withdraw, tr_date, balance) \n"
            + " select ACCOUNT_SEQ_ID.nextval, ?, 0, sysdate, balance + ? \n" + " from account \n"
            + " where seq_id = (select max(seq_id) from account)";
        pstmt = conn.prepareStatement(sql);
        pstmt.setLong(1, money);
        pstmt.setLong(2, money);

        insertedCount = pstmt.executeUpdate();
      } else if ("withdraw".equals(depositWithdraw)) {
        AccountVO vo = getBalance();
        Long a = (long) vo.getBalance(); // 잔액
        if (money > a) {
          System.out.println("잔액이 부족합니다.");
        } else {
          conn = getConnection();
          String sql = " INSERT INTO account \n" + " (seq_id, deposit, withdraw, tr_date, balance) \n"
              + " select ACCOUNT_SEQ_ID.nextval, 0, ?, sysdate, balance - ? \n" + " from account \n"
              + " where seq_id = (select max(seq_id) from account)";
          pstmt = conn.prepareStatement(sql);
          pstmt.setLong(1, money);
          pstmt.setLong(2, money);

          insertedCount = pstmt.executeUpdate();
        }

      }
    } catch (SQLException e) {
      System.err.println("ERROR : " + e.getMessage());
    } finally {
      try {
        if (pstmt != null) { pstmt.close(); }
        if (conn != null) { conn.close(); }
      } catch (Exception e) {
        System.err.println("ERROR : " + e.getMessage());
      }
    }
    return insertedCount;
  }

}
