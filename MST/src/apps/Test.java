package apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import structures.Graph;
import structures.Vertex;

public class Test {

	static BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {
		System.out.print("Enter Graph File, 'quit' to stop => ");
		String graphfile = keyboard.readLine();
		
		while(!graphfile.endsWith("quit")){
			Graph g = new Graph(graphfile);
//			g.print();
			PartialTreeList tree = MST.initialize(g);
			
//			tree.remove();
//			for(int x = 0; x < g.vertices.length ;x++)
//				if(g.vertices[x].name.equals("C"))
//					System.out.println("Partial Tree - " + tree.removeTreeContaining(g.vertices[x]));
			
//			System.out.println(tree.remove());
//			System.out.println(tree.remove());
//			System.out.println(tree.remove());
//			System.out.println(tree.remove());
//			System.out.println(tree.remove());
			
//			System.out.println("Partial Tree List:");
//			Iterator iter = tree.iterator();
//			while(iter.hasNext())
//				System.out.println(iter.next().toString());
//			
//			tree.remove();
//			for(int x = 0; x < g.vertices.length ;x++)
//				if(g.vertices[x].name.equals("D"))
//					System.out.println("Partial Tree - " + tree.removeTreeContaining(g.vertices[x]));
//			
//			System.out.println("Partial Tree List:");
//			Iterator iters = tree.iterator();
//			while(iters.hasNext())
//				System.out.println(iters.next().toString());
			
			ArrayList<PartialTree.Arc> path = MST.execute(tree);
			
			System.out.println(path.toString());
			int ans = 0;
			for(PartialTree.Arc temp: path)
				ans += temp.weight;
			
			System.out.println("The sum of the edge weights for this spanning tree is " + ans);
			
			System.out.print("\nEnter Graph File, 'quit' to stop => ");
			graphfile = keyboard.readLine();
		}
	}
}


