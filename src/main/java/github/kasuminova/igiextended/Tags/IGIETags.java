package github.kasuminova.igiextended.Tags;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;

public abstract class IGIETags extends Tag {
    static long lastRemoteUpdate = 0;

    @Override
    public String getCategory() {
        return "IGIExtended";
    }

    public static void register() {
        TagRegistry.INSTANCE.register(new TPS().setName("tps"));
        TagRegistry.INSTANCE.register(new MSPT().setName("mspt"));
    }
}
