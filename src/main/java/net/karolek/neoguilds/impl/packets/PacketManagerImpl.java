package net.karolek.neoguilds.impl.packets;

import com.mojang.authlib.GameProfile;
import net.karolek.neoguilds.api.Profile;
import net.karolek.neoguilds.api.packets.PacketManager;
import net.karolek.neoguilds.api.packets.PlayerInfoAction;
import net.karolek.neoguilds.utils.Reflection;
import net.karolek.neoguilds.utils.Util;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class PacketManagerImpl implements PacketManager {

    private static Reflection.MethodInvoker handleMethod = Reflection.getMethod(Reflection.getCraftBukkitClass("entity.CraftEntity"), "getHandle");
    private static Reflection.MethodInvoker sendPacket = Reflection.getMethod(Reflection.getMinecraftClass("PlayerConnection"), "sendPacket", Reflection.getMinecraftClass("Packet"));
    private static Reflection.FieldAccessor<Object> playerConnection = Reflection.getSimpleField(Reflection.getMinecraftClass("EntityPlayer"), "playerConnection");

    private static Class<?> PPOPI_CLASS = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo");
    private static Class<?> EPIA_CLASS = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo$EnumPlayerInfoAction");
    private static Class<?> PID_CLASS = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo$PlayerInfoData");
    private static Class<?> EG_CLASS = Reflection.getMinecraftClass("WorldSettings$EnumGamemode");
    private static Class<?> CS_CLASS = Reflection.getMinecraftClass("IChatBaseComponent$ChatSerializer");
    private static Class<?> ICBC_CLASS = Reflection.getMinecraftClass("IChatBaseComponent");
    private static Class<?> PPOPLHF_CLASS = Reflection.getMinecraftClass("PacketPlayOutPlayerListHeaderFooter");

    private static Reflection.MethodInvoker CS_A_METHOD = Reflection.getTypedMethod(CS_CLASS, "a", ICBC_CLASS, String.class);

    private static Reflection.ConstructorInvoker PPOPI_CONSTRUCTOR = Reflection.getConstructor(PPOPI_CLASS);
    private static Reflection.ConstructorInvoker PPOPLHF_CONSTRUCTOR = Reflection.getConstructor(PPOPLHF_CLASS);
    private static Constructor<?> PID_CONSTRUCTOR;

    private static Reflection.FieldAccessor PPOPI_A_FIELD = Reflection.getSimpleField(PPOPI_CLASS, "a");
    private static Reflection.FieldAccessor PPOPI_B_FIELD = Reflection.getSimpleField(PPOPI_CLASS, "b");
    private static Reflection.FieldAccessor PPOPLHF_A_FIELD = Reflection.getSimpleField(PPOPLHF_CLASS, "a");
    private static Reflection.FieldAccessor PPOPLHF_B_FIELD = Reflection.getSimpleField(PPOPLHF_CLASS, "b");


    static {
        try {
            PID_CONSTRUCTOR = PID_CLASS.getDeclaredConstructor(PPOPI_CLASS, GameProfile.class, int.class, EG_CLASS, ICBC_CLASS);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendPackets(Player player, Object... objects) {
        if (handleMethod == null)
            throw new RuntimeException("HandleMethod can not be null!");
        Object handle = handleMethod.invoke(player);
        for (Object o : objects)
            sendPacket.invoke(playerConnection.get(handle), o);
    }

    @Override
    public void sendTablistHeaderPacket(Player player, String header, String footer) {
        Object packet = PPOPLHF_CONSTRUCTOR.invoke();
        PPOPLHF_A_FIELD.set(packet, buildJSON(header));
        PPOPLHF_B_FIELD.set(packet, buildJSON(footer));
        sendPackets(player, packet);
    }

    @Override
    public void sendPlayerListPacket(Player player, Profile profile, String displayName, PlayerInfoAction action) {

        Object packet = PPOPI_CONSTRUCTOR.invoke();

        PPOPI_A_FIELD.set(packet, Enum.valueOf((Class<Enum>) EPIA_CLASS, action.name()));

        List list = new ArrayList<>();

        list.add(getPlayerInfoData(packet, profile, displayName));

        PPOPI_B_FIELD.set(packet, list);

        sendPackets(player, packet);

    }

    private Object getPlayerInfoData(Object packet, Profile profile, String displayName) {
        try {
            return PID_CONSTRUCTOR.newInstance(packet, profile, 9999, Enum.valueOf((Class<Enum>) EG_CLASS, "SURVIVAL"), buildJSON(displayName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Object buildJSON(String string) {
        return CS_A_METHOD.invoke(null, "{\"text\": \"" + Util.fixColor(string) + "\"}");
    }
}
