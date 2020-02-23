package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
class RemarkCommandTest {
    private UserPrefs userPrefs = new UserPrefs();
    private Model model = new ModelManager(TypicalPersons.getTypicalAddressBook(),
            userPrefs);
    private String remarkStub = "Test Remark";

    @Test
    void execute_addRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(remarkStub).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(editedPerson.getRemark().value));
        CommandResult expectedCommandResult = new CommandResult(
                String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, firstPerson));
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                userPrefs);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    void execute_addRemarkFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark(remarkStub).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(remarkStub));
        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS,
                firstPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                userPrefs);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_deleteRemarkUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(""));
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                firstPerson.toString());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                userPrefs);
        expectedModel.setPerson(firstPerson, editedPerson);
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_deleteRemarkFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark(""));
        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS,
                firstPerson.toString());
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                userPrefs);
        expectedModel.setPerson(firstPerson, editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBounds = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBounds,
                new Remark(remarkStub));
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(remarkCommand, model, expectedMessage);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBounds = INDEX_SECOND_PERSON;
        // Ensure index given is still within size of address book
        assertTrue(outOfBounds.getZeroBased() < model.getAddressBook().getPersonList().size());
        RemarkCommand remarkCommand = new RemarkCommand(outOfBounds,
                new Remark(remarkStub));
        String expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(remarkCommand, model, expectedMessage);
    }
}
