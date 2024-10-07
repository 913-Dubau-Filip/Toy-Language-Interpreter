package GUI;

public class FileTableModule {

    private String id = null;
    private String name = null;

    public FileTableModule(String a, String b) {
        this.id = a;
        this.name = b;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}