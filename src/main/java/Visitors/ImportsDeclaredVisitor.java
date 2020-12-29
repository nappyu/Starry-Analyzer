package Visitors;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.HashMap;

public class ImportsDeclaredVisitor extends VoidVisitorAdapter<HashMap<String, String>> {

    @Override
    public void visit(ImportDeclaration id, HashMap<String, String> outList) {
        super.visit(id, outList);
        // Adding the import statements to the map, identifier -> full qualified name
        // Can't resolve if a * import is used
        if (!id.isAsterisk()) {
            outList.put(id.getName().getIdentifier(), id.getName().asString());
        }
    }
}
