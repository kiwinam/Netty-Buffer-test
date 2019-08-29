package BufferTest;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

/**
 * 자바에서 제공하는 ByteBuffer 와 Netty 에서 자체적으로 사용하고 있는 ByteBuf 의 처리 속도 차이를 테스트 하기 위한 클래스
 * @author charlie
 */

public class StringTest {
	// ByteBuffer 와 ByteBuf 를 비교하기 위한 byteArray
	public static byte[] words = {'0','1','2','3','4','5','6','7','8','9'};
	
	public static void main(String[] args) {
		// 실행 시간을 측정하기 위한 long 변수
		// System.nanoTime() 메서드에서 바이트 버퍼 실행 전과 후의 시간을 구하고
		// 바이트 버퍼에 words 를 읽고 쓰는데 걸리는 시간을 측정한다.
		long before = 0;
		long after = 0;
		
		
		//ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
		before = System.nanoTime();
		
		for(int t = 0; t<10000000; t++) {
			byteBuffer.clear();
			byteBuffer.put(words);
//			for(int i=0; i<byteBuffer.limit();i++) {
//				byteBuffer.get(i);
//			}
		}
	
		after = System.nanoTime();
		System.out.println("Java : "+(after-before));
		
		
		//ByteBuf byteBuf = Unpooled.buffer(10);
		ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(4096);
		before = System.nanoTime();
	
		for(int t = 0 ; t <10000000; t++) {
			byteBuf.clear();
			byteBuf.writeBytes(words);
//			for(int i=0; i<byteBuf.capacity();i++) {
//				byteBuf.getByte(i);
//			}
		}
		after = System.nanoTime();
		System.out.println("Nett : "+(after-before));
		
		ByteBuf heapBuf = Unpooled.buffer(4096);
		//ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(10);
		before = System.nanoTime();
	
		for(int t = 0 ; t <10000000; t++) {
			heapBuf.clear();
			heapBuf.writeBytes(words);
//		for(int i=0; i<byteBuf.capacity();i++) {
//				byteBuf.getByte(i);
//			}
		}
		after = System.nanoTime();
//		System.out.println("heap : "+(after-before));
	}
}
