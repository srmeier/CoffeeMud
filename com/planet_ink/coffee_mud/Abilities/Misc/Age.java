package com.planet_ink.coffee_mud.Abilities.Misc;
import com.planet_ink.coffee_mud.Libraries.interfaces.*;
import com.planet_ink.coffee_mud.Libraries.interfaces.AchievementLibrary.AchievementLoadFlag;
import com.planet_ink.coffee_mud.Abilities.StdAbility;
import com.planet_ink.coffee_mud.core.interfaces.*;
import com.planet_ink.coffee_mud.core.*;
import com.planet_ink.coffee_mud.core.CMLib.Library;
import com.planet_ink.coffee_mud.core.collections.*;
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
import com.planet_ink.coffee_mud.MOBS.interfaces.MOB.Attrib;
import com.planet_ink.coffee_mud.Races.interfaces.*;

import java.io.IOException;
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
public class Age extends StdAbility
{
	@Override
	public String ID()
	{
		return "Age";
	}

	private final static String	localizedName	= CMLib.lang().L("Age");

	@Override
	public String name()
	{
		return localizedName;
	}

	@Override
	protected int canAffectCode()
	{
		return CAN_MOBS | CAN_ITEMS;
	}

	@Override
	protected int canTargetCode()
	{
		return 0;
	}

	@Override
	public int abstractQuality()
	{
		return Ability.QUALITY_MALICIOUS;
	}

	@Override
	public boolean putInCommandlist()
	{
		return false;
	}

	@Override
	public int classificationCode()
	{
		return Ability.ACODE_PROPERTY;
	}

	private static final String[]	triggerStrings	= I(new String[] { "AGIFY" });

	@Override
	public String[] triggerStrings()
	{
		return triggerStrings;
	}

	@Override
	public long flags()
	{
		return super.flags() | Ability.FLAG_NONENCHANTMENT;
	}

	@Override
	public String accountForYourself()
	{
		return displayText();
	}

	@Override
	public String displayText()
	{
		final long start=CMath.s_long(text());
		if(start<Short.MAX_VALUE)
			return "";
		final TimeClock C=CMLib.time().localClock(affected);
		final long days=((System.currentTimeMillis()-start)/CMProps.getTickMillis())/CMProps.getIntVar(CMProps.Int.TICKSPERMUDDAY); // down to days;
		final long months=days/C.getDaysInMonth();
		final long years=months/C.getMonthsInYear();
		if(days<1)
			return "(<1 day old)";
		else
		if(months<1)
			return "("+days+" day(s) old)";
		else
		if(years<1)
			return "("+months+" month(s) old)";
		else
			return "("+years+" year(s) old)";
	}

	protected boolean			norecurse		= false;
	protected Race				myRace			= null;
	protected double			divisor			= 0.0;
	protected long				lastSoiling		= 0;
	protected long				lastFollowCheck	= 0;

	public final static String happyBabyEmoter="min=1 mingrp=3 max=50 chance=100;makes goo goo noises.;loves its mommy.;loves its daddy.;smiles.;makes a spit bubble.;wiggles its toes.;chews on their finger.;holds up a finger.;stretches its little body.";
	public final static String otherBabyEmoter="min=1 max=5 chance=10;wants its mommy.;wants its daddy.;cries.;doesnt like you.;cries for its mommy.;cries for its daddy.";
	public final static String downBabyEmoter="min=1 max=2 chance=50;wants its mommy.;wants its daddy.;cries.;cries!;cries.";

	protected Race getMyRace()
	{
		if((myRace==null)&&(affected != null))
		{
			if(affected instanceof CagedAnimal)
			{
				final MOB M=((CagedAnimal)affected).unCageMe();
				if(M!=null)
				{
					myRace=M.baseCharStats().getMyRace();
					M.delEffect(M.fetchEffect(ID()));
					M.destroy();
				}
				else
				{
					final Room R=CMLib.map().roomLocation(affected);
					if(R!=null)
						R.showHappens(CMMsg.MSG_OK_VISUAL,L("@x1 died.",affected.name()));
					((Item)affected).destroy();
				}
			}
			else
			if(affected instanceof MOB)
				myRace=((MOB)affected).charStats().getMyRace();
		}
		return myRace;
	}

	protected MOB getFollowing(final Environmental babe)
	{
		MOB following=null;
		if(babe instanceof MOB)
			following=((MOB)babe).amFollowing();
		else
		if((babe instanceof Item)
		&&(((Item)babe).owner() instanceof MOB)
		&&(CMLib.flags().isInTheGame((MOB)((Item)babe).owner(),true)))
			following=(MOB)((Item)babe).owner();
		final Room room=CMLib.map().roomLocation(babe);
		if((room!=null)
		&&(following==null))
		{
			boolean lastFollowEllapsed=false;
			if(lastFollowCheck>0)
				lastFollowEllapsed=(System.currentTimeMillis()-lastFollowCheck)>(60 * 60 * 1000);
			MOB babeM = null;
			if((babe instanceof MOB)
			&&(CMLib.flags().isAnimalIntelligence((MOB)babe) || lastFollowEllapsed)
			&&(((MOB)babe).isMonster()))
				babeM=(MOB)babe;
			else
			if((!(babe instanceof CagedAnimal))
			||(!lastFollowEllapsed && !CMLib.flags().isAnimalIntelligence(babeM=((CagedAnimal)babe).unCageMe())))
				babeM=null;
			if(babeM!=null)
			for(int i=0;i<room.numInhabitants();i++)
			{
				final MOB M=room.fetchInhabitant(i);
				if((M!=null)
				&&(M!=babe)
				&&(babeM.description().toUpperCase().indexOf(M.Name().toUpperCase())>=0))
					following=M;
			}
			if((following!=null)&&(babe instanceof MOB))
				CMLib.commands().postFollow((MOB)babe, following, true);
		}
		if((following==null)&&(lastFollowCheck==0))
			lastFollowCheck=System.currentTimeMillis();
		if((following!=null)&&(babe.description().toUpperCase().indexOf(following.Name().toUpperCase())<0)&&(room!=null))
		{
			MOB M=null;
			final List<MOB> choices=new ArrayList<MOB>();
			for(int i=0;i<room.numInhabitants();i++)
			{
				M=room.fetchInhabitant(i);
				if((M!=null)
				&&(M!=babe)
				&&(M!=following)
				&&(babe.description().toUpperCase().indexOf(following.Name().toUpperCase())>=0))
				{
					if(M.isMonster())
						choices.add(M);
					else
					if(choices.size()==0)
						choices.add(M);
					else
						choices.add(0,M);
				}
			}
			if(choices.size()>0)
			{
				if(babe instanceof MOB)
					((MOB)babe).setFollowing(choices.get(0));
				following=choices.get(0);
			}
		}
		return following;
	}

