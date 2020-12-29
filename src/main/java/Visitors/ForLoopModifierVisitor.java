package Visitors;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.visitor.ModifierVisitor;
import com.github.javaparser.ast.visitor.Visitable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ForLoopModifierVisitor extends ModifierVisitor<ArrayList<String>> {

    @Override
    public Visitable visit(ForStmt n, ArrayList<String> arg) {
        List<Expression> initializationList = n.getInitialization();
        Optional<Expression> compareExp = n.getCompare();
        // Check rule: (for) loops should not be infinite
        if (!compareExp.isPresent()) {
            // only check for a break or return statement if there is no end condition specified
            List<BreakStmt> b = n.findAll(BreakStmt.class);
            List<ReturnStmt> r = n.findAll(ReturnStmt.class);
            boolean breakOrReturnPresent = !b.isEmpty() || !r.isEmpty();
            if (!breakOrReturnPresent) {
                int lineNumber = n.getRange().get().begin.line;
                arg.add(String.format("The for loop at line %d does not have an end condition.", lineNumber));
            }
        }
        // Check rule: Loop conditions should be true at least once
        compareExp.ifPresent(comparisonExpr -> {
            // Check if the comparison expression is present
            comparisonExpr.ifBinaryExpr(binaryExpr -> {
                // check if the comparison expression is a binary expression
                boolean rightIsNumber = binaryExpr.getRight().isIntegerLiteralExpr();
                for (Expression expr : initializationList) {
                    expr.ifVariableDeclarationExpr(varDecExpr -> {
                        NodeList<VariableDeclarator> varDeclarators = varDecExpr.getVariables();
                        for (VariableDeclarator v : varDeclarators) {
                            if (v.getName().toString().equals(binaryExpr.getLeft().toString()) && v.getInitializer().isPresent() && v.getInitializer().get().isIntegerLiteralExpr() && rightIsNumber) {
                                // the variable being evaluated is declared and initialized as an int
                                // and that the check is against an int
                                boolean iteratesAtLeastOnce = willIterateAtLeastOnce(binaryExpr.getOperator().asString(), v.getInitializer().get().asIntegerLiteralExpr().asNumber().intValue(), binaryExpr.getRight().asIntegerLiteralExpr().asNumber().intValue());
                                if (!iteratesAtLeastOnce) {
                                    // the for loop does not iterate at least once
                                    int lineNumber = n.getRange().get().begin.line;
                                    arg.add(String.format("The for loop's condition at line %d should be true at least once.", lineNumber));
                                }
                            }
                        }
                    });
                }
            });
        });
        return super.visit(n, arg);
    }

    private boolean willIterateAtLeastOnce(String operator, int init, int compare) {
        boolean returnVal = true;
        if (operator.equals("<")) {
            returnVal = init < compare;
        } else if (operator.equals(">")) {
            returnVal = init > compare;
        } else if (operator.equals("==")) {
            returnVal = init == compare;
        } else if (operator.equals("<=")) {
            returnVal = init <= compare;
        } else if (operator.equals(">=")) {
            returnVal = init >= compare;
        }
        return returnVal;
    }
}
