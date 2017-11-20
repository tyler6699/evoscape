package uk.co.carelesslabs;

/**
 * Class to hold all of the Enumerators for the game
 */
public class Enums {
    
    /**
     * The types of tiles that are available
     */
    public enum TileType {
        GRASS,
        WATER,
        CLIFF
    }
    
    /**
     * The types of entities that are available
     */
    public enum EntityType {
        HERO, 
        TREE, 
        BIRD
    }
    
    /**
     * The states that the entities can be in
     */
    public enum EnityState {
        NONE,
        IDLE,
        FEEDING,
        WALKING,
        FLYING,
        HOVERING, 
        LANDING
    }
    
    /**
     * The states that a menu or menu button can be
     */
    public enum MenuState {
        ACTIVE,
        DISABLED,
        HOVEROVER,
        CLICKED
    }

}
