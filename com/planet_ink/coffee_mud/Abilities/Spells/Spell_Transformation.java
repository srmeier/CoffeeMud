package com.planet_ink.coffee_mud.Abilities.Spells;

import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Spell_Transformation extends Spell
{
	public String ID() { return "Spell_Transformation"; }
	public String name(){return "Transformation";}
	public String displayText(){return "(Transformation)";}
	public int quality(){return BENEFICIAL_SELF;};
	public Environmental newInstance(){	return new Spell_Transformation();}
	public int classificationCode(){return Ability.SPELL|Ability.DOMAIN_TRANSMUTATION;}
	private int inc=0;

	public void unInvoke()
	{
		MOB mob=(MOB)affected;
		super.unInvoke();
		if((canBeUninvoked())&&(mob!=null))
			mob.tell("You no longer seem as brutish.");
	}

	public void affectEnvStats(Environmental affected, EnvStats affectableStats)
	{
		super.affectEnvStats(affected,affectableStats);
		affectableStats.setDamage(affectableStats.damage()+affected.envStats().level());
	}
	public void affectCharStats(MOB affected, CharStats affectableStats)
	{
		super.affectCharStats(affected,affectableStats);
		affectableStats.setStat(CharStats.DEXTERITY,affectableStats.getStat(CharStats.DEXTERITY)+inc);
		affectableStats.setStat(CharStats.STRENGTH,affectableStats.getStat(CharStats.STRENGTH)+inc);
		affectableStats.setStat(CharStats.INTELLIGENCE,3);
	}
	
	public boolean invoke(MOB mob, Vector commands, Environmental givenTarget, boolean auto)
	{
		if(!super.invoke(mob,commands,givenTarget,auto))
			return false;

		boolean success=profficiencyCheck(0,auto);

		if(success)
		{
			invoker=mob;
			FullMsg msg=new FullMsg(mob,null,this,affectType(auto),auto?"":"^S<S-NAME> encant(s), transforming into a large brutish warrior!^?");
			if(mob.location().okAffect(msg))
			{
				mob.location().send(mob,msg);
				inc=Dice.roll(2,4,0);
				beneficialAffect(mob,mob,0);
			}
		}
		else
			return beneficialWordsFizzle(mob,null,"<S-NAME> encant(s), but fizzle(s) the spell.");

		// return whether it worked
		return success;
	}

}
