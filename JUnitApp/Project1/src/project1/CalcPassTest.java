package project1;

import org.junit.Test;
import junit.framework.TestCase;
import static org.junit.Assert.assertEquals;
 
public class CalcPassTest {
 @Test
 public void testPass() {
  assertEquals(5, new Calculator().add(2, 3));
 }
 
    
}