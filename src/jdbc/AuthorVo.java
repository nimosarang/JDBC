package jdbc;

public class AuthorVo {
  private int author_id ;
  private String author_name ;
  private String author_desc ;
  /**
   * @return the author_id
   */
  public int getAuthor_id() {
    return author_id;
  }
  /**
   * @param author_id the author_id to set
   */
  public void setAuthor_id(int author_id) {
    this.author_id = author_id;
  }
  /**
   * @return the author_name
   */
  public String getAuthor_name() {
    return author_name;
  }
  /**
   * @param author_name the author_name to set
   */
  public void setAuthor_name(String author_name) {
    this.author_name = author_name;
  }
  /**
   * @return the author_desc
   */
  public String getAuthor_desc() {
    return author_desc;
  }
  /**
   * @param author_desc the author_desc to set
   */
  public void setAuthor_desc(String author_desc) {
    this.author_desc = author_desc;
  }
  @Override
  public String toString() {
    return "AuthorVo [author_id=" + author_id + ", author_name=" + author_name + ", author_desc=" + author_desc + "]";
  }
  /**
   * 
   */
  public AuthorVo() {
  }
  /**
   * @param author_id
   * @param author_name
   * @param author_desc
   */
  public AuthorVo(int author_id, String author_name, String author_desc) {
    this.author_id = author_id;
    this.author_name = author_name;
    this.author_desc = author_desc;
  }
}
