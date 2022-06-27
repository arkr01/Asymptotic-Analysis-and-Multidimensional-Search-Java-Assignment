/**
 * A 2D cartesian plane implemented as with an array. Each (x,y) coordinate can
 * hold a single item of type <T>.
 *
 * @param <T> The type of element held in the data structure
 */
public class ArrayCartesianPlane<T> implements CartesianPlane<T> {
    /** Cartesian plane representation */
    private T[][] grid;

    /** The minimum x co-ordinate in which an item can be stored at */
    private int minimumX;

    /** The minimum y co-ordinate in which an item can be stored at */
    private int minimumY;

    /** The maximum x co-ordinate in which an item can be stored at */
    private int maximumX;

    /** The maximum y co-ordinate in which an item can be stored at */
    private int maximumY;

    /**
     * Constructs a new ArrayCartesianPlane object with given minimum and
     * maximum bounds.
     *
     * Note that these bounds are allowed to be negative.
     *
     * @param minimumX A new minimum bound for the x values of
     *         elements.
     * @param maximumX A new maximum bound for the x values of
     *         elements.
     * @param minimumY A new minimum bound for the y values of
     *         elements.
     * @param maximumY A new maximum bound for the y values of
     *         elements.
     * @throws IllegalArgumentException if the x minimum is greater
     *         than the x maximum (and resp. with y min/max)
     */
    public ArrayCartesianPlane(int minimumX, int maximumX, int minimumY,
            int maximumY) throws IllegalArgumentException {
        if (minimumX > maximumX || minimumY > maximumY) {
            throw new IllegalArgumentException("Invalid bounds given.");
        }
        this.minimumX = minimumX;
        this.minimumY = minimumY;
        this.maximumX = maximumX;
        this.maximumY = maximumY;

        this.grid = (T[][]) new
                Object[maximumX - minimumX + 1][maximumY - minimumY + 1];
    }

    /**
     * Add an element at a fixed position, overriding any existing element
     * there.
     *
     * @param x The x-coordinate of the element's position
     * @param y The y-coordinate of the element's position
     * @param element The element to be added at the indicated
     *         position
     * @throws IllegalArgumentException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public void add(int x, int y, T element) throws IllegalArgumentException {
        if (this.isWithinBounds(x, y, this.minimumX, this.minimumY,
                this.maximumX, this.maximumY)) {
            this.grid[x - this.minimumX][y - this.minimumY] = element;
        } else {
            throw new IllegalArgumentException("Given co-ordinates not within" +
                    " grid bounds");
        }
    }

    /**
     * Checks whether given co-ordinates are within the given bounds.
     *
     * @param x The x-coordinate to check
     * @param y The y-coordinate to check
     * @param minX The minimum x bound to check
     * @param minY The minimum y bound to check
     * @param maxX The maximum x bound to check
     * @param maxY The maximum y bound to check
     * @return whether given co-ordinates are within the given bounds
     */
    private boolean isWithinBounds(int x, int y, int minX, int minY, int maxX,
            int maxY) {
        return x >= minX && x <= maxX && y >= minY && y <= maxY;
    }

    /**
     * Returns the element at the indicated position.
     *
     * @param x The x-coordinate of the element to retrieve
     * @param y The y-coordinate of the element to retrieve
     * @return The element at this position, or null is no elements exist
     * @throws IndexOutOfBoundsException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public T get(int x, int y) throws IndexOutOfBoundsException {
        if (!this.isWithinBounds(x, y, this.minimumX, this.minimumY,
                this.maximumX, this.maximumY)) {
            throw new IndexOutOfBoundsException("Invalid co-ordinates given.");
        }
        return this.grid[x - this.minimumX][y - this.minimumY];
    }

    /**
     * Removes the element at the indicated position.
     *
     * @param x The x-coordinate of the element to remove
     * @param y The y-coordinate of the element to remove
     * @return true if an element was successfully removed, false if no element
     *         exists at (x, y)
     * @throws IndexOutOfBoundsException If the x or y value is out of
     *         the grid's minimum/maximum bounds
     */
    public boolean remove(int x, int y) throws IndexOutOfBoundsException {
        if (this.isWithinBounds(x, y, this.minimumX, this.minimumY,
                this.maximumX, this.maximumY)) {
            if (this.grid[x - this.minimumX][y - this.minimumY] == null) {
                return false;
            }
            this.grid[x - this.minimumX][y - this.minimumY] = null;
            return true;
        }
        throw new IndexOutOfBoundsException("Invalid co-ordinates given.");
    }

    /**
     * Removes all elements stored in the grid.
     */
    public void clear() {
        this.grid = (T[][]) new
                Object[maximumX - minimumX + 1][maximumY - minimumY + 1];
    }

    /**
     * Changes the size of the grid. Existing elements should remain at the
     * same (x, y) coordinate. If a resizing operation has invalid dimensions or
     * causes an element to be lost, the grid should remain unmodified and an
     * IllegalArgumentException thrown
     *
     * @param newMinimumX A new minimum bound for the x values of
     *         elements.
     * @param newMaximumX A new maximum bound for the x values of
     *         elements.
     * @param newMinimumY A new minimum bound for the y values of
     *         elements.
     * @param newMaximumY A new maximum bound for the y values of
     *         elements.
     * @throws IllegalArgumentException if the x minimum is greater
     *         than the x maximum (and resp. with y min/max) or if an element
     *         would be lost after this resizing operation
     */
    public void resize(int newMinimumX, int newMaximumX, int newMinimumY,
            int newMaximumY) throws IllegalArgumentException {
        if (newMaximumX < newMinimumX || newMaximumY < newMinimumY) {
            throw new IllegalArgumentException("Invalid bounds given.");
        }
        T[][] newGrid = (T[][]) new
                Object[newMaximumX - newMinimumX + 1][newMaximumY - newMinimumY
                + 1];

        for (int x = this.minimumX; x <= this.maximumX; x++) {
            for (int y = this.minimumY; y <= this.maximumY; y++) {
                T current = this.get(x, y);
                if (current != null) {
                    if (!this.isWithinBounds(x, y, newMinimumX, newMinimumY,
                            newMaximumX, newMaximumY)) {
                        throw new IllegalArgumentException("Given bounds " +
                                "result in element(s) being lost.");
                    }
                    newGrid[x - newMinimumX][y - newMinimumY] = current;
                }
            }
        }
        this.grid = newGrid;
        this.minimumX = newMinimumX;
        this.minimumY = newMinimumY;
        this.maximumX = newMaximumX;
        this.maximumY = newMaximumY;
    }
}

