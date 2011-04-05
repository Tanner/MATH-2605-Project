package animation;

import java.util.Comparator;

public class ZMaxComparator implements Comparator<Side> {
	@Override
	public int compare(Side a, Side b) {
		//a negative integer, zero, or a positive integer as the 1st argument is less than, equal to, or greater than the 2nd

		if (maxZ(a) < maxZ(b)) {
			return -1;
		} else if (maxZ(a) > maxZ(b)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private double maxZ(Side a) {
		double max = Double.MIN_VALUE;
		double[][] array = a.getMatrix().getArray();
		for (int i = 0; i < array[0].length; i++) {
			double z = array[2][i];
			if (z > max) {
				max = z;
			}
		}
		
		return max;
	}
}
