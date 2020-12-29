package Visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.HashMap;

public class MethodDependencyVisitor extends VoidVisitorAdapter<ArrayList<String>> {

    @Override
    public void visit(MethodCallExpr n, ArrayList<String> collector) {
        // String s = n.getDeclarationAsString(false, false, false);
        collector.add(n.getName().asString());
    // collector.put(s, new ArrayList<>());
    }
}
