package com.example.demo;

import org.myself.pojo.Person;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by bruce on 2019/7/2 22:44
 */
//@Slf4j
public class Main {

    static String studentString =
            "package org.myself.pojo;\n\n" +
                    "public class Student{\n" +
                    "\tprivate String studentId;\n\n" +
                    "\tpublic String getStudentId(){\n" +
                    "\t\treturn this.studentId;\n" +
                    "\t}\n\n" +
                    "\tpublic void setStudentId(String studentId){\n" +
                    "\t\tthis.studentId = studentId;\n" +
                    "\t}\n" +
                    "}\n";

    //private static String studentString = "/* hehha */"                +
    //        "public class Student{                                  "+
    //        "       private String  studentId;                      "+
    //        "       public String getStudentId(){                   "+
    //        "           return this.studentId;                      "+
    //        "       }                                               "+
    //        "}";


    public static void main(String[] args) throws Exception {
        //createStudent();
        Package aPackage = Person.class.getPackage();
        String name = aPackage.getName();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("package ").append(name).append(";\n\n");
        stringBuilder.append("{import}");

        stringBuilder.append("public class Student {\n");

        HashSet<String> imports = new HashSet<>();
        Field[] declaredFields = Person.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            int modifiers = declaredField.getModifiers();
            if (Modifier.isPublic(modifiers)){
                Class<?> type = declaredField.getType();
                imports.add(type.getName());
                stringBuilder.append("    public").append(" ").append(type.getSimpleName()).append(" ").append(declaredField.getName()).append(";\n");
            }
            if (Modifier.isProtected(modifiers)){
                Class<?> type = declaredField.getType();
                imports.add(type.getName());
                stringBuilder.append("    protected").append(" ").append(type.getSimpleName()).append(" ").append(declaredField.getName()).append(";\n");
            }
            System.out.println();

        }

        Method[] declaredMethods = Person.class.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {


            //System.out.println();

        }

        StringBuilder stringBuilder1 = new StringBuilder();
        for (String anImport : imports) {
            stringBuilder1.append("import ").append(anImport).append(";\n");
        }

        stringBuilder.append("}\n\n");

        String replace = stringBuilder.toString().replace("{import}", stringBuilder1.toString());

        System.out.println(replace);
    }


    private static void createStudent() throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(null, null, null);
        ClassJavaFileManager classJavaFileManager = new ClassJavaFileManager(standardFileManager);

        //String dest = "D:/workspaces/demo/src";

        StringObject stringObject = new StringObject(new URI("/org/myself/pojo/"+"Student.java"), JavaFileObject.Kind.SOURCE, studentString);
        JavaCompiler.CompilationTask task = compiler.getTask(null, classJavaFileManager, null, null, null, Arrays.asList(stringObject));
        if (task.call()) {
            ClassJavaFileObject javaFileObject = classJavaFileManager.getClassJavaFileObject();
            ClassLoader classLoader = new MyClassLoader(javaFileObject);
            Object student = classLoader.loadClass("org.myself.pojo.Student").newInstance();
            Method getStudetnId = student.getClass().getMethod("getStudentId");
            Object invoke = getStudetnId.invoke(student);
            //logger.info("class==={}", student);
            System.out.println(student);
        }
    }

    /**
     * 自定义fileManager
     */
    static class ClassJavaFileManager extends ForwardingJavaFileManager {

        private ClassJavaFileObject classJavaFileObject;

        public ClassJavaFileManager(JavaFileManager fileManager) {
            super(fileManager);
        }

        public ClassJavaFileObject getClassJavaFileObject() {
            return classJavaFileObject;
        }

        //这个方法一定要自定义
        @Override
        public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
            return (classJavaFileObject = new ClassJavaFileObject(className, kind));
        }
    }

    /**
     * 存储源文件
     */
    static class StringObject extends SimpleJavaFileObject {

        private String content;

        public StringObject(URI uri, Kind kind, String content) {
            super(uri, kind);
            this.content = content;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
            return this.content;
        }
    }

    /**
     * class文件（不需要存到文件中）
     */
    static class ClassJavaFileObject extends SimpleJavaFileObject {

        ByteArrayOutputStream outputStream;

        public ClassJavaFileObject(String className, Kind kind) {
            super(URI.create(className + kind.extension), kind);
            this.outputStream = new ByteArrayOutputStream();
        }

        //这个也要实现
        @Override
        public OutputStream openOutputStream() throws IOException {
            return this.outputStream;
        }

        public byte[] getBytes() {
            return this.outputStream.toByteArray();
        }
    }

    //自定义classloader
    static class MyClassLoader extends ClassLoader {
        private ClassJavaFileObject stringObject;

        public MyClassLoader(ClassJavaFileObject stringObject) {
            this.stringObject = stringObject;
        }

        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            byte[] bytes = this.stringObject.getBytes();
            return defineClass(name, bytes, 0, bytes.length);
        }
    }


}
