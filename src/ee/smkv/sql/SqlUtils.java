package ee.smkv.sql;

import java.util.Arrays;

/**
 * @author samko
 */
public class SqlUtils {
  private SqlUtils() {
  }


  public static String getPlaceHoldersForParameters(Object ... parameters) {
    char[] placeHolders = new char[parameters.length];
    Arrays.fill(placeHolders, '?');
    String joined = Arrays.toString(placeHolders);
    return joined.substring(1, joined.length()-1);
  }
}
