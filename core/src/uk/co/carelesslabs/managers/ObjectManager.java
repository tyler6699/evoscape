package uk.co.carelesslabs.managers;

import java.util.ArrayList;
import java.util.TreeMap;
import uk.co.carelesslabs.entity.Entity;
import uk.co.carelesslabs.map.Chunk;

public class ObjectManager {
    public ArrayList<Entity> entities = new ArrayList<Entity>();
    public TreeMap<Integer, ArrayList<Chunk>> chunks = new TreeMap<Integer, ArrayList<Chunk>>();
    transient public Chunk currentChunk;
}
