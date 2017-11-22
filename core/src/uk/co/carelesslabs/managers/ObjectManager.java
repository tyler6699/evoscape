package uk.co.carelesslabs.managers;

import java.util.ArrayList;
import java.util.TreeMap;
import uk.co.carelesslabs.entity.Entity;
import uk.co.carelesslabs.map.Chunk;

public class ObjectManager {
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public TreeMap<Integer, Chunk> chunks = new TreeMap<Integer, Chunk>();
    transient public Chunk currentChunk;
}
