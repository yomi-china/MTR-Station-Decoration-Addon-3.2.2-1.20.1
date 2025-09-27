package top.mcmtr.blocks;


import net.minecraft.world.level.material.MapColor;

public class BlockFloor extends BlockChangeModelBase{
    public BlockFloor() {
        super(1, Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(2.0F));
    }
}