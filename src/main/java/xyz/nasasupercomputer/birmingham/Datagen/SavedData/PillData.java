package xyz.nasasupercomputer.birmingham.Datagen.SavedData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import xyz.nasasupercomputer.birmingham.MainRegistry;

public class PillData extends SavedData {
	
	// First Int = Pill Texture File
	// Second Int = Pill Effect #
	public HashMap<String, Integer> data = new HashMap();
	public static int EXISTING_PILL_EFFECTS = 14; // <-- How many pill effects are programmed (Look in Items/custom/pillitem.java)
	// private static final String DATA_TYPE = "pill_data";
	
	private static List<Integer> effectsOrder = new ArrayList<Integer>();
	private static List<Integer> textureOrder = new ArrayList<Integer>();

	public static PillData createData(ServerLevel serverLevel) {
		PillData toReturn = new PillData();
		
		long seed = serverLevel.getSeed();
		Random random = new Random(seed);
		
		// Add all 'effects' to a list
        List<Integer> effects = new ArrayList<>();

        for (int i = 0; i < EXISTING_PILL_EFFECTS; i++) {
            effects.add(i);
        }

        Collections.shuffle(effects, random);
        effectsOrder = effects;
        textureOrder = new ArrayList<>(effectsOrder);
        Collections.shuffle(textureOrder, random);
        
        // Link it to pills
        for (int i = 0; i < EXISTING_PILL_EFFECTS; i++) {
        	toReturn.data.put("pill_" + i, effects.get(i));
        }

        toReturn.setDirty();
        return toReturn;
	}
	
    public static PillData load(CompoundTag tag) {
        PillData toReturn = new PillData();

        for (int i = 0; i < EXISTING_PILL_EFFECTS; i++) {
        	toReturn.data.put("pill_" + i, tag.getInt("pill_" + i));
        }

        return toReturn;
    }
	

    @Override
    public CompoundTag save(CompoundTag tag) {

        for (String key : data.keySet()) {
            tag.putInt(key, data.get(key));
        }

        return tag;
    }
    
    public static int getTextureInt(int effectInt) {
    	// Cool crash happens where the GUI is actually built before worlds oddly
    	// anwyaysa gotta check for that.
    	// Fo' Shizzle.
    	if (effectsOrder.isEmpty()) { return effectInt; }
    	
    	if (effectsOrder.get(effectInt) != null) {
    		int toReturn = effectsOrder.indexOf(effectInt);
    		return textureOrder.get(toReturn);
    	}
    	else {
    		return 0;
    	}
    }


}
