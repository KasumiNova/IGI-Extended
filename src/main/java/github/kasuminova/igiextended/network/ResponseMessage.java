package github.kasuminova.igiextended.network;

import github.kasuminova.igiextended.IGIExtended;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ResponseMessage implements IMessage {

	public NBTTagCompound data;

	public ResponseMessage() {
	}

	public ResponseMessage(NBTTagCompound data) {
		this.data = data.copy();
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);
	}

	public static class ResponseHandler implements IMessageHandler<ResponseMessage, IMessage> {

		public ResponseHandler() {
		}

		@Override
		public IMessage onMessage(ResponseMessage message, final MessageContext ctx) {
			if (message.data.hasKey("meanTickTime")) {
				IGIExtended.cachedData = message.data.copy();
			}
			return null;
		}
	}
}
