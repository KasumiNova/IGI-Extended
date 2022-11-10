package github.kasuminova.igiextended.Tags;

import github.kasuminova.igiextended.IGIExtended;
import github.kasuminova.igiextended.network.RemoteDataMessage;
import net.minecraft.server.MinecraftServer;

import static github.kasuminova.igiextended.IGIExtended.logger;
import static github.kasuminova.igiextended.network.RemoteDataMessage.mean;

public class TPS extends IGIETags {
    @Override
    public String getValue() {
        try {
            if (world.isRemote) {
                long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                if (delay > 1500 || delay < 0) {
                    IGIExtended.network.sendToServer(new RemoteDataMessage());
                    lastRemoteUpdate = System.currentTimeMillis();
                }
                return String.format("%.2f", IGIExtended.cachedData.getFloat("meanTPS"));
            } else {
                MinecraftServer server = world.getMinecraftServer();
                if (server != null) {
                    double meanTickTime = mean(server.tickTimeArray) * 1.0E-6D;
                    double meanTPS = Math.min(1000.0 / meanTickTime, 20);
                    return String.format("%.2f", meanTPS);
                }
            }
        } catch (Throwable e) {
            logger.error("Error When Get Tag Data: TPS.", e);
        }
        return "-1";
    }
}
