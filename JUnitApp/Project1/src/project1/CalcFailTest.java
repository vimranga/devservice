package project1;

import org.junit.Test;
import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;

public class CalcFailTest {
    public CalcFailTest() {
        super();
    }

    @Test
    public void testFail() {
        assertEquals(5, new Calculator().add(2, 4));
    }

  public void testHello() {
      System.out.println("hello");
  }
}
