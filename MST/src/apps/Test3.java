package apps;

import structures.*;

import java.io.IOException;

public class Test3 {

	public static void main(String[] args) throws IOException{
		Graph test1 = new Graph("graph1.txt");
		PartialTreeList list1 = MST.initialize(test1);
		
		
		Graph test2 = new Graph("graph2.txt");
		PartialTreeList list2 = MST.initialize(test2);

		Vertex v1 = list1.remove().getArcs().getMin().v2;
		list1.removeTreeContaining(v1);
		//while(list1.size() != 0){
			//System.out.println(list1.remove());
	//	}
		
		Vertex v2 = list1.remove().getArcs().getMin().v2;
		System.out.println(list1.removeTreeContaining(v2));
		while(list1.size() != 0){
			System.out.println(list1.remove());
		}
	
	
		Vertex v3 = list2.remove().getArcs().getMin().v2;
		list2.removeTreeContaining(v3);
		
		
		System.out.println();
		Vertex v4 = list2.remove().getArcs().getMin().v2;
		System.out.println(list2.removeTreeContaining(v4));
		while(list2.size() != 0){
			System.out.println(list2.remove());
		}
		
	}
	

}