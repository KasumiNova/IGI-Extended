package github.kasuminova.igiextended.network;

import github.kasuminova.igiextended.IGIExtended;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class RemoteDataMessage implements IMessage {

	public RemoteDataMessage() {
	}

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	public static class Handler implements IMessageHandler<RemoteDataMessage, ResponseMessage> {

		public Handler() {
		}

		@Override
		public ResponseMessage onMessage(RemoteDataMessage message, MessageContext ctx) {
			final NBTTagCompound data = new NBTTagCompound();
			final EntityPlayerMP player = ctx.getServerHandler().player;
			IThreadListener mainThread = (WorldServer) player.world;
			mainThread.addScheduledTask(() -> {
				try {
					double meanTickTime = mean(player.server.tickTimeArray) * 1.0E-6D;
					double meanTPS = Math.min(1000.0/meanTickTime, 20);

					data.setDouble("meanTickTime", meanTickTime);
					data.setDouble("meanTPS", meanTPS);
					ResponseMessage response = new ResponseMessage(data);
					IGIExtended.network.sendTo(response, player);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			return null;
		}
	}

	public static long mean(long[] values)
	{
		long sum = 0L;
		for (long v : values)
		{
			sum += v;
		}
		return sum / values.length;
	}
}
