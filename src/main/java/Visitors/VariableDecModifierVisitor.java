package Visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import java.util.ArrayList;

public class VariableDecModifierVisitor extends ModifierVisitor<ArrayList<SimpleName>> {

    @Override
    public Visitable visit(VariableDeclarationExpr n, ArrayList<SimpleName> arg) {
        NodeList<VariableDeclarator> variables = n.getVariables();
        for (VariableDeclarator var : variables) {
            arg.add(var.getName());
        }
        return super.visit(n, arg);
    }
}
