package org.mckilliam.lattices.cvp;

import Jama.Matrix;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mckilliam.lattices.An.AnFastSelect;
import org.mckilliam.lattices.Lattice;
import org.mckilliam.lattices.LatticeInterface;
import org.mckilliam.lattices.Zn;
import pubsim.VectorFunctions;

/**
 *
 * @author Robby McKilliam
 */
public class GreedyRelevantVectorsTest {
    
    public GreedyRelevantVectorsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void sameAsSphereDecoder() {
        System.out.println("sameAsSphereDecoder");

        int iters = 10;
        int n = 7;
        int m = 9;
        double[] origin = new double[m];

        LatticeInterface lattice = new Lattice(Matrix.random(m, n));

        SphereDecoder sdSE = new SphereDecoderSchnorrEuchner(lattice);
        GreedyRelevantVectors grv = new GreedyRelevantVectors(lattice);

        for(int t = 0; t < iters; t++){
            double[] y = VectorFunctions.randomGaussian(m, 0.0, 20.0);
            //test with default Babai starting point
            double decdist = VectorFunctions.distance_between2(sdSE.nearestPoint(y), grv.nearestPoint(y));
            assertTrue(decdist <= 0.000001);
            //test with origin for starting point
            decdist = VectorFunctions.distance_between2(sdSE.nearestPoint(y), grv.nearestPoint(y,origin));
            assertTrue(decdist <= 0.000001);
        }
        
    }

    /**
     * Test of closestRelevantVector method with Zn lattice
     */
    @Test
    public void testClosestRelevantVectorWithZn() {
        System.out.println("test closestRelevantVector with Zn");
        int n = 3;
        LatticeInterface lattice = new Zn(n);
        GreedyRelevantVectors grv = new GreedyRelevantVectors(lattice);
        double[] ya = {0.6,0,0};
        Matrix em = new Matrix(n,1,0); em.set(0,0,1.0); //expect relevant vector (1,0,0)
        Matrix rm = grv.closestRelevantVector(ya);
        assertTrue(em.minus(rm).normF() <= 0.000001);
        
        double[] yb = {0.2,-1.6,0.3};
        em = new Matrix(n,1,0); em.set(1,0,-1.0); //expect relevant vector (0,-1,0)
        rm = grv.closestRelevantVector(yb);
        assertTrue(em.minus(rm).normF() <= 0.000001);
        
        double[] yc = {-0.16,0.2,0.3};
        em = new Matrix(n,1,0); //expect relevant vector (0,0,0)
        rm = grv.closestRelevantVector(yc);
        assertTrue(em.minus(rm).normF() <= 0.000001);
    }
    
    /**
     * Test of closestRelevantVector method with An lattice.
     */
    @Test
    public void testClosestRelevantVectorWithAn() {
        System.out.println("test closestRelevantVector with An");
        int n = 2;
        LatticeInterface lattice = new AnFastSelect(n);
        GreedyRelevantVectors grv = new GreedyRelevantVectors(lattice);
        
        double[] ya = {0.6,-0.6,0};
        Matrix em = new Matrix(n+1,1,0); em.set(0,0,1.0); em.set(1,0,-1.0); //expect relevant vector (1,-1,0)
        Matrix rm = grv.closestRelevantVector(ya);
        assertTrue(em.minus(rm).normF() <= 0.000001);
                
        double[] yc = {-0.1,0.1,0.02};
        em = new Matrix(n+1,1,0); //expect relevant vector (0,0,0)
        rm = grv.closestRelevantVector(yc);
        assertTrue(em.minus(rm).normF() <= 0.000001);
    }
    
}
