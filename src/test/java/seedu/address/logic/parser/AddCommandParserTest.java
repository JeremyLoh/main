package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COD_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.COD_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_INSTRUCTION;
import static seedu.address.logic.commands.CommandTestUtil.COMMENT_DESC_NIL;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DELIVERY_TIMESTAMP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMMENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_DATE_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DELIVERY_TIMESTAMP_TIME_ONLY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_WAREHOUSE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TID_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COD_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESTAMP_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAREHOUSE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.WAREHOUSE_DESC_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalOrders.AMY;
import static seedu.address.testutil.TypicalOrders.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.comment.Comment;
import seedu.address.model.order.Address;
import seedu.address.model.order.Name;
import seedu.address.model.order.Order;
import seedu.address.model.order.Phone;
import seedu.address.model.order.TimeStamp;
import seedu.address.model.order.Warehouse;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.OrderBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Order expectedOrder = new OrderBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + TID_DESC_BOB +NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // multiple TIDs - last TID accepted
        assertParseSuccess(parser, TID_DESC_AMY + TID_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                        + TAG_DESC_FRIEND, new AddCommand(expectedOrder));

        // multiple names - last name accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // multiple timeStamps - last timeStamp accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_AMY + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COMMENT_DESC_NIL
                + TAG_DESC_FRIEND, new AddCommand(expectedOrder));

        // multiple warehouses - last warehouse accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + WAREHOUSE_DESC_AMY + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // multiple cashOnDeliveries - last cashOnDelivery accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_AMY + COD_DESC_BOB +
                        COMMENT_DESC_NIL + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // multiple comment - last comment accepted
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_INSTRUCTION + COMMENT_DESC_NIL + TAG_DESC_FRIEND,
                new AddCommand(expectedOrder));

        // multiple tags - all accepted
        Order expectedOrderMultipleTags = new OrderBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedOrderMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags and comment
        Order expectedOrder = new OrderBuilder(AMY).withTags().build();
        assertParseSuccess(parser, TID_DESC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + DELIVERY_TIMESTAMP_DESC_AMY + WAREHOUSE_DESC_AMY + COD_DESC_AMY , new AddCommand(expectedOrder));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing TID prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing name prefix
        assertParseFailure(parser, TID_DESC_BOB + VALID_NAME_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + VALID_PHONE_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, expectedMessage);
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing timeStamp prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + VALID_ADDRESS_BOB
                + VALID_TIMESTAMP_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // missing warehouse prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB +
                        DELIVERY_TIMESTAMP_DESC_BOB + COD_DESC_BOB, expectedMessage);

        // missing cashOnDelivery prefix
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB +
                DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_TID_BOB + VALID_NAME_BOB + VALID_PHONE_BOB + VALID_ADDRESS_BOB
                + VALID_TIMESTAMP_BOB + VALID_WAREHOUSE_BOB + VALID_COD_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid TID
        assertParseFailure(parser, INVALID_TID_DESC + TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB
                        + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB
                        + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid name
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                        + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                        + COMMENT_DESC_NIL + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + INVALID_PHONE_DESC + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_ADDRESS_DESC
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Address.MESSAGE_CONSTRAINTS);

        // invalid timeStamp
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_DELIVERY_TIMESTAMP_DATE + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_DELIVERY_TIMESTAMP_DATE_ONLY + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL
                + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_DELIVERY_TIMESTAMP_TIME + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, TimeStamp.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_DELIVERY_TIMESTAMP_TIME_ONLY + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, TimeStamp.MESSAGE_CONSTRAINTS);

        // invalid warehouse
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + INVALID_WAREHOUSE_DESC + COD_DESC_BOB + TAG_DESC_FRIEND,
                Warehouse.MESSAGE_CONSTRAINTS);

        // invalid cashOnDelivery
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + INVALID_COD_DESC + TAG_DESC_FRIEND,
                Warehouse.MESSAGE_CONSTRAINTS);

        // invalid comment
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_COMMENT_DESC + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB
                        + COD_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                Comment.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, TID_DESC_BOB + NAME_DESC_BOB + PHONE_DESC_BOB + ADDRESS_DESC_BOB
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB + COMMENT_DESC_NIL + INVALID_TAG_DESC
                + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, TID_DESC_BOB + INVALID_NAME_DESC + PHONE_DESC_BOB + INVALID_ADDRESS_DESC
                + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + TID_DESC_BOB +  NAME_DESC_BOB + PHONE_DESC_BOB
                + ADDRESS_DESC_BOB + DELIVERY_TIMESTAMP_DESC_BOB + WAREHOUSE_DESC_BOB + COD_DESC_BOB
                + COMMENT_DESC_NIL + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
