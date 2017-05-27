package com.mogujie.tt.imlib.proto;

import java.util.ArrayList;
import java.util.List;

import com.mogujie.tt.config.ProtocolConstant;
import com.mogujie.tt.config.SysConstant;
import com.mogujie.tt.log.Logger;
import com.mogujie.tt.packet.base.DataBuffer;
import com.mogujie.tt.packet.base.DefaultHeader;
import com.mogujie.tt.packet.base.Header;
import com.mogujie.tt.packet.base.Packet;

/**
 * MsgServerPacket:请求(返回)登陆消息服务器 yugui 2014-05-04
 */

public class RecentContactsPacket extends Packet {

	private Logger logger = Logger.getLogger(RecentContactsPacket.class);

	public RecentContactsPacket() {
		mRequest = new PacketRequest();
		setNeedMonitor(true);
	}

	@Override
	public DataBuffer encode() {

		Header header = mRequest.getHeader();
		DataBuffer headerBuffer = header.encode();
		DataBuffer bodyBuffer = new DataBuffer();

		PacketRequest req = (PacketRequest) mRequest;
		if (null == req)
			return null;

		// bodyBuffer.writeString(req.getUser_id_url());
		// bodyBuffer.writeString(req.getUser_token());
		// bodyBuffer.writeInt(req.getOnline_status());
		// bodyBuffer.writeInt(req.getClient_type());
		// bodyBuffer.writeString(req.getClient_version());

		int headLength = headerBuffer.readableBytes();
		int bodyLength = bodyBuffer.readableBytes();

		DataBuffer buffer = new DataBuffer(headLength + bodyLength);
		buffer.writeDataBuffer(headerBuffer);
		buffer.writeDataBuffer(bodyBuffer);

		return buffer;
	}

	@Override
	public void decode(DataBuffer buffer) {

		if (null == buffer)
			return;
		try {
			PacketResponse res = new PacketResponse();

			Header header = new Header();
			header.decode(buffer);
			res.setHeader(header);

			// starts filling from here
			int cnt = buffer.readInt();
			for (int i = 0; i < cnt; ++i) {
				UserEntity entity = new UserEntity();

				entity.id = buffer.readString();
				entity.userUpdated = buffer.readInt();
				res.entityList.add(entity);
			}

			mResponse = res;
		} catch (Exception e) {
			logger.e(e.getMessage());
		}

	}

	public static class PacketRequest extends Request {
		public PacketRequest() {

			Header header = new DefaultHeader(ProtocolConstant.SID_BUDDY_LIST,
					ProtocolConstant.CID_BUDDY_LIST_REQUEST);

			int contentLength = 0;
			header.setLength(SysConstant.PROTOCOL_HEADER_LENGTH + contentLength);

			setHeader(header);
		}
	}

	public class UserEntity {
		public String id;
		public int userUpdated;

		@Override
		public String toString() {
			return String.format("id:%s, userUpdated:%d", id, userUpdated);
		}
	}

	public static class PacketResponse extends Response {

		public List<UserEntity> entityList = new ArrayList<RecentContactsPacket.UserEntity>();
	}
}
