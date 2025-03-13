package de.prog3.projektarbeit.data.objects;

public abstract class DataObject {

    private int id;

    public DataObject(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
