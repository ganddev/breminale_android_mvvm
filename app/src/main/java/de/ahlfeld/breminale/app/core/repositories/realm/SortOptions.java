package de.ahlfeld.breminale.app.core.repositories.realm;

import android.os.Parcel;
import android.os.Parcelable;

import de.ahlfeld.breminale.app.R;

/**
 * Created by bjornahlfeld on 18.06.16.
 */
public enum SortOptions implements Parcelable{
    ALPHABETICALLY(R.string.by_alphabet),
    TIME(R.string.by_time),
    LOCATION(R.string.by_location);


    private final int name;

    SortOptions(int name) {
        this.name = name;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeInt(ordinal());
    }


    public static final Creator<SortOptions> CREATOR = new Creator<SortOptions>() {
        @Override
        public SortOptions createFromParcel(final Parcel source) {
            return SortOptions.values()[source.readInt()];
        }

        @Override
        public SortOptions[] newArray(final int size) {
            return new SortOptions[size];
        }
    };

    public int getName() {
        return name;
    }
}
