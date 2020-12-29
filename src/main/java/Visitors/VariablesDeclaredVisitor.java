package Visitors;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.expr.Expression;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class VariablesDeclaredVisitor extends VoidVisitorAdapter<List<String>> {

    @Override
    public void visit(VariableDeclarator vd, List<String> outList) {
        super.visit(vd, outList);
        // Get all the variables in a class of reference type
        if (vd.getType().isClassOrInterfaceType()) {
            String fieldType = vd.getType().asClassOrInterfaceType().getName().asString();
            // Mapping the type to the initial value
            outList.add(fieldType);
        }
    }
}
