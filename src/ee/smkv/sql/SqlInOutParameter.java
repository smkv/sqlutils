package ee.smkv.sql;

/**
 * @author samko
 */
public class SqlInOutParameter extends SqlOutParameter {
  protected Object value;
  public SqlInOutParameter(String name , Object value) {
    super(name);
    this.value =  value;
  }


  public Object getValue() {
    return value;
  }
}
