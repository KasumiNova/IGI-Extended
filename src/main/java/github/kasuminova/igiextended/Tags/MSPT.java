package github.kasuminova.igiextended.Tags;

import github.kasuminova.igiextended.IGIExtended;
import github.kasuminova.igiextended.network.RemoteDataMessage;
import net.minecraft.server.MinecraftServer;

import static github.kasuminova.igiextended.IGIExtended.logger;
import static github.kasuminova.igiextended.network.RemoteDataMessage.mean;

public class MSPT extends IGIETags {
    @Override
    public String getValue() {
        try {
            if (world.isRemote) {
                long delay = (System.currentTimeMillis() - lastRemoteUpdate);
                if (delay > 1500 || delay < 0) {
                    IGIExtended.network.sendToServer(new RemoteDataMessage());
                    lastRemoteUpdate = System.currentTimeMillis();
                }
                return String.format("%.2f", IGIExtended.cachedData.getFloat("meanTickTime"));
            } else {
                MinecraftServer server = world.getMinecraftServer();
                if (server != null) {
                    double meanTickTime = mean(server.tickTimeArray) * 1.0E-6D;
                    return String.format("%.2f", meanTickTime);
                }
            }
        } catch (Throwable e) {
            logger.error("Error When Get Tag Data: MSPT.", e);
        }
        return "-1";
    }
}
