package Visitors;

import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import java.util.ArrayList;
import java.util.Locale;

public class SimpleNameModifierVisitor extends ModifierVisitor<ArrayList<SimpleName>> {

    @Override
    public Visitable visit(SimpleName n, ArrayList<SimpleName> arg) {
        arg.add(n);
        return super.visit(n, arg);
    }
}
