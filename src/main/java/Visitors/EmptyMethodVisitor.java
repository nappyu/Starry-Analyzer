package Visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmptyMethodVisitor extends ModifierVisitor<ArrayList<String>> {

    @Override
    public Visitable visit(BlockStmt n, ArrayList<String> arg) {
        if (n.getStatements().size() == 0) {
            arg.add("Contains an empty block statement" + n.getRange());
        }
        return super.visit(n, arg);
    }
}
