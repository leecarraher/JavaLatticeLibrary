package org.mckilliam.lattices.firstkind;

import Jama.Matrix;
import org.mckilliam.distributions.RealRandomVariable;
import org.mckilliam.lattices.Lattice;
import org.mckilliam.lattices.LatticeAndClosestVectorInterface;

/**
 * Class describes lattices of Voronoi's first kind.  These lattices have an obtuse superbasis.
 * Many usually hard problems, such as computing a vector or finding a nearest point are polynomial
 * time for this class of lattices.  See the papers:
 * 
 * R. McKilliam, A. Grant, "Finding short vectors in a lattice of Voronoi's first kind", ISIT 2012, pages 2157 - 2160
 * 
 * R. McKilliam, A. Grant, I. V. L. Clarkson, "Finding a closest lattice point in a lattice of Voronoi's first kind" submitted to SIAM Journal of Discrete Mathematics, Jan 2014.
 * 
 * @author Robby McKilliam
 */
public class LatticeOfFirstKind extends Lattice implements LatticeAndClosestVectorInterface {
    
    /** Matrix with columns contains the obtuse superbasis */
    final protected Matrix sB;
    /** The extended Gram matrix */
    final protected Matrix eQ;

    /** Construct a lattice of first kind with basis B.  Will check whether the basis can
     * be extended to an obtuse superbasis and will throw a runtime exception if it cannot.  To
     * avoid potential problem with numerical precision during this check, you can set the
     * tolerance.  Entries with absolute values less that tolerance are set to zero
     * in the extended Gram matrix.
     * @param B
     * @param tolerance 
     */
    public LatticeOfFirstKind(Matrix B, double tolerance) {
        super(B);
        final int M = B.getRowDimension();
        final int N = B.getColumnDimension();
        
        //setup obtuse superbasis matrix 
        sB = new Matrix(M,N+1);
        for(int m = 0; m < M; m++)
            for(int n = 0; n < N; n++)
                sB.set(m,n, B.get(m, n));
        for(int m = 0; m < M; m++){
            double b = 0.0;
            for(int n = 0; n < N; n++) b += sB.get(m, n);
            sB.set(m,N,-b);
        }
        
        //compute extended gram matrix and assert that this is a lattice of first type
        //i.e., assert that off diagonal entries are zero
        eQ = sB.transpose().times(sB);
        for(int i = 0; i < N+1; i++){
            for(int j = i+1; j < N+1; j++) {
                if(Math.abs(eQ.get(i,j)) < tolerance) eQ.set(i,j,0.0);
                if(eQ.get(i,j) > 0) throw new RuntimeException("Not an obtuse superbasis!");
            }
        }
    }
    
    
    /** Default tolerance is 1e-12 */
    public LatticeOfFirstKind(Matrix B) {
        this(B,1e-12);
    }
    
//    /** Returns a LatticeOfFirstKind given extended Gram matrix eQ.  
//     * Currently does not compute the obtuse superbasis from Q and might break other code
//     * It is possible be compute the basis from this Q, use a reduced rank Cholesky
//     */
//    public static LatticeOfFirstKind constructFromExtendedGram(Matrix eQ){
//        final int N = eQ.getColumnDimension()-1;
//        final Matrix Q = eQ.getMatrix(0, N-1, 0, N-1);
//        final Matrix B = Q.chol().getL().transpose(); //Cholesky decomposition to get back generator
//        //System.out.println(VectorFunctions.print(B));
//        return new LatticeOfFirstKind(B, 1e-10);
//    }
    
//    /** 
//     * Constructs a random lattice of first kind if dimension n
//     * The off diagonal elements of the extended Gram matrix are generated by 
//     * input argument gen.
//     */
//    public static LatticeOfFirstKind randomLatticeOfFirstKind(int n, RealRandomVariable gen){
//        Matrix extQ = new Matrix(n+1,n+1, 0.0); //n+1 by n+1 matrix of zeros
//        
//        //generate random offdiagonal entries
//        for(int i = 0; i < n+1; i++){
//            for(int j = i+1; j < n+1; j++){
//                double noiseval = gen.noise();
//                if(noiseval > 0.0) throw new RuntimeException("The random generator produced a positive number.  It must be negative only produce negative numbers to construct the diagonals of an obtuse superbasis");
//                extQ.set(i,j,noiseval);
//                extQ.set(j,i,noiseval);
//            }
//        }
//        
//        //fill diagonal so that row sums are zero
//        for(int i = 0; i < n+1; i++){
//            double b = 0.0;
//            for(int j = 0; j < n+1; j++) b = b + extQ.get(i, j);
//            extQ.set(i,i,-b);
//        }
//        
//        //return lattice constructed from this extened gram matrix
//        return constructFromExtendedGram(extQ);      
//    }
    
    /** @returns a random extended Gram matrix of a lattice of Voronoi's first kind */
    public static Matrix extendGramMatrix(int n, RealRandomVariable gen){
    Matrix extQ = new Matrix(n+1,n+1, 0.0); //n+1 by n+1 matrix of zeros
        
        //generate random offdiagonal entries
        for(int i = 0; i < n+1; i++){
            for(int j = i+1; j < n+1; j++){
                double noiseval = gen.noise();
                if(noiseval > 0.0) throw new RuntimeException("The random generator produced a positive number.  It must be negative only produce negative numbers to construct the diagonals of an obtuse superbasis");
                extQ.set(i,j,noiseval);
                extQ.set(j,i,noiseval);
            }
        }
        
        //fill diagonal so that row sums are zero
        for(int i = 0; i < n+1; i++){
            double b = 0.0;
            for(int j = 0; j < n+1; j++) b = b + extQ.get(i, j);
            extQ.set(i,i,-b);
        }
        
        return extQ;
    }
    
    /** Return a matrix with columns containing the superbasis vectors */
    public Matrix superbasis() {
        return sB;
    }
    
    /** Return the extended Gram matrix */
    public Matrix extendedGram() {
        return eQ;
    }
    
    //THIS FAILS SOMETIMES FOR SOME REASON.  MIGHT BE AN ERROR IN MINCUT SOMEWHERE?
//    /** 
//     * Compute the norm of the lattice of first type by computing a minimum cut
//     * in a weighted graph.
//     */
//    @Override
//    public double norm() {
//        MinCutShortVector mincut = new MinCutShortVector(this);
//        return VectorFunctions.sum2(mincut.getShortestVector());
//    }

    @Override
    public double[] closestPoint(double[] y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getLatticePoint() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double[] getIndex() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double distance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
