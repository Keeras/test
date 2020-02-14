package model;

public class FoundPostModel {
    private String name, color, description, fnlandmark, fncity, relandmark, recity, image;

    public FoundPostModel(String name, String color, String description, String fnlandmark, String fncity, String relandmark, String recity, String image) {
        this.name = name;
        this.color = color;
        this.description = description;
        this.fnlandmark = fnlandmark;
        this.fncity = fncity;
        this.relandmark = relandmark;
        this.recity = recity;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFnlandmark() {
        return fnlandmark;
    }

    public void setFnlandmark(String fnlandmark) {
        this.fnlandmark = fnlandmark;
    }

    public String getFncity() {
        return fncity;
    }

    public void setFncity(String fncity) {
        this.fncity = fncity;
    }

    public String getRelandmark() {
        return relandmark;
    }

    public void setRelandmark(String relandmark) {
        this.relandmark = relandmark;
    }

    public String getRecity() {
        return recity;
    }

    public void setRecity(String recity) {
        this.recity = recity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}