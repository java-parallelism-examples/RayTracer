package org.jraytrace.util;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

public class Interval {

	private double max;
	private double min;
	private boolean maxInclusive;
	private boolean minInclusive;

	public static final Interval emptyInterval = new Interval(0, false, 0, false);
	public static final Interval realNumbers =
		new Interval(Double.NEGATIVE_INFINITY, false, Double.POSITIVE_INFINITY, false);
	public static final Interval positiveReals =
		new Interval(0, true, Double.POSITIVE_INFINITY,false);
	public static final Interval negativeReals =
		new Interval(Double.NEGATIVE_INFINITY,false,0, true);

	public Interval() {
		max = min = 0;
		maxInclusive = minInclusive = false;
	}

	public Interval(double min, boolean minInclusive, double max, boolean maxInclusive) {
		this.min = min;
		this.minInclusive = minInclusive;
		this.max = max;
		this.maxInclusive = maxInclusive;
	}

	public Interval(double min, double max) {
		this.min = min;
		this.max = max;
		this.minInclusive = this.maxInclusive = true;
	}

	public Interval(Interval b) {
		this.min = b.min;
		this.minInclusive = b.minInclusive;
		this.max = b.max;
		this.maxInclusive = b.maxInclusive;
	}

	public boolean isIn(double val) {
		return ((val > min && val < max) ||
				(val == min && minInclusive) ||
				(val == max && maxInclusive));
	}

	public boolean intersects(Interval b) {
		return getIntersection(b).isEmpty();
	}

	public Interval getIntersection(Interval b) {
		Interval ret;
		int mn, mx;
		mn = (int) Math.max(this.min, b.min);
		mx = (int) Math.min(this.max, b.max);
		if (mn <= mx) {
			ret = new Interval(mn,mx);
			if (mn == this.min) {
				ret.minInclusive = this.minInclusive;
			} else {
				ret.minInclusive = b.minInclusive;
			}
			if (mx == this.max) {
				ret.maxInclusive = this.maxInclusive;
			} else {
				ret.maxInclusive = b.maxInclusive;
			}
		} else {
			ret = new Interval(this.emptyInterval);
		}
		return ret;
	}

	public boolean isEmpty() {
		return (((min == max) && !minInclusive && !maxInclusive) ||
			(min > max));
	}
}