package com.example.lla;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
    private static List<QuestionList> javaQuestion() {

        final List<QuestionList> QuestionLists = new ArrayList<>();

        final QuestionList question1=new QuestionList("What is the size of int variable?","16 bit","32 bit", "8 bit","64 bit","32 bit","");
        final QuestionList question2=new QuestionList("Number of primitive data types in Java are?","6","7", "8","9","8","");
        final QuestionList question3=new QuestionList("Which of the following option leads to the portability and security of Java?","Bytecode is executed by JVM","The applet makes the Java code secure and portable", "Use of exception handling","Dynamic binding between objects","Bytecode is executed by the JVM","");
        final QuestionList question4=new QuestionList("Which of the following is not a Java features?","Dynamic","Architecture Neutral", "Use of pointers","Object-oriented","Use of pointers","");
        final QuestionList question5=new QuestionList("_____ is used to find and fix bugs in the Java programs.","JVM","JRE", "JDK","JDB","JDB","");


        QuestionLists.add(question1);
        QuestionLists.add(question2);
        QuestionLists.add(question3);
        QuestionLists.add(question4);
        QuestionLists.add(question5);

        return QuestionLists;

    }

    private static List<QuestionList> phpQuestion() {

        final List<QuestionList> QuestionLists = new ArrayList<>();

        final QuestionList question1=new QuestionList("Full form of php","Hypertext preprocessor","Pretext hypertext", "Preprocessor Home Page","professional Home Page","Hypertext preprocessor","");
        final QuestionList question2=new QuestionList(" Who is known as the father of PHP ?","Drek Kolkevi","List Barely", "Rasmus Lerdrof","None of the above","Rasmus Lerdrof","");
        final QuestionList question3=new QuestionList(" Variable name in PHP starts with ","! (Exclamation)","$ (Dollar)", "& (Ampersand)","# (Hash)","$ (Dollar)","");
        final QuestionList question4=new QuestionList(" Which of the following is the default file extension of PHP?",".php",".html", ".hphp",".xml",".php","");
        final QuestionList question5=new QuestionList("Which of the following is not a variable scope in PHP?","Extern","Local", "Global","static","Extern","");


        QuestionLists.add(question1);
        QuestionLists.add(question2);
        QuestionLists.add(question3);
        QuestionLists.add(question4);
        QuestionLists.add(question5);

        return QuestionLists;

    }

    private static List<QuestionList> htmlQuestion() {

        final List<QuestionList> QuestionLists = new ArrayList<>();

        final QuestionList question1=new QuestionList("Full form of HTML","Hypertext Markup Language","High text markup language", "All of the above ","None of this ","Hypertext Markup Language","");
        final QuestionList question2=new QuestionList("Who is the father of HTML?","Rasmus Lerdorf","Brendan Eich", "Tim Berners-Lee","Sergey Brin","Tim Berners-Lee","");
        final QuestionList question3=new QuestionList("What is the correct syntax of doctype in HTML5?","</doctype html>","<doctype html>", "<!doctype html>","<doctype html!>","<!doctype html>","");
        final QuestionList question4=new QuestionList("Which of the following is used to read an HTML page and render it?","Web server","Web network", "Web browser","Web matrix","Web browser","");
        final QuestionList question5=new QuestionList("What is HTML?","HTML describes the structure of a webpage","HTML is the standard markup language mainly used to create web pages", "HTML consists of a set of elements that helps the browser how to view the content","All of the mentioned","All of the mentioned","");


        QuestionLists.add(question1);
        QuestionLists.add(question2);
        QuestionLists.add(question3);
        QuestionLists.add(question4);
        QuestionLists.add(question5);

        return QuestionLists;

    }

    private static List<QuestionList> androidQuestion() {

        final List<QuestionList> QuestionLists = new ArrayList<>();

        final QuestionList question1=new QuestionList("Android introduced in year","2008","2007", "2009","None of this ","2007","");
        final QuestionList question2=new QuestionList("Android is -","an operating system","a web browser", "a web server","None of the above","an operating system","");
        final QuestionList question3=new QuestionList("Under which of the following Android is licensed?","Sourceforge","OSS", "None of the above","Apache/MIT","Apache/MIT","");
        final QuestionList question4=new QuestionList("For which of the following Android is mainly developed?","server","desktop", "Laptop","Mobile devices","Mobile devices","");
        final QuestionList question5=new QuestionList("Android is based on which of the following language?","Java","C", "C++","Noe of the above","java","");


        QuestionLists.add(question1);
        QuestionLists.add(question2);
        QuestionLists.add(question3);
        QuestionLists.add(question4);
        QuestionLists.add(question5);

        return QuestionLists;

    }

    public static List<QuestionList> getQuestion(String selectedTopicName){
        switch (selectedTopicName){
            case "java":
                return javaQuestion();
            case "php":
                return phpQuestion();
            case"Android ":
                return androidQuestion();
            default:
                return htmlQuestion();

        }

    }

}