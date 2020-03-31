package com.thestoryofus;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class AddNewBookScreen extends DatabaseBasedScreen implements AdapterView.OnItemSelectedListener, TextWatcher, RadioGroup.OnCheckedChangeListener {

    private static final String TAG = "AddNewBookScreen";

    private EditText inputName;
    private EditText inputAuthor;
    private EditText inputPublishYear;
    private Spinner inputLanguage;
    private Spinner inputGenre;
    private RadioGroup inputIsAvailable;
    private Button submitButton;
    private ImageButton clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book_screen);

        this.inputName = findViewById(R.id.addNewBook_nameInput);
        this.inputName.addTextChangedListener(this);

        this.inputAuthor = findViewById(R.id.addNewBook_authorInput);
        this.inputAuthor.addTextChangedListener(this);

        this.inputPublishYear = findViewById(R.id.addNewBook_publishYearInput);
        this.inputPublishYear.addTextChangedListener(this);

        this.inputLanguage = findViewById(R.id.addNewBook_languagesSpinner);
        setSpinnerSelector(this.inputLanguage, R.array.books_languages);

        this.inputGenre = findViewById(R.id.addNewBook_genresSpinner);
        setSpinnerSelector(this.inputGenre, R.array.books_genres);

        this.inputIsAvailable = findViewById(R.id.addNewBook_isAvailableGroupInput);
        this.inputIsAvailable.setOnCheckedChangeListener(this);

        this.submitButton = findViewById(R.id.addNewBook_btn_addBook);
        this.clearButton = findViewById(R.id.addNewBook_btn_clearInput);

        initScreen();

        submitButtonListener();
        clearButtonListener();
    }

    /*
        Function initializes screen - resets all input fields to be empty/default values, and
        disables submit button.
        Input: None
        Output: None
     */
    private void initScreen() {
        this.submitButton.setEnabled(false);
        this.inputName.setText("");
        this.inputAuthor.setText("");
        this.inputPublishYear.setText("");
        this.inputLanguage.setSelection(0);
        this.inputGenre.setSelection(0);
        this.inputIsAvailable.clearCheck();
    }

    /*
        Function creates a listener to the Submit button in the screen.
        The listener will create a new book using the information given in user input fields,
        insert the book to the SQL database file, and finish the activity.
        Input: None
        Output: None
     */
    private void submitButtonListener() {
        this.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewBookScreen.this.sqlDB.insertBook(new ArcBook(
                        -1,
                        new Book(
                                AddNewBookScreen.this.inputName.getText().toString(),
                                AddNewBookScreen.this.inputAuthor.getText().toString(),
                                mapToLanguage(AddNewBookScreen.this.inputLanguage.getSelectedItem().toString()),
                                Integer.parseInt(AddNewBookScreen.this.inputPublishYear.getText().toString()),
                                mapToGenre(AddNewBookScreen.this.inputGenre.getSelectedItem().toString()),
                                AddNewBookScreen.this.inputIsAvailable.getCheckedRadioButtonId() == R.id.addNewBook_isAvailableTrueInput,
                                false
                        ),
                        DatetimeHelper.getNow()
                ));
                finish();
            }
        });
    }


    /*
        Function receives a language as displayed on UI screen, and retrieves its matching numeric
        value in the SQL database.
        Input: A String of a language
        Output: Integer identifier of the language in SQL database file
     */
    public int mapToLanguage(String language) {
        int bookLang = 0;
        if (language.equals("עברית")) {
            bookLang = Book.b_langs.HEBREW.ordinal();
        }
        else if (language.equals("English")) {
            bookLang = Book.b_langs.ENGLISH.ordinal();
        }
        return bookLang;
    }

    /*
        Function receives a genre as displayed on UI screen, and retrieves its matching numeric
        value in the SQL database.
        Input: A String of a genre
        Output: Integer identifier of the genre in SQL database file
     */
    public int mapToGenre(String genre) {
        int bookGenr = 0;

        if (genre.equals("פנטזיה")) {
            bookGenr = Book.b_genres.FANTASY.ordinal();
        }
        else if (genre.equals("מדע בדיוני")) {
            bookGenr = Book.b_genres.SCIENCE_FICTION.ordinal();
        }
        else if (genre.equals("פשע")){
            bookGenr = Book.b_genres.CRIME.ordinal();
        }
        else if (genre.equals("דרמה")) {
            bookGenr = Book.b_genres.DRAMA.ordinal();
        }
        else if (genre.equals("אימה")) {
            bookGenr = Book.b_genres.HORROR.ordinal();
        }
        else if (genre.equals("מסתורין")) {
            bookGenr = Book.b_genres.MYSTERY.ordinal();
        }
        else if (genre.equals("שירה")) {
            bookGenr = Book.b_genres.POETRY.ordinal();
        }
        else if (genre.equals("רומן רומנטי")) {
            bookGenr = Book.b_genres.ROMANCE.ordinal();
        }
        else if (genre.equals("מותחן")) {
            bookGenr = Book.b_genres.THRILLER.ordinal();
        }
        else if (genre.equals("נוער")) {
            bookGenr = Book.b_genres.YOUNG_ADULT.ordinal();
        }
        else if (genre.equals("הרפתקאות")) {
            bookGenr = Book.b_genres.ADVENTURE.ordinal();
        }
        else if (genre.equals("רומן תקופתי")) {
            bookGenr = Book.b_genres.HISTORICAL_FICTION.ordinal();
        }
        else if (genre.equals("רומן")) {
            bookGenr = Book.b_genres.REALISTIC.ordinal();
        }
        else if (genre.equals("ביוגרפיה")) {
            bookGenr = Book.b_genres.BIOGRAPHY.ordinal();
        }
        else if (genre.equals("אוטוביוגרפיה")) {
            bookGenr = Book.b_genres.AUTOBIOGRAPHY.ordinal();
        }
        return bookGenr;
    }

    /*
        Function creates a listener to the Clear Input button in the screen.
        The listener will initialize screen when called (resets all input fields).
        Input: None
        Output: None
     */
    private void clearButtonListener() {
        this.clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewBookScreen.this.initScreen();
            }
        });
    }

    /*
        Function checks if all the fields in the screen are filled with input from user.
        Input: None
        Output: true if all the required input is given by the user, false otherwise.
     */
    private boolean isCompleteInput() {
        return !this.inputName.getText().toString().isEmpty() &&
                !this.inputAuthor.getText().toString().isEmpty() &&
                !this.inputPublishYear.getText().toString().isEmpty() &&
                this.inputIsAvailable.getCheckedRadioButtonId() != -1;
    }

   /* public void showDatePickerDialog(View v) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "publishYearPicker");
    }
    */

   /*
        Function creates and sets an ArrayAdapter for an AdapterView of type spinner.
        In addition, function sets this class to be the listener of the spinner.
        Input: AdapterView of type Spinner, ID of a CharSequence resource
        Output: None
    */
    private void setSpinnerSelector(Spinner spinner, int resID) {
        // Create adapter (provide context, data source and layout for each source)
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, resID, android.R.layout.simple_spinner_item);

        // Specify a whole menu layout
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply adapter and selection listener
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }


    /*
        Function is called when an option of a spinner is set.
        Input: The AdapterView from which the data is received, the specific View (inside the AdapterView),
                the position of the data in the adapter and row ID (if adapter is cursor. Otherwise - id equals position)
        Output: None
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        int parentID = parent.getId();
        switch (parentID) {
            case R.id.addNewBook_languagesSpinner:
                Log.i(TAG, parent.getItemAtPosition(pos).toString());
                break;
            case R.id.addNewBook_genresSpinner:
                Log.i(TAG, parent.getItemAtPosition(pos).toString());
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    /*
        Function is called when text is changed inside EditText box (that had set this class as its listener).
        Checks if all the fields are filled, and changes submit button state accordingly.
        Input: The newly added text, start index of change, length of old text and length of newly added text.
        Output: None
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.submitButton.setEnabled(isCompleteInput());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    /*
        Function is called when a RadioGroup (that had set this class as its listener) member is checked.
        Checks if all the fields are filled, and changes submit button state accordingly.
        Input: The RadioGroup view and the checked item ID
        Output: None
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        this.submitButton.setEnabled(isCompleteInput());
    }
}
