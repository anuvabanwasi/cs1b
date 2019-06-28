package assignment3;

public class FHsdTree<E> implements Cloneable {
	
	protected int mSize;
	protected int vSize;

	protected FHsdTreeNode<E> mRoot;

	public FHsdTree() {
		clear();
	}
	
	public boolean emptyPhysical() {
		return (mSize == 0);
	}

	public boolean empty() {
		return (size() == 0);
	}
	
	public int sizePhysical() {
		return mSize;
	}
	
	public int size() {
		vSize = 0;
		return sizeVirtual(mRoot, 0);
	}

	// often helper of typical public version, but also callable by on subtree
	private int sizeVirtual(FHsdTreeNode<E> treeNode, int level) {
		
		if (treeNode == null)
			return 0;

		if (!treeNode.deleted) {
			vSize++;

			// recursive step done here
			sizeVirtual(treeNode.firstChild, level + 1);
		}
		
		if (level > 0)
			sizeVirtual(treeNode.sib, level);

		return vSize;
	}
	
	/* 
	 * Alternate method of computing size of virtual tree
	 * often helper of typical public version, but also callable by on subtree
	private int sizeVirtual(FHsdTreeNode<E> treeNode, int level, int vSize) {
		
		if (treeNode == null)
			return 0;

		if (!treeNode.deleted) {

			// recursive step done here
			vSize = sizeVirtual(treeNode.sib, level, vSize) +  1 + sizeVirtual(treeNode.firstChild, level + 1, vSize);
			
		} else {
		
			if (level > 0)
				vSize = vSize + sizeVirtual(treeNode.sib, level, vSize);
		}

		return vSize;
	}*/
	

	public void clear() {
		mSize = 0;
		mRoot = null;
	}

	public FHsdTreeNode<E> addChild(FHsdTreeNode<E> treeNode, E x) {
		// empty tree? - create a root node if user passes in null
		if (mSize == 0) {
			if (treeNode != null)
				return null; // error something's fishy. treeNode can't right
			mRoot = new FHsdTreeNode<E>(x, null, null, null, false);
			mRoot.myRoot = mRoot;
			mSize = 1;
			return mRoot;
		}
		if (treeNode == null)
			return null; // error inserting into non_null tree with a null
							// parent

		if (treeNode.deleted)
			return null;

		if (treeNode.myRoot != mRoot)
			return null; // silent error, node does not belong to this tree

		// push this node into the head of the sibling list; adjust prev
		// pointers
		FHsdTreeNode<E> newNode = new FHsdTreeNode<E>(x, treeNode.firstChild, null, treeNode, mRoot, false); // sb,
																												// rt
		treeNode.firstChild = newNode;
		if (newNode.sib != null)
			newNode.sib.prev = newNode;
		++mSize;
		return newNode;
	}

	public FHsdTreeNode<E> find(E x) {
		return find(mRoot, x, 0);
	}

	FHsdTreeNode<E> find(FHsdTreeNode<E> root, E x, int level) {
		
		FHsdTreeNode<E> retval = null;

		if (mSize == 0 || root == null)
			return null;

		if (root.data.equals(x) && !root.deleted)
			return root;

		// otherwise, recurse. don't process sibs if this was the original call
		if (level > 0 && (retval = find(root.sib, x, level)) != null)
			return retval;
		
		if(!root.deleted){
			return find(root.firstChild, x, ++level);
		}
		
		return retval;
	}

	public boolean remove(E x) {
		return remove(mRoot, x);
	}

	private boolean remove(FHsdTreeNode<E> root, E x) {
		FHsdTreeNode<E> tn = null;

		if (mSize == 0 || root == null)
			return false;

		if ((tn = find(root, x, 0)) != null) {
			tn.deleted = true;
			return true;
		}
		
		return false;
	}

	
	public void collectGarbage() {
		collectGarbage(mRoot, 0);
	}

	private void collectGarbage(FHsdTreeNode<E> treeNode, int level) {

		if (mSize == 0 || treeNode == null)
			return;

		if (treeNode.deleted) {
			removeNode(treeNode);
		}

		// recursive step done here
		collectGarbage(treeNode.firstChild, level + 1);
		if (level > 0)
			collectGarbage(treeNode.sib, level);

	}

	private void removeNode(FHsdTreeNode<E> nodeToDelete) {

		if (nodeToDelete == null || mRoot == null)
			return;
		if (nodeToDelete.myRoot != mRoot)
			return; // silent error, node does not belong to this tree

		// remove all the children of this node
		while (nodeToDelete.firstChild != null)
			removeNode(nodeToDelete.firstChild);

		if (nodeToDelete.prev == null)
			mRoot = null; // last node in tree
		else if (nodeToDelete.prev.sib == nodeToDelete)
			nodeToDelete.prev.sib = nodeToDelete.sib; // adjust left sibling
		else
			nodeToDelete.prev.firstChild = nodeToDelete.sib; // adjust parent

		// adjust the successor sib's prev pointer
		if (nodeToDelete.sib != null)
			nodeToDelete.sib.prev = nodeToDelete.prev;
		
		mSize--;
	}

