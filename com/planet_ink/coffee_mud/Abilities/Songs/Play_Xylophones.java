package com.planet_ink.coffee_mud.Abilities.Songs;
import com.planet_ink.coffee_mud.interfaces.*;
import com.planet_ink.coffee_mud.common.*;
import com.planet_ink.coffee_mud.utils.*;
import java.util.*;

public class Play_Xylophones extends Play_Instrument
{
	public String ID() { return "Play_Xylophones"; }
	public String name(){ return "Xylophones";}
	protected int requiredInstrumentType(){return MusicalInstrument.TYPE_XYLOPHONES;}
	public Environmental newInstance(){	return new Play_Xylophones();}
	public String mimicSpell(){return "Spell_Slow";}

}
