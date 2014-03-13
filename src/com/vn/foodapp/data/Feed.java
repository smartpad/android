package com.vn.foodapp.data;

import java.util.Collection;

public interface Feed {
	
	int getOrder();
	
	Object getTarget();
	
	Collection<GPSLocation> getGPSLocations();

}
