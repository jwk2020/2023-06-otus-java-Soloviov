
import tester.annotations.After;
import tester.annotations.Before;
import tester.annotations.Test;

public class SomeTest {

    @Before
    public void before() {
        System.out.println("Before method passed");
    }

    @Test
    public void methodA() {
        System.out.println("Method A passed");
    }

    @Test
    public void methodB() {
        System.out.println("Method B passed");
    }

    @Test
    public void methodC() {
        System.out.println("Method C throws exception!");
        throw new RuntimeException("Method C throws exception!");
    }

    @After
    public void after() {
        System.out.println("After method passed");
    }
}
