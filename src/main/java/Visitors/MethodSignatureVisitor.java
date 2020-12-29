package Visitors;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.List;

public class MethodSignatureVisitor extends VoidVisitorAdapter<List<String>> {

    @Override
    public void visit(MethodDeclaration md, List<String> outList) {
        super.visit(md, outList);
        outList.add(md.getDeclarationAsString(false, true, true));
    }
}
