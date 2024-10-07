package Repository;


import Exception.MyException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Repo implements IRepo {
    //List of ProgState objects
    private List<PrgState> lst;
    private String logFilePath = " repository_content";

    //constructor, getter
    public Repo() {
        this.lst = new ArrayList<PrgState>();
        Scanner input = new Scanner(System.in);
    }

    //constructor with path
    public Repo(String path) {
        this.lst = new ArrayList<PrgState>();
        this.logFilePath = path;
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.lst;
    }


    @Override
    public void addPrg(PrgState prg) {
        this.lst.add(prg);
    }

    @Override
    public void logPrgStateExec(PrgState p_state) throws MyException {
        String file_contains = p_state.toString();
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(file_contains);
            logFile.close();
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }

    }


    @Override
    public void setPrgList(List<PrgState> list) {
        this.lst = list;
    }


}
