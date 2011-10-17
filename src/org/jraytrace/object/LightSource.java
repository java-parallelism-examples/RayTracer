package org.jraytrace.object;

/**
 * Title:        JRayTrace
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Daniel Collins
 * @version 1.0
 */

import org.jraytrace.util.*;

public interface LightSource extends Surface {
	public Color getLightColor();
	public Ray getRayToLight(Vector3 pt);
}