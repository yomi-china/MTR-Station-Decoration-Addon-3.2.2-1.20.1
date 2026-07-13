package top.mcmtr;

import mtr.CreativeModeTabs;
import mtr.RegistryObject;
import mtr.item.ItemWithCreativeTabBase;
import mtr.mappings.BlockEntityMapper;
import mtr.mappings.RegistryUtilities;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import top.mcmtr.mappings.FabricRegistryUtilities;

public class MSDMainFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MSDMain.init(MSDMainFabric::registerItem, MSDMainFabric::registerBlock, MSDMainFabric::registerBlock, MSDMainFabric::registerBlockEntityType, MSDMainFabric::registerEntityType, MSDMainFabric::registerSoundEvent);
    }

    private static void RegisterCreativeModeTab(CreativeModeTab tab, Item item) {
        if (tab == null) return;
        if (BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(tab).isPresent()) {
            FabricRegistryUtilities.registerCreativeModeTab(tab, item);
        } else {
            System.out.println("[MSD] Skipped creative tab registration for item " + item + ": CreativeModeTab not yet registered in BuiltInRegistries.");
        }
    }

    private static void registerItem(String path, RegistryObject<Item> item) {
        final Item itemObject = item.get();
        Registry.register(RegistryUtilities.registryGetItem(), new ResourceLocation(MSDMain.MOD_ID, path), itemObject);
        if (itemObject instanceof ItemWithCreativeTabBase) {
            RegisterCreativeModeTab(((ItemWithCreativeTabBase) itemObject).creativeModeTab.get(), itemObject);
        } else if (itemObject instanceof ItemWithCreativeTabBase.ItemPlaceOnWater) {
            RegisterCreativeModeTab(((ItemWithCreativeTabBase.ItemPlaceOnWater) itemObject).creativeModeTab.get(), itemObject);
        }
    }

    private static void registerBlock(String path, RegistryObject<Block> block) {
        Registry.register(RegistryUtilities.registryGetBlock(), new ResourceLocation(MSDMain.MOD_ID, path), block.get());
    }

    private static void registerBlock(String path, RegistryObject<Block> block, CreativeModeTabs.Wrapper creativeModeTab) {
        registerBlock(path, block);
        final BlockItem blockItem = new BlockItem(block.get(), RegistryUtilities.createItemProperties(creativeModeTab::get));
        Registry.register(RegistryUtilities.registryGetItem(), new ResourceLocation(MSDMain.MOD_ID, path), blockItem);
        RegisterCreativeModeTab(creativeModeTab.get(), blockItem);
    }

    private static void registerBlockEntityType(String path, RegistryObject<? extends BlockEntityType<? extends BlockEntityMapper>> blockEntityType) {
        Registry.register(RegistryUtilities.registryGetBlockEntityType(), new ResourceLocation(MSDMain.MOD_ID, path), blockEntityType.get());
    }

    private static void registerSoundEvent(String path, SoundEvent soundEvent) {
        Registry.register(RegistryUtilities.registryGetSoundEvent(), new ResourceLocation(MSDMain.MOD_ID, path), soundEvent);
    }

    private static void registerEntityType(String path, RegistryObject<? extends EntityType<? extends Entity>> entityType) {
        Registry.register(RegistryUtilities.registryGetEntityType(), new ResourceLocation(MSDMain.MOD_ID, path), entityType.get());
    }
}