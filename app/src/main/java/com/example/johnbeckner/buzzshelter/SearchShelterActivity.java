package com.example.johnbeckner.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SearchShelterActivity extends AppCompatActivity {

    private EditText shelterName;
    private Spinner genderSpinner;
    private Spinner ageSpinner;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shelter);

        shelterName = (EditText) findViewById(R.id.NameField);
        genderSpinner = (Spinner) findViewById(R.id.GenderSpinner);
        genderSpinner.setAdapter(new ArrayAdapter<Gender>(this,
                android.R.layout.simple_spinner_item, Gender.values()));
        ageSpinner = (Spinner) findViewById(R.id.AgeSpinner);
        ageSpinner.setAdapter((new ArrayAdapter<AgeRange>(this,
                android.R.layout.simple_spinner_item, AgeRange.values())));
        searchButton = (Button) findViewById(R.id.Search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameFilter = "" + shelterName.getText();
                String genderFilter = genderSpinner.getSelectedItem().toString();
                String ageRangeFilter = ageSpinner.getSelectedItem().toString();

                ShelterList.filterShelters(nameFilter, genderFilter, ageRangeFilter);

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.putParcelableArrayListExtra("Filtered Shelter List", ShelterList.getFilteredList());
                startActivity(intent);
            }
        });

    }

    enum Gender {
        ANYONE ("Anyone"),
        WOMEN ("Women"),
        MEN ("Men");

        private String genderString;
        Gender(String genderString) {
            this.genderString = genderString;
        }

        @Override
        public String toString() {
            return genderString;
        }
    }

    enum AgeRange {
        ANYONE ("Anyone"),
        FAMILIES_W_NEWBORN ("Families w/ newborns"),
        FAMILIES_W_Children ("Families w/ Children under 5"),
        CHILDREN ("Children"),
        YOUNG_ADULT ("Young Adult");

        private String AgeRangeString;
        AgeRange(String AgeRangeString) {
            this.AgeRangeString = AgeRangeString;
        }

        @Override
        public String toString() {
            return AgeRangeString;
        }
    }

}
