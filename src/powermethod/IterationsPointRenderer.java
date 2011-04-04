package powermethod;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;

import de.erichseifert.gral.AbstractDrawable;
import de.erichseifert.gral.Drawable;
import de.erichseifert.gral.DrawingContext;
import de.erichseifert.gral.data.Row;
import de.erichseifert.gral.plots.axes.Axis;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.points.AbstractPointRenderer;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.util.GraphicsUtils;

public class IterationsPointRenderer extends AbstractPointRenderer {
	public static final Key COLOR_MIN = new Key("point.color.min");
	public static final Key VALUE_MIN = new Key("point.value.min");
	public static final Key VALUE_MAX = new Key("point.value.max");
	private int num = 5;
	
	public IterationsPointRenderer() {
		super();
		
		setSettingDefault(COLOR_MIN, new Color(50, 50, 50));
		setSettingDefault(COLOR, new Color(254, 254, 254));
		setSettingDefault(VALUE_MIN, Double.MIN_VALUE);
		setSettingDefault(VALUE_MAX, Double.MAX_VALUE);
	}
	
	@Override
	public Drawable getPoint(final Axis axisY, final AxisRenderer axisYRenderer, final Row row) {
		double iterations = 500;
		
		double minValue = (Double)(this.getSetting(VALUE_MIN));
		double maxValue = (Double)(this.getSetting(VALUE_MAX));
		Color maxColor = (Color)(this.getSetting(COLOR));
		Color minColor = (Color)(this.getSetting(COLOR_MIN));
		final int red = (int)scale(iterations, minValue, maxValue, minColor.getRed(), maxColor.getRed());
		final int green = (int)scale(iterations, minValue, maxValue, minColor.getGreen(), maxColor.getGreen());
		final int blue = (int)scale(iterations, minValue, maxValue, minColor.getBlue(), maxColor.getBlue());
		
		Drawable drawable = new AbstractDrawable() {
			@Override
			public void draw(DrawingContext context) {
				System.out.println("("+red+","+green+","+blue+")");
				Paint paint = new Color(red, green, blue);
				
				Shape point = getPointPath(row);
				GraphicsUtils.fillPaintedShape(
					context.getGraphics(), point, paint, null);
				PointRenderer renderer = IterationsPointRenderer.this;

				if (renderer.<Boolean>getSetting(VALUE_DISPLAYED)) {
					drawValue(context, point, row.get(1).doubleValue());
				}
				if (renderer.<Boolean>getSetting(ERROR_DISPLAYED)) {
					int columnIndex = row.size() - 1;
					drawError(context, point, row.get(1).doubleValue(),
							row.get(columnIndex - 1).doubleValue(),
							row.get(columnIndex).doubleValue(),
							axisY, axisYRenderer);
				}
			}
		};

		return drawable;
	}

	@Override
	public Shape getPointPath(Row row) {
		Shape shape = getSetting(SHAPE);
		return shape;
	}
	
	private double scale(double current, double currentMin, double currentMax, double targetMin, double targetMax) {
		double scaled = targetMin + (targetMax - targetMin) * ((current - currentMin) / (currentMax - currentMin));
		
		/*if (scaled > targetMax) {
			scaled = targetMax;
		} else if (scaled < targetMin) {
			scaled = targetMin;
		}*/
		
		return scaled;
	}
}