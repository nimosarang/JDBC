package jdbc;

import java.util.List;

public interface AuthorDao {
  public int insert(AuthorVo vo);       // 입력하기 
  public List<AuthorVo> getList(); // 전체가져오기
  public int delete(int authorId);     // 삭제하기
  public int update(AuthorVo vo);       // 업데이트하기
}
