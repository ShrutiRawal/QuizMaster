package com.example.quizmaster;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import com.example.quizmaster.QuizContract.CategoriesTable;

import com.example.quizmaster.QuizContract.QuestionsTable;


public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyAwesomeQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private static QuizDbHelper instance;

    private SQLiteDatabase db;

    private QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new QuizDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("DBMS");
        insertCategory(c1);
        Category c2 = new Category("Computer Networks");
        insertCategory(c2);
        Category c3 = new Category("Operating System");
        insertCategory(c3);
    }

    public void addCategory(Category category) {
        db = getWritableDatabase();
        insertCategory(category);
    }

    public void addCategories(List<Category> categories) {
        db = getWritableDatabase();

        for (Category category : categories) {
            insertCategory(category);
        }
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionsTable() {
        Question q1 = new Question("____ uniquely identifies each record in the table.",
                "Primary Key", "Foreign Key", "Super Key", 1,
                Question.DIFFICULTY_EASY, Category.DBMS);
        insertQuestion(q1);
        Question q2 = new Question("The location of a resource on the internet is given by?",
                "Protocol", "URL", "E-Mail address", 2,
                Question.DIFFICULTY_EASY, Category.COMPUTER_NETWORKS);
        insertQuestion(q2);
        Question q3 = new Question("Which of the following is not an operating system?",
                "Windows", "Linux", "Oracle", 3,
                Question.DIFFICULTY_EASY, Category.OPERATING_SYSTEMS);
        insertQuestion(q3);
        Question q4 = new Question("When does page fault occur?",
                "The page is not present in memory", "The page is present in memory", "Page Buffering occured", 1,
                Question.DIFFICULTY_EASY, Category.OPERATING_SYSTEMS);
        insertQuestion(q4);
        Question q5 = new Question("Non existing, Easy: A is correct",
                "A", "B", "C", 1,
                Question.DIFFICULTY_EASY, 4);
        insertQuestion(q5);
        Question q6 = new Question("Non existing, Medium: B is correct",
                "A", "B", "C", 2,
                Question.DIFFICULTY_MEDIUM, 5);
        insertQuestion(q6);
        Question q7 = new Question("HTTP stands for?",
                "Hypertext transfer protocol","Hypertext tracing protocol","Hypertext transfer program",1,
                Question.DIFFICULTY_EASY,Category.COMPUTER_NETWORKS);
        insertQuestion(q7);
        Question q8 = new Question("Which of the following enables us to view data from a table based on a specific criterion?",
                "Macro","Query","Form",2,
                Question.DIFFICULTY_EASY,Category.DBMS);
        insertQuestion(q8);
        Question q9 = new Question("The overall description of a database is called______.",
                "Database definition","Data integrity","Database schema",3,
                Question.DIFFICULTY_MEDIUM,Category.DBMS);
        insertQuestion(q9);
        Question q10 = new Question("A data dictionary is a repository that manages _____.",
                "Memory","Metadata","Data Validator",2,
                Question.DIFFICULTY_MEDIUM,Category.DBMS);
        insertQuestion(q10);
        Question q11 = new Question("Which software prevents the unauthorized access to a system?",
                "Router","Firewall","Gateway",2,
                Question.DIFFICULTY_MEDIUM,Category.COMPUTER_NETWORKS);
        insertQuestion(q11);
        Question q12 = new Question("The maximum length (in bytes) of an IPv4 datagram is?",
                "65535","32","512",1,
                Question.DIFFICULTY_MEDIUM,Category.COMPUTER_NETWORKS);
        insertQuestion(q12);
        Question q13 = new Question("Banker's algorithm is used to ?",
                "prevent deadlock","recover from deadlock","None of these",1,
                Question.DIFFICULTY_MEDIUM,Category.OPERATING_SYSTEMS);
        insertQuestion(q13);
        Question q14 = new Question("If the page size increases, the internal fragmentation ______",
                "remain constant","Decreases","Increases",3,
                Question.DIFFICULTY_MEDIUM,Category.OPERATING_SYSTEMS);
        insertQuestion(q14);
        Question q15 = new Question(" Entity is a _________",
                "Thing in real world","Object of relation","Present working model",1,
                Question.DIFFICULTY_HARD,Category.DBMS);
        insertQuestion(q15);
        Question q16 = new Question("In an E-R diagram, weak entity set is represented as - ",
                "Underline","Double line","Double diamond",3,
                Question.DIFFICULTY_HARD,Category.DBMS);
        insertQuestion(q16);
        Question q17 = new Question("Parity bits are used for?",
                "Encryption of data","Identifying users","Detecting errors",3,
                Question.DIFFICULTY_HARD,Category.COMPUTER_NETWORKS);
        insertQuestion(q17);
        Question q18 = new Question("IANA stands for?",
                "Internal Associative Numbers Authority","Internet Assigned Numbers Authority","Internal Assigned Numbers Authority",2,
                Question.DIFFICULTY_HARD,Category.COMPUTER_NETWORKS);
        insertQuestion(q18);
        Question q19 = new Question("Thrashing occurs when ________. ",
                "excessive swapping takes place ","deadlock occurs ","no swapping takes place",1,
                Question.DIFFICULTY_HARD,Category.OPERATING_SYSTEMS);
        insertQuestion(q19);
        Question q20 = new Question("The address generated by the CPU is referred to as ______",
                "Physical address","Logical address","None of these",2,
                Question.DIFFICULTY_HARD,Category.OPERATING_SYSTEMS);
        insertQuestion(q20);
    }

    public void addQuestion(Question question) {
        db = getWritableDatabase();
        insertQuestion(question);
    }

    public void addQuestions(List<Question> questions) {
        db = getWritableDatabase();

        for (Question question : questions) {
            insertQuestion(question);
        }
    }

    private void insertQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Category category = new Category();
                category.setId(c.getInt(c.getColumnIndex(CategoriesTable._ID)));
                category.setName(c.getString(c.getColumnIndex(CategoriesTable.COLUMN_NAME)));
                categoryList.add(category);
            } while (c.moveToNext());
        }

        c.close();
        return categoryList;
    }

    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(int categoryID, String difficulty) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? " +
                " AND " + QuestionsTable.COLUMN_DIFFICULTY + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(categoryID), difficulty};

        Cursor c = db.query(
                QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }
}
