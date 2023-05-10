package com.AIE.WindowPackage.ColorPackage;

import java.io.*;
import java.util.ArrayList;

public class PaletteChipSerialization {

    private static final String PALETTE_FILE = "AIE.palette";
    public static void save(ArrayList<Palette.PaletteChip> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PALETTE_FILE))) {
            oos.writeObject(list);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Palette.PaletteChip> load(ArrayList<Palette.PaletteChip> defaultList) {
        ArrayList<Palette.PaletteChip> list;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PALETTE_FILE))) {
            list = (ArrayList<Palette.PaletteChip>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            save(defaultList);
            list = load(defaultList);
        }
        return list;
    }



}
