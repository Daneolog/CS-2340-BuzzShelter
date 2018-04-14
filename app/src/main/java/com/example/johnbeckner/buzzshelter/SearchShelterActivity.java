package com.example.johnbeckner.buzzshelter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Shelter search activity
 * @author team
 * @version 1.0
 */
public class SearchShelterActivity extends AppCompatActivity {

    private EditText shelterName;
    private Spinner genderSpinner;
    private Spinner ageSpinner;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_shelter);

        shelterName = findViewById(R.id.NameField);
        genderSpinner = findViewById(R.id.GenderSpinner);
        genderSpinner.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Gender.values()));
        ageSpinner = findViewById(R.id.AgeSpinner);
        ageSpinner.setAdapter((new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, AgeRange.values())));
        searchButton = findViewById(R.id.Search);

        searchButton.setOnClickListener(view -> {
            String nameFilter = "" + shelterName.getText();
            Object selectedGenderSpinner = genderSpinner.getSelectedItem();
            String genderFilter = ((Gender) selectedGenderSpinner).toString();
            Object selectedAgeRange = ageSpinner.getSelectedItem();
            String ageRangeFilter = ((AgeRange) selectedAgeRange).toString();

            ShelterList.filterShelters(nameFilter, genderFilter, ageRangeFilter);

            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.putParcelableArrayListExtra("Filtered Shelter List",
                    ShelterList.getFilteredList());
            startActivity(intent);
        });

    }

    enum Gender {
        ANYONE ("Anyone"),
        WOMEN ("Women"),
        MEN ("Men");

        private final String genderString;
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

        private final String AgeRangeString;
        AgeRange(String AgeRangeString) {
            this.AgeRangeString = AgeRangeString;
        }

        @Override
        public String toString() {
            return AgeRangeString;
        }
    }

}