	public Object clone() throws CloneNotSupportedException {
		FHsdTree<E> newObject = (FHsdTree<E>) super.clone();
		newObject.clear(); // can't point to other's data

		newObject.mRoot = cloneSubtree(mRoot);
		newObject.mSize = mSize;
		newObject.setMyRoots(newObject.mRoot);

		return newObject;
	}

	private FHsdTreeNode<E> cloneSubtree(FHsdTreeNode<E> root) {
		FHsdTreeNode<E> newNode = null;
		if (root == null)
			return null;
		
		if (root.deleted) {
			newNode = cloneSubtree(root.sib);
		}
		else
			newNode = new FHsdTreeNode<E>(root.data, cloneSubtree(root.sib), cloneSubtree(root.firstChild), null, root.deleted);


		// does not set myRoot which must be done by caller
		// the prev pointer is set by parent recursive call ... this is the
		// code:
	
		if (newNode != null && newNode.sib != null)
			newNode.sib.prev = newNode;
		if (newNode != null && newNode.firstChild != null) 
			newNode.firstChild.prev = newNode;
		
		
		return newNode;
	}

	// recursively sets all myRoots to mRoot
	private void setMyRoots(FHsdTreeNode<E> treeNode) {
		if (treeNode == null)
			return;

		treeNode.myRoot = mRoot;
		setMyRoots(treeNode.sib);
		setMyRoots(treeNode.firstChild);
	}

	// define this as a static member so recursive display() does not need
	// a local version
	final static String blankString = "                                    ";

	public void displayPhysical() {
		displayPhysical(mRoot, 0);
	}

	// let be public so client can call on subtree
	void displayPhysical(FHsdTreeNode<E> treeNode, int level) {
		String indent;

		// stop runaway indentation/recursion
		if (level > (int) blankString.length() - 1) {
			System.out.println(blankString + " ... ");
			return;
		}

		if (treeNode == null)
			return;

		indent = blankString.substring(0, level);

		// pre-order processing done here ("visit")
		if (treeNode.deleted)
			System.out.println(indent + treeNode.data + "(D)");
		else
			System.out.println(indent + treeNode.data);

		// recursive step done here
		displayPhysical(treeNode.firstChild, level + 1);
		if (level > 0)
			displayPhysical(treeNode.sib, level);
	}

	public void display() {
		displayVirtual(mRoot, 0);
	}

	// let be public so client can call on subtree
	private void displayVirtual(FHsdTreeNode<E> treeNode, int level) {
		String indent;

		// stop runaway indentation/recursion
		if (level > (int) blankString.length() - 1) {
			System.out.println(blankString + " ... ");
			return;
		}

		if (treeNode == null)
			return;

		indent = blankString.substring(0, level);

		// pre-order processing done here ("visit")
		if (!treeNode.deleted) {
			System.out.println(indent + treeNode.data);

			// recursive step done here
			displayVirtual(treeNode.firstChild, level + 1);
		}

		if (level > 0) {
			displayVirtual(treeNode.sib, level);
		}
	}

	public <F extends Traverser<? super E>> void traverse(F func) {
		traverse(func, mRoot, 0);
	}

	// often helper of typical public version, but also callable by on subtree
	public <F extends Traverser<? super E>> void traverse(F func, FHsdTreeNode<E> treeNode, int level) {
		if (treeNode == null)
			return;

		func.visit(treeNode.data);

		// recursive step done here
		traverse(func, treeNode.firstChild, level + 1);
		if (level > 0)
			traverse(func, treeNode.sib, level);
	}

	public <F extends Traverser<? super E>> void traverseVirtual(F func) {
		traverseVirtual(func, mRoot, 0);
	}
	
	// often helper of typical public version, but also callable by on subtree
	public <F extends Traverser<? super E>> void traverseVirtual(F func, FHsdTreeNode<E> treeNode, int level) {
		if (treeNode == null)
			return;

		if(!treeNode.deleted)
			func.visit(treeNode.data);

		if(treeNode.deleted) {
			if (level > 0)
				traverseVirtual(func, treeNode.sib, level);
			
		} else {
			// recursive step done here
			traverseVirtual(func, treeNode.firstChild, level + 1);
			if (level > 0)
				traverseVirtual(func, treeNode.sib, level);
		}
		
	}

}
