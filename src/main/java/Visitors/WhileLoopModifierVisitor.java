package Visitors;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import java.util.ArrayList;
import java.util.List;

public class WhileLoopModifierVisitor extends ModifierVisitor<ArrayList<String>> {

    @Override
    public Visitable visit(WhileStmt n, ArrayList<String> arg) {
        n.getCondition().ifBooleanLiteralExpr(booleanLiteralExpr -> {
            if (booleanLiteralExpr.getValue()) {
                // while loop has "true" condition, so check for a return or break statement
                List<BreakStmt> b = n.findAll(BreakStmt.class);
                List<ReturnStmt> r = n.findAll(ReturnStmt.class);
                boolean breakOrReturnPresent = !b.isEmpty() || !r.isEmpty();
                if (!breakOrReturnPresent) {
                    int lineNumber = n.getRange().get().begin.line;
                    arg.add(String.format("The while loop at line %d does not have an end condition.", lineNumber));
                }
            }
        });
        n.getCondition().ifNameExpr(nameExpr -> {
            List<AssignExpr> assignExprs = n.findAll(AssignExpr.class);
            boolean loopConditionWrittenToInsideLoop = false;
            for (AssignExpr assignment : assignExprs) {
                if (assignment.getTarget().toString().equals(nameExpr.toString())) {
                    loopConditionWrittenToInsideLoop = true;
                }
            }
            if (!loopConditionWrittenToInsideLoop) {
                int lineNumber = n.getRange().get().begin.line;
                arg.add(String.format("The loop condition at line %d is not written to within the body.", lineNumber));
            }
        });
        return super.visit(n, arg);
    }
}
