package com.example.a7;

import Controller.Controller;
import Exception.MyException;
import GUI.FileTableModule;
import GUI.HeapTableModule;
import GUI.SymTableModule;
import Model.ADT.*;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Value.IValue;
import Repository.Repo;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private int selectedID = 1;

    public Controller controller;
    private PrgState progState;

    @FXML
    private Button runOneStepButton;

    @FXML
    private Label myLable;

    @FXML
    private TextField nrProgStates;

    @FXML
    private TableView<HeapTableModule> heapTable;

    @FXML
    private TableColumn<HeapTableModule, String> heapAddressColumn;

    @FXML
    private TableColumn<HeapTableModule, String> heapValueColumn;

    @FXML
    private TableView<SymTableModule> symTable;

    @FXML
    private TableColumn<SymTableModule, String> symVarNameColumn;

    @FXML
    private TableColumn<SymTableModule, String> symValueColumn;

    @FXML
    private ListView<String> exeStack;

    @FXML
    private ListView<IValue> outTable;

    @FXML
    private TableView<FileTableModule> fileTable;

    @FXML
    private TableColumn<FileTableModule, Integer> fietableid;

    @FXML
    private TableColumn<FileTableModule, String> filetablename;

    @FXML
    private ListView<Integer> progStateIdentifiers;

    public MainController(Controller c) {
        this.controller = c;
    }

    public IStmt selectedProg;

    public void setcontroler(IStmt selectedProg) {
        this.selectedProg = selectedProg;
    }

    public MainController() {
        //this.controller = c;
    }

    public void displayprog(String prog) {
        myLable.setText(prog);
    }

    public void swich1(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("SelectFormController.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public void initializefirst() throws MyException {
        selectedProg.typeCheck(new MyDictionary<>());
        PrgState p = new PrgState(new MyStack<>(), new MyDictionary<>(), new MyList<>(), new MyDictionary<>(), new MyHeap<>(), selectedProg, 1);
        Repo r = new Repo();
        r.addPrg(p);
        Controller c = new Controller(r);
        this.controller = c;
        this.progState = p;
    }

    public void refresh() throws MyException {
        populateAll();
    }

    @FXML
    TableColumn symTableName = new TableColumn();
    @FXML
    TableColumn symTableValue = new TableColumn();

    private void populateAll() throws MyException {
        this.selectedID = 1;
        populateIdentifiers();
        populateOutput(this.progState);
        populateFileTable(this.progState);
        populateHeapTable(this.progState);
        populateSymbolTable(this.progState);
        populateExecutionStack(this.progState);
        populateExeStack(this.progState);
    }

    private void populateIdentifiers() {
        List<PrgState> programStates = controller.getRepo().getPrgList();
        progStateIdentifiers.setItems(FXCollections.observableList(programStates.stream().map(PrgState::getId).collect(Collectors.toList()))); //set the identifiers
        nrProgStates.setText("" + programStates.size());
    }

    private void populateHeapTable(PrgState state) {
        this.heapTable.getItems().clear();
        MyIHeap<Integer, IValue> heap = (MyIHeap<Integer, IValue>) state.getHeap();
        var iterator = heap.getIterator();
        while (iterator.hasNext()) {
            Integer item = iterator.next();
            this.heapTable.getItems().add(
                    new HeapTableModule(item.toString(),
                            heap.lookup(item).toString()));
        }
        this.heapTable.refresh();
    }

    private void populateFileTable(PrgState state) {

        this.fileTable.getItems().clear();
        MyIDictionary<String, BufferedReader> file = state.getFileTable();
        var iterator = file.getIterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            this.fileTable.getItems().add(
                    new FileTableModule(item,
                            file.lookup(item).toString()));
        }
        this.fileTable.refresh();
    }

    private void populateOutput(PrgState state) {
        MyIList<IValue> output = (MyIList<IValue>) state.getOutList();
        this.outTable.setItems(FXCollections.observableList(output.getall()));
        this.outTable.refresh();
    }

    private void populateSymbolTable(PrgState state) {
        this.symTable.getItems().clear();
        MyIDictionary<String, IValue> symbolTable = state.getSymTable();
        var iterator = symbolTable.getIterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            this.symTable.getItems().add(
                    new SymTableModule(item.toString(),
                            symbolTable.lookup(item).toString()));
        }
        this.heapTable.refresh();
    }


    private void populateExecutionStack(PrgState state) {
        MyIStack<IStmt> execStack = state.getStk();
        if (state.getId() == selectedID) {
            this.exeStack.setItems(FXCollections.observableList(execStack.getContent().stream().map(Object::toString).toList()));
            this.exeStack.refresh();
        }
    }

    private void populateExeStack(PrgState ps) {
        if (ps == null) return;
        MyIStack<IStmt> exeStack = ps.getStk();
        List<String> exeStackList = new ArrayList<>();
        if (exeStack.isEmpty()) {
            this.exeStack.setItems(FXCollections.observableList(FXCollections.emptyObservableList()));
            this.exeStack.refresh();
            return;
        }
        for (IStmt s : exeStack.getContent().stream().toList())
            exeStackList.add(s.toString());
        Collections.reverse(exeStackList);

        this.exeStack.setItems(FXCollections.observableList(exeStackList));
        this.exeStack.refresh();
    }

    @FXML
    public void populate() throws MyException {
        populateAll();
        try {
            this.controller.allStep();
            populateAll();
        } catch (MyException | InterruptedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heapAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        symVarNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        symValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        fietableid.setCellValueFactory(new PropertyValueFactory<>("id"));
        filetablename.setCellValueFactory(new PropertyValueFactory<>("name"));

        progStateIdentifiers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                if (progStateIdentifiers.getSelectionModel().getSelectedItem() != null) {

                    for (PrgState item : controller.getRepo().getPrgList())
                        if (item.getIID() == progStateIdentifiers.getSelectionModel().getSelectedItem())
                            progState = item;

                    populateExeStack(progState);
                    populateSymbolTable(progState);
                }
            }
        });
    }
}