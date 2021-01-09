package com.dvreiter.starassault.LevelEditor;
import android.graphics.*;
import java.io.*;

public class ImageControl
{
	public ImageControl(){
		
	}
	public static Bitmap FlipImageVertically(Bitmap source,float  cx ,float cy){
		Matrix matrix = new Matrix();
		matrix.postScale(1, -1, cx, cy);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
	
	public static Bitmap FlipImageHorizontally(Bitmap source,float  cx ,float cy){
		Matrix matrix = new Matrix();
		matrix.postScale(-1, 1, cx, cy);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
	
	public static Bitmap RotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
	
	public static void SaveBitmap(Bitmap bmp , String SaveLocation){
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(SaveLocation);
			bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
			// PNG is a lossless format, the compression factor (100) is ignored
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static Bitmap OverlayImages(Bitmap bmp1, Bitmap bmp2) {
        Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(), bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(bmp1, new Matrix(), null);
        canvas.drawBitmap(bmp2, new Matrix(), null);
        return bmOverlay;
    }
	
}
