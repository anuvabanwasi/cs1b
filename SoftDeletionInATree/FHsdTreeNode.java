package assignment3;

public class FHsdTreeNode<E>
{
   // use protected access so the tree, in the same package, 
   // or derived classes can access members 
   protected FHsdTreeNode<E> firstChild, sib, prev;
   protected E data;
   protected FHsdTreeNode<E> myRoot;  // needed to test for certain error
   protected boolean deleted;
   
   public FHsdTreeNode( E d, FHsdTreeNode<E> sb, FHsdTreeNode<E> chld, FHsdTreeNode<E> prv, boolean dltd )
   {
      firstChild = chld; 
      sib = sb;
      prev = prv;
      data = d;
      myRoot = null;
      deleted = dltd;
   }
   
   public FHsdTreeNode()
   {
      this(null, null, null, null, false);
   }
   
   public E getData() { return data; }

   // for use only by FHtree (default access)
   protected FHsdTreeNode( E d, FHsdTreeNode<E> sb, FHsdTreeNode<E> chld, 
      FHsdTreeNode<E> prv, FHsdTreeNode<E> root, boolean dltd )
   {
      this(d, sb, chld, prv, dltd);
      myRoot = root;
   }
}
