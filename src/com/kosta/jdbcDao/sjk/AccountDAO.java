package com.kosta.jdbcDao.sjk;

import java.util.List;

public interface AccountDAO {
  public AccountVO getBalance();
  public List<AccountVO> getListAll();
  public List<AccountVO> getList(String searchStartDate, String searchEndDate);
  public List<AccountVO> getList(String TradingDate);
  public int insertTradeInfo(String tr, Long money);
}
