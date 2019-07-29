package poc.ishanjoshi.me.exploremonash.model;

import com.google.android.gms.maps.model.LatLng;

public class Place {

    public String description, id, image, name;
    public String longDescription = "";
    public double lat, lng;

    private boolean hasBeenVisited = false;




    public Place() {}

    public Place(String description, String id, String image, String name, String longDescription, double lat, double lng) {
        this.description = description;
        this.id = id;
        this.image = image;
        this.name = name;
        this.longDescription = longDescription;
        this.lat = lat;
        this.lng = lng;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public LatLng getLatLng() {
        return new LatLng(this.lat, this.lng);
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public boolean isHasBeenVisited() {
        return hasBeenVisited;
    }

    public void setHasBeenVisited(boolean hasBeenVisited) {
        this.hasBeenVisited = hasBeenVisited;
    }


    @Override
    public String toString() {
        return "Place{" +
                "description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", name='" + name + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", hasBeenVisited=" + hasBeenVisited +
                '}';
    }
}
