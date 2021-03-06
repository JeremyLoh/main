package seedu.delino.model;

import static java.util.Objects.requireNonNull;
import static seedu.delino.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.delino.commons.core.GuiSettings;
import seedu.delino.commons.core.LogsCenter;
import seedu.delino.model.parcel.Parcel;
import seedu.delino.model.parcel.order.Order;
import seedu.delino.model.parcel.returnorder.ReturnOrder;

/**
 * Represents the in-memory model of the order book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final OrderBook orderBook;
    private final ReturnOrderBook returnOrderBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Order> filteredOrders;
    private final FilteredList<ReturnOrder> filteredReturnOrders;
    private List<String> startUpMessages;

    /**
     * Initializes a ModelManager with the given orderBook and userPrefs.
     */
    public ModelManager(ReadOnlyOrderBook orderBook, ReadOnlyReturnOrderBook returnOrderBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(orderBook, returnOrderBook, userPrefs);

        logger.fine("Initializing with order book: " + orderBook + " and user prefs " + userPrefs);
        logger.fine("Initializing with return book: " + returnOrderBook + " and user prefs " + userPrefs);
        this.orderBook = new OrderBook(orderBook);
        this.returnOrderBook = new ReturnOrderBook(returnOrderBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredOrders = new FilteredList<>(this.orderBook.getOrderList());
        filteredReturnOrders = new FilteredList<>(this.returnOrderBook.getReturnOrderList());
    }

    /**
     * Initializes a ModelManager with the given orderBook, returnOrderBook and userPrefs.
     */
    public ModelManager(ReadOnlyOrderBook orderBook, ReadOnlyReturnOrderBook returnOrderBook,
                        ReadOnlyUserPrefs userPrefs, List<String> startUpMessages) {
        this(orderBook, returnOrderBook, userPrefs);
        requireAllNonNull(startUpMessages);
        this.startUpMessages = startUpMessages;
    }

    public ModelManager() {
        this(new OrderBook(), new ReturnOrderBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getOrderBookFilePath() {
        return userPrefs.getOrderBookFilePath();
    }

    @Override
    public Path getReturnOrderBookFilePath() {
        return userPrefs.getReturnOrderBookFilePath();
    }

    @Override
    public void setOrderBookFilePath(Path orderBookFilePath) {
        requireNonNull(orderBookFilePath);
        userPrefs.setOrderBookFilePath(orderBookFilePath);
    }

    @Override
    public void setReturnOrderBookFilePath(Path returnOrderBookFilePath) {
        requireNonNull(returnOrderBookFilePath);
        userPrefs.setReturnOrderBookFilePath(returnOrderBookFilePath);
    }

    //=========== OrderBook ================================================================================

    @Override
    public void setOrderBook(ReadOnlyOrderBook orderBook) {
        this.orderBook.resetData(orderBook);
    }

    @Override
    public ReadOnlyOrderBook getOrderBook() {
        return orderBook;
    }

    @Override
    public void deleteOrder(Order target) {
        orderBook.removeOrder(target);
    }

    @Override
    public void addOrder(Order order) {
        orderBook.addOrder(order);
        updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDERS);
    }

    @Override
    public void setOrder(Order target, Order editedOrder) {
        requireAllNonNull(target, editedOrder);
        orderBook.setOrder(target, editedOrder);
    }

    //=========== ReturnOrderBook ================================================================================

    @Override
    public void setReturnOrderBook(ReadOnlyReturnOrderBook returnOrderBook) {
        this.returnOrderBook.resetData(returnOrderBook);
    }

    @Override
    public ReadOnlyReturnOrderBook getReturnOrderBook() {
        return returnOrderBook;
    }

    @Override
    public void deleteReturnOrder(ReturnOrder target) {
        returnOrderBook.removeReturnOrder(target);
    }

    @Override
    public void addReturnOrder(ReturnOrder orderToBeReturned) {
        returnOrderBook.addReturnOrder(orderToBeReturned);
        updateFilteredReturnOrderList(PREDICATE_SHOW_ALL_RETURNS);
    }

    @Override
    public void setReturnOrder(ReturnOrder target, ReturnOrder editedReturnOrder) {
        requireAllNonNull(target, editedReturnOrder);
        returnOrderBook.setReturnOrder(target, editedReturnOrder);
    }

    //=========== Parcel state check =============================================================

    @Override
    public boolean hasParcel(Parcel parcel) {
        requireNonNull(parcel);
        return orderBook.hasOrder(parcel) || returnOrderBook.hasReturnOrder(parcel);
    }

    //=========== Filtered Order List Accessors =============================================================
    @Override
    public void deliverOrder(Order target) {
        orderBook.deliverOrder(target);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Order} backed by the internal list of
     * {@code versionedOrderBook}
     */
    @Override
    public ObservableList<Order> getFilteredOrderList() {
        return filteredOrders;
    }

    @Override
    public void updateFilteredOrderList(Predicate<Order> predicate) {
        requireNonNull(predicate);
        filteredOrders.setPredicate(predicate);
    }

    //=========== Filtered Return Order List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReturnOrder} backed by the internal list of
     * {@code versionedReturnOrderBook}
     */
    @Override
    public ObservableList<ReturnOrder> getFilteredReturnOrderList() {
        return filteredReturnOrders;
    }

    @Override
    public void updateFilteredReturnOrderList(Predicate<ReturnOrder> predicate) {
        requireNonNull(predicate);
        filteredReturnOrders.setPredicate(predicate);
    }

    //=============== StartUp Messages ==============================================================================
    @Override
    public List<String> getStartUpMessages() {
        return startUpMessages;
    }

    @Override
    public void deliverReturnOrder(ReturnOrder target) {
        returnOrderBook.deliverReturnOrder(target);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return orderBook.equals(other.orderBook)
                && returnOrderBook.equals(other.returnOrderBook)
                && userPrefs.equals(other.userPrefs)
                && filteredOrders.equals(other.filteredOrders)
                && filteredReturnOrders.equals(other.filteredReturnOrders);
    }

}
