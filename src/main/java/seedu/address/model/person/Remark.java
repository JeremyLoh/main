package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;

/**
 * Represents a Person's remark in the address book.
 * Guarantees: immutable, is always valid.
 */
public class Remark {
    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        this.value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj
     * argument; {@code false} otherwise.
     * @see #hashCode()
     * @see HashMap
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        // instanceof handles null values
        if (!(obj instanceof Remark)) {
            return false;
        }
        Remark other = (Remark) obj;
        return value.equals(other.value);
    }

    /**
     * Returns a hash code value for the object. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link HashMap}.
     *
     * @return a hash code value for this object.
     * @see Object#equals(Object)
     * @see System#identityHashCode
     */
    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
