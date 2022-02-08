package hu.petrik.etlap;

public class Etlap {
    private  int id;
    private int cost;
    private String name;
    private String description;
    private String category;

    public Etlap(int id, int cost, String name, String description, String category) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
