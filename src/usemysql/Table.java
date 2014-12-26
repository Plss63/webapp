package usemysql;

public class Table {
    private int ID;
    private String localName;
    private String generateName;
    
    
    public int getID() {
        return ID;
    }
    public void setID(int iD) {
        ID = iD;
    }
    public String getLocalName() {
        return localName;
    }
    public void setLocalName(String localName) {
        this.localName = localName;
    }
    public String getGenerateName() {
        return generateName;
    }
    public void setGenerateName(String generateName) {
        this.generateName = generateName;
    }
}
