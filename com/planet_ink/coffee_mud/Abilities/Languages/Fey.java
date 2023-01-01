package com.planet_ink.coffee_mud.Abilities.Languages;
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
public class Fey extends StdLanguage
{
	//Based on Gevey from http://www.kalieda.org/gevey/lexicon.html
	@Override
	public String ID()
	{
		return "Fey";
	}

	private final static String	localizedName	= CMLib.lang().L("Fey");

	@Override
	public String name()
	{
		return localizedName;
	}

	public static List<String[]> wordLists=null;
	public Fey()
	{
		super();
	}

	@Override
	public CMObject newInstance()
	{
		return new Fey();
	}

	@Override
	public List<String[]> translationLists(final String language)
	{
		if(wordLists==null)
		{
			final String[] one={"se","rh","al","o","na","e","ce","uu","si","lag"};
			final String[] two={"ta","Sha","rha","den","oc","han","mij","ma","ke","isc","ga","en","di","de","an","brois","ajet","ev","ba","eh","bas"};
			final String[] three={"vav","set","sav","sen","rhig","viy","ok","nash","mik","kiy","hask","tal","gha","dos","diy","ceg","dak","aeln","huu","beksi","bat","bal","aghblajliyookuu","agluef"};
			final String[] four={"tath","pehm","nith","nana","jaath","isc","glou","gafr","dost","daes","ciel","cahve","begm","braup","blom","belag","abrol","adeci","agehr","ajet","basacont"};
			final String[] five={"shasy","rewist","pae","nuwan","noest","nafozdomuu","kreet","jaas","guhnt","glaa","caebiste","abdist","afosk","belguu","andenav","asatehrh"};
			final String[] six={"sats","retas","pokat","os","mieplust-eh","iha","hosh","griste","fot","begmevrhe","abdisem","soufuuwh","aelnebaduuu"};
			wordLists=new Vector<String[]>();
			wordLists.add(one);
			wordLists.add(two);
			wordLists.add(three);
			wordLists.add(four);
			wordLists.add(five);
			wordLists.add(six);
		}
		return wordLists;
	}

	private static final Map<String,String> exactWords=new TreeMap<String,String>();