	@Override
	public void setMiscText(final String miscText)
	{
		super.setMiscText(miscText);
		if((miscText.length()>0)
		&&(miscText.startsWith("+")))
		{
			super.setMiscText(""+(System.currentTimeMillis()
					- (CMProps.getMillisPerMudHour() * CMath.s_long(miscText.substring(1).trim()))));
		}
	}

	protected void doAgeChangeCheck()
	{
		final Physical affected=this.affected;
		if(affected==null)
			return;
		if(text().length()==0)
			return;
		final long l=CMath.s_long(text());
		if(l<Short.MAX_VALUE)
			return;
		if(norecurse)
			return;
		try
		{
			norecurse=true;
			doUnprotectedAgeChangeCheck(affected,l);
		}
		finally
		{
			norecurse=false;
		}
	}

	protected boolean isBeingCaredForByAPlayer(final MOB mob)
	{
		if((mob.amFollowing()!=null)
		&&(mob.amFollowing().playerStats()!=null)
		&&(!mob.amFollowing().isMonster())
		&&(mob.location().isInhabitant(mob))
		&&(mob.location().isInhabitant(mob.amFollowing())))
			return true;
		return false;
	}

	protected boolean isBeingCaredForByAnNPC(final MOB mob)
	{
		if((mob.amFollowing()!=null)
		&&(mob.amFollowing().playerStats()==null)
		&&(mob.amFollowing().isMonster())
		&&(mob.location().isInhabitant(mob))
		&&(mob.location().isInhabitant(mob.amFollowing())))
			return true;
		return false;
	}

	protected boolean hasUnnaturalParentage(final MOB mob)
	{
		if(mob==null)
			return false;
		for(final Enumeration<Tattoo> t= mob.tattoos();t.hasMoreElements();)
		{
			final Tattoo T=t.nextElement();
			if(T.name().startsWith("PARENTAGE:NPC"))
				return true;
		}
		final List<String> parents=CMLib.flags().getParents(mob);
		if(parents.size()>=2)
		{
			for(final String parent : parents)
			{
				if(parent.indexOf(' ')<0)
					return false;
			}
			return true;
		}
		return false;
	}

	protected boolean isAnNPCBirth(final MOB mob)
	{
		if(mob==null)
			return false;
		final List<String> str=CMLib.flags().getParents(mob);
		if(str.size()==0)
			return true;
		if(mob.Name().indexOf(' ')<0)
			return false;
		for(final String name : str)
		{
			if((mob.Name().indexOf(' ')<0)
			&&(CMLib.players().playerExistsAllHosts(name)))
				return false;
		}
		return true;
	}

