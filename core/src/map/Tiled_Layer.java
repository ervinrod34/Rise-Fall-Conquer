package map;

@SuppressWarnings("unused")
public class Tiled_Layer {
	private int[] data;
	private int height;
	private String name;
	private String opacity;
	private String type;
	private boolean visible;
	private int width;
	private int x;
	private int y;
	
	private int [][] mapData;
	
	public int [][] getmapData(){
		if(mapData == null){
			if(width == 0 || height == 0){
				try {
					throw new Exception("Tiled Layer Cannot have 0 height or 0 width.");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			mapData = new int[height][width];
			int i = 0;
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					mapData[y][x] = data[i];
					i++;
				}
			}
			Tiled_Layer.reverseRowsInPlace(mapData);
		}
		return mapData;
	}
	
	public static void reverseRowsInPlace(int[][] matrix){
	    for(int row = 0; row < matrix.length; row++){
	        for(int col = 0; col < matrix[row].length / 2; col++) {
	            int temp = matrix[row][col];
	            matrix[row][col] = matrix[row][matrix[row].length - col - 1];
	            matrix[row][matrix[row].length - col - 1] = temp;
	        }
	    }
	}
	public static void reverseColumnsInPlace(int[][] matrix){
        for(int col = 0;col < matrix[0].length; col++){
            for(int row = 0; row < matrix.length/2; row++) {
                int temp = matrix[row][col];
                matrix[row][col] = matrix[matrix.length - row - 1][col];
                matrix[matrix.length - row - 1][col] = temp;
            }
    }
	}
}
