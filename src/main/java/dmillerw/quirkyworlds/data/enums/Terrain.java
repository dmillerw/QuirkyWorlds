package dmillerw.quirkyworlds.data.enums;

import dmillerw.quirkyworlds.data.world.generic.GenericTerrainProvider;
import dmillerw.quirkyworlds.data.world.terrain.FlatTerrainProvider;
import dmillerw.quirkyworlds.data.world.terrain.LakeTerrainProvider;
import dmillerw.quirkyworlds.data.world.terrain.VanillaTerrainGenerator;
import dmillerw.quirkyworlds.data.world.terrain.VoidTerrainProvider;

public enum Terrain {

    VANILLA,
    LAKE,
    FLAT,
    VOID;

    public GenericTerrainProvider get() {
        switch (this) {
            case VOID:
                return new VoidTerrainProvider();

            case LAKE:
                return new LakeTerrainProvider();

            case FLAT:
                return new FlatTerrainProvider();

            case VANILLA:
            default:
                return new VanillaTerrainGenerator();
        }
    }
}