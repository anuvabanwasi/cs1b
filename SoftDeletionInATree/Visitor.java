package assignment3;

public class Visitor implements Traverser {

	@Override
	public void visit(Object x) {
		System.out.println("visited node : " + (String)x);

	}

}
