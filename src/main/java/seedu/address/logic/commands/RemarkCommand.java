package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashMap;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * Changes the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {
    public static final String NEWLINE = System.lineSeparator();
    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the remark of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing remark will be overwritten by the input." + NEWLINE
            + "Parameters: INDEX (must be a positive integer) "
            + "r/ [REMARK]" + NEWLINE
            + "Example: " + COMMAND_WORD + " 1 "
            + "r/ Likes to swim.";
    public static final String MESSAGE_ADD_REMARK_SUCCESS = "Added remark to Person: %1$s";
    public static final String MESSAGE_DELETE_REMARK_SUCCESS = "Removed remark from Person: %1$s";

    private Index index;
    private Remark remark;

    /**
     * Returns a new RemarkCommand based on the given index and remark.
     *
     * @param index  of the person in the filtered person list to edit the remark
     * @param remark of the person to be updated to
     */
    public RemarkCommand(Index index, Remark remark) {
        requireAllNonNull(index, remark);
        this.index = index;
        this.remark = remark;
    }

    public Remark getRemark() {
        return remark;
    }

    public Index getIndex() {
        return index;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> personList = model.getFilteredPersonList();

        int zeroBasedIndex = index.getZeroBased();
        if (zeroBasedIndex >= personList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = personList.get(zeroBasedIndex);
        Person editedPerson = new Person(personToEdit.getName(),
                personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(),
                remark);
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(personToEdit));
    }

    /**
     * Generates a command execution success message based on whether the remark is added to or removed from
     * {@code personToEdit}.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = remark.value.isEmpty()
                ? MESSAGE_DELETE_REMARK_SUCCESS
                : MESSAGE_ADD_REMARK_SUCCESS;
        return String.format(message, personToEdit.toString());
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
        // Short circuit if same object
        if (obj == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(obj instanceof RemarkCommand)) {
            return false;
        }
        // State check
        RemarkCommand rc = (RemarkCommand) obj;
        return index.equals(rc.getIndex())
                && remark.equals(rc.getRemark());
    }
}
