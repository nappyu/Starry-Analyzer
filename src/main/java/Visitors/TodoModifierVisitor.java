package Visitors;

import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import java.util.ArrayList;
import java.util.Locale;

public class TodoModifierVisitor extends ModifierVisitor<ArrayList<String>> {

    @Override
    public Visitable visit(BlockComment n, ArrayList<String> arg) {
        String comment = n.getContent();
        if (comment.toLowerCase(Locale.CANADA).contains("todo")) {
            int lineNumber = n.getRange().get().begin.line;
            arg.add(String.format("TODO Comment was found at line %d", lineNumber));
        }
        return super.visit(n, arg);
    }

    @Override
    public Visitable visit(LineComment n, ArrayList<String> arg) {
        String comment = n.getContent();
        if (comment.toLowerCase(Locale.CANADA).contains("todo")) {
            int lineNumber = n.getRange().get().begin.line;
            arg.add(String.format("TODO Comment was found at line %d", lineNumber));
        }
        return super.visit(n, arg);
    }
}
