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

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/*
   Copyright 2016-2016 Bo Zimmerman

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

public class Spell_Planeshift extends Spell
{
	@Override
	public String ID()
	{
		return "Spell_Planeshift";
	}

	private final static String	localizedName	= CMLib.lang().L("Planeshift");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_SPELL | Ability.DOMAIN_CONJURATION;
	}

	@Override
	public long flags()
	{
		return 0;
	}

	@Override
	protected int overrideMana()
	{
		return Ability.COST_ALL - 90;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_INDIFFERENT;
	}

	protected WeakReference<Room>			oldRoom			= null;
	protected Area							planeArea		= null;
	protected Map<String, String>			planeVars		= null;
	protected WeakArrayList<Room>			roomsDone		= new WeakArrayList<Room>();
	protected int							planarLevel		= 1;
	protected String						planarPrefix	= null;
	protected List<Pair<String, String>>	behavList		= null;
	protected int							bonusDmgStat	= -1;
	protected Set<String>					reqWeapons		= null;
	protected Pair<Pair<Integer,Integer>,List<Pair<String,String>>> enableList=null;

	protected enum PlanarVar
	{
		ID,
		TRANSITIONAL,
		ALIGNMENT,
		PREFIX,
		LEVELADJ,
		MOBRESIST,
		SETSTAT,
		BEHAVAFFID,
		ADJSTAT,
		ADJSIZE,
		ADJUST,
		MOBCOPY,
		BEHAVE,
		ENABLE,
		WEAPONMAXRANGE,
		BONUSDAMAGESTAT,
		REQWEAPONS,
		ATMOSPHERE,
		AREABLURBS
	}
	
	protected static final AtomicInteger planeIDNum = new AtomicInteger(0);

	public void clearVars()
	{
		planeArea = null;
		planarLevel=1;
		roomsDone=new WeakArrayList<Room>();
		planeVars=null;
		planarPrefix=null;
		this.behavList=null;
		this.enableList=null;
		bonusDmgStat=-1;
		this.reqWeapons=null;
	}

	@Override
	public void setMiscText(String newText)
	{
		super.setMiscText(newText);
		clearVars();
		if(newText.length()>0)
		{
			this.planeVars=getPlane(newText);
			this.roomsDone=new WeakArrayList<Room>();
			this.planarPrefix=planeVars.get(PlanarVar.PREFIX.toString());
			if((planarPrefix!=null)&&(planarPrefix.indexOf(',')>0))
			{
				List<String> choices=CMParms.parseCommas(planarPrefix, true);
				planarPrefix=choices.get(CMLib.dice().roll(1, choices.size(), -1));
			}
			if(affected instanceof Area)
			{
				planeArea=(Area)affected;
				int medianLevel=planeArea.getAreaIStats()[Area.Stats.MED_LEVEL.ordinal()];
				planarLevel=medianLevel;
			}
			String areablurbs = planeVars.get(Spell_Planeshift.PlanarVar.AREABLURBS.toString());
			if((areablurbs!=null)&&(areablurbs.length()>0))
			{
				Map<String,String> blurbSets=CMParms.parseEQParms(areablurbs);
				for(String key : blurbSets.keySet())
					planeArea.addBlurbFlag(key.toUpperCase().trim().replace(' ', '_')+" "+blurbSets.get(key));
			}
			String behaves = planeVars.get(Spell_Planeshift.PlanarVar.BEHAVE.toString());
			if(behaves!=null)
				this.behavList=CMParms.parseSpaceParenList(behaves);
			String enables = planeVars.get(Spell_Planeshift.PlanarVar.ENABLE.toString());
			if(enables!=null)
			{
				List<Pair<String,String>> enableAs=CMParms.parseSpaceParenList(enables);
				Integer perLevel=new Integer(CMProps.getIntVar(CMProps.Int.LASTPLAYERLEVEL));
				Integer numSkills=Integer.valueOf(Integer.MAX_VALUE);
				for(Iterator<Pair<String,String>> p=enableAs.iterator();p.hasNext();)
				{
					Pair<String,String> P=p.next();
					if(P.first.toLowerCase().equals("number"))
					{
						p.remove();
						String parms=P.second;
						int x=parms.indexOf('/');
						if(x<0)
							numSkills=Integer.valueOf(CMath.s_int(parms.trim()));
						else
						{
							numSkills=Integer.valueOf(CMath.s_int(parms.substring(0,x).trim()));
							perLevel=Integer.valueOf(CMath.s_int(parms.substring(x+1).trim()));
						}
					}
				}
				if(enableAs.size()>0)
				{
					PairList<String,String> addThese = new PairVector<String,String>();
					for(Iterator<Pair<String,String>> p = enableAs.iterator();p.hasNext();)
					{
						Pair<String,String> P=p.next();
						Ability A=CMClass.getAbility(P.first);
						if(A==null)
						{
							p.remove();
							boolean foundSomething=false;
							long flag=CMParms.indexOf(Ability.FLAG_DESCS, P.first);
							if(flag >=0 )
								flag=CMath.pow(2, flag);
							int domain=CMParms.indexOf(Ability.DOMAIN_DESCS, P.first);
							if(domain > 0)
								domain = domain << 5;
							int acode=CMParms.indexOf(Ability.ACODE_DESCS, P.first);
							for(Enumeration<Ability> a=CMClass.abilities();a.hasMoreElements();)
							{
								A=a.nextElement();
								if((A.Name().toUpperCase().equals(P.first))
								||((flag>0)&&(CMath.bset(A.flags(),flag)))
								||((domain>0)&&(A.classificationCode()&Ability.ALL_DOMAINS)==domain)
								||((acode>=0)&&(A.classificationCode()&Ability.ALL_ACODES)==acode)
								)
								{
									if(!addThese.containsFirst(A.ID().toUpperCase()))
									{
										addThese.add(A.ID().toUpperCase(), P.second);
										foundSomething=true;
									}
								}
							}
							if(!foundSomething)
								Log.errOut("Spell_Planeshift","Unknown skill type/domain/flag: "+P.first);
						}
					}
					enableAs.addAll(addThese);
					if(enableAs.size()>0)
						this.enableList=new Pair<Pair<Integer,Integer>,List<Pair<String,String>>>(new Pair<Integer,Integer>(numSkills,perLevel),enableAs);
				}
			}
			String bonusDamageStat = planeVars.get(Spell_Planeshift.PlanarVar.BONUSDAMAGESTAT.toString());
			if(bonusDamageStat!=null)
			{
				this.bonusDmgStat=CMParms.indexOf(CharStats.CODES.BASENAMES(), bonusDamageStat.toUpperCase().trim());
			}
			String reqWeapons = planeVars.get(Spell_Planeshift.PlanarVar.REQWEAPONS.toString());
			if(reqWeapons != null)
				this.reqWeapons = new HashSet<String>(CMParms.parse(reqWeapons.toUpperCase().trim()));
			String atmosphere = planeVars.get(Spell_Planeshift.PlanarVar.ATMOSPHERE.toString());
			if(atmosphere!=null)
			{
				if(atmosphere.length()==0)
					this.planeArea.setAtmosphere(Integer.MIN_VALUE);
				else
				{
					int atmo=RawMaterial.CODES.FIND_IgnoreCase(atmosphere);
					this.planeArea.setAtmosphere(atmo);
				}
			}
		}
	}
	
	protected void reEffect(MOB M, String ID, String parms)
	{
		Ability A=M.fetchEffect(ID);
		if(A!=null)
			M.delEffect(A);
		else
			A=CMClass.getAbility(ID);
		if(A!=null)
		{
			M.addNonUninvokableEffect(A);
			A.setMiscText((parms+" "+A.text()).trim());
		}
	}

	public synchronized void fixRoom(Room room)
	{
		try
		{
			room.toggleMobility(false);
			for(int i=0;i<Directions.NUM_DIRECTIONS();i++)
			{
				Room R=room.rawDoors()[i];
				if((R!=null)&&(R.getArea()!=planeArea))
					room.rawDoors()[i]=null;
			}
			if(planeVars.containsKey(Spell_Planeshift.PlanarVar.ATMOSPHERE.toString()))
				room.setAtmosphere(planeArea.getAtmosphere());
			int allLevelAdj=CMath.s_int(planeVars.get(PlanarVar.LEVELADJ.toString()));
			List<Item> delItems=new ArrayList<Item>(0);
			for(Enumeration<Item> i=room.items();i.hasMoreElements();)
			{
				Item I=i.nextElement();
				if(I==null)
					continue;
				if((I instanceof Exit)&&((I instanceof BoardableShip)))
				{
					for(int x=0;x<100;x++)
					{
						Room R2=((Exit)I).lastRoomUsedFrom(room);
						if((R2!=null)&&(R2.getArea()!=planeArea))
						{
							delItems.add(I);
							break;
						}
					}
				}
				else
				if((I instanceof Weapon)||(I instanceof Armor))
				{
					int newILevelAdj = (planarLevel - I.phyStats().level());
					int newILevel=invoker.phyStats().level() + newILevelAdj + allLevelAdj;
					if(newILevel <= 0)
						newILevel = 1;
					I.basePhyStats().setLevel(newILevel);
					I.phyStats().setLevel(newILevel);
					CMLib.itemBuilder().balanceItemByLevel(I);
					if((I instanceof Weapon)
					&&(this.reqWeapons!=null)
					&&(this.reqWeapons.contains("MAGICAL")))
					{
						I.basePhyStats().setDisposition(I.basePhyStats().disposition()|PhyStats.IS_BONUS);
						I.phyStats().setDisposition(I.phyStats().disposition()|PhyStats.IS_BONUS);
					}
					I.text();
				}
			}
			for(Item I : delItems)
				I.destroy();
			for(Enumeration<MOB> m=room.inhabitants();m.hasMoreElements();)
			{
				MOB M=m.nextElement();
				if((M!=null)
				&&(M.isMonster())
				&&(M.getStartRoom()!=null)
				&&(M.getStartRoom().getArea()==planeArea))
				{
					if(planeVars.containsKey(Spell_Planeshift.PlanarVar.ATMOSPHERE.toString()))
						M.baseCharStats().setBreathables(new int[]{room.getAtmosphere()});
					int newLevelAdj = (planarLevel - M.phyStats().level());
					int newLevel = invoker.phyStats().level() + newLevelAdj + allLevelAdj;
					if(newLevel <= 0)
						newLevel = 1;
					if((planarPrefix!=null)&&(planarPrefix.length()>0))
					{
						String oldName=M.Name();
						int x;
						if(oldName.toLowerCase().indexOf(planarPrefix.toLowerCase())<0)
						{
							if(CMLib.english().startsWithAnArticle(M.Name()))
							{
								String Name = M.Name().substring(M.Name().indexOf(' ')).trim();
								M.setName(CMLib.english().startWithAorAn(planarPrefix+" "+Name));
							}
							else
							{
								M.setName(CMStrings.capitalizeFirstLetter(planarPrefix)+" "+M.Name());
							}
							if((x=M.displayText().toLowerCase().indexOf(oldName.toLowerCase()))>=0)
							{
								M.setDisplayText(M.displayText().substring(0,x)+M.Name()+M.displayText().substring(x+oldName.length()));
							}
							else
							if(CMLib.english().startsWithAnArticle(M.displayText()))
							{
								String Name = M.displayText().substring(M.displayText().indexOf(' ')).trim();
								M.setDisplayText(CMLib.english().startWithAorAn(planarPrefix+" "+Name));
							}
							else
							if((x=M.displayText().toLowerCase().indexOf(M.charStats().getMyRace().name().toLowerCase()))>=0)
							{
								int len=M.charStats().getMyRace().name().toLowerCase().length();
								M.setDisplayText(M.displayText().substring(0,x)+planarPrefix+M.Name()+M.displayText().substring(x+len));
							}
						}
					}
					M.basePhyStats().setLevel(newLevel);
					M.phyStats().setLevel(newLevel);
					CMLib.leveler().fillOutMOB(M,M.basePhyStats().level());
					String align=planeVars.get(Spell_Planeshift.PlanarVar.ALIGNMENT.toString());
					if(align!=null)
					{
						M.removeFaction(CMLib.factions().AlignID());
						M.addFaction(CMLib.factions().AlignID(), CMath.s_int(align));
					}
					for(Enumeration<Item> mi=M.items();mi.hasMoreElements();)
					{
						Item mI=mi.nextElement();
						if(mI!=null)
						{
							int newILevelAdj = (planarLevel - mI.phyStats().level());
							int newILevel=invoker.phyStats().level() + newILevelAdj + allLevelAdj;
							if(newILevel <= 0)
								newILevel = 1;
							mI.basePhyStats().setLevel(newILevel);
							mI.phyStats().setLevel(newILevel);
							CMLib.itemBuilder().balanceItemByLevel(mI);
							if((mI instanceof Weapon)
							&&(this.reqWeapons!=null)
							&&(this.reqWeapons.contains("MAGICAL")))
							{
								mI.basePhyStats().setDisposition(mI.basePhyStats().disposition()|PhyStats.IS_BONUS);
								mI.phyStats().setDisposition(mI.phyStats().disposition()|PhyStats.IS_BONUS);
							}
							mI.text();
						}
					}
					String resistWeak = planeVars.get(Spell_Planeshift.PlanarVar.MOBRESIST.toString());
					if(resistWeak != null)
						reEffect(M,"Prop_Resistance",resistWeak);
					String setStat = planeVars.get(Spell_Planeshift.PlanarVar.SETSTAT.toString());
					if(setStat != null)
						reEffect(M,"Prop_StatTrainer",setStat);
					String behavaffid=planeVars.get(Spell_Planeshift.PlanarVar.BEHAVAFFID.toString());
					if(behavaffid!=null)
					{
						String changeToID;
						for(Enumeration<Behavior> b=M.behaviors();b.hasMoreElements();)
						{
							Behavior B=b.nextElement();
							if((B!=null)&&((changeToID=CMParms.getParmStr(behavaffid, B.ID(), "")).length()>0))
							{
								boolean copyParms=false;
								if(changeToID.startsWith("*"))
								{
									copyParms=true;
									changeToID=changeToID.substring(1);
								}
								M.delBehavior(B);
								Behavior B2=CMClass.getBehavior(changeToID);
								if(B2 != null)
								{
									if(copyParms)
										B2.setParms(B.getParms());
									M.addBehavior(B2);
								}
							}
						}
					}
					String adjStat = planeVars.get(Spell_Planeshift.PlanarVar.ADJSTAT.toString());
					if(adjStat != null)
						reEffect(M,"Prop_StatAdjuster",adjStat);
					String adjust = planeVars.get(Spell_Planeshift.PlanarVar.ADJUST.toString());
					if(adjust != null)
						reEffect(M,"Prop_Adjuster",adjust);
					String adjSize = planeVars.get(Spell_Planeshift.PlanarVar.ADJSIZE.toString());
					if(adjSize != null)
					{
						double heightAdj = CMParms.getParmDouble(adjSize, "HEIGHT", Double.MIN_VALUE);
						if(heightAdj > Double.MIN_VALUE)
							reEffect(M,"Prop_Adjuster","height+"+(int)Math.round(CMath.mul(M.basePhyStats().height(),heightAdj)));
						double weightAdj = CMParms.getParmDouble(adjSize, "WEIGHT", Double.MIN_VALUE);
						if(weightAdj > Double.MIN_VALUE)
							reEffect(M,"Prop_StatAdjuster","weightadj="+(int)Math.round(CMath.mul(M.baseWeight(),weightAdj)));
					}
					if(this.behavList!=null)
					{
						for(Pair<String,String> p : this.behavList)
						{
							if(M.fetchBehavior(p.first)==null)
							{
								Behavior B=CMClass.getBehavior(p.first);
								if(B==null)
									Log.errOut("Spell_Planeshift","Unknown behavior : "+p.first);
								else
								{
									B.setParms(p.second);
									M.addBehavior(B);
								}
							}
						}
					}
					if(this.enableList != null)
					{
						Pair<Integer,Integer> lv=this.enableList.first;
						PairList<String,String> unused = new PairVector<String,String>(this.enableList.second);
						for(int l=0;l<M.phyStats().level() && (unused.size()>0);l+=lv.second.intValue())
						{
							for(int a=0;a<lv.first.intValue()  && (unused.size()>0);a++)
							{
								int aindex=CMLib.dice().roll(1, unused.size(), -1);
								Pair<String,String> P=unused.remove(aindex);
								Ability A=CMClass.getAbility(P.first);
								if(M.fetchAbility(A.ID())==null)
								{
									A.setMiscText(P.second);
									M.addAbility(A);
								}
							}
						}
					}
					M.text();
				}
			}
			int mobCopy=CMath.s_int(planeVars.get(Spell_Planeshift.PlanarVar.MOBCOPY.toString()));
			if(mobCopy>0)
			{
				final List<MOB> list=new ArrayList<MOB>(room.numInhabitants());
				for(Enumeration<MOB> m=room.inhabitants();m.hasMoreElements();)
				{
					final MOB M=m.nextElement();
					if((M!=null)
					&&(M.isMonster())
					&&(M.getStartRoom()!=null)
					&&(M.getStartRoom().getArea()==planeArea))
					{
						list.add(M);
					}
				}
				for(final MOB M : list)
				{
					for(int i=0;i<mobCopy;i++)
					{
						MOB M2=(MOB)M.copyOf();
						M2.text();
						M2.setSavable(M.isSavable());
						M2.bringToLife(room, true);
					}
				}
			}
		}
		catch(Exception e)
		{
			Log.errOut(e);
		}
		finally
		{
			room.toggleMobility(true);
		}
	}

	@Override
	public void affectPhyStats(Physical affected, PhyStats affectableStats)
	{
		super.affectPhyStats(affected, affectableStats);
		if((this.bonusDmgStat>=0)&&(affected instanceof MOB))
			affectableStats.setDamage(affectableStats.damage() + (((((MOB)affected).charStats().getStat(this.bonusDmgStat))-10)/2));
	}
	
	@Override
	public boolean okMessage(Environmental myHost, CMMsg msg)
	{
		if(!super.okMessage(myHost, msg))
			return false;
		switch(msg.targetMinor())
		{
		case CMMsg.TYP_LOOK:
		case CMMsg.TYP_EXAMINE:
			if((msg.target() instanceof Room)
			&&(this.roomsDone!=null)
			&&(!this.roomsDone.contains(msg.target()))
			&&(((Room)msg.target()).getArea()==planeArea))
			{
				fixRoom((Room)msg.target());
				this.roomsDone.add((Room)msg.target());
			}
			break;
		case CMMsg.TYP_WEAPONATTACK:
			if((msg.tool() instanceof AmmunitionWeapon)
			&&(((AmmunitionWeapon)msg.tool()).requiresAmmunition())
			&&(msg.target() instanceof MOB)
			&&(planeVars!=null)
			&&(planeVars.containsKey(Spell_Planeshift.PlanarVar.WEAPONMAXRANGE.toString()))
			&&((msg.source().rangeToTarget()>0)||(((MOB)msg.target()).rangeToTarget()>0)))
			{
				int maxRange=CMath.s_int(planeVars.get(Spell_Planeshift.PlanarVar.WEAPONMAXRANGE.toString()));
				if(((msg.source().rangeToTarget()>maxRange)||(((MOB)msg.target()).rangeToTarget()>maxRange)))
				{
					final String ammo=((AmmunitionWeapon)msg.tool()).ammunitionType();
					final String msgOut=L("The @x1 fired by <S-NAME> from <O-NAME> at <T-NAME> stops moving!",ammo);
					final Room R=msg.source().location();
					if(R!=null)
						R.show(msg.source(), msg.target(), msg.tool(), CMMsg.MSG_OK_VISUAL, msgOut);
					return false;
				}
			}
			break;
		case CMMsg.TYP_DAMAGE:
			if((msg.tool() instanceof Weapon)
			&&(this.reqWeapons!=null)
			&&(msg.value()>0))
			{
				if((CMLib.flags().isABonusItems((Weapon)msg.tool()) && (this.reqWeapons.contains("MAGICAL")))
				||(this.reqWeapons.contains(Weapon.CLASS_DESCS[((Weapon)msg.tool()).weaponClassification()]))
				||(this.reqWeapons.contains(Weapon.TYPE_DESCS[((Weapon)msg.tool()).weaponDamageType()])))
				{ // pass
				}
				else
					msg.setValue(0);
			}
			break;
		}
		return true;
	}
	
	protected static List<String> getAllPlaneKeys()
	{
		Map<String,Map<String,String>> map = getPlaneMap();
		List<String> transitions=new ArrayList<String>(map.size());
		for(String key : map.keySet())
			transitions.add(key);
		return transitions;
	}
	
	protected static List<String> getTransitionPlaneKeys()
	{
		Map<String,Map<String,String>> map = getPlaneMap();
		List<String> transitions=new ArrayList<String>(2);
		for(String key : map.keySet())
		{
			Map<String,String> entry=map.get(key);
			if(CMath.s_bool(entry.get(PlanarVar.TRANSITIONAL.toString())))
				transitions.add(key);
		}
		return transitions;
	}
	
	protected static String listOfPlanes()
	{
		Map<String,Map<String,String>> map = getPlaneMap();
		StringBuilder str=new StringBuilder();
		for(String key : map.keySet())
		{
			Map<String,String> entry=map.get(key);
			str.append(entry.get(PlanarVar.ID.toString())).append(", ");
		}
		if(str.length()<2)
			return "";
		return str.toString().substring(0,str.length()-2);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected static Map<String,Map<String,String>> getPlaneMap()
	{
		Map<String,Map<String,String>> map = (Map)Resources.getResource("SKILL_PLANES_OF_EXISTENCE");
		if(map == null)
		{
			map = new TreeMap<String,Map<String,String>>();
			CMFile F=new CMFile(Resources.makeFileResourceName("skills/planesofexistence.txt"),null);
			List<String> lines = Resources.getFileLineVector(F.text());
			for(String line : lines)
			{
				line=line.trim();
				String planename=null;
				if(line.startsWith("\""))
				{
					int x=line.indexOf("\"",1);
					if(x>1)
					{
						planename=line.substring(1,x);
						line=line.substring(x+1).trim();
					}
				}
				if(planename != null)
				{
					Map<String,String> planeParms = CMParms.parseEQParms(line);
					planeParms.put(PlanarVar.ID.toString(), planename);
					map.put(planename.toUpperCase(), planeParms);
				}
			}
			Resources.submitResource("SKILL_PLANES_OF_EXISTENCE", map);
		}
		return map;
	}
	
	protected static Map<String,String> getPlane(String name)
	{
		Map<String,Map<String,String>> map = getPlaneMap();
		name=name.trim().toUpperCase();
		if(map.containsKey(name))
			return map.get(name);
		for(String key : map.keySet())
		{
			if(key.startsWith(name))
				return map.get(key);
		}
		for(String key : map.keySet())
		{
			if(key.indexOf(name)>=0)
				return map.get(key);
		}
		for(String key : map.keySet())
		{
			if(key.endsWith(name))
				return map.get(key);
		}
		return null;
	}
	
	protected void destroyPlane(Area planeA)
	{
		if(planeA != null)
		{
			for(Enumeration<Room> r=planeA.getProperMap();r.hasMoreElements();)
			{
				final Room R=r.nextElement();
				if(R!=null)
				{
					if(R.numInhabitants()>0)
						R.showHappens(CMMsg.MSG_OK_ACTION, L("This plane is fading away..."));
					for(Enumeration<MOB> i=R.inhabitants();i.hasMoreElements();)
					{
						MOB M=i.nextElement();
						if((M!=null)&&(M.isPlayer()))
						{
							Room oldRoom = (this.oldRoom!=null) ? CMLib.map().getRoom(this.oldRoom.get()) : null;
							if((oldRoom==null)||(oldRoom.amDestroyed())||(oldRoom.getArea()==null)||(!oldRoom.getArea().isRoom(oldRoom)))
								oldRoom=M.getStartRoom();
							oldRoom.bringMobHere(M, true);
						}
					}
				}
			}
			planeA.destroy();
		}
	}
	
	protected void destroyPlane()
	{
		destroyPlane(planeArea);
		this.planeArea=null;
	}
	
	protected String getStrippedRoomID(String roomID)
	{
		final int x=roomID.indexOf('#');
		if(x<0)
			return null;
		return roomID.substring(x);
	}

	protected String convertToMyArea(String Name, String roomID)
	{
		final String strippedID=getStrippedRoomID(roomID);
		if(strippedID==null)
			return null;
		return Name+strippedID;
	}
	
	@Override
	public void unInvoke()
	{
		if(canBeUninvoked())
		{
			destroyPlane();
		}
		super.unInvoke();
	}

	@Override
	public boolean invoke(MOB mob, List<String> commands, Physical givenTarget, boolean auto, int asLevel)
	{
		oldRoom = null;
		clearVars();
		
		if(commands.size()<1)
		{
			mob.tell(L("Planeshift to where?"));
			mob.tell(L("Known planes: @x1",Spell_Planeshift.listOfPlanes())); 
			return false;
		}
		String planeName=CMParms.combine(commands,0).trim().toUpperCase();
		oldRoom=new WeakReference<Room>(mob.location());
		Map<String,String> planeFound = getPlane(planeName);
		if(planeFound == null)
		{
			mob.tell(L("There is no known plane '@x1'.",planeName));
			mob.tell(L("Known planes: @x1",Spell_Planeshift.listOfPlanes())); 
			return false;
		}
		planeName = planeFound.get(Spell_Planeshift.PlanarVar.ID.toString()).toUpperCase().trim();

		if(!super.invoke(mob,commands,givenTarget,auto,asLevel))
			return false;

		Area cloneArea = mob.location().getArea();
		String cloneRoomID=CMLib.map().getExtendedRoomID(mob.location());
		final boolean success=proficiencyCheck(mob,0,auto);
		
		boolean randomPlane=false;
		boolean randomTransitionPlane=false;
		boolean randomArea=false;
		if(!success)
		{
			if(CMLib.dice().rollPercentage()>5)
			{
				this.beneficialVisualFizzle(mob, null, L("^S<S-NAME> attempt(s) to conjur a powerful planar connection, and fails."));
				return false;
			}
			else
			{
				if(proficiency()<50)
				{
					randomPlane=true;
					randomArea=true;
				}
				else
				if(proficiency()<75)
				{
					randomTransitionPlane=true;
					randomArea=true;
				}
				else
				if(proficiency()<100)
				{
					randomTransitionPlane=true;
				}
				else
					randomArea=true;
			}
		}
		else
		if(proficiencyCheck(mob,-95,auto))
		{
			// kaplah!
		}
		else
		if(proficiencyCheck(mob,-50,auto))
		{
			if(proficiency()<75)
			{
				randomTransitionPlane=true;
				randomArea=true;
			}
			else
			if(proficiency()<100)
			{
				randomTransitionPlane=true;
			}
		}
		else
		{
			if(proficiency()<75)
			{
				randomTransitionPlane=true;
				randomArea=true;
			}
			else
			if(proficiency()<100)
			{
				randomArea=true;
			}
			else
				randomTransitionPlane=true;
		}

		List<String> transitionalPlaneKeys = getTransitionPlaneKeys();
		Spell_Planeshift currentShift = (Spell_Planeshift)mob.location().getArea().fetchEffect(ID());
		if(currentShift!=null)
		{
			if(transitionalPlaneKeys.contains(currentShift.text().toUpperCase().trim()))
			{
				if(randomTransitionPlane)
					randomTransitionPlane=false;
				else
				if(randomPlane)
				{
					randomPlane=false;
					randomTransitionPlane=true;
					randomArea=true;
				}
			}
		}
		
		if(randomArea)
		{
			int tries=0;
			while(((++tries)<10000))
			{
				final Room room=CMLib.map().getRandomRoom();
				if((room!=null)
				&&(CMLib.flags().canAccess(mob,room))
				&&(CMLib.map().getExtendedRoomID(room).length()>0)
				&&(room.getArea().numberOfProperIDedRooms()>2))
				{
					cloneArea=room.getArea();
					cloneRoomID=CMLib.map().getExtendedRoomID(room);
					break;
				}
			}
		}
		if(randomTransitionPlane)
		{
			planeName = transitionalPlaneKeys.get(CMLib.dice().roll(1, transitionalPlaneKeys.size(), -1));
			planeFound = getPlane(planeName);
		}
		if(randomPlane)
		{
			List<String> allPlaneKeys = getAllPlaneKeys();
			planeName = allPlaneKeys.get(CMLib.dice().roll(1, allPlaneKeys.size(), -1));
			planeFound = getPlane(planeName);
		}
		
		String newPlaneName = planeIDNum.addAndGet(1)+"_"+cloneArea.Name();
		Area planeArea = CMClass.getAreaType("SubThinInstance");
		planeArea.setName(newPlaneName);
		CMLib.map().addArea(planeArea);
		planeArea.setAreaState(Area.State.ACTIVE); // starts ticking
		Room target=CMClass.getLocale("StdRoom");
		String newRoomID=this.convertToMyArea(newPlaneName,cloneRoomID);
		if(newRoomID==null)
			newRoomID=cloneRoomID;
		target.setRoomID(newRoomID);
		target.setDisplayText("Between The Planes of Existence");
		target.setDescription("You are a floating consciousness between the planes of existence...");
		target.setArea(planeArea);
		TimeClock C=(TimeClock)CMClass.getCommon("DefaultTimeClock");
		planeArea.setTimeObj(C);
		planeArea.addNonUninvokableEffect(CMClass.getAbility("Prop_NoTeleportOut"));
		planeArea.addNonUninvokableEffect(CMClass.getAbility("Prop_NoTeleport"));
		planeArea.addNonUninvokableEffect(CMClass.getAbility("Prop_NoRecall"));
		
		
		//CMLib.map().delArea(this.planeArea);
		
		final CMMsg msg=CMClass.getMsg(mob,target,this,CMMsg.MASK_MOVE|verbalCastCode(mob,target,auto),L("^S<S-NAME> conjur(s) a powerful planar connection!^?"));
		if((mob.location().okMessage(mob,msg))&&(target!=null)&&(target.okMessage(mob,msg)))
		{
			mob.location().send(mob,msg);
			final Set<MOB> h=properTargets(mob,givenTarget,false);
			if(h==null)
				return false;

			final Room thisRoom=mob.location();
			for (final Object element : h)
			{
				final MOB follower=(MOB)element;
				final CMMsg enterMsg=CMClass.getMsg(follower,target,this,CMMsg.MSG_ENTER,null,CMMsg.MSG_ENTER,null,CMMsg.MSG_ENTER,("<S-NAME> fade(s) into view.")+CMLib.protocol().msp("appear.wav",10));
				final CMMsg leaveMsg=CMClass.getMsg(follower,thisRoom,this,CMMsg.MSG_LEAVE|CMMsg.MASK_MAGIC,L("<S-NAME> fade(s) away."));
				if(thisRoom.okMessage(follower,leaveMsg)&&target.okMessage(follower,enterMsg))
				{
					Spell_Planeshift A=(Spell_Planeshift)this.beneficialAffect(mob, planeArea, asLevel, 0);
					if((currentShift != null)&&(currentShift.oldRoom!=null)&&(currentShift.oldRoom.get()!=null))
						A.oldRoom=currentShift.oldRoom;
					else
					if(currentShift != null)
						A.oldRoom=new WeakReference<Room>(mob.getStartRoom());
					else
						A.oldRoom=new WeakReference<Room>(mob.location());
					A.setMiscText(planeName);
					
					if(follower.isInCombat())
					{
						CMLib.commands().postFlee(follower,("NOWHERE"));
						follower.makePeace(false);
					}
					thisRoom.send(follower,leaveMsg);
					((Room)enterMsg.target()).bringMobHere(follower,false);
					((Room)enterMsg.target()).send(follower,enterMsg);
					follower.tell(L("\n\r\n\r"));
					CMLib.commands().postLook(follower,true);
				}
			}
		}
		// return whether it worked
		return success;
	}
}
