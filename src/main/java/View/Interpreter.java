package View;

import Controller.Controller;
import Exception.MyException;
import Model.ADT.MyDictionary;
import Model.ADT.MyHeap;
import Model.ADT.MyList;
import Model.ADT.MyStack;
import Model.Expression.*;
import Model.PrgState;
import Model.Statements.*;
import Model.Type.*;
import Model.Value.BoolIValue;
import Model.Value.IntIValue;
import Model.Value.StringIValue;
import Repository.Repo;

public class Interpreter {

    public static void main(String[] args) {

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        IStmt ex1 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("v", new ValueExp(new IntIValue(2))), new PrintStmt(new VarExp("v"))));

        try {
            ex1.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState p1 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex1, 1);
            Repo r1 = new Repo();
            r1.addPrg(p1);
            Controller c1 = new Controller(r1);
            menu.addCommand(new RunExample("1", "int v; v=2; Print(v)", c1));
        }

        IStmt ex2 = new CompStmt(new VarDeclStmt("a", new IntType()), new CompStmt(new VarDeclStmt("b", new IntType()), new CompStmt(new AssignStmt("a", new ArithExp('+', new ValueExp(new IntIValue(2)), new ArithExp('*', new ValueExp(new IntIValue(3)), new ValueExp(new IntIValue(5))))), new CompStmt(new AssignStmt("b", new ArithExp('+', new VarExp("a"), new ValueExp(new IntIValue(1)))), new PrintStmt(new VarExp("b"))))));
        try {
            ex2.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState p2 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex2, 1);
            Repo r2 = new Repo();
            r2.addPrg(p2);
            Controller c2 = new Controller(r2);
            menu.addCommand(new RunExample("2", "int a; int b; a=2+3*5; b=a+1; Print(b)", c2));
        }

        IStmt ex3 = new CompStmt(new VarDeclStmt("a", new BoolType()), new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(new AssignStmt("a", new ValueExp(new BoolIValue(true))), new CompStmt(new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntIValue(2))), new AssignStmt("v", new ValueExp(new IntIValue(3)))), new PrintStmt(new VarExp("v"))))));
        try {
            ex3.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState p3 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex3, 1);
            Repo r3 = new Repo();
            r3.addPrg(p3);
            Controller c3 = new Controller(r3);
            menu.addCommand(new RunExample("3", "bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)", c3));
        }

        IStmt ex4 = new CompStmt(new VarDeclStmt("varf", new StringType()), new CompStmt(
                new AssignStmt("varf", new ValueExp(new StringIValue("src/test.in"))), new CompStmt(
                new OpenRFileStmt(new VarExp("varf")), new CompStmt(
                new VarDeclStmt("varc", new IntType()), new CompStmt(
                new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(
                new PrintStmt(new VarExp("varc")), new CompStmt(
                new ReadFileStmt(new VarExp("varf"), "varc"), new CompStmt(
                new PrintStmt(new VarExp("varc")), new CloseRFileStmt(new VarExp("varf"))))))))));
        try {
            ex4.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState p4 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex4, 1);
            Repo r4 = new Repo();
            r4.addPrg(p4);
            Controller c4 = new Controller(r4);
            menu.addCommand(new RunExample("4", "string varf; varf=\"test.in\"; openRFile(varf); int varc; readFile(varf,varc); print(varc); readFile(varf,varc); print(varc); closeRFile(varf)", c4));
        }

        IStmt ex5 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new newStmt("a", new VarExp("v")), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new VarExp("a")))))));
        try {
            ex5.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState p5 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex5, 1);
            Repo r5 = new Repo();
            r5.addPrg(p5);
            Controller c5 = new Controller(r5);
            menu.addCommand(new RunExample("5", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)", c5));
        }

        IStmt ex6 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new newStmt("a", new VarExp("v")), new CompStmt(
                new PrintStmt(new rH(new VarExp("v"))),
                new PrintStmt(new ArithExp('+', new rH(new rH(new VarExp("a"))), new ValueExp(new IntIValue(5)))))))));
        try {
            ex6.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            Repo r6 = new Repo();
            PrgState crtPrgState6 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex6, 1);
            r6.addPrg(crtPrgState6);
            Controller c6 = new Controller(r6);
            menu.addCommand(new RunExample("6", "Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)", c6));
        }

        IStmt ex7 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new PrintStmt(new rH(new VarExp("v"))), new CompStmt(
                new wH("v", new ValueExp(new IntIValue(30))),
                new PrintStmt(new ArithExp('+', new rH(new VarExp("v")), new ValueExp(new IntIValue(5))))))));
        try {
            ex7.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            Repo r7 = new Repo();
            PrgState crtPrgState7 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex7, 1);
            r7.addPrg(crtPrgState7);
            Controller c7 = new Controller(r7);
            menu.addCommand(new RunExample("7", "Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5)", c7));
        }

        Repo r8 = new Repo();
        IStmt ex8 = new CompStmt(new VarDeclStmt("v", new RefType(new IntType())), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(20))), new CompStmt(
                new VarDeclStmt("a", new RefType(new RefType(new IntType()))), new CompStmt(
                new newStmt("a", new VarExp("v")), new CompStmt(
                new newStmt("v", new ValueExp(new IntIValue(30))),
                new PrintStmt(new rH(new rH(new VarExp("a")))))))));
        try {
            ex8.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState crtPrgState8 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex8, 1);
            r8.addPrg(crtPrgState8);
            Controller c8 = new Controller(r8);
            menu.addCommand(new RunExample("8", "Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))", c8));

        }

        Repo r9 = new Repo();
        IStmt ex9 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntIValue(4))), new CompStmt(
                new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntIValue(0)), ">"), new CompStmt(
                        new PrintStmt(new VarExp("v")),
                        new AssignStmt("v", new ArithExp('-', new VarExp("v"), new ValueExp(new IntIValue(1)))))),
                new PrintStmt(new VarExp("v")))));
        try {
            ex9.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState crtPrgState9 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex9, 1);
            r9.addPrg(crtPrgState9);
            Controller c9 = new Controller(r9);
            menu.addCommand(new RunExample("9", "int v; v=4; (while (v>0) print(v);v=v-1);print(v)", c9));


        }

        Repo r10 = new Repo();
        IStmt ex10 = new CompStmt(new VarDeclStmt("v", new IntType()), new CompStmt(
                new VarDeclStmt("a", new RefType(new IntType())), new CompStmt(
                new AssignStmt("v", new ValueExp(new IntIValue(10))), new CompStmt(
                new newStmt("a", new ValueExp(new IntIValue(22))), new CompStmt(
                new ForkStmt(new CompStmt(
                        new wH("a", new ValueExp(new IntIValue(30))), new CompStmt(
                        new AssignStmt("v", new ValueExp(new IntIValue(32))), new CompStmt(
                        new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a"))))))), new CompStmt(
                new PrintStmt(new VarExp("v")), new PrintStmt(new rH(new VarExp("a")))))))));
        try {
            ex10.typeCheck(new MyDictionary<String, Type>());
        } catch (MyException e) {
            e.printStackTrace();
        } finally {
            PrgState crtPrgState10 = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), ex10, 1);
            r10.addPrg(crtPrgState10);
            Controller c10 = new Controller(r10);
            menu.addCommand(new RunExample("10", "int v; Ref int a; v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a))); print(v);print(rH(a))", c10));
        }

        menu.show();
    }
}