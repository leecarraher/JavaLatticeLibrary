package org.mckilliam.lattices.firstkind.shortvector;

import org.mckilliam.lattices.firstkind.shortvector.MinCutShortVector;
import static org.junit.Assert.assertEquals;
import org.junit.*;
import pubsim.VectorFunctions;
import org.mckilliam.lattices.An.AnSorted;
import org.mckilliam.lattices.Anstar.AnstarSorted;
import org.mckilliam.lattices.decoder.ShortVectorSphereDecoded;
import org.mckilliam.lattices.firstkind.LatticeOfFirstKind;

/**
 *
 * @author Robby McKilliam
 */
public class MinCutShortVectorTest {
    
    public MinCutShortVectorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getShortestVector method, of class MinCutShortVector.
     */
    @Test
    public void testWithAnStar() {
        System.out.println("testWithAnStar");
        int n = 50;
        LatticeOfFirstKind lattice = new LatticeOfFirstKind(new AnstarSorted(n).getGeneratorMatrix());
        MinCutShortVector instance = new MinCutShortVector(lattice);
        ShortVectorSphereDecoded tester = new ShortVectorSphereDecoded(lattice);
        
        double td = VectorFunctions.sum2(instance.getShortestVector());
        double id = VectorFunctions.sum2(tester.getShortestVector());
        
        //System.out.println(VectorFunctions.print(instance.getShortestVector()));
        //System.out.println(VectorFunctions.print(tester.getShortestVector()));
        
        assertEquals(td, id, 0.000001);
        
    }
    
    /**
     * Test of getShortestVector method, of class MinCutShortVector.
     */
    @Test
    public void testWithAn() {
        System.out.println("testWithAn");
        int n = 50;
        LatticeOfFirstKind lattice = new LatticeOfFirstKind(new AnSorted(n).getGeneratorMatrix());
        MinCutShortVector instance = new MinCutShortVector(lattice);
        ShortVectorSphereDecoded tester = new ShortVectorSphereDecoded(lattice);
        
        double td = VectorFunctions.sum2(instance.getShortestVector());
        double id = VectorFunctions.sum2(tester.getShortestVector());
        
        //System.out.println(VectorFunctions.print(instance.getShortestVector()));
        //System.out.println(VectorFunctions.print(tester.getShortestVector()));
        
        assertEquals(td, id, 0.000001);
        
    }

}
