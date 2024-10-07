package com.example.a7;

import Model.Expression.*;
import Model.Statements.*;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Value.BoolIValue;
import Model.Value.IntIValue;
import Model.Value.StringIValue;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SelectFormController.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public ArrayList<IStmt> getStatements() {
        ArrayList<IStmt> programs = new ArrayList<>();

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntIValue(2))), new PrintStmt(new VarExp("v"))));


        //int a;
        //int b;
        //a = 2 + 3 * 5;
        //b = a + 1;
        //Print(b) is represented as:
        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntIValue(2)), new ArithExp('*', new ValueExp(new IntIValue(3)), new ValueExp(new IntIValue(5))))), new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntIValue(1)))), new PrintStmt(new VarExp("b"))))));

        //boolean a;
        //a = true;
        //(If a Then v = 2 Else v = 3);
        //Print(v) is represented as
        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolIValue(true))), new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntIValue(2))), new AssignStmt("v", new ValueExp(new IntIValue(3)))), new PrintStmt(new VarExp("v"))))));

        //String varf;
        //varf="test.in";
        //openRFile(varf);
        //int varc;
        //readFile(varf,varc);print(varc);
        //readFile(varf,varc);print(varc);
        //closeRFile(varf);
        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(
                new AssignStmt("varf", new ValueExp(new StringIValue("src/test.in"))), new CompStmt(
                new OpenRFileStmt(new VarExp("varf")), new CompStmt(
                new VarDeclStmt("varc", new IntType()), new CompStmt(
                new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(
                new PrintStmt(new VarExp("varc")), new CompStmt(
                new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(
                new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf"))))))))));
        // Ref int v;
        // new(v,20);
        // Ref Ref int a;
        // new(a,v);
        // print(v);print(a)
        // At the end of execution:
        // Heap={1->20, 2->(1,int)}, SymTable={v->(1,int), a->(2,Ref int)} and Out={(1,int),(2,Ref int)}
        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new newStmt("a", new VarExp("v")), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        // Ref int v;
        // new(v,20);
        // Ref Ref int a;
        // new(a,v);
        // print(rH(v));print(rH(rH(a))+5)
        // At the end of execution:  Heap={1->20, 2->(1,int)}, SymTable={v->(1,int), a->(2,Ref int)} and Out={20, 25}
        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new newStmt("a", new VarExp("v")), new CompStmt(
                new PrintStmt(new rH(new VarExp("v"))),
                new PrintStmt(new ArithExp('+', new rH(new rH(new VarExp("a"))), new ValueExp(new IntIValue(5)))))))));
        //Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
        // At the end of execution:  Heap={1->30}, SymTable={v->(1,int)} and Out={20, 35}
        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new PrintStmt(new rH(new VarExp("v"))), new CompStmt(
                new wH("v", new ValueExp(new IntIValue(30))),
                new PrintStmt(new ArithExp('+', new rH(new VarExp("v")), new ValueExp(new IntIValue(5))))))));

        //Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new newStmt("a", new VarExp("v")), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(30))),
                new PrintStmt(new rH(new rH(new VarExp("a")))))))));
        // int v; v=4; (while (v>0) print(v);v=v-1);print(v)
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntIValue(4))), new CompStmt(
                new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntIValue(0)), ">"), new CompStmt(
                        new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntIValue(1)))))),
                new PrintStmt(new VarExp("v")))));
        //  int v;
        // Ref int a;
        // v=10;
        // new(a,22);
        // fork(wH(a,30); v=32; print(v); print(rH(a)));
        // print(v);
        // print(rH(a))
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntIValue(10))), new CompStmt(
                new newStmt("a", new ValueExp(new IntIValue(22))), new CompStmt(
                new ForkStmt(new CompStmt(
                        new wH("a", new ValueExp(new IntIValue(30))), new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntIValue(32))), new CompStmt(
                        new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));

        //a=1;b=2;c=5;
        //(switch(a*10)
        // (case (b*c) : print(a);print(b))
        // (case (10) : print(100);print(200))
        // (default : print(300)));
        //print(300)
        IStmt ex11 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(
                new VarDeclStmt("b", new IntType()), new CompStmt(
                new VarDeclStmt("c", new IntType()), new CompStmt(
                new AssignStmt("a", new ValueExp(new IntIValue(1))), new CompStmt(
                new AssignStmt("b", new ValueExp(new IntIValue(2))), new CompStmt(
                new AssignStmt("c", new ValueExp(new IntIValue(5))), new CompStmt(
                new SwitchStmt(new ArithExp('*',new VarExp("a"),new ValueExp(new IntIValue(10))),new ArithExp('*',new VarExp("b"),new VarExp("c")),new ValueExp(new IntIValue(10)),new CompStmt(new PrintStmt(new VarExp("a")),new PrintStmt(new VarExp("b"))),new CompStmt(new PrintStmt(new ValueExp(new IntIValue(100))),new PrintStmt(new ValueExp(new IntIValue(200)))),new PrintStmt(new ValueExp(new IntIValue(300)))),
                new PrintStmt(new ValueExp(new IntIValue(300))))))))));

        programs.add(ex1);
        programs.add(ex2);
        programs.add(ex3);
        programs.add(ex4);
        programs.add(ex5);
        programs.add(ex6);
        programs.add(ex7);
        programs.add(ex8);
        programs.add(ex9);
        programs.add(ex10);
        programs.add(ex11);
        return programs;
    }

    public static void main(String[] args) {
        launch();
    }
}