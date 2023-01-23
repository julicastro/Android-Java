package demo.android.fruitworld.models;

public class Fruit {

    private String name;
    private String origen;
    private Integer image;

    public Fruit(String name, String origen, Integer image) {
        this.name = name;
        this.origen = origen;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }
}
