package Controller;

import Exception.MyException;
import Model.ADT.MyIHeap;
import Model.PrgState;
import Model.Value.IValue;
import Model.Value.RefIValue;
import Repository.IRepo;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    IRepo repo;
    ExecutorService executor;

    public IRepo getRepo() {
        return this.repo;
    }

    public Controller(IRepo repo) {
        this.repo = repo;
    }

    Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    Set<Integer> getAddrFromSymTable(Collection<IValue> symTableValues, MyIHeap<Integer, IValue> heap) {
        Set<Integer> result = new HashSet<>();
        symTableValues.stream()
                .filter(v -> v instanceof RefIValue)
                .forEach(v -> {
                    RefIValue v1 = (RefIValue) v;
                    result.add(v1.getAddress());
                    var heapValue = heap.lookup(v1.getAddress());
                    while (heapValue instanceof RefIValue) {
                        result.add(((RefIValue) heapValue).getAddress());
                        heapValue = heap.lookup(((RefIValue) heapValue).getAddress());
                    }
                });
        return result;
    }

    List<PrgState> removeCompletedPrg(List<PrgState> inPrgList) {
        return inPrgList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void allStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgList());
        if (!prgList.isEmpty()) {
            List<Integer> addressed = new ArrayList<>();
            for (PrgState prg : prgList) {
                addressed.addAll(getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap()));
            }
            for (PrgState prg : prgList) {
                prg.getHeap().setContent(unsafeGarbageCollector(addressed, prg.getHeap().getContent()));
            }
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(repo.getPrgList());
        }
        executor.shutdownNow();
        repo.setPrgList(prgList);
    }

    public void oneStepForAllPrg(List<PrgState> prgList) throws MyException {
        prgList.forEach(prg -> {
            int iid = prg.getIID();
            System.out.println(iid);
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (p::oneStep))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println(e.getMessage());
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            System.out.println("Could not invoke the callables!");
        }

        if (newPrgList == null) throw new MyException("PrgList null");
        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            int iid = prg.getIID();
            System.out.println(iid);
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                e.printStackTrace();
            }
        });
        repo.setPrgList(prgList);
    }
}