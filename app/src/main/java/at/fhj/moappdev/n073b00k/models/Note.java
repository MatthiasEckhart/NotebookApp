package at.fhj.moappdev.n073b00k.models;

import com.j256.ormlite.field.DatabaseField;

import java.util.Date;

/**
 * This class represents the Note model and depends on {@code OrmLite}.
 */
public class Note {

    /**
     * The main identifier of the note object.
     */
    @DatabaseField(generatedId = true)
    private int id;

    /**
     * The note's title.
     */
    @DatabaseField
    private String title;

    /**
     * The note's content.
     */
    @DatabaseField
    private String content;

    /**
     * The note's due date. Users generally aim to finish this task until due date.
     */
    @DatabaseField
    private Date date;

    /**
     * The latitude of the user's location.
     */
    @DatabaseField
    private double latitude;

    /**
     * The longitude of the user's location.
     */
    @DatabaseField
    private double longitude;

    /**
     * This field is used to indicate that the user has finished this task.
     * The initial value is always {@code false}.
     */
    @DatabaseField
    private boolean done;

    /**
     * Explicit empty constructor.
     */
    public Note() {

    }

    /**
     * Constructor for note objects.
     *
     * @param title     the note's title
     * @param content   the note's content
     * @param date      the note's due date
     * @param latitude  the latitude of the user's location
     * @param longitude the longitude of the user's location
     */
    public Note(String title, String content, Date date, double latitude, double longitude) {
        this.setTitle(title);
        this.setContent(content);
        this.setDate(date);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
        this.setDone(false);
    }

    /**
     * @return the note's ID
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return the note's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return the note's content
     */
    public String getContent() {
        return this.content;
    }

    /**
     * @return the note's due date
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * @return the latitude of the user's location
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * @return the longitude of the user's location
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * @return {@code true} if the task has been finished
     */
    public boolean isDone() {
        return this.done;
    }

    /**
     * Sets the note's title.
     *
     * @param title the note's title to set
     * @return the note's instance
     */
    public Note setTitle(String title) {
        if (title == null) throw new IllegalArgumentException("Note title is null.");
        this.title = title;
        return this;
    }

    /**
     * Sets the note's content.
     *
     * @param content the note's content to set
     * @return the note's instance
     */
    public Note setContent(String content) {
        if (content == null) throw new IllegalArgumentException("Note content is null.");
        this.content = content;
        return this;
    }

    /**
     * Sets the note's due date.
     *
     * @param date the note's due date to set
     * @return the note's instance
     */
    public Note setDate(Date date) {
        if (date == null) throw new IllegalArgumentException("Note due date is null.");
        this.date = date;
        return this;
    }

    /**
     * Sets the latitude of the user's location.
     *
     * @param latitude the latitude to set
     * @return the note's instance
     */
    public Note setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     * Sets the longitude of the user's location.
     *
     * @param longitude the longitude to set
     * @return the note's instance
     */
    public Note setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     * Sets the note's status.
     *
     * @param done the note's status to set
     * @return the note's instance
     */
    public Note setDone(boolean done) {
        this.done = done;
        return this;
    }
}
