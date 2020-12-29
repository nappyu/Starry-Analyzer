package Visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Gets the names of all methods in a java file
 */
public class MethodNameVisitor extends VoidVisitorAdapter<HashMap<MethodDeclaration, ArrayList<String>>> {

    @Override
    public void visit(MethodDeclaration n, HashMap<MethodDeclaration, ArrayList<String>> collector) {
        String s = n.getDeclarationAsString(false, false, false);
        // System.out.println(s);
        collector.put(n, new ArrayList<>());
    }
}
