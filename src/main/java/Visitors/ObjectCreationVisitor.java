package Visitors;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;

public class ObjectCreationVisitor extends VoidVisitorAdapter<List<String>> {

    public void visit(ObjectCreationExpr oc, List<String> outList) {
        super.visit(oc, outList);
        outList.add(oc.toString());
    }
}
