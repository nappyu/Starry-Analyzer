package data;

import java.util.ArrayList;

public class MethodData {

    private String methodName;

    private Status status;

    private ArrayList<String> messages;

    private ArrayList<String> dependencies;

    public MethodData(String methodName) {
        this.methodName = methodName;
        status = Status.OK;
        messages = new ArrayList<>();
        dependencies = new ArrayList<>();
    }

    public String getMethodName() {
        return methodName;
    }

    public Status getStatus() {
        return status;
    }

    public ArrayList<String> getMessages() {
        return messages;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {

        OK, WARNING, HIGH_RISK
    }
}
