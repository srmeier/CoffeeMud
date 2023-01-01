package com.planet_ink.coffee_mud.Abilities.Druid;
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
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.List;

/*
   Copyright 2003-2023 Bo Zimmerman

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
public class Chant_Dehydrate extends Chant
{
	@Override
	public String ID()
	{
		return "Chant_Dehydrate";
	}

	private final static String localizedName = CMLib.lang().L("Dehydrate");

	@Override
	public String name()
	{
		return localizedName;
	}

	private final static String localizedStaticDisplay = CMLib.lang().L("(Dehydrate)");

	@Override
	public String displayText()
	{
		return localizedStaticDisplay;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_CHANT|Ability.DOMAIN_ENDURING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_MALICIOUS;
	}

	@Override
	public boolean invoke(final MOB mob, final List<String> commands, final Physical givenTarget, final boolean auto, final int asLevel)
	{
		final Physical target=getAnyTarget(mob,commands,givenTarget,Wearable.FILTER_ANY);
		if(target==null)
			return false;

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;
		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			invoker=mob;
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> chant(s) to <T-NAMESELF>.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				if(msg.value()<=0)
				{
					if(target instanceof MOB)
					{
						((MOB)target).curState().adjThirst(-150 - ((mob.phyStats().level()+(2*getXLEVELLevel(mob))) * 100),((MOB)target).maxState().maxThirst(((MOB)target).baseWeight()));
						mob.location().show(((MOB)target),null,CMMsg.MSG_OK_VISUAL,L("<S-NAME> feel(s) incredibly thirsty!"));
					}
					else
					if(target instanceof Item)
					{
						if(target instanceof Container)
						{
							final List<Item> V=((Container)target).getDeepContents();
							for(int i=0;i<V.size();i++)
							{
								final Item I=V.get(i);
								if(I instanceof Drink)
								{
									if(((Drink)I).liquidRemaining()<10000)
										((Drink)I).setLiquidRemaining(0);
									if(I instanceof RawMaterial)
										I.destroy();
								}
							}
							if(target instanceof Drink)
							{
								if(((Drink)target).liquidRemaining()<10000)
									((Drink)target).setLiquidRemaining(0);
							}
							if(target instanceof RawMaterial)
								((Item)target).destroy();
						}
					}
				}
			}
		}
		else
			return maliciousFizzle(mob,target,L("<S-NAME> chant(s) to <T-NAMESELF>, but nothing happens."));
		// return whether it worked
		return success;
	}
}
