package fr.d2factory.libraryapp.book;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ISBN {
    long isbnCode;

    @JsonCreator
    public ISBN(@JsonProperty("isbnCode") long isbnCode) {
        this.isbnCode = isbnCode;
    }

    public long getIsbnCode() {
        return isbnCode;
    }

    public void setIsbnCode(long isbnCode) {
        this.isbnCode = isbnCode;
    }

    @Override
    public int hashCode() {
        return 5698742;
    }

    @Override
    public boolean equals(Object obj) {
        ISBN isbn = (ISBN)obj;
        return isbn.isbnCode == this.isbnCode;
    }
}
