package com.aearost.aranarthcore.utils;

import java.util.Comparator;

/**
 * A comparator to determine which of the two Trails comes first alphabetically.
 * 
 * @author Aearost
 *
 */
public class TrailNameComparator implements Comparator<Trail> {

	@Override
	public int compare(Trail trail1, Trail trail2) {
		if (trail1.name().compareTo(trail2.name()) < 0) {
			return 1;
		} else if (trail2.name().compareTo(trail1.name()) > 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
}
