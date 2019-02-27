package com.sandbox.sandbox.gallery;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sandbox.sandbox.R;

import java.util.ArrayList;

public class MainGallery extends AppCompatActivity {

    private final String image_titles[] = {
            "Bonnie Schiffman - Iggy Pop",
            "Catherine Graffam",
            "Daguerreotype Diagram 1",
            "Daguerreotype Diagram 2",
            "Daguerreotype Diagram 3",
            "Haun",
            "Hinkle",
            "Jacobi Photograph of Albert Einstein 2001",
            "Jacobi Photograph of  Eleanor Roosevelt",
            "Jacobi Photograph of Mark Chagall and his daughter Ida",
            "Library Install Shots 1",
            "Library Install Shots 2",
            "Library Install Shots 3",
            "Library Install Shots 4",
            "Library Install Shots 5",
            "Library Install Shots 6",
            "Library Install Shots 7",
            "Map of Deering NH",
            "Wendy Red Star Yakima or Yakama not for me to say"
    };

    private final Integer image_ids[] = {
            R.drawable.mp_bonnieschiffmaniggypop,
            R.drawable.mp_catherine_graffam,
            R.drawable.mp_daguerreotype_diagram,
            R.drawable.mp_daguerreotype_diagram_2,
            R.drawable.mp_daguerreotype_diagram_3,
            R.drawable.mp_hahn,
            R.drawable.mp_hinkle,
            R.drawable.mp_jacobi_photograph_of_albert_einstein_2001461,
            R.drawable.mp_jacobi_photograph_of_eleanor_roosevelt,
            R.drawable.mp_jacobi_photograph_of_mark_chagall_and_his_daughter_ida,
            R.drawable.mp_library_install_shots_1,
            R.drawable.mp_library_install_shots_2,
            R.drawable.mp_library_install_shots_3,
            R.drawable.mp_library_install_shots_4,
            R.drawable.mp_library_install_shots_5,
            R.drawable.mp_library_install_shots_6,
            R.drawable.mp_library_install_shots_7,
            R.drawable.mp_map_of_deering_nh,
            R.drawable.mp_wendy_red_star_yakima_or_yakama_not_for_me_to_say
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.imagegallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CreateList> createLists = prepareData();
        GalleryAdapter adapter = new GalleryAdapter(getApplicationContext(), createLists);
        recyclerView.setAdapter(adapter);


    }
    private ArrayList<CreateList> prepareData(){

        ArrayList<CreateList> theimage = new ArrayList<>();
        for(int i = 0; i< image_titles.length; i++){
            CreateList createList = new CreateList();
            createList.setImage_title(image_titles[i]);
            createList.setImage_ID(image_ids[i]);
            theimage.add(createList);
        }
        return theimage;
    }

}
