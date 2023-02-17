package core.requests;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Map;

import core.InputMap;
import request_handling.MayBeResponse;
import utils.FeaturesSnake;
import utils.items.FeaturesItem;

public class MapState extends MayBeResponse{

	
//	[int nb_items] ; List of size nb_items {[char item] ; [int pos_x] ; [int pos_y]} ; 
//	[int nb_snakes] ; List of size nb_snakes {[char id_snake] ; [int snake_size] ; List of size snake_size {[int pos_x]; [int pos_y]}}

	
// first list : Items 
// second list : Snakes (players) containing a list of positions

	public final static int ID = 5;
	
	private InputMap map;
	
	public MapState(byte[] content_recieved) {
		super(content_recieved);	
	}
	
	public MapState(InputMap map) {
		super(true);
		this.map = map;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return MapState.ID;
	}
	
	@Override
	protected byte[] parseContent(byte[] given_content) {
		byte[] content = super.parseContent(given_content);
		ByteBuffer buf = ByteBuffer.wrap(content);
		
		buf.getInt();
		
		
		return new byte[] {};
	}
	
	@Override
	protected byte[] encodeRequest(byte[] base) {
		
		ArrayList<FeaturesItem> items = this.map.getStart_items();
		
		ByteBuffer buff = ByteBuffer.allocate(this.getAllocatedSize());
		
//		nb items;
		buff.putInt(map.getStart_items().size());
		
//		List of size nb_items {[char item] ; [int pos_x] ; [int pos_y]} ; 
		for(FeaturesItem it : this.map.getStart_items()) {
			
//			[char item]
			buff.putChar(it.getItemType().toChar());
//			[int pos_x]
			buff.putInt(it.getX());
//			[int pos_y]
			buff.putInt(it.getY());
		}
		
//		nb_snakes
		buff.putInt(this.map.getStart_snakes().size());
		
//		List of size nb_snakes
		for(FeaturesSnake snake : this.map.getStart_snakes()) {
			[]
		}
		
		return super.encodeRequest(new byte[] {});
		
	}

	
	private int getAllocatedSize() {
		
		int size = 0;
		
//		Items
		size += Integer.BYTES + ( this.map.getStart_items().size() * (Character.BYTES + Integer.BYTES + Integer.BYTES));
		
		int total_snake_size = 0;		
		for(FeaturesSnake s : this.map.getStart_snakes()) {
			total_snake_size+= s.getPositions().size();			
		}
		
//		Snakes
		size += Integer.BYTES + (total_snake_size * (Integer.BYTES + Integer.BYTES)) + this.map.getStart_snakes().size() * (Character.BYTES + Integer.BYTES);
		
		return size;
	}
	
	public InputMap getMap() {
		return this.map;
	}
}
