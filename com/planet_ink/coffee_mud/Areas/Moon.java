package com.planet_ink.coffee_mud.Areas;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.core.interfaces.BoundedObject.BoundedCube;
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
   Copyright 2014-2023 Bo Zimmerman

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
public class Moon extends StdThinPlanet
{
	@Override
	public String ID()
	{
		return "Moon";
	}

	public Moon()
	{
		super();

		coordinates=new long[]{Math.round(Long.MAX_VALUE*Math.random()),Math.round(Long.MAX_VALUE*Math.random()),Math.round(Long.MAX_VALUE*Math.random())};
		final Random random=new Random(System.currentTimeMillis());
		radius=SpaceObject.Distance.MoonRadius.dm + (random.nextLong() % Math.round(CMath.mul(SpaceObject.Distance.MoonRadius.dm,0.20)));
		//TODO: need a behavior or something that "fills it out" first time it's hit.
	}

	@Override
	public String genericName()
	{
		if(radius >= SpaceObject.Distance.SaturnRadius.dm)
			return L("a gargatuan moon");
		else
		if(radius >= SpaceObject.Distance.PlanetRadius.dm)
			return L("an enormous moon");
		else
		if(radius >= SpaceObject.Distance.PlanetRadius.dm/2)
			return L("a large moon");
		else
		if(radius <= SpaceObject.Distance.MoonRadius.dm/2)
			return L("a small moon");
		else
			return L("a moon");
	}
}
