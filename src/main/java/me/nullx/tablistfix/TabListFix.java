package me.nullx.tablistfix;

import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.SettingsElement;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.lang.reflect.Field;
import java.util.List;

public class TabListFix extends LabyModAddon {

    private Field registeredName = ScorePlayerTeam.class.getDeclaredField("b");

    public TabListFix() throws NoSuchFieldException {
        registeredName.setAccessible(true);
    }

    @Override
    public void onEnable() {
        getApi().registerForgeListener(this);
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent event) {
        Minecraft.getMinecraft().thePlayer.sendQueue.getPlayerInfoMap().forEach(networkPlayerInfo -> {
            ScorePlayerTeam team = networkPlayerInfo.getPlayerTeam();
            if (team != null && team.getRegisteredName().length() == 16) {
                try {
                    registeredName.set(team, team.getRegisteredName().substring(0, 5));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void loadConfig() {

    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {

    }
}
