package ee.smkv.sql;

/**
 * @author samko
 */
public class SqlOutParameter {
  protected final String name;


  public SqlOutParameter(String name) {
    this.name = name;
  }


  public String getName() {
    return name;
  }


}
