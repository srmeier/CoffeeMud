package com.planet_ink.coffee_mud.Commands;
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
   Copyright 2000-2009 Bo Zimmerman

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
@SuppressWarnings("unchecked")
public class Fire extends StdCommand
{
	public Fire(){}

	private String[] access={"FIRE"};
	public String[] getAccessWords(){return access;}
	public boolean execute(MOB mob, Vector commands, int metaFlags)
		throws java.io.IOException
	{
		String rest="ALL";
		if(commands.size()>1) rest=CMParms.combine(commands,1);

		Environmental target=mob.location().fetchFromRoomFavorMOBs(null,rest,Item.WORNREQ_ANY);
		if((target!=null)&&(!target.name().equalsIgnoreCase(rest))&&(rest.length()<4))
		   target=null;
		if((target!=null)&&(!CMLib.flags().canBeSeenBy(target,mob)))
			target=null;
		if(target==null)
			mob.tell("Fire whom?");
		else
		{
			CMMsg msg=CMClass.getMsg(mob,target,null,CMMsg.MSG_SPEAK,"^T<S-NAME> say(s) to <T-NAMESELF> 'You are fired!'^?");
			if(mob.location().okMessage(mob,msg))
				mob.location().send(mob,msg);
		}
		return false;
	}
    public double combatActionsCost(MOB mob, Vector cmds){return CMath.div(CMProps.getIntVar(CMProps.SYSTEMI_DEFCOMCMDTIME),100.0);}
    public double actionsCost(MOB mob, Vector cmds){return CMath.div(CMProps.getIntVar(CMProps.SYSTEMI_DEFCMDTIME),100.0);}
	public boolean canBeOrdered(){return true;}

	
}
