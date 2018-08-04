package client.packet.event;

import client.Communication;
import client.Log;
import client.Packet;
import client.World;

public class BuffInfoEvent extends Packet {
    @Override
	public void ready(World world, Communication comm) throws Exception {
    	Log.println("BuffInfo: "+getMessage());
    }
}

