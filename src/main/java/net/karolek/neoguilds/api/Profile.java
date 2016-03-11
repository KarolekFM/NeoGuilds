package net.karolek.neoguilds.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import java.util.UUID;

public class Profile extends GameProfile {

    public Profile(UUID uuid, String s) {
        super(uuid, s);
        setSkin();
    }

    public void setSkin() {
        getProperties().removeAll("textures");
        getProperties().put("textures", new Property("textures", "eyJ0aW1lc3RhbXAiOjE0NTA0NTQ0NDI4OTksInByb2ZpbGVJZCI6ImNhN2E2N2RlYzQ2OTQ4NGJhZTI2NzU1MGJkYzljZGI4IiwicHJvZmlsZU5hbWUiOiJNaWdodHlNZWdhIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS8zNTFhZmFmYzZmNjQ2ZGQ5ZmY5Yzc3NGE4MmVlODlkYTcwZDMzMjM3MjJjZDAyY2JjZjNkYzYxYWU3MTdiYmMifX19", "f108yl/soPMY6SVNUQbUiqlJay5c4O6Tm3PBKyCkcdJeBkNe7LpBOxbPMqJwB8DF5gXu5s8WWrRUB3Y6ozSGOZNdeqkohL2JMnvj8W2TK3HdDO68uIe958pileAAcnFQ628EJ8+jf4sZ+qWImzl8a5zc10qE3+oiO5kPTwHDQdfJ9y2scbRgZlzsZeRsQKdmge0HpxJ3CGV5ptTBpALgJrq/R3MlR89zAUYvHWu8b85ijgqvOAy/BHk0X3BABHY9MmTY+CguhIPLP4hzwU+pVRmYYzyC/txElm8c+/8ilNELfuaGPCWMDSc5rrrdKIz5BxT+ghlGdbkm/Ydo9/NT0wWm3iRYh3DSAcpBJlei9l2FkgJHbUfz6mAxrelfIfcwExD7csJGP7kbLrhlWPngFVZQ+Nm9lZPRvV+aDVmXjZ350dfVSBAAjW7fwC6QR1nGxDLKLLMDz38DwUPLzgARGPmB7C5nKBwQfKTMorjxtRD2FKM+wYiP15n5IkWW61j/H8CvD1BD77QAeMDaAakGo1CUza6Pb4n0DDImvn8QTk4wfdoUGhVIkF0ruqWXZmddH6uoJJRDRQtTU86hHbQfpjI+ibiSl+LE12vsoT3CZezPu8lJJR0tqRRksTBgQNETM2xZE+rWIEc07OSjA/xvcbXYBYUqnCXsB9gxTLSnR4o="));
    }
}
