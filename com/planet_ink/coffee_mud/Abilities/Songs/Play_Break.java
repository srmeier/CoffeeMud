package com.planet_ink.coffee_mud.Abilities.Songs;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;


public class Play_Break extends Play
{
	public String ID() { return "Play_Break"; }
	public String name(){ return "Break";}
	public int quality(){ return INDIFFERENT;}
	protected boolean skipStandardSongInvoke(){return true;}
	public Play_Break()
	{
		super();
		setProfficiency(100);
	}
	public Environmental newInstance(){	return new Play_Break();}
	public void setProfficiency(int newProfficiency){	super.setProfficiency(100);}
	

	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto)
	{
		boolean foundOne=false;
		for(int a=0;a<mob.numAffects();a++)
		{
			Ability A=(Ability)mob.fetchAffect(a);
			if((A!=null)&&(A instanceof Play))
				foundOne=true;
		}
		unplay(mob);
		if(!foundOne)
		{
			mob.tell(auto?"There is noone playing.":"You aren't playing anything.");
			return true;
		}

		mob.location().show(mob,null,Affect.MSG_NOISE,auto?"Silence.":"<S-NAME> stop(s) playing.");
		mob.location().recoverRoomStats();
		return true;
	}
}