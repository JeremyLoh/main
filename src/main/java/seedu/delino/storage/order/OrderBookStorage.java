package seedu.delino.storage.order;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.delino.commons.exceptions.DataConversionException;
import seedu.delino.model.OrderBook;
import seedu.delino.model.ReadOnlyOrderBook;

/**
 * Represents a storage for {@link OrderBook}.
 */
public interface OrderBookStorage {

    // ================ Order Book methods ==============================
    /**
     * Returns the file path of the data file.
     */
    Path getOrderBookFilePath();

    /**
     * Returns OrderBook data as a {@link ReadOnlyOrderBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyOrderBook> readOrderBook() throws DataConversionException, IOException;

    /**
     * @see #getOrderBookFilePath()
     */
    Optional<ReadOnlyOrderBook> readOrderBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyOrderBook} to the storage.
     * @param orderBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveOrderBook(ReadOnlyOrderBook orderBook) throws IOException;

    /**
     * @see #saveOrderBook(ReadOnlyOrderBook)
     */
    void saveOrderBook(ReadOnlyOrderBook orderBook, Path filePath) throws IOException;
}
