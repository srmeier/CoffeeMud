package com.planet_ink.coffee_mud.Behaviors;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.MaskingLibrary.CompiledZMask;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/*
   Copyright 2001-2023 Bo Zimmerman

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

	   http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
public class WimpyAggressive extends Aggressive
{
	@Override
	public String ID()
	{
		return "WimpyAggressive";
	}

	@Override
	public long flags()
	{
		return Behavior.FLAG_POTENTIALLYAGGRESSIVE|Behavior.FLAG_TROUBLEMAKING;
	}

	public WimpyAggressive()
	{
		super();

		tickWait = 0;
		tickDown = 0;
	}

	@Override
	public String accountForYourself()
	{
		if(maskStr.trim().length()>0)
			return "wimpy aggression against "+CMLib.masking().maskDesc(maskStr,true).toLowerCase();
		else
			return "wimpy aggressiveness";
	}

	@Override
	public boolean grantsAggressivenessTo(final MOB M)
	{
		return ((M!=null)&&(CMLib.flags().isSleeping(M)))&&
			CMLib.masking().maskCheck(this.mask,M,false);
	}

	@Override
	public void setParms(final String newParms)
	{
		super.setParms(newParms);
		tickWait=CMParms.getParmInt(newParms,"delay",0);
		tickDown=tickWait;
	}

	public static void pickAWimpyFight(final MOB observer, final boolean mobKiller, final boolean misBehave, final String attackMsg, final CompiledZMask zapMask)
	{
		if(!canFreelyBehaveNormal(observer))
			return;
		final Room R=observer.location();
		if(R!=null)
		for(int i=0;i<R.numInhabitants();i++)
		{
			final MOB mob=R.fetchInhabitant(i);
			if((mob!=null)
			&&(mob!=observer)
			&&(CMLib.flags().isSleeping(mob))
			&&(CMLib.masking().maskCheck(zapMask,observer,false)))
			{
				startFight(observer,mob,mobKiller,misBehave,attackMsg);
				if(observer.isInCombat())
					break;
			}
		}
	}

	public static void tickWimpyAggressively(final Tickable ticking, final boolean mobKiller, final boolean misBehave,
											final int tickID, final String attackMsg, final CompiledZMask zapMask)
	{
		if(tickID!=Tickable.TICKID_MOB)
			return;
		if(ticking==null)
			return;
		if(!(ticking instanceof MOB))
			return;

		pickAWimpyFight((MOB)ticking,mobKiller,misBehave,attackMsg,zapMask);
	}

	@Override
	public boolean tick(final Tickable ticking, final int tickID)
	{
		if(tickID!=Tickable.TICKID_MOB)
			return true;
		if((--tickDown)<0)
		{
			tickDown=tickWait;
			tickWimpyAggressively(ticking,mobkill,misbehave,tickID,attackMessage,mask);
		}
		return true;
	}
}
