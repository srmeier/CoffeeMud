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
import com.planet_ink.coffee_mud.Libraries.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

/*
   Copyright 2002-2023 Bo Zimmerman

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
public class Spell_MassHaste extends Spell
{

	@Override
	public String ID()
	{
		return "Spell_MassHaste";
	}

	private final static String localizedName = CMLib.lang().L("Mass Haste");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	public String displayText()
	{
		return "";
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_BENEFICIAL_OTHERS;
	}

	@Override
	protected int canAffectCode()
	{
		return 0;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SPELL|Ability.DOMAIN_ALTERATION;
	}

	@Override
	public int castingQuality(final MOB mob, final Physical target)
	{
		if(mob!=null)
		{
			if(target instanceof MOB)
			{
				if((((MOB)target).isInCombat())&&(((MOB)target).getGroupMembers(new HashSet<MOB>()).size()<2))
					return Ability.QUALITY_INDIFFERENT;
				if(((MOB)target).fetchEffect("Spell_Haste")!=null)
					return Ability.QUALITY_INDIFFERENT;
			}
		}
		return super.castingQuality(mob,target);
	}

	@Override
	public boolean invoke(final MOB mob, final List<String> commands, final Physical givenTarget, final boolean auto, final int asLevel)
	{
		final Set<MOB> h=properTargets(mob,givenTarget,false);
		if((h==null)||(h.size()==0))
		{
			mob.tell(L("There doesn't appear to be anyone here worth speeding up."));
			return false;
		}

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		final boolean success=proficiencyCheck(mob,0,auto);

		if(success)
		{
			if(mob.location().show(mob,null,this,somanticCastCode(mob,null,auto),auto?"":L("^S<S-NAME> wave(s) <S-HIS-HER> arms and speak(s) quickly.^?")))
			{
				for (final Object element : h)
				{
					final MOB target=(MOB)element;

					final CMMsg msg=CMClass.getMsg(mob,target,this,somanticCastCode(mob,target,auto),null);
					if((mob.location().okMessage(mob,msg))
					   &&(target.fetchEffect("Spell_Haste")==null)
					   &&(target.fetchEffect("Spell_MassHaste")==null))
					{
						mob.location().send(mob,msg);
						mob.location().show(target,null,CMMsg.MSG_OK_VISUAL,L("<S-NAME> speed(s) up!"));
						final Spell_Haste haste=new Spell_Haste();
						haste.setProficiency(proficiency());
						haste.beneficialAffect(mob,target,asLevel,0);
					}
				}
			}
		}
		else
			return beneficialVisualFizzle(mob,null,L("<S-NAME> wave(s) <S-HIS-HER> arms and speak(s) quickly, but the spell fizzles."));

		// return whether it worked
		return success;
	}
}
