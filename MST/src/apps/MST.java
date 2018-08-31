package apps;

import structures.*;

import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		PartialTreeList MinimumTree = new PartialTreeList();
		
		for(Vertex A: graph.vertices) {
			
			PartialTree B = new PartialTree(A);
			
			Vertex.Neighbor C = A.neighbors;
			
			while( C != null ) {
				
				PartialTree.Arc D = new PartialTree.Arc( A, C.vertex, C.weight );
				
				B.getArcs().insert(D);
				
				C = C.next;
				
			}
			
			MinimumTree.append(B);
		}
		
		return MinimumTree;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
        ArrayList<PartialTree.Arc> Tree = new ArrayList<>();
        ArrayList<Vertex> CHILD = new ArrayList<>();
        
		boolean A;
		
		PartialTree.Arc ARoot;
		
		while(ptlist.size() > 1) {
			
            PartialTree B = ptlist.remove();
           
            do {
            	
            	ARoot = B.getArcs().deleteMin();
                
                A = Helper(ARoot,B.getArcs());
                
            }
            
            while(A && !B.getArcs().isEmpty()); {
            
            	Tree.add(ARoot);
            	
		}
            
            Vertex C = ARoot.v2;
            
            for(Vertex D: CHILD) {
            	
                if( C == D ) {
                	
                    C = D.getRoot();
                    
                    break;
                }
            }
            
            PartialTree PTREE = ptlist.removeTreeContaining(C);
            B.merge(PTREE);
            
            CHILD.add(PTREE.getRoot());
            ptlist.append(B);
            
        }
		
		return Tree;
	}

   private static boolean Helper
		(PartialTree.Arc A, MinHeap<PartialTree.Arc> B) {
		
        for( PartialTree.Arc P : B ) {
        	
            if( A.v2 == P.v1 ) {
            	
                return true;
            }
        }
        
        return false;
    }
}