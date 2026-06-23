package xyz.nasasupercomputer.birmingham.Materials;

import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardRadioactive;
import xyz.nasasupercomputer.birmingham.MainRegistry;

import java.util.function.Function;

public class MaterialRegistry {

    public static final RegisteredMaterial RADIUM = MaterialsHelper.register(
//            MaterialProperties.builder("radium").build()); // most minimal example, does just all the defaults


            // RECIPES FROM BLOCK TO INGOT TO NUGGET ARE MANUAL !!!!!!!!!!!!!!!!!! THIS MERELY ADDS THEM AS BLOCKS AND ITEMS
            MaterialProperties.builder("radium")
                    .hasBlock(true) // defaults to true, adding for example purposes
                    .hasNugget(true) // defaults to true, adding for example purposes
                    .radioactivity(20) // radiopactiviy (per sec, nuggets have 1/9th this, blocks have 9x this)
                    .irradiationProperties(new IrradiationProperties(true, 30, 100)) // should irradiate is should it deal rads to entities nearby, range is range, power is how many rads (at most, falls off with distance)
                    .ingotFactory(Radium::new) // custom class if you need custom functionality, change ingotfactory to nuggetfactory or blockfactory if needed
                    //NOTICE: IF YOU HAVE A .blockFactory, YOU ******CANNOT****** USE IRRADIATION FROM HERE. IT WILL OVERRIDE YOUR CUSTOM BLOCK CODE. INSTEAD JUST LOOK AT RadioactiveBlock AND COPY IT OR EXTEND IT
                    .build());


    public static void init() {}   // touch the class so the static fields load
    // ^^ that top line of code is from claude and i have no idea why. i trust it with my life tho imma keep it

}
