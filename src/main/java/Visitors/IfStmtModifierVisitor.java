package Visitors;

import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import java.util.ArrayList;

public class IfStmtModifierVisitor extends ModifierVisitor<ArrayList<String>> {

    @Override
    public Visitable visit(IfStmt n, ArrayList<String> arg) {
        return super.visit(n, arg);
    }
}
