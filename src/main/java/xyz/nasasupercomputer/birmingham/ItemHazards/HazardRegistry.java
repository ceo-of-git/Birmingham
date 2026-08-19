package xyz.nasasupercomputer.birmingham.ItemHazards;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.Item;
import xyz.nasasupercomputer.birmingham.Fluids.FluidRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardRadioactive;
import xyz.nasasupercomputer.birmingham.MainRegistry;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardToxic;
import xyz.nasasupercomputer.birmingham.ItemHazards.Types.HazardMolten;
import xyz.nasasupercomputer.birmingham.Items.ItemRegistry;

// Adds Hazards to items.
public class HazardRegistry {
	
	public static final List<IHazardType> HazardVariantsList = new ArrayList<IHazardType>();
	
	// Register each hazard (And their 'intensity')
	// Reminder to add each of these to the "HazardVarientsList" in the RegisterHazardVarients method, thx.
	public static final HazardToxic Hazard_Toxic_T1 = new HazardToxic(1.0, "hazard.birmingham.toxic.title", "hazard.birmingham.toxic.description");
	public static final HazardToxic Hazard_Toxic_T2 = new HazardToxic(5.0, "hazard.birmingham.toxic.moderate.title", "hazard.birmingham.toxic.moderate.description");
	public static final HazardToxic Hazard_Toxic_T3 = new HazardToxic(15.0, "hazard.birmingham.toxic.strong.title", "hazard.birmingham.toxic.strong.description");
	public static final HazardMolten Hazard_Molten_T1 = new HazardMolten(2.0, "hazard.birmingham.molten.title", "hazard.birmingham.molten.description");
	public static final HazardMolten Hazard_Molten_T2 = new HazardMolten(8.0, "hazard.birmingham.molten.strong.title", "hazard.birmingham.molten.description");





	// =========================
	// HAZARD VARIANTS
	public static void RegisterHazardVarients() {
		HazardVariantsList.add(Hazard_Toxic_T1);
		HazardVariantsList.add(Hazard_Toxic_T2);
		HazardVariantsList.add(Hazard_Toxic_T3);
		HazardVariantsList.add(Hazard_Molten_T1);
		HazardVariantsList.add(Hazard_Molten_T2);
	}
	
	// =========================
	// ITEM HAZARDS
	// Note: - MaterialSets register Molten to all slag blocks
	// along with radiation
	public static void RegisterAllHazards() {
		RegisterHazardVarients();
		
		HazardSystem.RegisterHazard(ItemRegistry.TEST_ITEM.get(), Hazard_Toxic_T1);
		HazardSystem.RegisterHazard(ItemRegistry.TEST_ITEM.get(), Hazard_Toxic_T2);
		HazardSystem.RegisterHazard(ItemRegistry.TEST_ITEM.get(), Hazard_Toxic_T3);
		HazardSystem.RegisterHazard(ItemRegistry.TEST_ITEM.get(), Hazard_Molten_T1);
		HazardSystem.RegisterHazard(ItemRegistry.TEST_ITEM.get(), Hazard_Molten_T2);
		HazardSystem.RegisterHazard(ItemRegistry.TEST_ITEM.get(), new HazardRadioactive(10000.05));
		HazardSystem.RegisterHazard(FluidRegistry.CONTAMINATED_WATER.bucket.get(), new HazardRadioactive(1));

		HazardSystem.RegisterHazard(ItemRegistry.FLAME_GEM.get(), Hazard_Molten_T1);
		HazardSystem.RegisterHazard(ItemRegistry.RADIOACTIVE_GEM.get(), new HazardRadioactive(90.00)); // TODO: MAKE THIS IRRADIATE THE ITEM ITS APPLIED TO

	}

}
