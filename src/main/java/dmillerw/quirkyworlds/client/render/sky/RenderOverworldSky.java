package dmillerw.quirkyworlds.client.render.sky;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraftforge.client.IRenderHandler;

/**
 * @author dmillerw
 */
public class RenderOverworldSky implements IRenderHandler {

    private boolean renderSun;
    private boolean renderMoon;
    private boolean renderStars;

    public RenderOverworldSky(boolean renderSun, boolean renderMoon, boolean renderStars) {
        this.renderSun = renderSun;
        this.renderMoon = renderMoon;
        this.renderStars = renderStars;
    }

    @Override
    public void render(float partialTicks, WorldClient world, Minecraft mc) {

    }
}
