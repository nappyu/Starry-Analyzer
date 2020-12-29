package data;

import java.util.ArrayList;

public class ClassData {

    private String className;

    private ArrayList<MethodData> methods;

    public ClassData(String className) {
        this.className = className;
        methods = new ArrayList<>();
    }

    public String getClassName() {
        return this.className;
    }

    public ArrayList<MethodData> getMethods() {
        return this.methods;
    }

    public void addMethod(MethodData method) {
        methods.add(method);
    }
}