	@Override
	public Map<String, String> translationHash(final String language)
	{
		if((exactWords!=null)&&(exactWords.size()>0))
			return exactWords;

		exactWords.put("ABANDON","fnuuthan");
		exactWords.put("ABOVE","vosy");
		exactWords.put("ACCEPTABLE","wilr");
		exactWords.put("ACROSS","mava");
		exactWords.put("ACTION","baluu");
		exactWords.put("ADD","segafran");
		exactWords.put("ADDITIONAL","gafr");
		exactWords.put("ADMIRE","abdismuu");
		exactWords.put("ADMIRER","abdiste");
		exactWords.put("ADULT","cuklame");
		exactWords.put("ADULTHOOD","cuklamconuu");
		exactWords.put("ADVENTURE","noi'holhguu");
		exactWords.put("AFTERNOON","viejluu");
		exactWords.put("AGAIN","gant-eh");
		exactWords.put("AGE","sekaskrhandomuu");
		exactWords.put("AGGRESSIVE","mahc");
		exactWords.put("AGREE","noiraznan");
		exactWords.put("AGREEMENT","razent");
		exactWords.put("AIR","khaasluu");
		exactWords.put("AIRSHIP","shunoguu");
		exactWords.put("ALL","laes");
		exactWords.put("ALONE","pout");
		exactWords.put("ALONG","basy");
		exactWords.put("ALPHABET","beecuu");
		exactWords.put("ALTHOUGH","cuhset");
		exactWords.put("AM","en");
		exactWords.put("AMAZING","oomuhfeshp");
		exactWords.put("AMAZINGLY","oomuhfeshp-eh");
		exactWords.put("AMUSE","sneklan");
		exactWords.put("AMUSEMENT","sneklizmuu");
		exactWords.put("ANCIENT","askrhan");
		exactWords.put("AND","he");
		exactWords.put("ANGER","zhgishte");
		exactWords.put("ANGRILY","moghl-eh");
		exactWords.put("ANGRY","moghl");
		exactWords.put("ANIMAL","akhmar");
		exactWords.put("ANIMALS","jozge");
		exactWords.put("ANKLE","vaguu");
		exactWords.put("ANSWER","straacohpuu");
		exactWords.put("ANT","seze");
		exactWords.put("ANTHILL","sezroubuu");
		exactWords.put("APARTMENT","jesmitaluu");
		exactWords.put("APARTMENT","oekefuu");
		exactWords.put("APPEAR","rintsan");
		exactWords.put("ARE","en");
		exactWords.put("AREA","lhikuu");
		exactWords.put("ARENA","goeksnekluu");
		exactWords.put("ARGUE","zavben");
		exactWords.put("ARM","vroiduu");
		exactWords.put("AROUND","celka");
		exactWords.put("ARSENAL","mokhovuu");
		exactWords.put("ASK","dhoetrhan");
		exactWords.put("ASSERTIVE","ahhid");
		exactWords.put("ASSISTANCE","akothuu");
		exactWords.put("ASTONISHING","oomuhfeshp");
		exactWords.put("ASTONISHINGLY","oomuhfeshp-eh");
		exactWords.put("ASTONISHMENT","oomuhfuu");
		exactWords.put("ATTENDANT","pundiste");
		exactWords.put("AUNT","agatafe");
		exactWords.put("AVALANCHE","blajliswujuu");
		exactWords.put("AWE","oomuhfuu");
		exactWords.put("BACK","glawuu");
		exactWords.put("BAD","dos");
		exactWords.put("BAKER","begmevehr");
		exactWords.put("BAKER","panevrhe");
		exactWords.put("BAKERY","begmov");
		exactWords.put("BAKERY","panagzuu");
		exactWords.put("BALL","laebuu");
		exactWords.put("BANANA","dhijuu");
		exactWords.put("BAND","nounshove");
		exactWords.put("BANDIT","loiflaeye");
		exactWords.put("BAR","goekceguu");
		exactWords.put("BARRICADE","noidoeftan");
		exactWords.put("BASIN","oshantuu");
		exactWords.put("BASKET","caatuu");
		exactWords.put("BATHROOM","goekkhoivuu");
		exactWords.put("BE","en");
		exactWords.put("BEAST","jozge");
		exactWords.put("BEAUTIFULLY","stoen-eh");
		exactWords.put("BECOME","sekan");
		exactWords.put("BED","gribdaluu");
		exactWords.put("BEDROOM","goektaluu");
		exactWords.put("BEFORE","ant");
		exactWords.put("BEFREIDN","butsohslan");
		exactWords.put("BEG","tuktlen");
		exactWords.put("BEHIND","deby");
		exactWords.put("BELLOW","zavben");
		exactWords.put("BELLY","osucuu");
		exactWords.put("BELOW","suus");
		exactWords.put("BETWEEN","dezde");
		exactWords.put("BIG","jarh");
		exactWords.put("BIRD","thoele");
		exactWords.put("BIRTH","butsnoeman");
		exactWords.put("BITE","trhalhan");
		exactWords.put("BLACK","sal");
		exactWords.put("BLIMP","shunoguu");
		exactWords.put("BLOCK","noidoeftan");
		exactWords.put("BLUE","oun");
		exactWords.put("BOAT","osemuu");
		exactWords.put("BOATMAN","osemevrhe");
		exactWords.put("BODY","ces");
		exactWords.put("BOIL","ezezhman");
		exactWords.put("BONE","koguu");
		exactWords.put("BOOK","shubuu");
		exactWords.put("BOTTLE","yapthuu");
		exactWords.put("BOWL","oshantuu");
		exactWords.put("BOX","glomuu");
		exactWords.put("BOXED","zdiglom");
		exactWords.put("BOY","rapte");
		exactWords.put("BRANCH","pouzluu");
		exactWords.put("BRAWL","bwoefohp");
		exactWords.put("BREAD","panuu");
		exactWords.put("BREAK","shaablan");
		exactWords.put("BREED","budzgoegan");
		exactWords.put("BREEDER","budzgoeganuu");
		exactWords.put("BREW","gadran");
		exactWords.put("BRICK","jethuu");
		exactWords.put("BRIDGE","vulmuu");
		exactWords.put("BRIGHT","bludr");
		exactWords.put("BRING","riethan");
		exactWords.put("BROAD","foeddh");
		exactWords.put("BROTHEL","sharhavuu");
		exactWords.put("BROTHER","huspe");
		exactWords.put("BROWN","zaec");
		exactWords.put("BUCKET","wuujuu");
		exactWords.put("BUFFALO","oekuefse");
		exactWords.put("BUILD","butsan");
		exactWords.put("BUILDS","nezhlan");
		exactWords.put("BURN","rofan");
		exactWords.put("BUT","ap");
		exactWords.put("BUY","tathan");
		exactWords.put("BY","ba");
		exactWords.put("CABBAGE","fuhstuu");
		exactWords.put("CAFE","spaghavuu");
		exactWords.put("CALLED","stosh");
		exactWords.put("CALM","juul");
		exactWords.put("CAN","brav");
		exactWords.put("CANDLE","shuafuu");
		exactWords.put("CANTEEN","goekfosuu");
		exactWords.put("CAPTIVE","kaptakel");
		exactWords.put("CAPTURE","akhkapet");
		exactWords.put("CAPTURE","kaptan");
		exactWords.put("CAR","poe'hajuu");
		exactWords.put("CAREFUL","grav");
		exactWords.put("CAREFULLY","grav-eh");
		exactWords.put("CARVE","muefan");
		exactWords.put("CAT","luetse");
		exactWords.put("CB","ojoikluu");
		exactWords.put("CEGMUU","shoe");
		exactWords.put("CELEBRATE","kezguzmen");
		exactWords.put("CHAIR","fresluu");
		exactWords.put("CHANGE","dhoshopen");
		exactWords.put("CHAOTIC","bab");
		exactWords.put("CHASE","rispan");
		exactWords.put("CHAT","kegen");
		exactWords.put("CHEESE","lizhvuu");
		exactWords.put("CHEST","storuu");
		exactWords.put("CHICKEN","oh'heele");
		exactWords.put("CHILD","basat");
		exactWords.put("CHILD","dhoire");
		exactWords.put("CHISEL","muefan");
		exactWords.put("CHOICE","trugoisuu");
		exactWords.put("CHOOSE","trugan");
		exactWords.put("CHOP","shaablan");
		exactWords.put("CHORE","akhuebnisk");
		exactWords.put("CHRISTEN","sekstoshan");
		exactWords.put("CITRUS","zerajuu");
		exactWords.put("CITY","vopshe");
		exactWords.put("CLAN","galn'hiete");
		exactWords.put("CLAY","luzhnuu");
		exactWords.put("CLEAR","tsaman");
		exactWords.put("CLIMB","mispen");
		exactWords.put("CLINIC","jaarveefuu");
		exactWords.put("CLOAK","lufluu");
		exactWords.put("CLOAK","noiluflan");
		exactWords.put("CLOTHES","dhoftuu");
		exactWords.put("CLOTHING","dondomuu");
		exactWords.put("CLOUD","drhelhuu");
		exactWords.put("CLOVER","noiluflan");
		exactWords.put("CLUB","goekceguu");
		exactWords.put("COBBLER","cegmathe");
		exactWords.put("COBBLING","cegm");
		exactWords.put("COCKEREL","oh'heele");
		exactWords.put("COFFEE","spaghuu");
		exactWords.put("COLD","skug");
		exactWords.put("COLLEGE","krasovuu");
		exactWords.put("COMMENCE","boh");
		exactWords.put("COMMUNICATOR","threezuu");
		exactWords.put("COMPARE","bas");
		exactWords.put("COMPLETE","suatan");
		exactWords.put("COMPOSE","butsnounshan");
		exactWords.put("COMPOSITION","butsnounshanuu");
		exactWords.put("COMPUTER","gaurhtuu");
		exactWords.put("CONFUSE","noibaban");
		exactWords.put("CONFUSION","noibabuu");
		exactWords.put("CONSTRUCT","nezhlan");
		exactWords.put("CONTINENT","shkuubuu");
		exactWords.put("CONTINUOUS","les");
		exactWords.put("CONTRACT","raznuu");
		exactWords.put("CONTROL","tuuznan");
		exactWords.put("COOK","noibegman");
		exactWords.put("CORKBOARD","doeftkhavyuu");
		exactWords.put("CORN","sastrhuu");
		exactWords.put("COUGH","lekuu");
		exactWords.put("COUNT","jekan");
		exactWords.put("COUNTING","okijekizmuu");
		exactWords.put("COURTROOM","soghovuu");
		exactWords.put("COURTYARD","bodmuu");
		exactWords.put("COUSIN","rhaaje");
		exactWords.put("CRAB","stuuwhe");
		exactWords.put("CREATION","nezhlakluu");
		exactWords.put("CRY","wihdren");
		exactWords.put("SUN","wage");
		exactWords.put("CUP","ladhuu");
		exactWords.put("DAD","bizhev");
		exactWords.put("DATA","ghawuu");
		exactWords.put("DAUGHTER","joose");
		exactWords.put("DAY","resuu");
		exactWords.put("DAYCARE","basatag");
		exactWords.put("DAYLIGHT","jevuu");
		exactWords.put("DECOMPOSE","thuzden");
		exactWords.put("DEEP","yaez");
		exactWords.put("DELIVER","riethan");
		exactWords.put("DELTA","dhoun'hietuu");
		exactWords.put("DEMON","leshpe");
		exactWords.put("DEPARTURE","agruu");
		exactWords.put("DERIGIBLE","shunoguu");
		exactWords.put("DESPITE","grhaetap");
		exactWords.put("DESERT","thraasan");
		exactWords.put("DESTROY","kezan");
		exactWords.put("DETERMINATION","emuhstruu");
		exactWords.put("DETERMINED","emuhstralb");
		exactWords.put("DIFFERENT","fot");
		exactWords.put("DIRT","flaeyuu");
		exactWords.put("DISASTER","grhuhkuu");
		exactWords.put("DISEASE","oshaathuu");
		exactWords.put("DISEASED","jaarvan");
		exactWords.put("DISTANCE","nuubruu");
		exactWords.put("DO","gven");
		exactWords.put("DOCTOR","picatehrh");
		exactWords.put("DOES","noi'han");
		exactWords.put("DOG","tuusrhe");
		exactWords.put("DOMESTICAED","maw");
		exactWords.put("DOMESTICATE","mawam");
		exactWords.put("DOOMSAYER","jaarviste");
		exactWords.put("DOOR","tuerhuu");
		exactWords.put("DOUGH","panetuu");
		exactWords.put("DOWN","modo");
		exactWords.put("DRAW","cublan");
		exactWords.put("DRINK","cegan");
		exactWords.put("DRINKS","luedh");
		exactWords.put("DRIVE","zetsan");
		exactWords.put("DRIVER","zetsiste");
		exactWords.put("DROP","blajlan");
		exactWords.put("DRY","altrh");
		exactWords.put("DURING","vih");
		exactWords.put("DUSK","tincuu");
		exactWords.put("EACH","brants");
		exactWords.put("EAR","aelne");
		exactWords.put("EAT","fosan");
		exactWords.put("EFFECT","miepluu");
		exactWords.put("EFFICIENT","dvojust");
		exactWords.put("EFFICIENTLY","dvojust-eh");
		exactWords.put("EIGHT","espenu");
		exactWords.put("ELBOW","vaguu");
		exactWords.put("ELECTRIC","thecshp");
		exactWords.put("ELECTRICITY","thecye");
		exactWords.put("ELSEWHERE","kuuntsau");
		exactWords.put("EMBER","thrumuu");
		exactWords.put("EMOTION","meghe");
		exactWords.put("EMPTY","prozd");
		exactWords.put("END","suatismuu");
		exactWords.put("ENGINEER","batath");
		exactWords.put("ENOUGH","jarh");
		exactWords.put("ENJOY","sekraajan");
		exactWords.put("ENJOYMENT","traraaje");
		exactWords.put("ENTER","puuzen");
		exactWords.put("ENTERTAINER","sneklinde");
		exactWords.put("EQUALLY","blodrh-eh");
		exactWords.put("EVENING","salhkuu");
		exactWords.put("EVENT","gouthrhuu");
		exactWords.put("EVERY","brants");
		exactWords.put("EXCHANGE","rheegrhan");
		exactWords.put("EXCLUDE","khipan");
		exactWords.put("EXPERIENCE","noishloektan");
		exactWords.put("EXTRA","gafr");
		exactWords.put("EYE","wanhe");
		exactWords.put("EYESIGHT","agluefuu");
		exactWords.put("FACTORY","nezhleefuu");
		exactWords.put("FAIL","khuulman");
		exactWords.put("FAILING","khuulmizmuu");
		exactWords.put("FAILURE","khuulmohpuu");
		exactWords.put("FALL","blajlen");
		exactWords.put("FAMILY","cahve");
		exactWords.put("FARM","luutrhan");
		exactWords.put("FARMING","thraejwan");
		exactWords.put("FAST","vit");
		exactWords.put("FAT","zdej");
		exactWords.put("FATHER","bizhve");
		exactWords.put("FEAST","guzmuu");
		exactWords.put("FEATHER","ujetsuu");
		exactWords.put("FEED","whohnan");
		exactWords.put("FEW","suuft");
		exactWords.put("FEWER","suuft");
		exactWords.put("FIELD","deefsuu");
		exactWords.put("FIGTH","bwoefan");
		exactWords.put("FILL","shopcan");
		exactWords.put("FIND","frudhan");
		exactWords.put("FINDING","frudhdomuu");
		exactWords.put("FINGER","bazhev");
		exactWords.put("FINISH","suatismuu");
		exactWords.put("FIRE","heptalte");
		exactWords.put("FIREARM","mokhuu");
		exactWords.put("FIREWOOD","pouzlaelhuu");
		exactWords.put("FIVE","finu");
		exactWords.put("FLAME","hepte");
		exactWords.put("FLESH","shievuu");
		exactWords.put("FLINT","strotuu");
		exactWords.put("FLOOR","gripnuu");
		exactWords.put("FLOUR","sastrhaelhuu");
		exactWords.put("FLOWER","hosh");
		exactWords.put("FLY","ta'shunen");
		exactWords.put("FOLD","akhlathel");
		exactWords.put("FOLLISHLY","nivw-eh");
		exactWords.put("FOOD","afosuu");
		exactWords.put("FOOL","tranivwe");
		exactWords.put("FOOLISH","nivw");
		exactWords.put("FOOT","ezekuu");
		exactWords.put("FOR","ist");
		exactWords.put("FOREHEAD","drestuu");
		exactWords.put("FOREST","rhabgietuu");
		exactWords.put("FORK","zobuu");
		exactWords.put("FORTY","vilhki");
		exactWords.put("FOUR","vilu");
		exactWords.put("FREEZE","griban");
		exactWords.put("FRIEND","ohsle");
		exactWords.put("FRIENDLY","ohslust");
		exactWords.put("FRIENDSHIP","ohsle'hiete");
		exactWords.put("FROM","dost");
		exactWords.put("FRONT","cisa");
		exactWords.put("FROTH","shaesen");
		exactWords.put("FRUIT","brayuu");
		exactWords.put("FUEL","prinyuu");
		exactWords.put("FUN","tratejre");
		exactWords.put("FUNCTION","gouthrhuu");
		exactWords.put("FUNNY","tejr");
		exactWords.put("FUTURE","tragistruu");
		exactWords.put("GALLEY","goegwiekfuu");
		exactWords.put("GARDEN","andent");
		exactWords.put("GARDENER","andenevrhe");
		exactWords.put("GENERALLY","as");
		exactWords.put("GIANT","loifuhrhe");
		exactWords.put("GIFT","zguprhuu");
		exactWords.put("GIRL","vuefne");
		exactWords.put("GIVE","priman");
		exactWords.put("GLASS","strivuu");
		exactWords.put("GLOBE","laebuu");
		exactWords.put("GLOVE","prhihduu");
		exactWords.put("GO","gren");
		exactWords.put("GOAT","rhahshke");
		exactWords.put("GOD","jaakra");
		exactWords.put("GOLD","liguu");
		exactWords.put("GOOD","cul");
		exactWords.put("GOODNESS","traculuu");
		exactWords.put("GOSSIP","doscismuu");
		exactWords.put("GOUGE","muefan");
		exactWords.put("GRAIN","sastrhuu");
		exactWords.put("GRANDFATHER","bizhvete");
		exactWords.put("GRASS","brituu");
		exactWords.put("GRATITUDE","woimuu");
		exactWords.put("GRATZ","dak");
		exactWords.put("GRAY","spoj");
		exactWords.put("GREEDY","laejwiv");
		exactWords.put("GREEN","fuubl");
		exactWords.put("GREETINGS","haetahzu");
		exactWords.put("GREY","spoj");
		exactWords.put("GROUP","bustan");
		exactWords.put("GROW","butsnumpan");
		exactWords.put("GUIDE","zetsiste");
		exactWords.put("GUILD","galn'hiete");
		exactWords.put("GUN","mokhuu");
		exactWords.put("GURU","soghathrhe");
		exactWords.put("HALL","goe'gaeshuu");
		exactWords.put("HALT","jetan");
		exactWords.put("HAMLET","shlepietuu");
		exactWords.put("HAMMER","zwuunhuu");
		exactWords.put("HAND","shaawe");
		exactWords.put("HARBOR","aruask");
		exactWords.put("HARDEN","segwithan");
		exactWords.put("HATCH","bwoeshen");
		exactWords.put("HAVE","ben");
		exactWords.put("HE","eske");
		exactWords.put("HEAD","slofre");
		exactWords.put("HEALER","picatrhe");
		exactWords.put("HEALTH","traswoenuu");
		exactWords.put("HEAR","dhevan");
		exactWords.put("HEAVILY","ouc-eh");
		exactWords.put("HEAVY","ouc");
		exactWords.put("HELLO","haetu");
		exactWords.put("HELP","akoth");
		exactWords.put("HELPFUL","kodhjasp");
		exactWords.put("HELPFULLY","kodhjasp-eh");
		exactWords.put("HEN","oh'heele");
		exactWords.put("HER","afkiy");
		exactWords.put("HERDER","mawatrhe");
		exactWords.put("HERE","delau");
		exactWords.put("HIGH","koun");
		exactWords.put("HILL","thakluu");
		exactWords.put("HILLS","thakel");
		exactWords.put("HIM","eskiy");
		exactWords.put("HIP","vaguu");
		exactWords.put("HIS","esken");
		exactWords.put("HIT","guucan");
		exactWords.put("HOLIDY","daesyuu");
		exactWords.put("HOLY","daes");
		exactWords.put("HOOF","ezekuu");
		exactWords.put("HORSE","krhovle");
		exactWords.put("HOSPITAL","jaavagzuu");
		exactWords.put("HOT","mohsh");
		exactWords.put("HOUR","feznuu");
		exactWords.put("HOUSE","roubuu");
		exactWords.put("HOW","keeb");
		exactWords.put("HUNGER","astragh");
		exactWords.put("HUNT","fnagan");
		exactWords.put("HURT","glikan");
		exactWords.put("HUSBAND","buukrhe");
		exactWords.put("I","ce");
		exactWords.put("ICE","gribohpuu");
		exactWords.put("IDIOT","tranivwe");
		exactWords.put("ILL","theen");
		exactWords.put("ILLNESS","oshaathuu");
		exactWords.put("IMMEDIATE","nasl");
		exactWords.put("IMMEDIATELY","nasl-eh");
		exactWords.put("IMPORTANT","bej");
		exactWords.put("IMPORTANTLY","bej-eh");
		exactWords.put("IN","ist");
		exactWords.put("INBETWEEN","dezd");
		exactWords.put("INFANT","dhoir");
		exactWords.put("INFO","ghawuu");
		exactWords.put("INFORMATION","ghawuu");
		exactWords.put("INSTEAD","ikr-eh");
		exactWords.put("INTO","isc");
		exactWords.put("IS","en");
		exactWords.put("ISLAND","yaabluu");
		exactWords.put("ISSUE","vraacshuu");
		exactWords.put("IT","ke");
		exactWords.put("ITS","kes");
		exactWords.put("JAR","febuu");
		exactWords.put("JESTER","tranivouw");
		exactWords.put("JEWELLER","ligevrhe");
		exactWords.put("JOURNEY","akhloegav");
		exactWords.put("JOY","traraaje");
		exactWords.put("JOYFUL","raaj");
		exactWords.put("JUDGE","soghiste");
		exactWords.put("JUMP","zneenken");
		exactWords.put("JUSTICE","soghe");
		exactWords.put("KIND","mezl");
		exactWords.put("KINDLY","mezl-eh");
		exactWords.put("KILL","zvejan");
		exactWords.put("KITCHEN","goegwiekfuu");
		exactWords.put("KNEE","vaguu");
		exactWords.put("KNEEL","glafen");
		exactWords.put("KNIFE","vahvruu");
		exactWords.put("KNOW","secan");
		exactWords.put("LADDER","snilhuu");
		exactWords.put("LAND","vuhvuu");
		exactWords.put("LANDSLIDE","blajliyookuu");
		exactWords.put("LANGUAGE","doscisme");
		exactWords.put("LARGE","jarh");
		exactWords.put("LASTLY","fints-eh");
		exactWords.put("LAVA","slukluu");
		exactWords.put("LEAF","numpuu");
		exactWords.put("LEAFLESS","numpimarh");
		exactWords.put("LEAFY","numpust");
		exactWords.put("LEARN","krasan");
		exactWords.put("LEAVE","gren");
		exactWords.put("LEG","joejuu");
		exactWords.put("LEMON","zerajuu");
		exactWords.put("LESS","suuft");
		exactWords.put("LESSON","krasakluu");
		exactWords.put("LETTER","beec");
		exactWords.put("LIFE","karm");
		exactWords.put("LIGHT","ethobluu");
		exactWords.put("LIGHTS","khek");
		exactWords.put("LIGHTNING","slinte");
		exactWords.put("LIKE","fuust");
		exactWords.put("LINE","froucuu");
		exactWords.put("LISTEN","dheban");
		exactWords.put("LISTEN","noi'haelnan");
		exactWords.put("LISTENER","aelnath");
		exactWords.put("LIVE","bekarmen");
		exactWords.put("LIZARD","coeshke");
		exactWords.put("LOCATION","pololhguu");
		exactWords.put("LOOK","gluefan");
		exactWords.put("LOOKS","Vleemen");
		exactWords.put("LONG","viej");
		exactWords.put("LOSE","hezdan");
		exactWords.put("LOUDLY","tov-eh");
		exactWords.put("LUSH","numbvalts");
		exactWords.put("MACHINE","batuu");
		exactWords.put("MAGIC","draafuu");
		exactWords.put("MAKE","butsan");
		exactWords.put("MANSION","roubistrhuu");
		exactWords.put("MANY","toum");
		exactWords.put("MARK","cublan");
		exactWords.put("MARKET","nuhgzuu");
		exactWords.put("MARRIAGE","bielakel");
		exactWords.put("MARRY","bielan");
		exactWords.put("MASCULINE","loifalb");
		exactWords.put("MATTER","vraacshuu");
		exactWords.put("ME","ciy");
		exactWords.put("MEAT","shievuu");
		exactWords.put("MEDICINE","gyi'huu");
		exactWords.put("MEET","gaeshan");
		exactWords.put("MELEE","bwoefohpuu");
		exactWords.put("MERCHANT","nuhgzevrhe");
		exactWords.put("MERRIMENT","traraaje");
		exactWords.put("MESSAGE","khavyuu");
		exactWords.put("MESSAGEBOARD","doeftkhavyuu");
		exactWords.put("METAL","jaevmuu");
		exactWords.put("METER","jaakuu");
		exactWords.put("MIDNIGHT","yaezluu");
		exactWords.put("MIGHT","tog");
		exactWords.put("MILK","benhuu");
		exactWords.put("MINIMAL","zgeg");
		exactWords.put("MINUTE","dhoeruu");
		exactWords.put("MODEL","sohmuu");
		exactWords.put("MONEY","numpuu");
		exactWords.put("MONSTER","leshpe");
		exactWords.put("MONTH","tecuu");
		exactWords.put("MORE","toum");
		exactWords.put("MOST","toum");
		exactWords.put("MOTHER","moencone");
		exactWords.put("MOTHER","noimoeman");
		exactWords.put("MOUNTAIN","soufwhuu");
		exactWords.put("MOUNTAIN","soufwhuu");
		exactWords.put("MUD","sleghuu");
		exactWords.put("MUG","ladhuu");
		exactWords.put("MUSCLE","ghimuu");
		exactWords.put("MUSIC","nounshuu");
		exactWords.put("MUSICAL","nounshalb");
		exactWords.put("MUSICALLY","nounshalb-eh");
		exactWords.put("MUSICIAN","nounshathe");
		exactWords.put("MUST","hont");
		exactWords.put("MY","cen");
		exactWords.put("NADA","zerhu");
		exactWords.put("NAME","herhuu");
		exactWords.put("NARROW","hal");
		exactWords.put("NEARBY","shas");
		exactWords.put("NECK","me'he");
		exactWords.put("NEED","stoukan");
		exactWords.put("NEPHEW","karise");
		exactWords.put("NEW","cot");
		exactWords.put("NEXT","dene");
		exactWords.put("NIECE","karafe");
		exactWords.put("NIL","zerhu");
		exactWords.put("NINE","nuunu");
		exactWords.put("NO","nash");
		exactWords.put("NOISE","januu");
		exactWords.put("NONE","nied");
		exactWords.put("NOR","en");
		exactWords.put("NOW","pret-eh");
		exactWords.put("NOTICE","gluefan");
		exactWords.put("NULL","zerhu");
		exactWords.put("NURSERY","basataguu");
		exactWords.put("NUT","faukuu");
		exactWords.put("OCCUR","khuaken");
		exactWords.put("OFFICE","trespeefuu");
		exactWords.put("OLD","vel");
		exactWords.put("ONE","onu");
		exactWords.put("ORACLE","soghathrhe");
		exactWords.put("ORB","laebuu");
		exactWords.put("ORBIT","jinsuu");
		exactWords.put("ORCHESTRA","nounshove");
		exactWords.put("ORDER","agizruu");
		exactWords.put("ORDERLY","talk");
		exactWords.put("OTHER","bozaa");
		exactWords.put("OTHERWISE","most");
		exactWords.put("OUGHT","tuum");
		exactWords.put("OUR","cozen");
		exactWords.put("OUT","dost");
		exactWords.put("OVEN","begmuu");
		exactWords.put("OVER","vos");
		exactWords.put("PALACE","roubistrhuu");
		exactWords.put("PALM","laujuu");
		exactWords.put("PAINT","zhablan");
		exactWords.put("PAINTER","zhabliste");
		exactWords.put("PAINTING","zhablakuu");
		exactWords.put("PARENT","nonhe");
		exactWords.put("PARTY","bustan");
		exactWords.put("PASS","rasluu");
		exactWords.put("PATH","kliy");
		exactWords.put("PATIENT","jaarvakle");
		exactWords.put("PAW","ezekuu");
		exactWords.put("PAY","pajan");
		exactWords.put("PAYEE","pajakle");
		exactWords.put("PAYER","pajatrhe");
		exactWords.put("PAYMENT","akhpaj");
		exactWords.put("PEOPLE","galn'hiete");
		exactWords.put("PERSON","galne");
		exactWords.put("PICKPOCKET","brolhist");
		exactWords.put("PILLAR","cuezhnuu");
		exactWords.put("PISS","ma'haelhuu");
		exactWords.put("PISTOL","mokhuu");
		exactWords.put("PITCHER","palshiste");
		exactWords.put("PK","mahcantsuu");
		exactWords.put("PLACE","pololhg");
		exactWords.put("PLAIN","cuskuu");
		exactWords.put("PLAINS","cuskuu");
		exactWords.put("PLANET","cohmpuu");
		exactWords.put("PLASTIC","sootruu");
		exactWords.put("PLATE","inkrhuu");
		exactWords.put("PLAY","noinounshan");
		exactWords.put("PLEAD","di'tuktlen");
		exactWords.put("PLEASE","vesk");
		exactWords.put("PLOUGH","oigyaaran");
		exactWords.put("PLOW","noideefsan");
		exactWords.put("POISON","narhma'huu");
		exactWords.put("POLE","goshtruu");
		exactWords.put("POOR","zgeg");
		exactWords.put("POSSESS","stigan");
		exactWords.put("POT","febuu");
		exactWords.put("POTATO","prhipfuu");
		exactWords.put("POTION","gyi'huu");
		exactWords.put("PRAIRIE","cuskuu");
		exactWords.put("PRECEDING","debe");
		exactWords.put("PREFER","vijan");
		exactWords.put("PREPARE","akhcaeb");
		exactWords.put("PREPARES","caeban");
		exactWords.put("PRESENT","zguprhuu");
		exactWords.put("PREVIOUS","huuc");
		exactWords.put("PREVIOUSLY","huuc-eh");
		exactWords.put("PRISON","khipfhietuu");
		exactWords.put("PRISONER","kaptakle");
		exactWords.put("PRISONER","kaptakle");
		exactWords.put("PROSTITUTE","sharhathe");
		exactWords.put("PROSTITUTION","shartuumuu");
		exactWords.put("PROVIDE","toprhan");
		exactWords.put("PROVISIONS","toprholhguu");
		exactWords.put("PUNISH","ciswhan");
		exactWords.put("PUNISHER","ciswhiste");
		exactWords.put("PUNISHMENT","ciswhoge");
		exactWords.put("PUT","vihblan");
		exactWords.put("QUESTION","dhoetrhopuu");
		exactWords.put("QUIET","im");
		exactWords.put("QUIETLY","im-eh");
		exactWords.put("RABBIT","leprhe");
		exactWords.put("RADIO","ojoikluu");
		exactWords.put("RAGE","zhgishte");
		exactWords.put("RAINING","noupethan");
		exactWords.put("RAIN","pethuu");
		exactWords.put("RANCH","shlepuu");
		exactWords.put("RANGER","zetsiste");
		exactWords.put("REACTION","vrhoshmuu");
		exactWords.put("READ","ekinan");
		exactWords.put("REAL","vosalb");
		exactWords.put("REALITY","vosuu");
		exactWords.put("REALLY","vosalb-eh");
		exactWords.put("RECALL","britismuu");
		exactWords.put("RED","tint");
		exactWords.put("REGRET","agraasnuu");
		exactWords.put("REGRETS","graasan");
		exactWords.put("REMAIN","bekan");
		exactWords.put("REPAIR","pican");
		exactWords.put("REPAIRER","gglounezhliste");
		exactWords.put("REPEATEDLY","gloef-eh");
		exactWords.put("REQUEST","agizehr");
		exactWords.put("REQUIRE","stoukan");
		exactWords.put("REQUIREMENT","stoukizmuu");
		exactWords.put("RESOURCE","poogrhuu");
		exactWords.put("RESPECT","abdan");
		exactWords.put("RESPECTFUL","abdistisp");
		exactWords.put("RESTAURANT","goekfosuu");
		exactWords.put("RESTRAINT","ajetuu");
		exactWords.put("RICE","maeyuu");
		exactWords.put("RICH","uegafst");
		exactWords.put("RICHLY","uegavuu");
		exactWords.put("RIFLE","mokhuu");
		exactWords.put("RIP","velban");
		exactWords.put("RIPE","upibm");
		exactWords.put("RIVER","dhounuu");
		exactWords.put("ROAD","kliyuu");
		exactWords.put("ROBBER","loiflaeye");
		exactWords.put("ROCK","yookuhrhuu");
		exactWords.put("ROCKY","yookuhrhst");
		exactWords.put("ROOF","fuejuu");
		exactWords.put("ROOM","ga'huu");
		exactWords.put("ROPE","naskuu");
		exactWords.put("ROT","thuzden");
		exactWords.put("RUN","strimen");
		exactWords.put("SAD","zhmekh");
		exactWords.put("SADNESS","trazhmekhe");
		exactWords.put("SAIL","zetsan");
		exactWords.put("SAILOR","osemevrhe");
		exactWords.put("SALOON","goekceguu");
		exactWords.put("SAME","feeg");
		exactWords.put("SAY","meeven");
		exactWords.put("SCARE","rhiknan");
		exactWords.put("SCATTER","paunan");
		exactWords.put("SCHOOL","krasovnisuu");
		exactWords.put("SCREAM","tas'uhshpieten");
		exactWords.put("SEA","maale");
		exactWords.put("SEASON","rhofnuu");
		exactWords.put("SEAT","asov");
		exactWords.put("SECTION","lhikuu");
		exactWords.put("SEE","gluefan");
		exactWords.put("SEED","brayuu");
		exactWords.put("SELECT","trugan");
		exactWords.put("SELECTION","trugoisuu");
		exactWords.put("SERVE","tsaughan");
		exactWords.put("SERVICE","tsaugholhguu");
		exactWords.put("SETTLE","eragen");
		exactWords.put("SEVEN","aderu");
		exactWords.put("SEX","sharhuu");
		exactWords.put("SHAKE","fuvzan");
		exactWords.put("SHALL","goudh");
		exactWords.put("SHAMAN","draafathe");
		exactWords.put("SHARE","dhruglan");
		exactWords.put("SHE","afke");
		exactWords.put("SHINY","bludr");
		exactWords.put("SHIP","osemjarhuu");
		exactWords.put("SHIRT","kuuthuu");
		exactWords.put("SHOP","magzuu");
		exactWords.put("SHORT","cugrh");
		exactWords.put("SHORT","jyek");
		exactWords.put("SHOULD","gaz");
		exactWords.put("SHOULDER","vaguu");
		exactWords.put("SHOUT","zavben");
		exactWords.put("SHRED","velban");
		exactWords.put("SIBLING","husplozde");
		exactWords.put("SIGHT","gluefismuu");
		exactWords.put("SISTER","lozde");
		exactWords.put("SIT","asen");
		exactWords.put("SITUATION","pletuu");
		exactWords.put("SIX","dizu");
		exactWords.put("SKY","bleshnuu");
		exactWords.put("SLEEP","talen");
		exactWords.put("SLEEPY","atalants");
		exactWords.put("SLOWLY","sham-eh");
		exactWords.put("SMARMY","ohslifl");
		exactWords.put("SMITH","butsan");
		exactWords.put("SNAKE","bokhre");
		exactWords.put("SNAKE","noibokhren");
		exactWords.put("SNOW","nouswujan");
		exactWords.put("SO","brois");
		exactWords.put("SOAK","sekma'hivan");
		exactWords.put("SOCIAL","jaft");
		exactWords.put("SOCIALS","jaftuu");
		exactWords.put("SOFT","angrh");
		exactWords.put("SOME","shonaa");
		exactWords.put("SON","vlefre");
		exactWords.put("SOMEWHERE","shuuzau");
		exactWords.put("SOUND","januu");
		exactWords.put("SPEAK","kegen");
		exactWords.put("SPERICAL","laebalb");
		exactWords.put("SPHERE","laebuu");
		exactWords.put("SPIRE","goshtruu");
		exactWords.put("SPIT","hasuu");
		exactWords.put("SPIT","noi'hasen");
		exactWords.put("SPRING","dhounalhetuu");
		exactWords.put("STAR","nazluu");
		exactWords.put("START","britan");
		exactWords.put("STARVE","traghan");
		exactWords.put("STARVATION","atraghuu");
		exactWords.put("STATE","vuhvuu");
		exactWords.put("STATUE","sohmuu");
		exactWords.put("STEAM","ezezhmohpuu");
		exactWords.put("STEEP","ghac");
		exactWords.put("STEER","zetsan");
		exactWords.put("STEERER","zetsiste");
		exactWords.put("STEPPE","cuskuu");
		exactWords.put("STERNLY","eekim-eh");
		exactWords.put("STICK","pouzluu");
		exactWords.put("STOMACH","osucuu");
		exactWords.put("STONE","yookuu");
		exactWords.put("STONY","yookalb");
		exactWords.put("STOP","glaa");
		exactWords.put("STORY","vuhsuu");
		exactWords.put("STRAIGHT","usth");
		exactWords.put("STREAM","dhounetuu");
		exactWords.put("STREET","kliyhietuu");
		exactWords.put("STRENGTH","trakhajuu");
		exactWords.put("STRONG","khaj");
		exactWords.put("STRONGLY","khaj-eh");
		exactWords.put("STUDENT","krasiste");
		exactWords.put("SUBWAY","poe'hajvituu");
		exactWords.put("SUFFER","glikan");
		exactWords.put("SUGAR","emiegvuu");
		exactWords.put("SUN","wage");
		exactWords.put("SUNLIGHT","jevuu");
		exactWords.put("SUNSHINE","jevuu");
		exactWords.put("SUPPLIES","toprholhguu");
		exactWords.put("SUPPLY","toprhan");
		exactWords.put("SURE","lesl");
		exactWords.put("SURROUND","celky");
		exactWords.put("SWAP","rheegrhan");
		exactWords.put("SWIM","alugzh");
		exactWords.put("SWIMS","lugzhen");
		exactWords.put("SYMPATHIZE","noimeghan");
		exactWords.put("TABLE","blomuu");
		exactWords.put("TAILOR","donatrhe");
		exactWords.put("TAKE","shokrhan");
		exactWords.put("TALK","kegen");
		exactWords.put("TAME","mawan");
		exactWords.put("TAMED","maw");
		exactWords.put("TAR","braupuu");
		exactWords.put("TEA","mauvyuu");
		exactWords.put("TEACH","dvuundan");
		exactWords.put("TEAR","velban");
		exactWords.put("TEMPLE","daesovuu");
		exactWords.put("TEN","oki");
		exactWords.put("THANK","budzdakan");
		exactWords.put("THANKS","dak");
		exactWords.put("THAT","evd");
		exactWords.put("THEFT","yabluu");
		exactWords.put("THEIR","brhen");
		exactWords.put("THEM","brhiy");
		exactWords.put("THEN","man");
		exactWords.put("THERE","valau");
		exactWords.put("THEY","brhek");
		exactWords.put("THICK","foeddh");
		exactWords.put("THIEF","yablathe");
		exactWords.put("THIEVERY","yabluu");
		exactWords.put("THIN","coik");
		exactWords.put("THINNING","hal");
		exactWords.put("THING","bal");
		exactWords.put("THREE","besu");
		exactWords.put("THROAT","me'he");
		exactWords.put("THROUGH","dezde");
		exactWords.put("THROW","apalsh");
		exactWords.put("THROWS","palshan");
		exactWords.put("THUS","uuj");
		exactWords.put("TIDY","sekstuaban");
		exactWords.put("TIME","skesuu");
		exactWords.put("TINKER","kliyevrhe");
		exactWords.put("TINY","nis");
		exactWords.put("TODAY","restasuu");
		exactWords.put("TOMORROW","gvugyuu");
		exactWords.put("TOOTH","rhuuluu");
		exactWords.put("TORSO","cesuu");
		exactWords.put("TOUCH","abrolhuu");
		exactWords.put("TOWER","conhuu");
		exactWords.put("TOWERS","zecrhuu");
		exactWords.put("TOWN","vaeyuu");
		exactWords.put("TRACK","kliyet");
		exactWords.put("TRAIN","poe'hajarhuu");
		exactWords.put("TRANSPORT","poe'han");
		exactWords.put("TRAVEL","loegven");
		exactWords.put("TREE","rhabuu");
		exactWords.put("TRENCHCOAT","lufluu");
		exactWords.put("TRIBE","galn'hiete");
		exactWords.put("GANG","galn'hiete");
		exactWords.put("TRUE","vosalb");
		exactWords.put("TRULY","vosalb-eh");
		exactWords.put("TRUELY","vosalb-eh");
		exactWords.put("TRUTH","vosuu");
		exactWords.put("TURKEY","oh'heele");
		exactWords.put("TWO","drasu");
		exactWords.put("UNCLE","zgate");
		exactWords.put("UNDER","suu");
		exactWords.put("UNDERNEATH","suusy");
		exactWords.put("UNFORGIVING","eekim");
		exactWords.put("UNFRIENDLY","ohslumb");
		exactWords.put("UNIVERSITY","krasovjarhuu");
		exactWords.put("UNSURE","narhlesl");
		exactWords.put("UNTIL","ista");
		exactWords.put("UNWISE","twing");
		exactWords.put("UNWISELY","twing-eh");
		exactWords.put("UP","tob");
		exactWords.put("UPON","zhuusy");
		exactWords.put("UPWARDS","toby");
		exactWords.put("URINE","ma'haelhuu");
		exactWords.put("US","coziy");
		exactWords.put("USE","gotan");
		exactWords.put("VALLEY","nausuu");
		exactWords.put("VIA","gles");
		exactWords.put("VIEW","gluefan");
		exactWords.put("VILLAGE","eragolhguu");
		exactWords.put("VILLAGERS","shlepietathe");
		exactWords.put("VISIONAIRY","soghathrhe");
		exactWords.put("VISITS","akhsloth");
		exactWords.put("VISIT","slothan");
		exactWords.put("VOLCANO","zeesuu");
		exactWords.put("WAGES","pajdomuu");
		exactWords.put("WALK","trhaden");
		exactWords.put("WALL","doeftuu");
		exactWords.put("WANT","gruugan");
		exactWords.put("WARLOCK","jaarviste");
		exactWords.put("WARM","noizh");
		exactWords.put("WAS","en");
		exactWords.put("WASH","khoivan");
		exactWords.put("WASTE","gvuljan");
		exactWords.put("WATER","ma'huu");
		exactWords.put("WATER","noima'han");
		exactWords.put("WATERFALL","blajlima'huu");
		exactWords.put("WATERY","ma'hust");
		exactWords.put("WE","cozelh");
		exactWords.put("WEALTH","uegafst");
		exactWords.put("WEALTHY","uegavuu");
		exactWords.put("WEEK","vahmuu");
		exactWords.put("WEEP","wihdren");
		exactWords.put("WELL","looghuu");
		exactWords.put("WERE","en");
		exactWords.put("WET","ciel");
		exactWords.put("WHAT","gziy");
		exactWords.put("WHEAT","sastrhuu");
		exactWords.put("WHERE","badh");
		exactWords.put("WHISPER","fnitlen");
		exactWords.put("WHITE","aakh");
		exactWords.put("WHO","gze");
		exactWords.put("WHOSE","gzen");
		exactWords.put("WIDE","foeddh");
		exactWords.put("WIFE","tothpe");
		exactWords.put("WILD","namaw");
		exactWords.put("WILDLY","namaw-eh");
		exactWords.put("WILL","nezh");
		exactWords.put("WIND","brishnuu");
		exactWords.put("WINE","rhaesuu");
		exactWords.put("WIRELESS","ojoikluu");
		exactWords.put("WISELY","steev-eh");
		exactWords.put("WITCH","jaarviste");
		exactWords.put("WITHOUT","deth");
		exactWords.put("WITHOUT","di");
		exactWords.put("WIZARD","draafath");
		exactWords.put("WOMAN","gyane");
		exactWords.put("WOMB","khuane");
		exactWords.put("WONDER","oomuhfuu");
		exactWords.put("WONDEROUS","oomuhfeshp");
		exactWords.put("WORD","wadoscuu");
		exactWords.put("WORK","akhueb");
		exactWords.put("WORKER","khuebatrhe");
		exactWords.put("WORKSHOP","khuebeefuu");
		exactWords.put("WORLD","cohmap");
		exactWords.put("WORM","rhieye");
		exactWords.put("WORTHLESS","dozdhoune");
		exactWords.put("WOULD","haz");
		exactWords.put("WRIST","vaguu");
		exactWords.put("WRITE","trespan");
		exactWords.put("XP","noishloektan");
		exactWords.put("YEAR","jinsuu");
		exactWords.put("YELL","zavben");
		exactWords.put("YELLOW","uekh");
		exactWords.put("YESTERDAY","rezdenuu");
		exactWords.put("YES","shas");
		exactWords.put("YET","fyal");
		exactWords.put("YOU","diy");
		exactWords.put("YOU","feselh");
		exactWords.put("YOUNG","cot");
		exactWords.put("YOUR","fesen");
		exactWords.put("YOURS","fesiy");
		exactWords.put("ZERO","zerhu");
		return exactWords;
		}
}
