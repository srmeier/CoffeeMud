package com.planet_ink.coffee_mud.Abilities.Thief;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Commands.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;



import java.util.*;

/* 
   Copyright 2000-2006 Bo Zimmerman

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
public class Thief_SenseLaw extends ThiefSkill
{
	public String ID() { return "Thief_SenseLaw"; }
	public String name(){ return "Sense Law";}
	public String displayText(){ return "";}
	protected int canAffectCode(){return CAN_MOBS;}
	protected int canTargetCode(){return 0;}
	public int abstractQuality(){return Ability.OK_SELF;}
	public boolean isAutoInvoked(){return true;}
	public boolean canBeUninvoked(){return false;}
	public static final Vector empty=new Vector();
	protected Room oldroom=null;
	protected String lastReport="";

	public Vector getLawMen(Area legalObject, Room room, LegalBehavior B)
	{
		if(room==null) return empty;
		if(room.numInhabitants()==0) return empty;
		if(B==null) return empty;
		Vector V=new Vector();
		for(int m=0;m<room.numInhabitants();m++)
		{
			MOB M=room.fetchInhabitant(m);
			if((M!=null)&&(M.isMonster())&&(B.isElligibleOfficer(legalObject,M)))
				V.addElement(M);
		}
		return V;
	}

	public boolean tick(Tickable ticking, int tickID)
	{
		if((affected!=null)&&(affected instanceof MOB))
		{
			MOB mob=(MOB)affected;
			if((mob.location()!=null)&&(!mob.isMonster()))
			{
                LegalBehavior B=CMLib.utensils().getLegalBehavior(mob.location());
				if(B==null)
					return super.tick(ticking,tickID);
				StringBuffer buf=new StringBuffer("");
				Vector V=getLawMen(CMLib.utensils().getLegalObject(mob.location()),mob.location(),B);
				for(int l=0;l<V.size();l++)
				{
					MOB M=(MOB)V.elementAt(l);
					if(CMLib.flags().canBeSeenBy(M,mob))
						buf.append(M.name()+" is an officer of the law.  ");
					else
						buf.append("There is an officer of the law here.  ");
				}
				for(int d=0;d<Directions.NUM_DIRECTIONS;d++)
				{
					Room R=mob.location().getRoomInDir(d);
					Exit E=mob.location().getExitInDir(d);
					if((R!=null)&&(E!=null)&&(E.isOpen()))
					{
						V=getLawMen(mob.location().getArea(),R,B);
						if((V!=null)&&(V.size()>0))
							buf.append("There is an officer of the law "+Directions.getInDirectionName(d)+".  ");
					}
				}
				if((buf.length()>0)
				&&((mob.location()!=oldroom)||(!buf.toString().equals(lastReport)))
				&&((mob.fetchAbility(ID())==null)||profficiencyCheck(mob,0,false)))
				{
					mob.tell("You sense: "+buf.toString());
					oldroom=mob.location();
					helpProfficiency(mob);
					lastReport=buf.toString();
				}
			}
		}
		return super.tick(ticking,tickID);
	}

	public boolean autoInvocation(MOB mob)
	{
		if(mob.charStats().getCurrentClass().ID().equals("Archon"))
			return false;
		return super.autoInvocation(mob);
	}
}
