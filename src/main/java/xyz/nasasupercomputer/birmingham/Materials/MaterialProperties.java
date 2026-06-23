package xyz.nasasupercomputer.birmingham.Materials;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

// key note: a good half of this is vibecoded because it is a lot more complex than i expected. tried to do my best to understnad it though and i tihnk i do, I put comments wherever i could

public record MaterialProperties( // a record is a weird ass type of class, does stuff
      String name,
      boolean hasNugget,
      boolean hasBlock,
      MapColor blockColor, // for i think when it displays on maps
      float blockHardness,
      float blockResistance,
      double radioactivity,
      IrradiationProperties irradiationProperties,// this is badly named but its essentially should it deal radiation to entities nearby
      Function<Item.Properties, Item> ingotFactory,   // default: Item::new
      Function<Item.Properties, Item> nuggetFactory,
      Function<BlockBehaviour.Properties, Block> blockFactory



) {

    public MaterialProperties { // makes it so if you dont specify a specific class then it keeps it as the regular block or item
        if (ingotFactory == null)  ingotFactory  = Item::new;
        if (nuggetFactory == null) nuggetFactory = Item::new;
        if (blockFactory == null)  blockFactory  = Block::new;
    }

    public MaterialProperties changeToRadioactive(){
        // there is 100000% a better way of doing this but idc
        return new MaterialProperties(this.name, this.hasNugget, this.hasBlock, this.blockColor, this.blockHardness, this.blockResistance, this.radioactivity, this.irradiationProperties, this.ingotFactory, this.nuggetFactory, props -> new RadioactiveBlock(props, this.irradiationProperties.irradiationRange(), this.irradiationProperties.irradiationPower()));

    }


    public static Builder builder(String name) {
        return new Builder(name);
    }

    public static final class Builder {
        private final String name; // setting up defaults incase you dont put any
        private boolean hasNugget = true;
        private boolean hasBlock = true;
        private MapColor blockColor = MapColor.METAL;
        private float blockHardness = 5.0f;
        private float blockResistance = 6.0f; // supposedly the iron values which is probably good for defaults
        private double radioactivity;
        private IrradiationProperties irradiationProperties = new IrradiationProperties(false, 0, 0);
        private Function<Item.Properties, Item> ingotFactory  = Item::new; // what this stuff does, not entirely sure. i THINK it initializes the class it should use but is overridable so we can use a custom class if needed
        private Function<Item.Properties, Item> nuggetFactory = Item::new;
        private Function<BlockBehaviour.Properties, Block> blockFactory = Block::new;


        public Builder(String name) { // the fuckass builder shit idk how to explain
            this.name = name;
        }

        public Builder hasNugget(boolean hasNugget) {
            this.hasNugget = hasNugget;
            return this;
        }
        public Builder hasBlock(boolean hasBlock) {
            this.hasBlock = hasBlock;
            return this;
        }

        public Builder blockColor(MapColor blockColor) {
            this.blockColor = blockColor;
            return this;
        }

        public Builder hardness(float blockHardness) {
            this.blockHardness = blockHardness;
            return this;
        }

        public Builder resistance(float blockResistance) {
            this.blockResistance = blockResistance;
            return this;
        }
        public Builder radioactivity(double radioactivity) {
            this.radioactivity = radioactivity;
            return this;
        }

        public Builder irradiationProperties(IrradiationProperties irradiationProperties) {
            this.irradiationProperties = irradiationProperties;
            return this;
        }


        public Builder ingotFactory(Function<Item.Properties, Item> f)  { this.ingotFactory = f; return this; } // i copy pasted these three becayuse i dont really knoiw wtf they are
        public Builder nuggetFactory(Function<Item.Properties, Item> f) { this.nuggetFactory = f; return this; }
        public Builder blockFactory(Function<BlockBehaviour.Properties, Block> f) { this.blockFactory = f; return this; }

        public MaterialProperties build() {
            return new MaterialProperties(name, hasNugget, hasBlock, blockColor, blockHardness, blockResistance, radioactivity, irradiationProperties, ingotFactory, nuggetFactory, blockFactory);
        }


    }


}
