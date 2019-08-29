package netty;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;

public class NettyBuffer {
	public static void main(String[] args) throws IOException {
		long before,after = 0;
		File imgFile = new File("res/a.jpeg");
		System.out.println(imgFile.getAbsolutePath());
		
		BufferedImage bufferdImage = ImageIO.read(imgFile);
		WritableRaster raster = bufferdImage.getRaster(); 
		DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
		byte[] byteData = data.getData();
		System.out.println("size : "+byteData.length);
		
		ByteBuf buff = Unpooled.buffer(4096); // heap
		//ByteBuf buff = Unpooled.directBuffer(4096); // direct
		//ByteBuf buff = PooledByteBufAllocator.DEFAULT.directBuffer(4096); // Pooled버퍼
		
		
		System.out.println("Netty \n\n");
		for(int t=0; t<20; t++) { // 테스트 20회 실행
			before = System.nanoTime();
			//buff.writeBytes(byteData); 
			
			// ByteBuf 에 이미지 바이트 배열 적재. 
			for(int i = 0; i < byteData.length; i+=4095) {
				for(int j=0; j < 4095;j++) {
					if(i+j < byteData.length) {
						buff.writeByte(byteData[i+j]);
					}
				}
				// 버퍼 크기가 가득 차면 clear 
				buff.clear();
			}
			after = System.nanoTime();
			
			// 버퍼에 쓰는 시간 출력
			System.out.println(""+ (after-before));
		}
		System.out.println("\n\nJava");
		for(int t=0; t<20; t++) {
			before = System.nanoTime();
			//ByteBuffer.wrap(byteData);
			ByteBuffer b = ByteBuffer.allocate(4096); // heap
			//ByteBuffer b = ByteBuffer.allocateDirect(4096); //direct
			
			for(int i = 0; i < byteData.length; i+=4095) {
				for(int j=0; j < 4095;j++) {
					if(i+j < byteData.length) {
						b.put(byteData[i+j]);
					}
				}
				b.clear();
			}	
			after = System.nanoTime();
		
			System.out.println(""+ (after-before));
		}
	
		
	}
}
