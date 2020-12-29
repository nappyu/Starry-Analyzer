import com.github.javaparser.utils.CodeGenerationUtils;
import com.github.javaparser.utils.SourceRoot;
import com.gruppesechs.ast_parser.Parser;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.io.*;
import java.util.HashMap;

public class Test {

    private String test = "abc";

    public List<String> stringList = new ArrayList<>();

    private int notRefType = 123;

    SourceRoot sourceRoot = new SourceRoot(CodeGenerationUtils.mavenModuleRoot(Parser.class).resolve("src/main/resources"));

    private void test1() {
        List<String> list = new ArrayList<>();
        int b;
        int a = 0;
        a = a + 1;
        list = new LinkedList<>();
        test2();
        test3();
        test4();
        while (true) {
            break;
        }
        int a = 0;
        while (a < 1) {
            break;
        }
        for (int i = 0; i < 10; i++) {
            break;
        }
        for (int i = 10; i < 10; i++) {
            break;
        }
        // should be skipped by ForLoopModifierVisitor when checking for conditions being true at least once
        for (; a < 10; a++) {
            break;
        }
        for (; ; ) {
            break;
        }
        for (; ; ) {
        }
        boolean b = true;
        while (b) {
            break;
        }
    }

    private void test2() {
        test1();
        while (true) {
        }
    }

    private void test2(String s) {
        test1();
        while (true) {
            break;
        }
        int a = 0;
        while (a < 0) {
            break;
        }
    }

    // param never used
    private void test3(String s) {
    // TODO Test one liner
    }

    // variable never used
    private void test4() {
        /*
        TODO: Test block comments
         */
        String s;
    }
}
