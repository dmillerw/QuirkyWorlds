package dmillerw.quirkyworlds.data.enums;

import dmillerw.quirkyworlds.data.generic.GenericTerrainProvider;
import dmillerw.quirkyworlds.data.terrain.VanillaTerrainGenerator;
import dmillerw.quirkyworlds.data.terrain.VoidTerrainProvider;

public enum Terrain {

    VANILLA,
    VOID;

    public GenericTerrainProvider get() {
        switch (this) {
            case VOID:
                return new VoidTerrainProvider();

            case VANILLA:
            default:
                return new VanillaTerrainGenerator();
        }
    }
}