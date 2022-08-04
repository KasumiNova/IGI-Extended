package github.kasuminova.igiextended;

import com.github.lunatrius.ingameinfo.tag.Tag;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import github.kasuminova.igiextended.network.RemoteDataMessage;

import static github.kasuminova.igiextended.IGIExtended.logger;
import static github.kasuminova.igiextended.network.RemoteDataMessage.mean;

public abstract class Tags extends Tag {
    static long lastRemoteUpdate = 0;

    @Override
    public String getCategory() {
        return "IGIExtended";
    }

    public static class TPS extends Tags {
        @Override
        public String getValue() {
            try {
                if (world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 1000 || delay < 0) {
                        IGIExtended.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.format("%.2f", IGIExtended.cachedData.getFloat("meanTPS"));
                } else {
                    double meanTickTime = mean(world.getMinecraftServer().tickTimeArray) * 1.0E-6D;
                    double meanTPS = Math.min(1000.0/meanTickTime, 20);

                    return String.format("%.2f", meanTPS);
                }
            } catch (Throwable e) {
                logger.error("Error When Get Tag Data: TPS.", e);
            }
            return "-1";
        }
    }

    public static class MSPT extends Tags {
        @Override
        public String getValue() {
            try {
                if (world.isRemote) {
                    long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                    if (delay > 1000 || delay < 0) {
                        IGIExtended.network.sendToServer(new RemoteDataMessage());
                        lastRemoteUpdate = System.currentTimeMillis();
                    }
                    return String.format("%.2f", IGIExtended.cachedData.getFloat("meanTickTime"));
                } else {
                    double meanTickTime = mean(world.getMinecraftServer().tickTimeArray) * 1.0E-6D;

                    return String.format("%.2f", meanTickTime);
                }
            } catch (Throwable e) {
                logger.error("Error When Get Tag Data: MSPT.", e);
            }
            return "-1";
        }
    }

    public static void register() {
        TagRegistry.INSTANCE.register(new TPS().setName("tps"));
        TagRegistry.INSTANCE.register(new MSPT().setName("mspt"));
    }
}
