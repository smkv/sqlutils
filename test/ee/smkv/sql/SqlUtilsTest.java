package ee.smkv.sql;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author samko
 */
public class SqlUtilsTest {
  @Test
  public void testGetPlaceHoldersForParametersEmpty() throws Exception {
    Assert.assertEquals("" , SqlUtils.getPlaceHoldersForParameters());
  }
  @Test
  public void testGetPlaceHoldersForParametersOne() throws Exception {
    Assert.assertEquals("?" , SqlUtils.getPlaceHoldersForParameters("1"));
  }

  @Test
  public void testGetPlaceHoldersForParametersMany() throws Exception {
    Assert.assertEquals("?, ?, ?" , SqlUtils.getPlaceHoldersForParameters("1" , "2" , "3"));
  }
}
