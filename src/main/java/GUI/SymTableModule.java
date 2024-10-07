package GUI;

public class SymTableModule {

    private String name = null;
    private String value = null;

    public SymTableModule(String a, String b) {
        this.name = a;
        this.value = b;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}