package dmillerw.quirkyworlds.data.enums;

import dmillerw.quirkyworlds.data.world.feature.LakeFeature;
import dmillerw.quirkyworlds.data.world.generic.GenericFeature;

/**
 * @author dmillerw
 */
public enum Feature {

    LAKES;

    public GenericFeature get() {
        switch (this) {
            case LAKES:
            default:
                return new LakeFeature();
        }
    }
}
