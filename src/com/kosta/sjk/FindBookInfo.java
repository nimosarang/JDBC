package com.kosta.sjk;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class FindBookInfo {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        while (true) {
            Scanner in = new Scanner(System.in);
            System.out.println(" 1.SELECT / 2.UPDATE / 3.INSERT / 4.끝 ");
            int menu = in.nextInt();

            switch (menu){
                case 1 :
                    FindBook findBook = new FindBook();
                    List<BookVo> select = Collections.unmodifiableList(findBook.getList());

                    for(BookVo vo : select){
                        System.out.println(vo);
                    }
                    System.out.println("--------------------------------");

                    break;

                case 2 :
                    System.out.println("UPDATE BOOK : TITLE / PUBS / PUB_DATE / AUTHOR_ID / WHERE BOOK_ID");
                    FindBook findBook1 = new FindBook();
                    try {
                        findBook1.update();
                    } catch (IOException e) {
                        System.out.println("업데이트 실패");
                    }
                    System.out.println("업데이트 성공!");

                    break;

                case 3 :
                    break;

                case 4 :
                    return;
            }
        }


    }
}
