package ht.auth;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by haho on 3/7/2017.
 */
public class Test {

  private static final Logger log = LogManager.getLogger(Test.class);

  public void methodA(int i, String msg){
    System.out.println(Integer.toString(i));
    System.out.println(msg);
  }

  public void methodB(int i, String msg){
    synchronized(this){
      System.out.println(Integer.toString(i));
      System.out.println(msg);
    }
  }

//  public static void main(String[] args) {
//    Test test = new Test();
//    test.methodA(1, "message A");
//    test.methodB(2, "message B");
//  }


  public static void main(String [] args) {
    Test test = new Test();
    test.testFinally();
  }


  public void testFinally() {
    try {
      System.out.println(setOne().toString());
    } catch (Exception e) {
      System.out.println("Error");
    }
  }

  public StringBuilder setOne() {
    StringBuilder builder = new StringBuilder();
    try {
      builder.append("Cool");
      return builder.append("Return");
    } finally {
      builder.append("+1");
      builder = null;
    }
  }


//  @Test
//  public void testNoRepositoryExceptionLoggedWhenContentNodeDoesNotExist() {
//    // GIVEN
//    // HOW SHOULD WE MOCK THE LOG FACTORY
//    // We will use Mockito to lock the functions generating the log such as debug, info, error, warn, etc..
//    MockitoAnnotations.initMocks(this);
//    ReflectionTestUtils.setField(AssetTypeColumnFormatter, "log", mockLog);
//
//    definition.setAddFileExtension(true);
//    node = new MockNode("document", AssetNodeTypes.Asset.NAME);
//    AssetTypeColumnFormatter formatter = new TestAssetTypeColumnFormatter(definition, node);
//
//    // WHEN
//    formatter.generateCell(null, null, null);
//
//    // THEN nothing was logged and also no {@link info.magnolia.jcr.RuntimeRepositoryException}
//    // HOW TO DO CHECK
//
//  }
}
