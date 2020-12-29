package helper;

import Visitors.ImportsDeclaredVisitor;
import Visitors.MethodSignatureVisitor;
import Visitors.ObjectCreationVisitor;
import Visitors.VariablesDeclaredVisitor;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.gruppesechs.ast_parser.Parser;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnusedImports {

    CompilationUnit compilationUnit;

    public UnusedImports(CompilationUnit cu) {
        this.compilationUnit = cu;
    }

    public ArrayList<String> getUnusedImports() {
        if (this.compilationUnit == null) {
            return new ArrayList<>();
        }
        ArrayList<String> warnings = new ArrayList<>();
        // example getting all the method declarations
        List<String> methodDeclarations = new ArrayList<>();
        VoidVisitor<List<String>> methodNameGetter = new MethodSignatureVisitor();
        methodNameGetter.visit(this.compilationUnit, methodDeclarations);
        // for (int i = 0; i < methodDeclarations.size(); i++) {
        // System.out.println("Method declaration " + i + " : " + methodDeclarations.get(i));
        // }
        // Getting all the import statements
        HashMap<String, String> importsMap = new HashMap<>();
        VoidVisitor<HashMap<String, String>> importsVisitor = new ImportsDeclaredVisitor();
        importsVisitor.visit(this.compilationUnit, importsMap);
        // for (String name: importsMap.keySet()) {
        // System.out.println("Import identifier : " + name +", \t Qualified name: " + importsMap.get(name));
        // }
        // Getting all declared types of variables
        List<String> varsMap = new ArrayList<>();
        VoidVisitor<List<String>> fieldsVisitor = new VariablesDeclaredVisitor();
        fieldsVisitor.visit(this.compilationUnit, varsMap);
        // for (String name: varsMap) {
        // System.out.println("Variable type : " + name);
        // }
        // Getting all the right hand side of object creation expressions
        List<String> objsCreatedList = new ArrayList<>();
        VoidVisitor<List<String>> objsCreatedVisitor = new ObjectCreationVisitor();
        objsCreatedVisitor.visit(this.compilationUnit, objsCreatedList);
        // for (String obj: objsCreatedList) {
        // System.out.println("Creation expression : " + obj);
        // }
        // Evaluate if each import statement is present at least once.
        // Join all the lists that indicate where an import could be used
        Set<String> combinedLists = Stream.of(methodDeclarations, varsMap, objsCreatedList).flatMap(Collection::stream).collect(Collectors.toSet());
        // System.out.println("COMBINED SET....");
        // for (String s : combinedLists){
        // System.out.println(s);
        // }
        // Map each import statement to a boolean value
        HashMap<String, Boolean> isImportUsedMap = new HashMap<>();
        boolean foundImport;
        for (String importDec : importsMap.keySet()) {
            foundImport = false;
            for (String s : combinedLists) {
                if (s.contains(importDec)) {
                    isImportUsedMap.put(importDec, new Boolean(true));
                    foundImport = true;
                    break;
                }
            }
            if (!foundImport) {
                isImportUsedMap.put(importDec, new Boolean(false));
            }
        }
        for (String key : isImportUsedMap.keySet()) {
            if (!isImportUsedMap.get(key)) {
                // System.out.println("WARNING! Import statement: " + importsMap.get(key) + " is NOT USED");
                warnings.add("Unused Import Statement: " + importsMap.get(key));
            } else {
            // System.out.println("Import statement: " + importsMap.get(key) + " is USED");
            }
        }
        return warnings;
    }
}
