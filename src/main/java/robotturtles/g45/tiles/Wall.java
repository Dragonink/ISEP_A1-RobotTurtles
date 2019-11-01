package robotturtles.g45.tiles;

/** Enum of the walls that can be encountered in game.
 * 
 * @author Tanguy Berthoud
 */
public enum Wall {
    /** Indestructible and non moveable wall. */
    STONE(false, false),
    /** Destructible and non moveable wall. */
    ICE(true, false),
    /** Indestructible and moveable wall.
     * 
     * @deprecated Unused here.
     */
    CRATE(false, true);


    /** <code>true</code> if the wall is destructible; <code>false</code> otherwise. */
    private final boolean destructible;
    /** Returns <code>true</code> if the wall is destructible; <code>false</code> otherwise.
     * 
     * @return {@link #destructible Wall#destructible}
     */
    public final boolean isDestructible() {
        return destructible;
    }

    /** <code>true</code> if the wall is moveable; <code>false</code> otherwise. */
    private final boolean moveable;
    /** Returns <code>true</code> if the wall is moveable; <code>false</code> otherwise.
     * 
     * @return {@link #moveable Wall#moveable}
     */
    public final boolean isMoveable() {
        return moveable;
    }


    /** Constructs a new <code>Wall</code>.
     * 
     * @param destructible <code>true</code> if the wall is destructible; <code>false</code> otherwise.
     * @param moveable <code>true</code> if the wall is moveable; <code>false</code> otherwise.
     */
    private Wall(boolean destructible, boolean moveable) {
        this.destructible = destructible;
        this.moveable = moveable;
    }
}
