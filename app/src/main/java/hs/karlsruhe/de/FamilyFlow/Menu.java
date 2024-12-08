package hs.karlsruhe.de.FamilyFlow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


public class Menu extends Activity {

    private class OnListItemClick implements AdapterView.OnItemClickListener {
//
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
//            Intent intent;
//            if(sprache[position] == "German"){
//                //GetApplicationContext liefert globaler app context (bleibt solange erhalten wie
//                // Applikations Objekt am leben ist
//                intent = new Intent(getApplicationContext(), BMI_Deutschland.class);
//                startActivity(intent);
//            }else if (sprache[position] == "English (USA)"){
//                intent = new Intent(getApplicationContext(), BMI_USA.class);
//                startActivity(intent);
//            }else if (sprache[position] == "Portugese"){
//                intent = new Intent(getApplicationContext(), BMI_Portugal.class);
//                startActivity(intent);
//            }else if(sprache[position] == "Denglisch"){
//                intent = new Intent(getApplicationContext(), BMI_Denglisch.class);
//                startActivity(intent);
//            }
//
//
        }
//    }

//    ListView list;
//    String[] sprache = {"German", "English (USA)", "Portugese", "Denglisch"};
//    Integer[] imageId = {R.mipmap.beer_icon,
//                        R.mipmap.burger_icon,
//                        R.mipmap.caipirinha_icon,
//                        R.mipmap.faitclub};


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_menu);
//
//        //Adapter ist Brücke zwischen einer View und der benötigten Dateien dieser View
//        CustomList adapter = new CustomList(Menu.this, sprache, imageId);
//        list = (ListView) findViewById(R.id.listViewMenue);
//        list.setAdapter(adapter);
//        list.setOnItemClickListener(new OnListItemClick());
//
    }
}
