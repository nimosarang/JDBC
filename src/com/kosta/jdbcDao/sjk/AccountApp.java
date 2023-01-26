package com.kosta.jdbcDao.sjk;

import java.util.List;
import java.util.Scanner;

public class AccountApp {

  public static void main(String[] args) {
//    AccountDAO dao = new AccountDAOImpl();
//    List<AccountVO> list =  dao.getListAll();
//    
//    for(AccountVO vo:list) {
//      System.out.println(vo);
//    }

    Scanner sc = new Scanner(System.in);
    AccountDAO a = new AccountDAOImpl();

    do {

      try {
        System.out.println("0.종료 1.예금 2.출금 3.잔액조회 4.거래일 조회 5.기간별 조회");
        System.out.println("---------------------------------------------------");

        String select = sc.nextLine();

        if (select.equals("0"))
          break;

        switch (Integer.parseInt(select)) {

        case 1:
          System.out.print("예금액>");
          String deposit = sc.nextLine();
          if (Integer.valueOf(deposit) <= 0) {
            System.out.println("예금액이 잘못되었습니다.");
          } else {
            a.insertTradeInfo("예금", Long.valueOf(deposit));
            System.out.println("예금이 완료되었습니다.");
          }
          break;

        case 2:
          System.out.println("출금액>");
          String withdraw = sc.nextLine();

          if (Integer.valueOf(withdraw) > a.getBalance().getBalance()) {
            System.out.println("출금액이 잘못되었습니다.");

          } else {
            a.insertTradeInfo("출금", Long.valueOf(withdraw));
            System.out.println("출금이 완료되었습니다.");

          }
          break;

        case 3:

          System.out.println(a.getBalance());
          System.out.println("현재 잔액은 " + a.getBalance().getBalance());

          break;

        case 4:

          System.out.println("조회일(YYYYMMDD)>");
          String result = sc.nextLine();

          List<AccountVO> list = a.getList(result);

          for (AccountVO vo : list) {
            System.out.println(vo.toString());
          }

          break;

        case 5:

          System.out.println("기간별 조회(YYYYMMDD)>");

          System.out.print("시작일(YYYYMMDD)");
          String startdate = sc.nextLine();

          System.out.print("종료일(YYYYMMDD)");
          String enddate = sc.nextLine();

          List<AccountVO> slist = a.getList(startdate, enddate);

          for (AccountVO vo : slist) {
            System.out.println(vo.toString());
          }

          break;

        }

      } catch (Exception e) {
        System.out.println("잘못 눌렀습니다. ");
      }
    } while (true);

    sc.close();
    System.out.println("프로그램을 종료합니다.");

  }

}
