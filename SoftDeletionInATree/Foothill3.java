package assignment3;

public class Foothill3 {

	public static void main(String[] args) {
		FHsdTree<String> sceneTree = new FHsdTree<String>();
		FHsdTreeNode<String> tn;

		System.out.println("Starting tree empty? " + sceneTree.emptyPhysical() + "\n");
		// create a scene in a room
		tn = sceneTree.addChild(null, "A");
		
		sceneTree.addChild(tn, "B");
		sceneTree.addChild(tn, "C");
		sceneTree.addChild(tn, "D");
		sceneTree.addChild(tn, "E");
		
		tn = sceneTree.find("C");
		sceneTree.addChild(tn, "F");
		sceneTree.addChild(tn, "G");
		sceneTree.addChild(tn, "I");
		
		sceneTree.remove("E");
		
		sceneTree.displayPhysical();
		
		tn = sceneTree.find("A");
		sceneTree.addChild(tn, "K");
		
		sceneTree.remove("C");
		
		System.out.println("tree after deletion");
		
		sceneTree.displayPhysical();
		
		System.out.println("physical tree size after deletion " + sceneTree.sizePhysical());

		System.out.println("virtual tree size after deletion " + sceneTree.size());
		
		//sceneTree.traverse(new CountTraverser());
		
		sceneTree.collectGarbage();
		
		tn = sceneTree.find("C");
		sceneTree.addChild(tn, "J");
		
		sceneTree.displayPhysical();
	}

}
