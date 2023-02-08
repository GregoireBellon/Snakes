package helpers;

import java.util.ArrayList;
import java.util.List;

public class ByteConversion {
	public static List<Byte> arrayToByteList(byte[] bytes){
		List<Byte> byte_list = new ArrayList<>();
		
		for(byte b: bytes)
			byte_list.add(b);
		
		return byte_list;
		
	}
}
