package poc.ishanjoshi.me.exploremonash.utils;

import com.google.android.gms.maps.model.LatLng;

public class C {

    //-------------------------------------------------------------------------------------
    //Intent Bundle Configuration
    public static String PLACE_TO_START_INDEX = "placesInSelectedTour";
    public static String SELECTED_TOUR_ID = "selectedTourId";
    public static String SELECTED_PLACE_DETAILED_VIEW = "selectedPlaceForDetailedView";
    public static String SELECTED_PLACE_NAME_DETAILED_VIEW = "selectedPlaceNameDetailedView";



    //-------------------------------------------------------------------------------------
    //Other constants
    public static int BLOCK_SIZE = 100;



    //-------------------------------------------------------------------------------------
    //Map configuration

    //Coordinates
    public static LatLng MONASH_CENTER = new LatLng(-37.911920, 145.133708);

    //Zoom levels
    public static float DEFAULT_ZOOM = 15.8f;

    //Nearby a place threshold
    public static double NEARBY_PLACE_THRESHOLD = 20;
    public static double RADIUS_CIRCLE_THRESHOLD = 1000;


    //---------------
    //Defaults
    public static String DEFAULT_MONASH_IMAGE = "https://upload.wikimedia.org/wikipedia/commons/thumb/f/f1/Monash_University_Menzies_Building.jpg/1024px-Monash_University_Menzies_Building.jpg";

}
