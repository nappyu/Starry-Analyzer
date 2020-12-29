package com.gruppesechs.ast_parser;

import Visitors.*;
import com.github.javaparser.ParseProblemException;
import com.github.javaparser.ParseResult;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.Log;
import com.github.javaparser.utils.SourceRoot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import data.ClassData;
import data.MethodData;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import helper.UnusedImports;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    private static void CheckForUnusedParams(MethodDeclaration method, ArrayList<String> out) {
        BlockStmt body = method.getBody().get();
        NodeList<Parameter> params = method.getParameters();
        ArrayList<SimpleName> simpleNames = new ArrayList<>();
        // Get all the simple names in the statements
        body.accept(new SimpleNameModifierVisitor(), simpleNames);
        // For each param, check if their name is in the list of simple name
        for (Parameter param : params) {
            boolean used = false;
            SimpleName name = param.getName();
            for (SimpleName ref : simpleNames) {
                if (ref.getIdentifier() == name.getIdentifier()) {
                    // this param is used, break
                    used = true;
                    break;
                }
            }
            // check if this param was used
            if (!used) {
                // Unused, add it to the messages
                out.add(String.format("Unused Param: %s in Method: %s on Line: %d", name.asString(), method.getNameAsString(), method.getRange().get().begin.line));
            }
        }
    }

    private static void CheckForUnusedVariables(MethodDeclaration method, ArrayList<String> out) {
        BlockStmt body = method.getBody().get();
        ArrayList<SimpleName> declaredVars = new ArrayList<>();
        ArrayList<SimpleName> varReferences = new ArrayList<>();
        // get all declared variable names
        body.accept(new VariableDecModifierVisitor(), declaredVars);
        // get all simple name references
        body.accept(new SimpleNameModifierVisitor(), varReferences);
        // create a map identifier : refCount
        HashMap<String, Integer> refCounts = new HashMap<>();
        // populate the map
        for (SimpleName ref : varReferences) {
            String name = ref.getIdentifier();
            Integer count = refCounts.getOrDefault(name, 0);
            refCounts.put(name, count + 1);
        }
        for (SimpleName var : declaredVars) {
            Integer count = refCounts.getOrDefault(var.getIdentifier(), 0);
            if (count < 2) {
                // why 2? because declaration itself contains 1
                out.add(String.format("Unused Variable: %s in Method: %s on Line: %d", var.asString(), method.getNameAsString(), method.getRange().get().begin.line));
            }
        }
    }

    private static void DecorateMessages(ArrayList<String> messages, boolean isWarning) {
        if (isWarning) {
            for (int i = 0; i < messages.size(); i++) {
                String msg = messages.get(i);
                msg = "Warning: " + msg;
                messages.set(i, msg);
            }
        } else {
            for (int i = 0; i < messages.size(); i++) {
                String msg = messages.get(i);
                msg = "Critical: " + msg;
                messages.set(i, msg);
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        // JavaParser has a minimal logging class that normally logs nothing.
        // Let's ask it to write to standard out:
        Log.setAdapter(new Log.StandardOutStandardErrorAdapter());
        // Set up a JFileChooser for the user to interact with
        JFileChooser chooser = new JFileChooser();
        File workingDirectory = new File(System.getProperty("user.dir"));
        chooser.setCurrentDirectory(workingDirectory);
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Java files", "java");
        chooser.setFileFilter(filter);
        // Loop until the user selects a file/directory or exits
        ArrayList<CompilationUnit> cuList = new ArrayList<>();
        Map<String, String> errors = new HashMap<>();
        while (true) {
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file/directory: " + chooser.getSelectedFile().getName());
            } else {
                System.out.println("No file was selected, exiting program");
                return;
            }
            try {
                if (chooser.getSelectedFile().isFile()) {
                    cuList.add(StaticJavaParser.parse(chooser.getSelectedFile()));
                } else if (chooser.getSelectedFile().isDirectory()) {
                    SourceRoot sourceRoot = new SourceRoot(chooser.getSelectedFile().toPath());
                    sourceRoot.parse("", new SourceRoot.Callback() {

                        @Override
                        public Result process(Path localPath, Path absolutePath, ParseResult<CompilationUnit> result) {
                            if (result.getProblems().size() == 0) {
                                if (result.getResult().isPresent()) {
                                    cuList.add(result.getResult().get());
                                }
                                return Result.SAVE;
                            } else {
                                errors.put(localPath.toString(), result.toString());
                                return Result.DONT_SAVE;
                            }
                        }
                    });
                }
                break;
            } catch (ParseProblemException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Please select a valid Java file.");
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
        Log.info("Parsing file(s)...");
        List<ClassData> classDataList = new ArrayList<>();
        for (CompilationUnit cu : cuList) {
            // each CompilationUnit is an AST of a single class file
            String className = cu.getPrimaryTypeName().isPresent() ? cu.getPrimaryTypeName().get() : "";
            ClassData classData = new ClassData(className);
            VoidVisitorAdapter<HashMap<MethodDeclaration, ArrayList<String>>> methodNamesVisitor = new MethodNameVisitor();
            HashMap<MethodDeclaration, ArrayList<String>> methods = new HashMap<>();
            cu.accept(methodNamesVisitor, methods);
            for (MethodDeclaration method : methods.keySet()) {
                ArrayList<String> ls = new ArrayList<String>();
                method.accept(new MethodDependencyVisitor(), ls);
                methods.put(method, ls);
            }
            // get a list of declared methods as strings to prepare to filter methods that aren't declared
            // within the same class
            List<String> declaredMethodStrings = new ArrayList<>();
            methods.keySet().forEach(methodDeclaration -> declaredMethodStrings.add(methodDeclaration.getDeclarationAsString(false, false, false)));
            for (MethodDeclaration method : methods.keySet()) {
                // filter out called methods that aren't declared within the same class
                List<String> methodCallStrings = methods.get(method);
                for (String methodCall : new ArrayList<>(methodCallStrings)) {
                    boolean calledMethodIsDeclaredInClass = false;
                    for (String declaredMethod : declaredMethodStrings) {
                        String[] tempList = declaredMethod.split("\\s+");
                        String temp = declaredMethod;
                        for (String s : tempList) {
                            if (s.contains("("))
                                temp = s.split("\\(")[0];
                        }
                        if (temp.equals(methodCall)) {
                            calledMethodIsDeclaredInClass = true;
                            break;
                        }
                    }
                    if (!calledMethodIsDeclaredInClass) {
                        methodCallStrings.remove(methodCall);
                    }
                }
                String methodName = method.getDeclarationAsString(false, false, false);
                String calls = methods.get(method).toString();
                // check for infinite loops
                // any messages returned by these three checks indicate a high risk for bugs
                ArrayList<String> messages = new ArrayList<>();
                // method.accept(new IfStmtModifierVisitor(), messages);
                method.accept(new EmptyMethodVisitor(), messages);
                method.accept(new WhileLoopModifierVisitor(), messages);
                method.accept(new ForLoopModifierVisitor(), messages);
                // messages for yellow tags - WARNINGs
                ArrayList<String> warnings = new ArrayList<>();
                // to do comment checks
                method.accept(new TodoModifierVisitor(), warnings);
                // unused parameter checks
                CheckForUnusedParams(method, warnings);
                // unused variable checks
                CheckForUnusedVariables(method, warnings);
                // unused imports checks
                UnusedImports unusedImports = new UnusedImports(cu);
                warnings.addAll(unusedImports.getUnusedImports());
                // append "Warning: " and "Critical: "
                DecorateMessages(messages, false);
                DecorateMessages(warnings, true);
                // create MethodData objects and attach them to the ClassData objects
                MethodData methodData = new MethodData(methodName);
                methodData.getDependencies().addAll(methods.get(method));
                if (!warnings.isEmpty()) {
                    methodData.setStatus(MethodData.Status.WARNING);
                    methodData.getMessages().addAll(warnings);
                }
                if (!messages.isEmpty()) {
                    methodData.setStatus(MethodData.Status.HIGH_RISK);
                    methodData.getMessages().addAll(messages);
                }
                classData.getMethods().add(methodData);
                System.out.println(methodName + " = " + calls);
            }
            classDataList.add(classData);
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Path path = Paths.get("src", "main", "resources", "output.json");
            Path path2 = Paths.get("src", "ui", "src", "output.json");
            Files.deleteIfExists(path);
            Files.deleteIfExists(path2);
            Writer writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE);
            gson.toJson(classDataList, writer);
            writer.close();
            Writer writer2 = Files.newBufferedWriter(path2, StandardOpenOption.CREATE);
            gson.toJson(classDataList, writer2);
            writer2.close();
            // execute front-end in the src/ui folder
            System.out.println("===================================================================");
            System.out.println("Starting front-end on Port 3000 using React . . .");
            String projectDirectory = System.getProperty("user.dir") + "\\src\\ui";
            StringBuffer output = new StringBuffer();
            String command = System.getProperty("os.name").startsWith("Windows") ? "npm.cmd start" : "npm start";
            Process p = Runtime.getRuntime().exec(command, null, new File(projectDirectory));
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
            System.out.println(output.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