	protected void doUnprotectedAgeChangeCheck(final Physical affected, final long l)
	{
		if(divisor==0.0)
		{
			final TimeClock C=CMLib.time().localClock(affected);
			divisor = C.getMonthsInYear() *C.getDaysInMonth() * CMProps.getIntVar( CMProps.Int.TICKSPERMUDDAY );
		}

		final int ellapsed=(int)Math.round(Math.floor(CMath.div(CMath.div(System.currentTimeMillis()-l,CMProps.getTickMillis()),divisor)));
		if((affected instanceof Item)
		&&(affected instanceof CagedAnimal)
		&&(!(affected instanceof DeadBody)))
		{
			((Item)affected).setExpirationDate(0);
			if(getMyRace()==null)
				return;
			if(ellapsed>=myRace.getAgingChart()[1])
			{
				final Room R=CMLib.map().roomLocation(affected);
				if(R!=null)
				{
					final Item I=(Item)affected;
					MOB following=getFollowing(I);
					if(following==null)
						return;

					final CagedAnimal C=(CagedAnimal)affected;
					final MOB babe=C.unCageMe();
					if((babe==null)||(babe.baseCharStats()==null))
					{
						R.showHappens(CMMsg.MSG_OK_VISUAL,L("@x1 JUST DIED OF DEFORMITIES!!",affected.name()));
						((Item)affected).destroy();
					}
					else
					{
						MOB leigeM=following;
						final Set<MOB> parents=new HashSet<MOB>();
						for(final Enumeration<Tattoo> t= babe.tattoos();t.hasMoreElements();)
						{
							final Tattoo T=t.nextElement();
							if(T.name().startsWith("PARENT:"))
							{
								final String parentName=T.name().substring(7).trim();
								if(CMLib.players().playerExistsAllHosts(parentName))
								{
									MOB M = CMLib.players().getPlayerAllHosts(parentName);
									if(M==null)
										M=CMLib.players().getLoadPlayer(parentName);
									if(M!=null)
										parents.add(M);
								}
								else
								{
									final MOB M=R.fetchInhabitant("$"+parentName+"$");
									if(M!=null)
										parents.add(M);
								}
							}
						}
						if((!parents.contains(following))
						&&(parents.size()>0))
						{
							final Iterator<MOB> i=parents.iterator();
							final MOB M=i.next();
							if((M!=null)&&(!M.isMonster()))
							{
								leigeM=M;
								if(M.location()==R)
									following=M;
								babe.setAttributesBitmap(M.getAttributesBitmap());
							}
						}
						babe.baseCharStats().setStat(CharStats.STAT_CHARISMA,10);
						babe.baseCharStats().setStat(CharStats.STAT_CONSTITUTION,7);
						babe.baseCharStats().setStat(CharStats.STAT_DEXTERITY,3);
						if(!CMLib.flags().isAnimalIntelligence(babe))
							babe.baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,3);
						babe.baseCharStats().setStat(CharStats.STAT_STRENGTH,2);
						babe.baseCharStats().setStat(CharStats.STAT_WISDOM,2);
						babe.basePhyStats().setHeight(babe.basePhyStats().height()*2);
						babe.basePhyStats().setWeight(babe.basePhyStats().weight()*2);
						babe.baseState().setHitPoints(2);
						babe.baseState().setMana(10);
						babe.baseState().setMovement(20);
						babe.setLiegeID(leigeM.Name());
						babe.recoverCharStats();
						babe.recoverPhyStats();
						babe.recoverMaxState();
						if(CMLib.dice().roll(1, 1000, 0)==1)
						{
							final Ability A=CMClass.getAbility("Disease_Tourettes");
							if(A!=null)
								A.invoke(babe, babe, true, CMProps.getIntVar(CMProps.Int.LASTPLAYERLEVEL));
						}
						final Age A=(Age)babe.fetchEffect(ID());
						if(A!=null)
							A.setMiscText(text());
						final Ability B=I.fetchEffect(ID());
						if(B!=null)
							I.delEffect(B);
						if(!CMLib.flags().isAnimalIntelligence(babe))
						{
							final Ability STAT=babe.fetchEffect("Prop_StatTrainer");
							if(STAT!=null)
								STAT.setMiscText("CHA=10 CON=7 DEX=3 INT=3 STR=2 WIS=2");
						}
						else
						{
							Ability A1=babe.fetchEffect("Prop_SafePet");
							if(A1==null)
							{
								A1=CMClass.getAbility("Prop_SafePet");
								babe.addNonUninvokableEffect(A);
							}
						}
						babe.text();
						babe.bringToLife(R,true);
						CMLib.beanCounter().clearZeroMoney(babe,null);
						babe.setMoneyVariation(0);
						babe.setFollowing(following);
						R.show(babe,null,CMMsg.MSG_NOISYMOVEMENT,L("<S-NAME> JUST TOOK <S-HIS-HER> FIRST STEPS!!!"));
						I.delEffect(this);
						final Runnable run=new Runnable()
						{
							final Item I2=I;

							@Override
							public void run()
							{
								final ItemPossessor poss = I2.owner();
								if(poss != null)
									poss.delItem(I2);
								I2.setOwner(null);
								I2.destroy();
							}
						};
						run.run();
						// this oughta kill it, but good.
						CMLib.threads().scheduleRunnable(run, 100);
						CMLib.threads().scheduleRunnable(run, 1000);
						if(!CMLib.flags().isAnimalIntelligence(babe))
							CMLib.database().DBReCreatePlayerData(leigeM.Name(),"HEAVEN",leigeM.Name()+"/HEAVEN/"+text(),babe.ID()+"/"+babe.basePhyStats().ability()+"/"+babe.text());
					}
				}
			}
		}
		else
		if((affected instanceof MOB)
		&&(isBeingCaredForByAPlayer((MOB)affected)))
		{
			final MOB babe=(MOB)affected;
			final MOB following=getFollowing(babe);
			if(getMyRace()==null)
				return;
			if((babe.getLiegeID().length()==0)
			&&(following != null)
			&&(!following.getLiegeID().equals(affected.Name())))
				babe.setLiegeID(following.Name());
			babe.setAttribute(MOB.Attrib.AUTOASSIST,false);
			if((ellapsed>=myRace.getAgingChart()[2])
			&&(babe.fetchBehavior("MudChat")==null))
			{
				final Room R=CMLib.map().roomLocation(affected);
				if(R!=null)
				{
					if(babe.Name().indexOf(' ')>0)
					{
						final String name=CMLib.english().startWithAorAn(getMyRace().makeMobName((char)babe.baseCharStats().getStat(CharStats.STAT_GENDER), 3)).toLowerCase();
						babe.setName(name);
						babe.setDisplayText(L("@x1 is here.",name));
					}
					babe.baseCharStats().setStat(CharStats.STAT_CHARISMA,10);
					babe.baseCharStats().setStat(CharStats.STAT_CONSTITUTION,10);
					babe.baseCharStats().setStat(CharStats.STAT_DEXTERITY,5);
					if(!CMLib.flags().isAnimalIntelligence(babe))
						babe.baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,6);
					babe.baseCharStats().setStat(CharStats.STAT_STRENGTH,6);
					babe.baseCharStats().setStat(CharStats.STAT_WISDOM,6);
					if(following!=null)
					{
						babe.copyFactions(following);
						final List<String> removeTheseV=new ArrayList<String>();
						for(final Enumeration<String> fid=babe.factions();fid.hasMoreElements();)
						{
							final String factionID=fid.nextElement();
							final Faction F=CMLib.factions().getFaction(factionID);
							if((F!=null)&&(!F.isInheritable()))
								removeTheseV.add(factionID);
						}
						for(final String fid : removeTheseV)
							babe.removeFaction(fid);
					}
					babe.basePhyStats().setHeight(babe.basePhyStats().height()*5);
					babe.basePhyStats().setWeight(babe.basePhyStats().weight()*5);
					babe.baseState().setHitPoints(4);
					babe.baseState().setMana(25);
					babe.baseState().setMovement(50);
					final Behavior B=CMClass.getBehavior("MudChat");
					if(B!=null)
						babe.addBehavior(B);
					else
						babe.delEffect(this);
					babe.recoverCharStats();
					babe.recoverPhyStats();
					babe.recoverMaxState();
					babe.text();
					if(following!=null)
						CMLib.database().DBReCreatePlayerData(following.Name(),"HEAVEN",following.Name()+"/HEAVEN/"+text(),babe.ID()+"/"+babe.basePhyStats().ability()+"/"+babe.text());
				}
			}
			else
			if((ellapsed>=myRace.getAgingChart()[3])
			&&(babe.fetchBehavior("MudChat")!=null)
			&&(babe.charStats().getStat(CharStats.STAT_INTELLIGENCE)>1))
			{
				Ability A=babe.fetchEffect("Prop_SafePet");
				if(A!=null)
					babe.delEffect(A);
				if(following != null)
					CMLib.database().DBDeletePlayerData(following.Name(),"HEAVEN",following.Name()+"/HEAVEN/"+text());

				final List<MOB> parents=new ArrayList<MOB>(2);
				PlayerLibrary players = CMLib.players();
				DatabaseEngine database = CMLib.database();
				ClanManager clans = CMLib.clans();
				PlayerAccount account = null;
				for(final Enumeration<Tattoo> e=babe.tattoos();e.hasMoreElements();)
				{
					final Tattoo T=e.nextElement();
					if(T.getTattooName().startsWith("PARENT:"))
					{
						final String parentName = T.getTattooName().substring(7);
						if(CMLib.players().playerExistsAllHosts(parentName))
						{
							MOB M=CMLib.players().getLoadPlayer(parentName);
							if(M==null)
							{
								for(final Enumeration<CMLibrary> p=CMLib.libraries(Library.PLAYERS); p.hasMoreElements();)
								{
									final PlayerLibrary playerLib=(PlayerLibrary)p.nextElement();
									if((playerLib != CMLib.players())
									&&((CMLib.map() == CMLib.library(CMLib.getLibraryThreadID(Library.PLAYERS, playerLib), Library.MAP)))
									&&(playerLib.playerExists(parentName)))
									{
										M=playerLib.getLoadPlayer(parentName);
										if(M!=null)
										{
											players=playerLib;
											final char threadId = CMLib.getLibraryThreadID(Library.PLAYERS, playerLib);
											database=(DatabaseEngine)CMLib.library(threadId, Library.DATABASE);
											clans=(ClanManager)CMLib.library(threadId, Library.CLANS);
											break;
										}
									}
								}
							}
							if(M!=null)
							{
								if(M.playerStats().getAccount()!=null)
									account=M.playerStats().getAccount();
								parents.add(M);
							}
						}
					}
				}

				final Room R=CMLib.map().roomLocation(affected);
				// check if player eligible,
				// and if not, grow up to be nothing but a mob
				if((R==null)
				||(affected.Name().indexOf(' ')>0)
				||players.playerExists(affected.Name()))
				{
					MOB liege=null;
					if(babe.getLiegeID().length()>0)
						liege=CMLib.players().getLoadPlayer(babe.getLiegeID());
					if(liege==null)
						liege=babe.amFollowing();
					if(babe.Name().indexOf(' ')>0)
					{
						final String name=CMLib.english().startWithAorAn(getMyRace().makeMobName((char)babe.baseCharStats().getStat(CharStats.STAT_GENDER), 4)).toLowerCase();
						babe.setName(name);
						babe.setDisplayText(L("@x1 stands here.",name));
					}
					final Ability psA=babe.fetchEffect("Prop_SafePet");
					if(psA != null)
						babe.delEffect(psA);
					if(following != null)
						CMLib.database().DBDeletePlayerData(following.Name(),"HEAVEN",following.Name()+"/HEAVEN/"+text());
					if(babe.amFollowing()!=null)
					{
						if(liege!=babe.amFollowing())
							babe.amFollowing().tell(L("@x1 has just grown up to be a mob.",babe.Name()));
					}
					if(liege!=null)
						liege.tell(L("@x1 has just grown up to be a mob.",babe.Name()));
					if((babe.amFollowing()==null)
					&&(liege == null)
					&&(babe.location()!=null))
						babe.location().showHappens(CMMsg.MSG_OK_VISUAL, L("@x1 has just grown up to be a mob.",babe.Name()));
					A=babe.fetchEffect(ID());
					A.setMiscText(""+ellapsed);
					babe.recoverCharStats();
					babe.recoverPhyStats();
					babe.recoverMaxState();
					babe.text();
					return;
				}

				MOB liege=null;
				if(babe.getLiegeID().length()>0)
					liege=CMLib.players().getLoadPlayer(babe.getLiegeID());
				if(liege==null)
					liege=babe.amFollowing();
				// catch any last second name collisions
				if(CMLib.players().playerExistsAllHosts(babe.Name()))
				{
					if((liege != null)&&(!liege.isMonster()))
						liege.tell(babe.name()+" can't grow up because someone stole that name.");
					else
						CMLib.commands().postSay(babe, "I can't grow up because someone stole my name!");
					if(CMath.isLong(text()))
						setMiscText(Long.toString(CMath.s_long(text())-600000L));
					return;
				}
				final MOB newMan=CMClass.getMOB("StdMOB");
				newMan.setAgeMinutes(babe.getAgeMinutes());
				newMan.setBaseCharStats(babe.baseCharStats());
				newMan.setBasePhyStats(babe.basePhyStats());
				newMan.setPlayerStats((PlayerStats)CMClass.getCommon("DefaultPlayerStats"));
				if(liege!=null)
					newMan.copyFactions(liege);
				newMan.basePhyStats().setLevel(1);
				newMan.setAttributesBitmap(babe.getAttributesBitmap());
				for(final Enumeration<Tattoo> e=babe.tattoos();e.hasMoreElements();)
					newMan.addTattoo(e.nextElement());
				String highestBaseClass="Orphan";
				final int highestBaseLevel=0;
				int highestParentLevel=0;
				for(final Pair<Clan,Integer> p : babe.clans())
					newMan.setClan(p.first.clanID(),p.second.intValue());
				int theme=Area.THEME_FANTASY;
				int highestLegacyLevel=0;
				for(final MOB M : parents)
				{
					if(M.basePhyStats().level()>highestParentLevel)
						highestParentLevel=M.basePhyStats().level();
					for(int i=0;i<M.baseCharStats().numClasses();i++)
					{
						if(M.baseCharStats().getClassLevel(M.baseCharStats().getMyClass(i))>highestBaseLevel)
							highestBaseClass=M.baseCharStats().getMyClass(i).baseClass();
					}
					if(!newMan.clans().iterator().hasNext())
					{
						for(final Pair<Clan,Integer> p : clans.findRivalrousClans(M))
						{
							final Pair<Clan,Integer> clanRole=M.getClanRole(p.first.clanID());
							if((clanRole!=null)
							&&(clanRole.first.getAuthority(clanRole.second.intValue(), Clan.Function.HOME_PRIVS)!=Clan.Authority.CAN_NOT_DO))
								newMan.setClan(p.first.clanID(),p.first.getAutoPosition());
						}
					}
					if((M.baseCharStats().getWorshipCharID().length()>0)
					&&(newMan.baseCharStats().getWorshipCharID().length()==0))
						newMan.baseCharStats().setWorshipCharID(M.baseCharStats().getWorshipCharID());
					for(final Enumeration<Ability> a=M.abilities();a.hasMoreElements();)
					{
						final Ability L=a.nextElement();
						if((L instanceof Language)&&(newMan.fetchAbility(L.ID())==null))
							newMan.addAbility((Ability)L.copyOf());
					}
					theme=M.playerStats().getTheme();

					final int legacyLevel=M.playerStats().getLegacyLevel(highestBaseClass);
					if(legacyLevel>highestLegacyLevel)
						highestLegacyLevel=legacyLevel;
					newMan.setAttributesBitmap(M.getAttributesBitmap());
				}
				if((!newMan.clans().iterator().hasNext())&&(liege!=null))
				{
					for(final Pair<Clan,Integer> p : clans.findRivalrousClans(liege))
					{
						final Pair<Clan,Integer> clanRole=liege.getClanRole(p.first.clanID());
						if((clanRole!=null)
						&&(clanRole.first.getAuthority(clanRole.second.intValue(), Clan.Function.HOME_PRIVS)!=Clan.Authority.CAN_NOT_DO))
							newMan.setClan(p.first.clanID(),p.first.getAutoPosition());
					}
				}
				if(clans.findRivalrousClan(newMan)!=null)
				{
					final Clan C = clans.findRivalrousClan(newMan);
					if(C!=null)
						C.addMember(newMan, C.getGovernment().getAcceptPos());
				}
				newMan.setDescription(babe.description());
				newMan.setDisplayText(babe.displayText());
				newMan.setExperience(babe.getExperience()/10);
				newMan.setFollowing(null);
				newMan.setLiegeID(babe.getLiegeID());
				newMan.setLocation(babe.location());
				CMLib.beanCounter().setMoney(newMan,CMLib.beanCounter().getMoney(babe));
				newMan.setName(babe.Name());
				newMan.setPractices(babe.getPractices());
				newMan.setQuestPoint(babe.getQuestPoint());
				newMan.setStartRoom(babe.getStartRoom());
				newMan.setTrains(babe.getTrains());
				newMan.setWimpHitPoint(babe.getWimpHitPoint());
				newMan.baseCharStats().setWorshipCharID(babe.baseCharStats().getWorshipCharID());
				if(liege!=null)
				{
					newMan.playerStats().setPassword(liege.playerStats().getPasswordStr());
					newMan.playerStats().setEmail(liege.playerStats().getEmail());
					if(account == null)
						account=liege.playerStats().getAccount();
				}
				else
					newMan.playerStats().setPassword(babe.Name());
				newMan.playerStats().setAccount(account);
				newMan.playerStats().setLastUpdated(System.currentTimeMillis());
				newMan.playerStats().setLastDateTime(System.currentTimeMillis());
				if(newMan.playerStats().getBirthday()==null)
				{
					int newAge=newMan.playerStats().initializeBirthday(CMLib.time().localClock(R),ellapsed*15,newMan.baseCharStats().getMyRace());
					if((newAge<0)||(newAge>newMan.baseCharStats().getMyRace().getAgingChart()[Race.AGE_MIDDLEAGED]))
						newAge=newMan.baseCharStats().getMyRace().getAgingChart()[Race.AGE_MIDDLEAGED];
					newMan.baseCharStats().setStat(CharStats.STAT_AGE,newAge);
				}
				newMan.baseCharStats().setStat(CharStats.STAT_AGE,ellapsed);
				newMan.baseState().setHitPoints(CMProps.getIntVar(CMProps.Int.STARTHP));
				newMan.baseState().setMana(CMProps.getIntVar(CMProps.Int.STARTMANA));
				newMan.baseState().setMovement(CMProps.getIntVar(CMProps.Int.STARTMOVE));
				newMan.baseCharStats().getMyRace().setHeightWeight(newMan.basePhyStats(),newMan.baseCharStats().reproductiveCode());
				final int baseStat=(CMProps.getIntVar(CMProps.Int.BASEMINSTAT)+CMProps.getIntVar(CMProps.Int.BASEMAXSTAT))/2;
				for(final int  i : CharStats.CODES.BASECODES())
					newMan.baseCharStats().setStat(i,baseStat);
				if(highestParentLevel>=CMProps.getIntVar(CMProps.Int.LASTPLAYERLEVEL))
				{
					for(int i=0;i<highestLegacyLevel+1;i++)
						newMan.playerStats().addLegacyLevel(highestBaseClass);
				}
				//final int bonusPoints=newMan.playerStats().getTotalLegacyLevels()+1;
				final Ability reRollA=CMClass.getAbility("Prop_ReRollStats");
				if(reRollA!=null)
				{
					reRollA.setMiscText("PICKCLASS=TRUE");
					newMan.setSavable(true);
					newMan.addNonUninvokableEffect(reRollA);
				}
				newMan.recoverCharStats();
				newMan.baseCharStats().getMyRace().startRacing(newMan,false);
				newMan.playerStats().setTheme(theme);
				try
				{
					newMan.baseCharStats().setMyClasses(";" + CMLib.login().promptCharClass(theme, newMan, null).name());
				}
				catch (final IOException e)
				{
				}
				newMan.baseCharStats().setMyLevels(";1");
				newMan.baseCharStats().getCurrentClass().startCharacter(newMan,false,false);
				for(int i=0;i<babe.numItems();i++)
					newMan.moveItemTo(babe.getItem(i));
				CMLib.utensils().outfit(newMan,newMan.baseCharStats().getMyRace().outfit(newMan));
				CMLib.utensils().outfit(newMan,newMan.baseCharStats().getCurrentClass().outfit(newMan));
				newMan.playerStats().setLastDateTime(System.currentTimeMillis());
				newMan.playerStats().setLastUpdated(System.currentTimeMillis());
				newMan.recoverCharStats();
				newMan.recoverPhyStats();
				newMan.recoverMaxState();
				newMan.resetToMaxState();
				if(CMLib.flags().isAnimalIntelligence(newMan))
				{
					newMan.baseCharStats().setMyClasses(";StdCharClass");
					newMan.recoverCharStats();
					newMan.recoverPhyStats();
					newMan.recoverMaxState();
					newMan.resetToMaxState();
				}
				//CMLib.achievements().loadAccountAchievements(newMan,AchievementLoadFlag.CHARCR_PRELOAD);

				database.DBCreateCharacter(newMan);
				players.addPlayer(newMan);

				newMan.setSession((Session)CMClass.getCommon("DefaultSession"));
				CMLib.achievements().possiblyBumpAchievement(newMan, AchievementLibrary.Event.PLAYERBORN, 1);
				newMan.setSession(null);
				for(final MOB parentM : parents)
				{
					if(parentM.isPlayer())
						CMLib.achievements().possiblyBumpAchievement(parentM, AchievementLibrary.Event.PLAYERBORNPARENT, 1, newMan);
				}

				if((liege != null) && (liege.session() != null))
					newMan.playerStats().setLastIP(liege.session().getAddress());
				Log.sysOut("Age","Created user: "+newMan.Name());
				if(!newMan.isAttributeSet(Attrib.PRIVACY))
					CMLib.login().notifyFriends(newMan,L("^X@x1 has just been created.^.^?",newMan.Name()));

				final List<String> channels=CMLib.channels().getFlaggedChannelNames(ChannelsLibrary.ChannelFlag.NEWPLAYERS, newMan);
				for(int i=0;i<channels.size();i++)
					CMLib.commands().postChannel(channels.get(i),newMan.clans(),L("@x1 has just been created.",newMan.Name()),true);

				if(liege != null)
				{
					String name = liege.Name();
					if(account != null)
						name = account.getAccountName();
					if(liege!=babe.amFollowing())
						babe.amFollowing().tell(L("@x1 has just grown up! @x2 password is the same as @x3's.",newMan.Name(),CMStrings.capitalizeAndLower(newMan.baseCharStats().hisher()),name));
					liege.tell(L("@x1 has just grown up! @x2 password is the same as @x3's.",newMan.Name(),CMStrings.capitalizeAndLower(newMan.baseCharStats().hisher()),name));
				}
				database.DBUpdatePlayer(newMan);
				newMan.removeFromGame(false,true);
				babe.setFollowing(null);
				babe.destroy();
				final MOB fol=newMan.amFollowing();
				newMan.setFollowing(null);
				if(liege != null)
				{
					for(final Enumeration<CMLibrary> p=CMLib.libraries(Library.PLAYERS); p.hasMoreElements();)
					{
						final PlayerLibrary playerLib=(PlayerLibrary)p.nextElement();
						if(playerLib.getPlayer(liege.Name())==liege)
						{
							final char threadId = CMLib.getLibraryThreadID(Library.PLAYERS, playerLib);
							((DatabaseEngine)CMLib.library(threadId, Library.DATABASE)).DBUpdateFollowers(liege);
							break;
						}
					}
				}
				newMan.setFollowing(fol);
			}
		}
		else
		if((affected instanceof MOB)
		&&(isBeingCaredForByAnNPC((MOB)affected) || hasUnnaturalParentage((MOB)affected))
		&&(CMLib.law().getPropertyOwnerName(((MOB)affected).location()).length()>0))
		{
			final MOB babe=(MOB)affected;
			if(getMyRace()==null)
				return;
			babe.setAttribute(MOB.Attrib.AUTOASSIST,true);
			if((ellapsed>=myRace.getAgingChart()[2])
			&&(babe.fetchBehavior("MudChat")==null)) // this is how we tell if we've already done this
			{
				final Room R=CMLib.map().roomLocation(affected);
				if(R!=null)
				{
					if(babe.Name().indexOf(' ')>0)
					{
						final String name=CMLib.english().startWithAorAn(getMyRace().makeMobName((char)babe.baseCharStats().getStat(CharStats.STAT_GENDER), 3)).toLowerCase();
						babe.setName(name);
						babe.setDisplayText(L("@x1 is here.",name));
					}
					babe.baseCharStats().setStat(CharStats.STAT_CHARISMA,10);
					babe.baseCharStats().setStat(CharStats.STAT_CONSTITUTION,10);
					babe.baseCharStats().setStat(CharStats.STAT_DEXTERITY,5);
					babe.baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,6);
					babe.baseCharStats().setStat(CharStats.STAT_STRENGTH,6);
					babe.baseCharStats().setStat(CharStats.STAT_WISDOM,6);
					babe.basePhyStats().setHeight(babe.basePhyStats().height()*5);
					babe.basePhyStats().setWeight(babe.basePhyStats().weight()*5);
					babe.baseState().setHitPoints(4);
					babe.baseState().setMana(25);
					babe.baseState().setMovement(50);
					final Behavior B=CMClass.getBehavior("MudChat");
					if(B!=null)
						babe.addBehavior(B);
					//babe.delEffect(this);
					babe.recoverCharStats();
					babe.recoverPhyStats();
					babe.recoverMaxState();
					babe.text();
				}
			}
			else
			if((ellapsed>=myRace.getAgingChart()[3])
			&&(babe.fetchEffect("Prop_SafePet")!=null)) // and this is how we tell if we've already done THIS!)
			{
				final Ability A=babe.fetchEffect("Prop_SafePet");
				if(A!=null)
					babe.delEffect(A);

				if(babe.Name().indexOf(' ')>0)
				{
					final String name=CMLib.english().startWithAorAn(getMyRace().makeMobName((char)babe.baseCharStats().getStat(CharStats.STAT_GENDER), 4)).toLowerCase();
					babe.setName(name);
					babe.setDisplayText(L("@x1 stands here.",name));
				}

				babe.baseCharStats().setStat(CharStats.STAT_CHARISMA,10);
				babe.baseCharStats().setStat(CharStats.STAT_CONSTITUTION,10);
				babe.baseCharStats().setStat(CharStats.STAT_DEXTERITY,10);
				if(!CMLib.flags().isAnimalIntelligence(babe))
					babe.baseCharStats().setStat(CharStats.STAT_INTELLIGENCE,10);
				babe.baseCharStats().setStat(CharStats.STAT_STRENGTH,10);
				babe.baseCharStats().setStat(CharStats.STAT_WISDOM,10);
				babe.baseCharStats().getMyRace().setHeightWeight(babe.basePhyStats(), babe.baseCharStats().reproductiveCode());
				babe.baseState().setHitPoints(15);
				babe.baseState().setMana(25);
				babe.baseState().setMovement(80);
				babe.recoverCharStats();
				babe.recoverPhyStats();
				babe.recoverMaxState();
				babe.text();
			}
			else
			if(ellapsed>=myRace.getAgingChart()[5])
			{
				if(babe.Name().indexOf(' ')>0)
				{
					final String name=CMLib.english().startWithAorAn(getMyRace().makeMobName((char)babe.baseCharStats().getStat(CharStats.STAT_GENDER), 5)).toLowerCase();
					babe.setName(name);
					babe.setDisplayText(L("@x1 stands here.",name));
				}
				babe.baseState().setHitPoints(20);
				babe.baseState().setMana(25);
				babe.baseState().setMovement(100);
				Ability A=babe.fetchEffect("Prop_SafePet");
				if(A!=null)
					babe.delEffect(A);
				A=babe.fetchEffect(ID());
				if(A!=null)
					babe.delEffect(A);
				babe.delEffect(this);
				babe.recoverCharStats();
				babe.recoverPhyStats();
				babe.recoverMaxState();
				babe.text();
			}
		}
	}

	protected volatile long nextAgeCheckTimeMs = System.currentTimeMillis();

	protected void doPeriodicAgeCheck()
	{
		long lastCheckedAt;
		synchronized(this)
		{
			lastCheckedAt = this.nextAgeCheckTimeMs;
		}
		if(System.currentTimeMillis() < lastCheckedAt)
			return;
		synchronized(this)
		{
			this.nextAgeCheckTimeMs = System.currentTimeMillis() + 5000;
		}
		this.doAgeChangeCheck();
	}

	@Override
	public void setStat(final String code, final String val)
	{
		if(code != null)
		{
			if (code.equalsIgnoreCase("BIRTHDATE"))
				this.setMiscText(""+CMath.s_long(val));
			else
			if (code.equalsIgnoreCase("AGEYEARS"))
			{
				final int numYears = CMath.s_int(val);
				if(numYears > 0)
				{
					final TimeClock C=CMLib.time().localClock(affected);
					final int months = C.getMonthsInYear() * numYears;
					final int days = months * C.getDaysInMonth();
					final long hours = days * C.getHoursInDay();
					setMiscText(""+(System.currentTimeMillis()-(hours * CMProps.getMillisPerMudHour())));
				}
			}
			else
				super.setStat(code, val);
		}
	}

	@Override
	public String getStat(final String code)
	{
		if(code != null)
		{
			if (code.equalsIgnoreCase("BIRTHDATE"))
				return text();
			else
			if (code.equalsIgnoreCase("AGEYEARS"))
			{
				final long start=CMath.s_long(text());
				if(start<Short.MAX_VALUE)
					return "";
				final TimeClock C=CMLib.time().localClock(affected);
				final long days=((System.currentTimeMillis()-start)/CMProps.getTickMillis())/CMProps.getIntVar(CMProps.Int.TICKSPERMUDDAY); // down to days;
				final long months=days/C.getDaysInMonth();
				final long years=months/C.getMonthsInYear();
				if(years > 0)
					return ""+years;
				return "0";
			}
			else
				return super.getStat(code);
		}
		return "";
	}

	@Override
	public void executeMsg(final Environmental myHost, final CMMsg msg)
	{
		super.executeMsg(myHost,msg);
		final Physical affected = this.affected;
		if((affected!=null)
		&&(!affected.amDestroyed()))
		{
			if(getMyRace()==null)
				return;
			if((msg.target()==affected)
			&&(msg.targetMinor()==CMMsg.TYP_EXAMINE))
			{
				if((((affected instanceof Item)&&(affected instanceof CagedAnimal)&&(!(affected instanceof DeadBody)))
					||((affected instanceof MOB)&&(!((MOB)affected).isSavable())))
				&&(affected.description().toUpperCase().indexOf(msg.source().name().toUpperCase())>=0))
				{
					if(divisor==0.0)
					{
						final TimeClock C=CMLib.time().localClock(affected);
						divisor = C.getMonthsInYear() * C.getDaysInMonth() * CMProps.getIntVar( CMProps.Int.TICKSPERMUDDAY );
					}
					final long l=CMath.s_long(text());
					if((l>0)&&(l<Long.MAX_VALUE))
					{
						final int ellapsed=(int)Math.round(Math.floor(CMath.div(CMath.div(System.currentTimeMillis()-l,CMProps.getTickMillis()),divisor)));
						if(ellapsed<=myRace.getAgingChart()[3])
						{
							String s=displayText();
							if(s.startsWith("("))s=s.substring(1);
							if(s.endsWith(")")
								)s=s.substring(0,s.length()-1);
							msg.source().tell(L("@x1 is @x2",Name(),s));
						}
					}
				}
			}
			if((affected instanceof Item)
			&&((msg.target()==affected)||(msg.tool()==affected))
			&&(!(affected instanceof DeadBody))
			&&(CMLib.flags().isInTheGame((Item)affected,true)))
			{
				final Item baby=(Item)affected;
				final Behavior B=baby.fetchBehavior("Emoter");
				if(B!=null)
				{
					Environmental o=baby.owner();
					switch(msg.targetMinor())
					{
					case CMMsg.TYP_DROP:
						if(msg.target() instanceof Room)
							o=msg.target();
						break;
					case CMMsg.TYP_GET:
						o=msg.source();
						break;
					}
					if(o instanceof Room)
					{
						if(!B.getParms().equalsIgnoreCase(downBabyEmoter))
							B.setParms(downBabyEmoter);
					}
					else
					if(o!=null)
					{
						final String desc = baby.description();
						final int x = desc.indexOf(" "+o.Name());
						if((x<0)
						||(Character.isLetter(desc.charAt(x+1+o.Name().length()))))
						{
							if(!B.getParms().equalsIgnoreCase(otherBabyEmoter))
								B.setParms(otherBabyEmoter);
						}
						else
						{
							if(!B.getParms().equalsIgnoreCase(happyBabyEmoter))
								B.setParms(happyBabyEmoter);
						}
					}
				}
			}
			if(((System.currentTimeMillis()-lastSoiling)>(TimeManager.MILI_MINUTE*30))&&(CMLib.dice().rollPercentage()<10))
			{
				if(lastSoiling==0)
					lastSoiling=System.currentTimeMillis();
				else
				{
					lastSoiling=System.currentTimeMillis();
					boolean soil=((affected instanceof CagedAnimal)&&(!(affected instanceof DeadBody)));
					MOB mob=null;
					if(affected instanceof MOB)
					{
						mob=(MOB)affected;
						if(getMyRace()==null)
							return;
						if(divisor==0.0)
						{
							final TimeClock C=CMLib.time().localClock(mob.getStartRoom());
							divisor = C.getMonthsInYear() * C.getDaysInMonth() * CMProps.getIntVar( CMProps.Int.TICKSPERMUDDAY );
						}
						final long l=CMath.s_long(text());
						if((l>0)&&(l<Long.MAX_VALUE))
						{
							final int ellapsed=(int)Math.round(Math.floor(CMath.div(CMath.div(System.currentTimeMillis()-l,CMProps.getTickMillis()),divisor)));
							if(ellapsed<=myRace.getAgingChart()[2])
								soil=true;
						}
					}
					if(invoker()!=null)
						mob=invoker();
					else
					if((affected instanceof Item)&&(((Item)affected).owner() instanceof MOB))
						mob=(MOB)((Item)affected).owner();
					if((mob==null)&&(((Item)affected).owner() instanceof Room))
						mob=((Room)((Item)affected).owner()).fetchInhabitant(0);

					if((soil)
					&&(affected.fetchEffect("Soiled")==null)
					&&(mob!=null)&&(!affected.name().toLowerCase().endsWith(" egg")))
					{
						final Ability A=CMClass.getAbility("Soiled");
						if(A!=null)
							A.invoke(mob,affected,true,0);
					}
				}
			}
			msg.addTrailerRunnable(myAgeCheckRunner);
		}
	}

	protected Runnable myAgeCheckRunner = new Runnable()
	{
		@Override
		public void run()
		{
			doPeriodicAgeCheck();
		}
	};

	@Override
	public CMObject copyOf()
	{
		final Age A=(Age)super.copyOf();
		if(A!=null)
		{
			A.myAgeCheckRunner = new Runnable()
			{
				@Override
				public void run()
				{
					A.doPeriodicAgeCheck();
				}
			};
		}
		return A;
	}

	@Override
	public CMObject newInstance()
	{
		final Age A=(Age)super.newInstance();
		if(A!=null)
		{
			A.myAgeCheckRunner = new Runnable()
			{
				@Override
				public void run()
				{
					A.doPeriodicAgeCheck();
				}
			};
		}
		return A;
	}

	@Override
	public void affectPhyStats(final Physical affected, final PhyStats affectableStats)
	{
		super.affectPhyStats(affected,affectableStats);
		this.doPeriodicAgeCheck();
	}

	@Override
	public void affectCharStats(final MOB affected, final CharStats affectableStats)
	{
		super.affectCharStats(affected,affectableStats);
		final long l=CMath.s_long(text());
		if((l<Short.MAX_VALUE)&&(l>0))
		{
			affected.baseCharStats().setStat(CharStats.STAT_AGE,(int)l);
			affectableStats.setStat(CharStats.STAT_AGE,(int)l);
		}
		else
		{
			if(divisor==0.0)
			{
				final TimeClock C=CMLib.time().localClock(affected);
				divisor = C.getMonthsInYear() * C.getDaysInMonth() * CMProps.getIntVar(CMProps.Int.TICKSPERMUDDAY );
			}
			final int age=(int)Math.round(Math.floor(CMath.div(CMath.div(System.currentTimeMillis()-l,CMProps.getTickMillis()),divisor)));

			if((age>=Short.MAX_VALUE)||(age<0))
				Log.errOut("Age","Recorded, on "+affected.name()+", age of "+age+", from tick values (("+System.currentTimeMillis()+"-"+text()+")/4000)/"+divisor);
			else
			{
				affected.baseCharStats().setStat(CharStats.STAT_AGE,age);
				affectableStats.setStat(CharStats.STAT_AGE,affected.baseCharStats().getStat(CharStats.STAT_AGE));
			}
		}
	}

	@Override
	public boolean invoke(final MOB mob, final List<String> commands, final Physical givenTarget, final boolean auto, final int asLevel)
	{
		if(commands.size()==0)
		{
			mob.tell(L("You can see current age with AGE <target>, or change age category with AGE <target> <number delta>"));
			return false;
		}
		int increment=0;
		if((commands.size()>1) && (CMath.isInteger(commands.get(commands.size()-1))))
			increment=CMath.s_int(commands.remove(commands.size()-1));
		final Physical target = super.getAnyTarget(mob, commands, givenTarget,null,false,true);
		if (target == null)
			return false;
		if (!super.invoke(mob, commands, givenTarget, auto, asLevel))
			return false;
		final Age A=(Age)target.fetchEffect(ID());
		if(A==null)
			mob.tell(L("@x1 is not yet aging.",target.Name()));
		else
		{
			A.doAgeChangeCheck();
			final long l=CMath.s_long(A.text());
			if(l==0)
			{
				mob.tell(L("Age is broken."));
				return false;
			}
			final long ellapsed=(int)Math.round(Math.floor(CMath.div(CMath.div(System.currentTimeMillis()-l,CMProps.getTickMillis()),A.divisor)));
			int currentAgeIndex=0;
			final Race R=A.getMyRace();
			if(l!=0)
			{
				for(int i=0;i<R.getAgingChart().length;i++)
				{
					if(ellapsed <= R.getAgingChart()[i])
					{
						currentAgeIndex=i;
						break;
					}
				}
			}
			if(increment==0)
			{
				mob.tell(L("@x1 is @x2.",target.Name(),A.displayText()));
				mob.tell(L("@x1 is currently @x2.",target.Name(),Race.AGE_DESCS[currentAgeIndex]));
				return false;
			}
			currentAgeIndex += increment;
			if((currentAgeIndex < 0)||(currentAgeIndex >= R.getAgingChart().length))
			{
				mob.tell(L("May not continue aging @x1 that way."));
				return false;
			}
			final long newEllapsed = System.currentTimeMillis() - Math.round(R.getAgingChart()[currentAgeIndex] * CMProps.getTickMillis() * A.divisor)+1;
			A.setMiscText(""+newEllapsed);
			mob.tell(L("@x1 is now a different age.",target.name()));
		}
		return false;
	}
}
