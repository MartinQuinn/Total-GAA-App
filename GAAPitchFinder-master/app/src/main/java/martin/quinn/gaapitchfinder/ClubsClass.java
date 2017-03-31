package martin.quinn.gaapitchfinder;

import android.os.Parcel;
import android.os.Parcelable;

// This is my Club Class.

public class ClubsClass implements Parcelable {

    private int id;
    private String name;
    private String countyName;
    private String colors;
    private String decription;
    private String locaiton;
    private String website;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getLocaiton() {
        return locaiton;
    }

    public void setLocaiton(String locaiton) {
        this.locaiton = locaiton;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.countyName);
        dest.writeString(this.colors);
        dest.writeString(this.decription);
        dest.writeString(this.locaiton);
        dest.writeString(this.website);
    }

    public ClubsClass() {
    }

    private ClubsClass(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.countyName = in.readString();
        this.colors = in.readString();
        this.decription = in.readString();
        this.locaiton = in.readString();
        this.website = in.readString();
    }

    public static final Parcelable.Creator<ClubsClass> CREATOR = new Parcelable.Creator<ClubsClass>() {
        public ClubsClass createFromParcel(Parcel source) {
            return new ClubsClass(source);
        }

        public ClubsClass[] newArray(int size) {
            return new ClubsClass[size];
        }
    };
}
