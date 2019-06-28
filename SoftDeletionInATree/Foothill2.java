package assignment3;


public class Foothill2 {

	// ------- main --------------
	// ------- main --------------
		public static void main(String[] args) throws Exception {
			FHsdTree<String> sceneTree = new FHsdTree<String>();
			FHsdTreeNode<String> tn;

			System.out.println("Starting tree empty? " + sceneTree.emptyPhysical() + "\n");
			// create a scene in a room
			tn = sceneTree.addChild(null, "room");

			// add three objects to the scene tree
			sceneTree.addChild(tn, "Lily the canine");
			sceneTree.addChild(tn, "Miguel the human");
			sceneTree.addChild(tn, "table");
			// add some parts to Miguel
			tn = sceneTree.find("Miguel the human");

			// Miguel's left arm
			tn = sceneTree.addChild(tn, "torso");
			tn = sceneTree.addChild(tn, "left arm");
			tn = sceneTree.addChild(tn, "left hand");
			sceneTree.addChild(tn, "thumb");
			sceneTree.addChild(tn, "index finger");
			sceneTree.addChild(tn, "middle finger");
			sceneTree.addChild(tn, "ring finger");
			sceneTree.addChild(tn, "pinky");

			// Miguel's right arm
			tn = sceneTree.find("Miguel the human");
			tn = sceneTree.find(tn, "torso", 0);
			tn = sceneTree.addChild(tn, "right arm");
			tn = sceneTree.addChild(tn, "right hand");
			sceneTree.addChild(tn, "thumb");
			sceneTree.addChild(tn, "index finger");
			sceneTree.addChild(tn, "middle finger");
			sceneTree.addChild(tn, "ring finger");
			sceneTree.addChild(tn, "pinky");

			// add some parts to Lily
			tn = sceneTree.find("Lily the canine");
			tn = sceneTree.addChild(tn, "torso");
			sceneTree.addChild(tn, "right front paw");
			sceneTree.addChild(tn, "left front paw");
			sceneTree.addChild(tn, "right rear paw");
			sceneTree.addChild(tn, "left rear paw");
			sceneTree.addChild(tn, "spare mutant paw");
			sceneTree.addChild(tn, "wagging tail");

			// add some parts to table
			tn = sceneTree.find("table");
			sceneTree.addChild(tn, "north east leg");
			sceneTree.addChild(tn, "north west leg");
			sceneTree.addChild(tn, "south east leg");
			sceneTree.addChild(tn, "south west leg");

			System.out.println("\n------------ Loaded Tree ----------------- \n");
			sceneTree.displayPhysical();
			
			System.out.println("\n------------ Physical and Virtual Size of tree ----------------- \n");
			System.out.println("virtual (soft) size: " + sceneTree.size());
			System.out.println("physiical (hard) size: " + sceneTree.sizePhysical());
			
			
			System.out.println("\n------------ Find Miguel the human ----------------- \n");
			tn = sceneTree.find("Miguel the human");
			if (tn != null)
				System.out.println("\n------------ Found Miguel the human ---------------- \n");
			else
				System.out.println("\n------------ Miguel the human not found --------------- \n");
			
			
			System.out.println("\n------------ Find Non existent node - Miguel the Hun ----------------- \n");
			tn = sceneTree.find("Miguel the Hun");
			if (tn != null)
				System.out.println("\n------------ Found Miguel the Hun---------------- \n");
			else
				System.out.println("\n------------ Miguel the Hun not found --------------- \n");
			
			
			
			System.out.println("\n------------ Find right arm ----------------- \n");
			tn = sceneTree.find("right arm");
			if (tn != null)
				System.out.println("\n------------ Found right arm ---------------- \n");
			else
				System.out.println("\n------------ right arm not found --------------- \n");
			
			
			System.out.println("\n------------ Find Miguel the human's pinky ----------------- \n");
			tn = sceneTree.find("Miguel the human");
			tn = sceneTree.find(tn, "pinky", 0);
			if (tn != null)
				System.out.println("\n------------ Found Miguel the human's pinky ---------------- \n");
			else
				System.out.println("\n------------ Miguel the human's pinky not found --------------- \n");
			
			System.out.println("\n------------ Removing a few nodes ----------------- \n");
			sceneTree.remove("spare mutant paw");
			sceneTree.remove("Miguel the human");
			sceneTree.remove("wagging tail");
			sceneTree.remove("an imagined higgs boson");
			
			System.out.println("\n----------- Virtual (soft) Tree After removing a few nodes --------------- \n");
			sceneTree.display();

			System.out.println("\n----------- Physical (hard) Display After removing a few nodes ------------- \n");
			sceneTree.displayPhysical();
			
			System.out.println("\n---------- Testing Sizes (compare with above) -------- \n");
			System.out.println("virtual (soft) size: " + sceneTree.size());
			System.out.println("physiical (hard) size: " + sceneTree.sizePhysical());
			
			
			System.out.println("\n------------ Find deleted node - Miguel the human's thumb ----------------- \n");
			tn = sceneTree.find("thumb");
			if (tn != null)
				System.out.println("\n------------ Found Miguel the human's thumb ---------------- \n");
			else
				System.out.println("\n------------ Miguel the human's thumb not found --------------- \n");
			
			
			System.out.println("\n------------ Collecting Garbage ---------------- \n");
				System.out.println("found soft-deleted nodes? " + (sceneTree.size() != sceneTree.sizePhysical()));
			sceneTree.collectGarbage();
			System.out.println("immediate collect again? " + (sceneTree.size() != sceneTree.sizePhysical()) );
			sceneTree.collectGarbage();

			System.out.println("\n---------Physical (hard) Display after garb col ------------ \n");
			sceneTree.displayPhysical();
			
			System.out.println("\n--------- Virtual (soft) Display after garb col ------------ \n");
			sceneTree.display();
			
			System.out.println("\n--------- Physical Size after garb col ------------ \n");
			System.out.println(sceneTree.sizePhysical());
			
			System.out.println("\n--------- Virtual Size after garb col ------------ \n");
			System.out.println(sceneTree.size());

			System.out.println("\nSemi-deleted tree empty? " + sceneTree.empty() + "\n");
			sceneTree.remove("room");
			System.out.println("Completely-deleted tree empty? " + sceneTree.empty() + "\n");
			System.out.println("\n----------- Physical (hard) Display ------------- \n");
			sceneTree.displayPhysical();
			
			System.out.println("\n----------- Virtual (soft) Tree --------------- \n");
			sceneTree.display();
			
			System.out.println("------------ Creating a new tree -----------------");
			
			// create a scene in the yard
			sceneTree = new FHsdTree<String>();
			tn = sceneTree.addChild(null, "yard");
			// add three objects to the scene tree
			sceneTree.addChild(tn, "Hugo the dog");
			sceneTree.addChild(tn, "Rover the rabbit");
			sceneTree.addChild(tn, "swings");
			
			tn = sceneTree.find("swings");
			sceneTree.addChild(tn, "baby swing");
			sceneTree.addChild(tn, "toddler swing");
			
			System.out.println("\n----------- Physical (hard) Display ------------- \n");
			sceneTree.displayPhysical();
			
			System.out.println("\n----------- Virtual (soft) Tree --------------- \n");
			sceneTree.display();
			
			
			System.out.println("\n----------- Physical (hard) Size ------------- \n");
			System.out.println(sceneTree.sizePhysical());
			
			System.out.println("\n----------- Virtual (soft) Size --------------- \n");
			System.out.println(sceneTree.size());
			
			System.out.println("\n------------ Testing Clone functionality ----------------- \n");
			FHsdTree<String> clone = (FHsdTree)sceneTree.clone();
			tn = clone.find("yard");
			clone.addChild(tn, "trampoline");

			System.out.println("\n------------ Remove from Original Tree ----------------- \n");
			sceneTree.remove("baby swing");
			
			System.out.println("\n------------ Loaded Clone Tree ----------------- \n");
			clone.displayPhysical();
			
			System.out.println("\n------------ Loaded Original Tree ----------------- \n");
			sceneTree.displayPhysical();
			
			System.out.println("\n----------- Testing pre order traversal --------------- \n");
			sceneTree.traverseVirtual(new Visitor());
			System.out.println("\n----------- Completed traversing --------------- \n");

		}


}
