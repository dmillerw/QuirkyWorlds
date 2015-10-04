package dmillerw.quirkyworlds.data.enums;

import dmillerw.quirkyworlds.data.world.generic.GenericTerrainProvider;
import dmillerw.quirkyworlds.data.world.terrain.VanillaTerrainGenerator;
import dmillerw.quirkyworlds.data.world.terrain.VoidTerrainProvider;

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