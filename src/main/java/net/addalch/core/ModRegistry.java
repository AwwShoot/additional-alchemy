package net.addalch.core;

import net.addalch.bottles.SipnPotion;
import net.addalch.bottles.SplashnPotion;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModRegistry {
    // convenience.
    public static Identifier getID(String path){
        return new Identifier("addalch", path);
    }

    // instantiation
    public static final Item SIPN_BOTTLE = new SipnPotion( new Item.Settings().group(ItemGroup.BREWING));
    public static final Item SPLASHN_BOTTLE = new SplashnPotion(new Item.Settings().group(ItemGroup.BREWING));
    // where I register the objects.
    public static void register(){
        Registry.register(Registry.ITEM, getID("sipn_potion"), SIPN_BOTTLE);
        Registry.register(Registry.ITEM, getID("splashn_potion"), SPLASHN_BOTTLE);
    }
}
