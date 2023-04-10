package core.requests;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import core.Context;
import request_handling.MayBeResponse;
import request_handling.Request;
import utils.FeaturesSnake;
import utils.items.FeaturesItem;

public class MapState extends MayBeResponse{

	//	TODO : ajouter gestion de l'ID de chaque snake (afin de gérer aussi le skin)

	//	[int nb_items] ; List of size nb_items {[char item] ; [int pos_x] ; [int pos_y]} ; 
	//	[int nb_snakes] ; List of size nb_snakes {([char id_snake]) ;  [byte last_action] ; [byte color_snake] ; [byte is_invincible] ; [byte is_sick] ;  [int snake_size] ; List of size snake_size {[int pos_x]; [int pos_y]}}


	// first list : Items 
	// second list : Snakes (players) containing a list of positions

	public final static int ID = 5;

	private Context context;

	public MapState(JsonNode content_recieved) {
		super(content_recieved);	
	}

	public MapState() {
		super(false);
	}

	public MapState(Context context) {
		super(true);
		this.context = context;
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return MapState.ID;
	}

	@Override
	protected void parseContent(JsonNode given_content) {
		super.parseContent(given_content);

		if(!this.isResponse()) {

			return;

		}

		try {

			List<FeaturesItem> items = MapState.mapper.readValue(MapState.mapper.treeAsTokens(given_content.get("items")), new TypeReference<List<FeaturesItem>>() {});
			List<FeaturesSnake> snakes = MapState.mapper.readValue(MapState.mapper.treeAsTokens(given_content.get("snakes")), new TypeReference<List<FeaturesSnake>>() {});
			
			this.context = new Context(snakes, items);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//		ByteBuffer buff = ByteBuffer.wrap(content);
		//		
		//		
		////		nb items;
		//		int nb_items = buff.getInt();
		//		List<FeaturesItem> items = new ArrayList<FeaturesItem>();
		//		
		////		List of size nb_items {[char item] ; [int pos_x] ; [int pos_y]} ; 
		//
		//		for(int i = 0; i < nb_items ; i++) {
		//			
		//			ItemType transmitted_type = ItemType.fromChar(buff.getChar());
		//			
		//			int transmitted_pos_x = buff.getInt();
		//			int transmitted_pos_y = buff.getInt();
		//			
		//			FeaturesItem transmitted_item = new FeaturesItem(transmitted_pos_x, transmitted_pos_y, transmitted_type);
		//			items.add(transmitted_item);			
		//			
		//		}
		//		
		////		[int nb_snakes] ; 
		//		
		//		int nb_snakes = buff.getInt();
		//		List<FeaturesSnake> snakes = new ArrayList<FeaturesSnake>();
		//		
		////		List of size nb_snakes {([char id_snake]) ;  [byte last_action] ; [byte color_snake] ; [byte is_invincible] ; [byte is_sick] ;  [int snake_size] ; List of size snake_size {[int pos_x]; [int pos_y]}}
		//
		//		for(int i = 0; i < nb_snakes; i++) {
		////			char transmitted_id = buff.getChar();
		//			
		//			AgentAction transmited_action = AgentAction.fromByte(buff.get());
		//			ColorSnake transmitted_color = ColorSnake.fromByte(buff.get());
		//			
		//			boolean transmitted_is_invincible = (int) buff.get() == 1;
		//			boolean transmitted_is_sick = (int) buff.get() == 1;
		//			
		//			int transmitted_nb_positions = buff.getInt();
		//			
		//			ArrayList<Position> transmitted_positions = new ArrayList<Position>();
		//			
		//			for(int j = 0; j < transmitted_nb_positions; j++) {
		//				
		//				int pos_x = buff.getInt();
		//				int pos_y = buff.getInt();
		//				
		//				transmitted_positions.add(new Position(pos_x, pos_y));
		//				
		//			}
		//			
		//			snakes.add(new FeaturesSnake(transmitted_positions, transmited_action, transmitted_color, transmitted_is_invincible, transmitted_is_sick));
		//		}
		//		
		//
		//		this.context = new Context(snakes, items);
		//
		//		
		//		return new byte[] {};
	}


	@Override
	protected ObjectNode encodeRequest(ObjectNode base) {

		if(base == null) {
			base = Request.mapper.createObjectNode();
		}


		if(!this.isResponse()) {
			return super.encodeRequest(base);
		}

		JsonNode items =  MapState.mapper.valueToTree(this.context.getItems());
		JsonNode fs =  MapState.mapper.valueToTree(this.context.getSnakes());

		base.set("items", items);
		base.set("snakes", fs);




		//		if(!this.isResponse()) {
		//			return super.encodeRequest(base);
		//		}
		//		
		//		List<FeaturesItem> items = this.context.getItems();
		//		
		//		ByteBuffer buff = ByteBuffer.allocate(this.getAllocatedSize());
		//		
		////		nb items;
		//		buff.putInt(items.size());
		//		
		////		List of size nb_items {[char item] ; [int pos_x] ; [int pos_y]} ; 
		//		for(FeaturesItem it : items) {
		//			
		////			[char item]
		//			buff.putChar(it.getItemType().toChar());
		////			[int pos_x]
		//			buff.putInt(it.getX());
		////			[int pos_y]
		//			buff.putInt(it.getY());
		//		}
		//		
		//		
		//		List<FeaturesSnake> snakes = this.context.getSnakes();
		//
		////		[int nb_snakes] ; List of size nb_snakes {([char id_snake]) ;  [byte last_action] ; [byte color_snake] ; [byte is_invincible] ; [byte is_sick] ;  [int snake_size] ; List of size snake_size {[int pos_x]; [int pos_y]}}
		//
		//		
		//		//		nb_snakes
		//		buff.putInt(snakes.size());
		//				
		//		for(FeaturesSnake snake : snakes) {
		//			//			buff.putChar(snake.getCharId()); 
		//
		//			buff.put(snake.getLastAction().toByte());
		//			buff.put(snake.getColorSnake().toByte());
		//		
		//			buff.put(snake.isInvincible() ? (byte) 1 : (byte) 0);
		//			buff.put(snake.isSick() ? (byte) 1 : (byte) 0);
		//
		//			
		//			List<Position> snake_pos = snake.getPositions();
		//			
		////			[int snake_size]
		//			buff.putInt(snake_pos.size());
		//			
		////			List of size snake_size {[int pos_x]; [int pos_y]}
		//			
		//			for(Position pos : snake_pos) {
		//				buff.putInt(pos.getX());
		//				buff.putInt(pos.getY());
		//			}
		//		}
		//		
		//		return super.encodeRequest(buff.array());

		return super.encodeRequest(base);
	}



	public Context getContext() {
		return this.context;
	}
}
