package poc.ishanjoshi.me.exploremonash.model;

/**
 * To hold data about the places
 */
public class Tour {


    public String id, name, image, description, place_start;


    public Tour() {
        //For firebase UI to map data to Java
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace_start() {
        return place_start;
    }

    public void setPlace_start(String place_start) {
        this.place_start = place_start;
    }


    @Override
    public String toString() {
        return "Tour{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", place_start='" + place_start + '\'' +
                '}';
    }
}
