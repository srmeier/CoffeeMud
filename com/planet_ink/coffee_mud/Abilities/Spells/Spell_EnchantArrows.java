package com.planet_ink.coffee_mud.Abilities.Spells;
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
import com.planet_ink.coffee_mud.Libraries.interfaces.ExpertiseLibrary;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/*
   Copyright 2004-2023 Bo Zimmerman

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
public class Spell_EnchantArrows extends Spell
{

	@Override
	public String ID()
	{
		return "Spell_EnchantArrows";
	}

	private final static String	localizedName	= CMLib.lang().L("Enchant Arrows");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canTargetCode()
	{
		return CAN_ITEMS;
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_ITEMS;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SPELL | Ability.DOMAIN_ENCHANTMENT;
	}

	@Override
	protected int overrideMana()
	{
		return Ability.COST_ALL;
	}

	@Override
	public long flags()
	{
		return Ability.FLAG_NOORDERING;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	@Override
	public void affectPhyStats(final Physical host, final PhyStats affectableStats)
	{
		affectableStats.setAbility(affectableStats.ability()+CMath.s_int(text()));
		affectableStats.setDisposition(affectableStats.disposition()|PhyStats.IS_BONUS);
	}

	@Override
	public boolean invoke(final MOB mob, final List<String> commands, final Physical givenTarget, final boolean auto, final int asLevel)
	{
		final Item target=super.getTarget(mob,mob.location(),givenTarget,commands,Wearable.FILTER_ANY);
		if(target==null)
			return false;

		if((target instanceof Ammunition)
		&&(((Ammunition)target).ammunitionType().equalsIgnoreCase("arrows")
			||((Ammunition)target).ammunitionType().equalsIgnoreCase("bolts")
			||((Ammunition)target).ammunitionType().equalsIgnoreCase("ballista-bolts")))
		{
			// this is what you can enchant
		}
		else
		{
			mob.tell(mob,target,null,L("You can't enchant <T-NAME> ith an Enchant Arrows spell!"));
			return false;
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		int experienceToLose=getXPCOSTAdjustment(mob,5);
		experienceToLose=-CMLib.leveler().postExperience(mob,null,null,-experienceToLose,false);
		mob.tell(L("The effort causes you to lose @x1 experience.",""+experienceToLose));

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			final CMMsg msg=CMClass.getMsg(mob,target,this,verbalCastCode(mob,target,auto),auto?"":L("^S<S-NAME> hold(s) <T-NAMESELF> and cast(s) a spell.^?"));
			if(mob.location().okMessage(mob,msg))
			{
				mob.location().send(mob,msg);
				Ability A=target.fetchEffect(ID());
				if((A!=null)&&(CMath.s_int(A.text())>2))
					mob.tell(L("You are not able to enchant @x1 further.",target.name(mob)));
				else
				{
					mob.location().show(mob,target,CMMsg.MSG_OK_VISUAL,L("<T-NAME> glows!"));
					if (A == null)
					{
						A = (Ability) copyOf();
						target.addNonUninvokableEffect(A);
					}
					A.setMiscText(""+(CMath.s_int(A.text())+1));
					target.recoverPhyStats();
					mob.recoverPhyStats();
				}
			}
		}
		else
			beneficialWordsFizzle(mob,target,L("<S-NAME> hold(s) <T-NAMESELF> tightly and whisper(s), but fail(s) to cast a spell."));

		// return whether it worked
		return success;
	}
}
