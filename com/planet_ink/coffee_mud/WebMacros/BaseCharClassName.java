package com.planet_ink.coffee_mud.WebMacros;

import com.planet_ink.coffee_web.interfaces.*;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.collections.*;
import com.planet_ink.coffee_mud.Abilities.interfaces.*;
import com.planet_ink.coffee_mud.Areas.interfaces.*;
import com.planet_ink.coffee_mud.Behaviors.interfaces.*;
import com.planet_ink.coffee_mud.CharClasses.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.*;
import com.planet_ink.coffee_mud.Common.interfaces.*;
import com.planet_ink.coffee_mud.Exits.interfaces.*;
import com.planet_ink.coffee_mud.Items.interfaces.*;
import com.planet_ink.coffee_mud.Locales.interfaces.*;
import com.planet_ink.coffee_mud.MOBS.interfaces.*;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.util.*;

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
public class BaseCharClassName extends StdWebMacro
{
	@Override
	public String name()
	{
		return "BaseCharClassName";
	}

	@Override
	public String runMacro(final HTTPRequest httpReq, final String parm, final HTTPResponse httpResp)
	{
		final String last=httpReq.getUrlParameter("BASECLASS");
		if(last==null)
			return " @break@";
		if(last.length()>0)
		{
			final java.util.Map<String,String> parms=parseParms(parm);
			CharClass C=CMClass.getCharClass(last);
			if(C!=null)
			{
				if(parms.containsKey("PLURAL"))
					return clearWebMacros(CMLib.english().makePlural(C.name()));
				else
					return clearWebMacros(C.name());
			}
			for(final Enumeration<CharClass> e=CMClass.charClasses();e.hasMoreElements();)
			{
				C=e.nextElement();
				if(C.baseClass().equalsIgnoreCase(last))
				{
					if(parms.containsKey("PLURAL"))
						return clearWebMacros(CMLib.english().makePlural(C.baseClass()));
					else
						return clearWebMacros(C.baseClass());
				}
			}
		}
		return "";
	}
}
