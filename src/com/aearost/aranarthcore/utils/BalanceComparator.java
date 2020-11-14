package com.aearost.aranarthcore.utils;

import java.util.Comparator;

import com.aearost.aranarthcore.objects.AranarthPlayer;

/**
 * A comparator to determine which of the two AranarthPlayer objects has a
 * larger balance.
 * 
 * @author Aearost
 *
 */
public class BalanceComparator implements Comparator<AranarthPlayer> {

	@Override
	public int compare(AranarthPlayer player1, AranarthPlayer player2) {
		if (player1.getBalance() < player2.getBalance()) {
			return 1;
		} else if (player1.getBalance() == player2.getBalance()) {
			return 0;
		} else {
			return -1;
		}
	}

}
