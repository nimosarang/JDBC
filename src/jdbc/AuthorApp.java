package jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class AuthorApp {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AuthorVo vo_c = new AuthorVo();
        vo_c.setAuthor_name("양쯔");
        vo_c.setAuthor_desc("깐루");

        AuthorDao dao = new AuthorDaoImpl();
        int count = dao.insert(vo_c);

        if (count == 1) {
            System.out.println("데이터 입력 성공");
        } else {
            System.out.println("실패");
        }

        //조회기능 R
        List<AuthorVo> list = new ArrayList<>();
        list = dao.getList();
        if (list.isEmpty()) {
            System.out.println("데이터가 없습니다");
        } else {
            System.out.println("데이터 조회 성공");
        }
        for (AuthorVo vo_r : list) {
            System.out.print("저자번호 : " + vo_r.getAuthor_id() + "\t");
            System.out.print("이름 : " + vo_r.getAuthor_name() + "\t");
            System.out.print("설명 : " + vo_r.getAuthor_desc() + "\t");
            System.out.println();
        }

        //수정기능 U
        AuthorVo vo_u = new AuthorVo();
        vo_u.setAuthor_name("홍길동2");
        vo_u.setAuthor_desc("홍길동전2");
        vo_u.setAuthor_id(30);

        if (count == 1) {
            System.out.println("데이터 수정 성공");
        } else {
            System.out.println("실패");
        }

        //삭제기능 D
        count = dao.delete(30);
        if (count == 1) {
            System.out.println("데이터 삭제 성공");
        } else {
            System.out.println("실패");
        }


    }

}
